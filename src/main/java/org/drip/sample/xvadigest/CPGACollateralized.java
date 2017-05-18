
package org.drip.sample.xvadigest;

import org.drip.analytics.date.*;
import org.drip.measure.bridge.BrokenDateInterpolatorLinearT;
import org.drip.measure.discrete.SequenceGenerator;
import org.drip.measure.dynamics.DiffusionEvaluatorLinear;
import org.drip.measure.process.DiffusionEvolver;
import org.drip.measure.realization.*;
import org.drip.measure.statistics.UnivariateDiscreteThin;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.xva.collateral.HypothecationGroupPath;
import org.drip.xva.collateral.HypothecationGroupVertexRegular;
import org.drip.xva.collateral.HypothecationAmountEstimator;
import org.drip.xva.cpty.*;
import org.drip.xva.numeraire.MarketPath;
import org.drip.xva.numeraire.MarketVertex;
import org.drip.xva.set.*;
import org.drip.xva.strategy.FundingGroupPathAA2014;
import org.drip.xva.strategy.NettingGroupPathAA2014;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   	you may not use this file except in compliance with the License.
 *   
 *  You may obtain a copy of the License at
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  	distributed under the License is distributed on an "AS IS" BASIS,
 *  	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  
 *  See the License for the specific language governing permissions and
 *  	limitations under the License.
 */

/**
 * CPGACollateralized illustrates the Counter Party Aggregation over Netting Groups based Collateralized
 *  Collateral Groups with several Fix-Float Swaps. The References are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk, Risk 20 (2) 86-90.
 *  
 *  - Li, B., and Y. Tang (2007): Quantitative Analysis, Derivatives Modeling, and Trading Strategies in the
 *  	Presence of Counter-party Credit Risk for the Fixed Income Market, World Scientific Publishing,
 *  	Singapore.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CPGACollateralized {

	private static final double[] ATMSwapRateOffsetRealization (
		final DiffusionEvolver deATMSwapRateOffset,
		final double dblATMSwapRateOffsetInitial,
		final double dblTime,
		final double dblTimeWidth,
		final int iNumStep)
		throws Exception
	{
		double[] adblATMSwapRateOffset = new double[iNumStep + 1];
		adblATMSwapRateOffset[0] = dblATMSwapRateOffsetInitial;

		JumpDiffusionEdge[] aJDE = deATMSwapRateOffset.incrementSequence (
			new JumpDiffusionVertex (
				dblTime,
				dblATMSwapRateOffsetInitial,
				0.,
				false
			),
			UnitRandomEdge.Diffusion (SequenceGenerator.Gaussian (iNumStep)),
			dblTimeWidth
		);

		for (int j = 1; j <= iNumStep; ++j)
			adblATMSwapRateOffset[j] = aJDE[j - 1].finish();

		return adblATMSwapRateOffset;
	}

	private static final double[] SwapPortfolioValueRealization (
		final DiffusionEvolver deATMSwapRate,
		final double dblATMSwapRateStart,
		final int iNumStep,
		final double dblTime,
		final double dblTimeWidth,
		final int iNumSwap)
		throws Exception
	{
		double[] adblSwapPortfolioValueRealization = new double[iNumStep + 1];

		for (int i = 0; i < iNumStep; ++i)
			adblSwapPortfolioValueRealization[i] = 0.;

		for (int i = 0; i < iNumSwap; ++i) {
			double[] adblATMSwapRateOffsetRealization = ATMSwapRateOffsetRealization (
				deATMSwapRate,
				dblATMSwapRateStart,
				dblTime,
				dblTimeWidth,
				iNumStep
			);

			for (int j = 0; j <= iNumStep; ++j)
				adblSwapPortfolioValueRealization[j] += dblTimeWidth * (iNumStep - j) * adblATMSwapRateOffsetRealization[j];
		}

		return adblSwapPortfolioValueRealization;
	}

	private static final double[][] SwapPortfolioValueRealization (
		final DiffusionEvolver deATMSwapRate,
		final double dblSwapPortfolioValueStart,
		final int iNumStep,
		final double dblTime,
		final double dblTimeWidth,
		final int iNumSwap,
		final int iNumSimulation)
		throws Exception
	{
		double[][] aadblSwapPortfolioValueRealization = new double[iNumSimulation][];

		for (int i = 0; i < iNumSimulation; ++i)
			aadblSwapPortfolioValueRealization[i] = SwapPortfolioValueRealization (
				deATMSwapRate,
				dblSwapPortfolioValueStart,
				iNumStep,
				dblTime,
				dblTimeWidth,
				iNumSwap
			);

		return aadblSwapPortfolioValueRealization;
	}

	private static final void UDTDump (
		final String strHeader,
		final JulianDate[] adtVertexNode,
		final UnivariateDiscreteThin[] aUDT)
		throws Exception
	{
		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");

		System.out.println (strHeader);

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");

		String strDump = "\t|       DATE      =>" ;

		for (int i = 0; i < adtVertexNode.length; ++i)
			strDump = strDump + " " + adtVertexNode[i] + "  |";

		System.out.println (strDump);

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");

		 strDump = "\t|     AVERAGE     =>";

		for (int j = 0; j < aUDT.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (aUDT[j].average(), 2, 4, 1.) + "   |";

		System.out.println (strDump);

		strDump = "\t|     MAXIMUM     =>";

		for (int j = 0; j < aUDT.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (aUDT[j].maximum(), 2, 4, 1.) + "   |";

		System.out.println (strDump);

		strDump = "\t|     MINIMUM     =>";

		for (int j = 0; j < aUDT.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (aUDT[j].minimum(), 2, 4, 1.) + "   |";

		System.out.println (strDump);

		strDump = "\t|      ERROR      =>";

		for (int j = 0; j < aUDT.length; ++j)
			strDump = strDump + "   " + FormatUtil.FormatDouble (aUDT[j].error(), 2, 4, 1.) + "   |";

		System.out.println (strDump);

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
	}

	private static final void UDTDump (
		final String strHeader,
		final UnivariateDiscreteThin udt)
		throws Exception
	{
		System.out.println (
			strHeader +
			FormatUtil.FormatDouble (udt.average(), 3, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (udt.maximum(), 3, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (udt.minimum(), 3, 2, 100.) + "% | " +
			FormatUtil.FormatDouble (udt.error(), 3, 2, 100.) + "% ||"
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int iNumStep = 10;
		int iNumSwap = 10;
		double dblTime = 5.;
		int iNumPath = 10000;
		double dblATMSwapRateStart = 0.;
		double dblATMSwapRateDrift = 0.0;
		double dblATMSwapRateVolatility = 0.25;
		double dblCSADrift = 0.01;
		double dblBankHazardRate = 0.015;
		double dblBankRecoveryRate = 0.40;
		double dblCounterPartyHazardRate = 0.030;
		double dblCounterPartyRecoveryRate = 0.30;
		double dblBankThreshold = -0.1;
		double dblCounterPartyThreshold = 0.1;

		JulianDate dtSpot = DateUtil.Today();

		double dblTimeWidth = dblTime / iNumStep;
		JulianDate[] adtVertex = new JulianDate[iNumStep + 1];
		MarketVertex[] aNV = new MarketVertex[iNumStep + 1];
		PathExposureAdjustment[] aCPGP = new PathExposureAdjustment[iNumPath];
		double dblBankFundingSpread = dblBankHazardRate / (1. - dblBankRecoveryRate);

		CollateralGroupSpecification cgs = CollateralGroupSpecification.FixedThreshold (
			"FIXEDTHRESHOLD",
			dblCounterPartyThreshold,
			dblBankThreshold
		);

		CounterPartyGroupSpecification cpgs = CounterPartyGroupSpecification.Standard ("CPGROUP");

		double[][] aadblSwapPortfolioValueRealization = SwapPortfolioValueRealization (
			new DiffusionEvolver (
				DiffusionEvaluatorLinear.Standard (
					dblATMSwapRateDrift,
					dblATMSwapRateVolatility
				)
			),
			dblATMSwapRateStart,
			iNumStep,
			dblTime,
			dblTimeWidth,
			iNumSwap,
			iNumPath
		);

		for (int i = 0; i <= iNumStep; ++i)
			aNV[i] = MarketVertex.Standard (
				adtVertex[i] = dtSpot.addMonths (6 * i),
				Math.exp (0.5 * dblCSADrift * i),
				Math.exp (-0.5 * dblBankHazardRate * i),
				dblBankRecoveryRate,
				dblBankFundingSpread,
				Math.exp (-0.5 * dblCounterPartyHazardRate * i),
				dblCounterPartyRecoveryRate
			);

		MarketPath np = new MarketPath (aNV);

		for (int i = 0; i < iNumPath; ++i) {
			JulianDate dtStart = dtSpot;
			double dblValueStart = dblTime * dblATMSwapRateStart;
			HypothecationGroupVertexRegular[] aCGV = new HypothecationGroupVertexRegular[iNumStep + 1];

			for (int j = 0; j <= iNumStep; ++j) {
				double dblCollateralBalance = 0.;
				JulianDate dtEnd = adtVertex[j];
				double dblValueEnd = aadblSwapPortfolioValueRealization[i][j];

				if (0 != j) {
					HypothecationAmountEstimator cae = new HypothecationAmountEstimator (
						cgs,
						cpgs,
						new BrokenDateInterpolatorLinearT (
							dtStart.julian(),
							dtEnd.julian(),
							dblValueStart,
							dblValueEnd
						),
						Double.NaN
					);

					dblCollateralBalance = cae.postingRequirement (dtEnd);
				}

				aCGV[j] = new HypothecationGroupVertexRegular (
					adtVertex[j],
					aadblSwapPortfolioValueRealization[i][j],
					0.,
					dblCollateralBalance
				);

				dtStart = dtEnd;
				dblValueStart = dblValueEnd;
			}

			HypothecationGroupPath[] aCGP = new HypothecationGroupPath[] {new HypothecationGroupPath (aCGV)};

			aCPGP[i] = new PathExposureAdjustment (
				new NettingGroupPathAA2014[] {
					new NettingGroupPathAA2014 (
						aCGP,
						np
					)
				},
				new FundingGroupPathAA2014[] {
					new FundingGroupPathAA2014 (
						aCGP,
						np
					)
				}
			);
		}

		ExposureAdjustmentAggregator cpga = new ExposureAdjustmentAggregator (aCPGP);

		ExposureAdjustmentDigest cpgd = cpga.digest();

		System.out.println();

		UDTDump (
			"\t|                                                                                COLLATERALIZED EXPOSURE                                                                                |",
			cpga.anchors(),
			cpgd.collateralizedExposure()
		);

		UDTDump (
			"\t|                                                                               UNCOLLATERALIZED EXPOSURE                                                                               |",
			cpga.anchors(),
			cpgd.uncollateralizedExposure()
		);

		UDTDump (
			"\t|                                                                                COLLATERALIZED EXPOSURE PV                                                                             |",
			cpga.anchors(),
			cpgd.collateralizedExposurePV()
		);

		UDTDump (
			"\t|                                                                               UNCOLLATERALIZED EXPOSURE PV                                                                            |",
			cpga.anchors(),
			cpgd.uncollateralizedExposurePV()
		);

		UDTDump (
			"\t|                                                                            COLLATERALIZED POSITIVE EXPOSURE PV                                                                        |",
			cpga.anchors(),
			cpgd.collateralizedPositiveExposure()
		);

		UDTDump (
			"\t|                                                                           UNCOLLATERALIZED POSITIVE EXPOSURE PV                                                                       |",
			cpga.anchors(),
			cpgd.uncollateralizedPositiveExposure()
		);

		UDTDump (
			"\t|                                                                            COLLATERALIZED NEGATIVE EXPOSURE PV                                                                        |",
			cpga.anchors(),
			cpgd.collateralizedNegativeExposure()
		);

		UDTDump (
			"\t|                                                                           UNCOLLATERALIZED NEGATIVE EXPOSURE PV                                                                       |",
			cpga.anchors(),
			cpgd.uncollateralizedNegativeExposure()
		);

		System.out.println();

		System.out.println ("\t||-----------------------------------------------------||");

		System.out.println ("\t||  UCVA CVA FTDCVA DVA FCA UNIVARIATE THIN STATISTICS ||");

		System.out.println ("\t||-----------------------------------------------------||");

		System.out.println ("\t||    L -> R:                                          ||");

		System.out.println ("\t||            - Path Average                           ||");

		System.out.println ("\t||            - Path Maximum                           ||");

		System.out.println ("\t||            - Path Minimum                           ||");

		System.out.println ("\t||            - Monte Carlo Error                      ||");

		System.out.println ("\t||-----------------------------------------------------||");

		UDTDump (
			"\t||  UCVA  => ",
			cpgd.ucva()
		);

		UDTDump (
			"\t|| FTDCVA => ",
			cpgd.ftdcva()
		);

		UDTDump (
			"\t||   CVA  => ",
			cpgd.cva()
		);

		UDTDump (
			"\t||  CVACL => ",
			cpgd.cvacl()
		);

		UDTDump (
			"\t||   DVA  => ",
			cpgd.dva()
		);

		UDTDump (
			"\t||   FVA  => ",
			cpgd.fva()
		);

		UDTDump (
			"\t||   FDA  => ",
			cpgd.fda()
		);

		UDTDump (
			"\t||   FCA  => ",
			cpgd.fca()
		);

		UDTDump (
			"\t||   FBA  => ",
			cpgd.fba()
		);

		UDTDump (
			"\t||  SFVA  => ",
			cpgd.sfva()
		);

		System.out.println ("\t||-----------------------------------------------------||");

		UDTDump (
			"\t||  Total => ",
			cpgd.totalVA()
		);

		System.out.println ("\t||-----------------------------------------------------||");

		System.out.println();
	}
}
