
package org.drip.xva.hypothecation;

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
 * BurgardKjaerVertexBuilder contains the Builders that construct the Burgard Kjaer Vertex using a Variant of
 *  the Generalized Burgard Kjaer (2013) Scheme. The References are:
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

public class BurgardKjaerVertexBuilder {

	public static final org.drip.xva.hypothecation.BurgardKjaerVertex BankPortfolioBuilder (
		final org.drip.analytics.date.JulianDate dtAnchor,
		final double dblExposure,
		final double dblRealizedCashFlow,
		final double dblCollateralBalance,
		final org.drip.xva.universe.MarketEdge me,
		final org.drip.xva.definition.CloseOutGeneral cog,
		final org.drip.xva.hypothecation.CollateralGroupVertexExposure cgve)
	{
		if (null == cgve) return null;

		return null;
	}

	/**
	 * Construct a Standard Instance of BurgardKjaerVertex using semi-replication with no Short-fall at own
	 *  Default using Two Bonds
	 * 
	 * @param dtAnchor The Vertex Date Anchor
	 * @param dblExposure The Exposure at the Path Vertex Time Node
	 * @param dblRealizedCashFlow The Default Window Realized Cash-flow at the Path Vertex Time Node
	 * @param dblCollateralBalance The Collateral Balance at the Path Vertex Time Node
	 * @param me The Market Edge
	 * @param cog The Generic Close-Out Evaluator Instance
	 * 
	 * @return The Standard Instance of BurgardKjaerVertex using semi-replication with no Short-fall at own
	 *  Default using Two Bonds
	 */

	public static final org.drip.xva.hypothecation.BurgardKjaerVertex SemiReplicationDualBond (
		final org.drip.analytics.date.JulianDate dtAnchor,
		final double dblExposure,
		final double dblRealizedCashFlow,
		final double dblCollateralBalance,
		final org.drip.xva.universe.MarketEdge me,
		final org.drip.xva.definition.CloseOutGeneral cog)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblExposure) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblRealizedCashFlow) ||
				!org.drip.quant.common.NumberUtil.IsValid (dblCollateralBalance) || null == me || null ==
					cog)
			return null;

		org.drip.xva.universe.MarketVertex mvFinish = me.finish();

		org.drip.xva.universe.EntityMarketVertex emvBankFinish = mvFinish.bank();

		org.drip.xva.universe.NumeraireMarketVertex nmvBankSubordinateFinish =
			emvBankFinish.subordinateFundingNumeraire();

		if (null == nmvBankSubordinateFinish) return null;

		double dblBankSeniorRecoveryFinish = emvBankFinish.seniorRecoveryRate();

		org.drip.xva.universe.NumeraireMarketVertex nmvBankSenior = emvBankFinish.seniorFundingNumeraire();

		double dblBankSeniorNumeraireFinish = nmvBankSenior.forward();

		double dblBankSurvivalFinish = emvBankFinish.survivalProbability();

		double dblIncrementalBankSurvival = dblBankSurvivalFinish - me.start().bank().survivalProbability();

		double dblCounterPartySurvivalFinish = mvFinish.counterParty().survivalProbability();

		double dblUncollateralizedExposure = dblExposure + dblRealizedCashFlow;
		double dblCollateralizedExposure = dblUncollateralizedExposure - dblCollateralBalance;
		double dblDebtExposure = 0. > dblCollateralizedExposure ? dblCollateralizedExposure : 0.;
		double dblCreditExposure = 0. < dblCollateralizedExposure ? dblCollateralizedExposure : 0.;
		double dblFundingExposure = 0. < dblCollateralizedExposure ? dblCollateralizedExposure : 0.;

		double dblAdjustedExposure = dblExposure + dblBankSurvivalFinish *
			(dblCounterPartySurvivalFinish - me.start().counterParty().survivalProbability()) * dblCreditExposure +
			dblCounterPartySurvivalFinish * dblIncrementalBankSurvival * dblDebtExposure +
			dblCounterPartySurvivalFinish * dblIncrementalBankSurvival * dblFundingExposure -
			dblBankSurvivalFinish * dblCounterPartySurvivalFinish * mvFinish.collateralSchemeSpread() * dblCollateralBalance;

		try {
			double dblBankDefaultCloseOut = cog.bankDefault (dblUncollateralizedExposure,
				dblCollateralBalance);

			double dblBankSubordinateRecoveryFinish = emvBankFinish.subordinateRecoveryRate();

			new org.drip.xva.hypothecation.BurgardKjaerVertex (
				dtAnchor,
				dblExposure,
				dblRealizedCashFlow,
				new org.drip.xva.hypothecation.CollateralGroupVertexExposure (
					dblCollateralBalance,
					dblCreditExposure,
					dblDebtExposure,
					dblFundingExposure
				),
				dblBankDefaultCloseOut,
				cog.counterPartyDefault (
					dblUncollateralizedExposure,
					dblCollateralBalance
				),
				new org.drip.xva.derivative.ReplicationPortfolioVertexBank (
					(dblFundingExposure + dblBankSubordinateRecoveryFinish * dblAdjustedExposure - dblBankDefaultCloseOut) /
						(dblBankSeniorRecoveryFinish - dblBankSubordinateRecoveryFinish) / dblBankSeniorNumeraireFinish,
					(dblFundingExposure + dblBankSeniorRecoveryFinish * dblAdjustedExposure - dblBankDefaultCloseOut) /
						(dblBankSubordinateRecoveryFinish - dblBankSeniorRecoveryFinish) / nmvBankSubordinateFinish.forward()
				)
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Standard Instance of BurgardKjaerVertex using a Fully Collateralized Strategy, i.e., also
	 * 	referred to as the 2 Way Gold Plated CSA
	 * 
	 * @param dtAnchor The Vertex Date Anchor
	 * @param dblExposure The Exposure at the Path Vertex Time Node
	 * @param dblRealizedCashFlow The Default Window Realized Cash-flow at the Path Vertex Time Node
	 * @param cog The Generic Close-Out Evaluator Instance
	 * 
	 * @return The Standard Instance of BurgardKjaerVertex using using a Fully Collateralized Strategy
	 */

	public static final org.drip.xva.hypothecation.BurgardKjaerVertex GoldPlatedTwoWayCSA (
		final org.drip.analytics.date.JulianDate dtAnchor,
		final double dblExposure,
		final double dblRealizedCashFlow,
		final org.drip.xva.definition.CloseOutGeneral cog)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblExposure) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblRealizedCashFlow) || null == cog)
			return null;

		double dblUncollateralizedExposure = dblExposure + dblRealizedCashFlow;

		try {
			new org.drip.xva.hypothecation.BurgardKjaerVertex (
				dtAnchor,
				dblExposure,
				dblRealizedCashFlow,
				new org.drip.xva.hypothecation.CollateralGroupVertexExposure (
					0.,
					0.,
					0.,
					dblUncollateralizedExposure
				),
				cog.bankDefault (
					dblUncollateralizedExposure,
					dblUncollateralizedExposure
				),
				cog.counterPartyDefault (
					dblUncollateralizedExposure,
					dblUncollateralizedExposure
				),
				new org.drip.xva.derivative.ReplicationPortfolioVertexBank (
					0.,
					0.
				)
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Standard Instance of BurgardKjaerVertex using One Way CSA
	 * 
	 * @param dtAnchor The Vertex Date Anchor
	 * @param dblExposure The Exposure at the Path Vertex Time Node
	 * @param dblRealizedCashFlow The Default Window Realized Cash-flow at the Path Vertex Time Node
	 * @param me The Market Edge
	 * @param cog The Generic Close-Out Evaluator Instance
	 * 
	 * @return The Standard Instance of BurgardKjaerVertex using One Way CSA
	 */

	public static final org.drip.xva.hypothecation.BurgardKjaerVertex OneWayCSA (
		final org.drip.analytics.date.JulianDate dtAnchor,
		final double dblExposure,
		final double dblRealizedCashFlow,
		final org.drip.xva.universe.MarketEdge me,
		final org.drip.xva.definition.CloseOutGeneral cog)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblExposure) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblRealizedCashFlow) || null == me || null == cog)
			return null;

		double dblDebtExposure = 0.;
		double dblUncollateralizedExposure = dblExposure + dblRealizedCashFlow;
		double dblCreditExposure = 0. < dblUncollateralizedExposure ? dblUncollateralizedExposure : 0.;
		double dblFundingExposure = 0. < dblUncollateralizedExposure ? dblUncollateralizedExposure : 0.;
		double dblCollateralBalance = 0. > dblUncollateralizedExposure ? dblUncollateralizedExposure : 0.;

		org.drip.xva.universe.MarketVertex mvFinish = me.finish();

		org.drip.xva.universe.EntityMarketVertex emvBankFinish = mvFinish.bank();

		org.drip.xva.universe.NumeraireMarketVertex nmvBankSubordinateFinish =
			emvBankFinish.subordinateFundingNumeraire();

		if (null == nmvBankSubordinateFinish) return null;

		double dblBankSeniorRecoveryFinish = emvBankFinish.seniorRecoveryRate();

		org.drip.xva.universe.NumeraireMarketVertex nmvBankSenior = emvBankFinish.seniorFundingNumeraire();

		double dblBankSeniorNumeraireFinish = nmvBankSenior.forward();

		double dblBankSurvivalFinish = emvBankFinish.survivalProbability();

		double dblIncrementalBankSurvival = dblBankSurvivalFinish - me.start().bank().survivalProbability();

		double dblCounterPartySurvivalFinish = mvFinish.counterParty().survivalProbability();

		double dblAdjustedExposure = dblExposure + dblBankSurvivalFinish *
			(dblCounterPartySurvivalFinish - me.start().counterParty().survivalProbability()) * dblCreditExposure +
			dblCounterPartySurvivalFinish * dblIncrementalBankSurvival * dblDebtExposure +
			dblCounterPartySurvivalFinish * dblIncrementalBankSurvival * dblFundingExposure -
			dblBankSurvivalFinish * dblCounterPartySurvivalFinish * mvFinish.collateralSchemeSpread() * dblCollateralBalance;

		try {
			double dblBankDefaultCloseOut = cog.bankDefault (dblUncollateralizedExposure,
				dblCollateralBalance);

			double dblBankSubordinateRecoveryFinish = emvBankFinish.subordinateRecoveryRate();

			new org.drip.xva.hypothecation.BurgardKjaerVertex (
				dtAnchor,
				dblExposure,
				dblRealizedCashFlow,
				new org.drip.xva.hypothecation.CollateralGroupVertexExposure (
					dblCollateralBalance,
					dblCreditExposure,
					dblDebtExposure,
					dblFundingExposure
				),
				dblBankDefaultCloseOut,
				cog.counterPartyDefault (
					dblUncollateralizedExposure,
					dblCollateralBalance
				),
				new org.drip.xva.derivative.ReplicationPortfolioVertexBank (
					(dblFundingExposure + dblBankSubordinateRecoveryFinish * dblAdjustedExposure - dblBankDefaultCloseOut) /
						(dblBankSeniorRecoveryFinish - dblBankSubordinateRecoveryFinish) / dblBankSeniorNumeraireFinish,
					(dblFundingExposure + dblBankSeniorRecoveryFinish * dblAdjustedExposure - dblBankDefaultCloseOut) /
						(dblBankSubordinateRecoveryFinish - dblBankSeniorRecoveryFinish) / nmvBankSubordinateFinish.forward()
				)
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Standard Instance of BurgardKjaerVertex using the "Set Off" Legal Agreement Scheme
	 * 
	 * @param dtAnchor The Vertex Date Anchor
	 * @param dblExposure The Exposure at the Path Vertex Time Node
	 * @param dblRealizedCashFlow The Default Window Realized Cash-flow at the Path Vertex Time Node
	 * @param dblCollateralBalance The Collateral Balance at the Path Vertex Time Node
	 * @param me The Market Edge
	 * 
	 * @return The Standard Instance of BurgardKjaerVertex using the "Set Off" Legal Agreement Scheme
	 */

	public static final org.drip.xva.hypothecation.BurgardKjaerVertex SetOff (
		final org.drip.analytics.date.JulianDate dtAnchor,
		final double dblExposure,
		final double dblRealizedCashFlow,
		final double dblCollateralBalance,
		final org.drip.xva.universe.MarketEdge me)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblExposure) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblRealizedCashFlow) ||
				!org.drip.quant.common.NumberUtil.IsValid (dblCollateralBalance) || null == me)
			return null;

		org.drip.xva.universe.MarketVertex mvFinish = me.finish();

		org.drip.xva.universe.EntityMarketVertex emvBankFinish = mvFinish.bank();

		org.drip.xva.universe.NumeraireMarketVertex nmvBankSubordinateFinish =
			emvBankFinish.subordinateFundingNumeraire();

		if (null == nmvBankSubordinateFinish) return null;

		org.drip.xva.universe.EntityMarketVertex emvCounterPartyFinish = mvFinish.counterParty();

		double dblCounterPartySurvivalFinish = emvCounterPartyFinish.survivalProbability();

		double dblCounterPartyRecoveryFinish = emvCounterPartyFinish.seniorRecoveryRate();

		double dblBankSeniorRecoveryFinish = emvBankFinish.seniorRecoveryRate();

		double dblBankSurvivalFinish = emvBankFinish.survivalProbability();

		double dblFundingExposure = 0.;
		double dblUncollateralizedExposure = dblExposure + dblRealizedCashFlow;
		double dblCollateralizedExposure = dblUncollateralizedExposure - dblCollateralBalance;
		double dblDebtExposure = dblCollateralizedExposure * (1. - dblBankSeniorRecoveryFinish);
		double dblBankDefaultCloseOut = dblCollateralizedExposure * dblBankSeniorRecoveryFinish;
		double dblCreditExposure = dblCollateralizedExposure * (1. - dblCounterPartyRecoveryFinish);

		double dblIncrementalBankSurvival = dblBankSurvivalFinish - me.start().bank().survivalProbability();

		double dblAdjustedExposure = dblExposure + dblBankSurvivalFinish *
			(dblCounterPartySurvivalFinish - me.start().counterParty().survivalProbability()) * dblCreditExposure +
			dblCounterPartySurvivalFinish * dblIncrementalBankSurvival * dblDebtExposure +
			dblCounterPartySurvivalFinish * dblIncrementalBankSurvival * dblFundingExposure -
			dblBankSurvivalFinish * dblCounterPartySurvivalFinish * mvFinish.collateralSchemeSpread() * dblCollateralBalance;

		try {
			double dblBankSubordinateRecoveryFinish = emvBankFinish.subordinateRecoveryRate();

			new org.drip.xva.hypothecation.BurgardKjaerVertex (
				dtAnchor,
				dblExposure,
				dblRealizedCashFlow,
				new org.drip.xva.hypothecation.CollateralGroupVertexExposure (
					dblCollateralBalance,
					dblCreditExposure,
					dblDebtExposure,
					dblFundingExposure
				),
				dblBankDefaultCloseOut,
				dblCollateralizedExposure * dblCounterPartyRecoveryFinish,
				new org.drip.xva.derivative.ReplicationPortfolioVertexBank (
					(dblFundingExposure + dblBankSubordinateRecoveryFinish * dblAdjustedExposure - dblBankDefaultCloseOut) /
						(dblBankSeniorRecoveryFinish - dblBankSubordinateRecoveryFinish) /
							emvBankFinish.seniorFundingNumeraire().forward(),
					(dblFundingExposure + dblBankSeniorRecoveryFinish * dblAdjustedExposure - dblBankDefaultCloseOut) /
						(dblBankSubordinateRecoveryFinish - dblBankSeniorRecoveryFinish) /
							nmvBankSubordinateFinish.forward()
				)
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
