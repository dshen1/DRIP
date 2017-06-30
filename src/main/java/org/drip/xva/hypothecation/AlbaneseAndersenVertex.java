
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
 * AlbaneseAndersenVertex holds the Albanese and Andersen (2014) Vertex Exposures of a Projected Path of a
 *  Simulation Run of a Collateral Hypothecation Group. The References are:
 *  
 *  - Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  	Re-Hypothecation Option, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955, eSSRN.
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk, Risk 20 (2) 86-90.
 *  
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class AlbaneseAndersenVertex implements
	org.drip.xva.hypothecation.CollateralGroupVertexExposureAttibution {
	private double _dblHedgeError = java.lang.Double.NaN;
	private double _dblForwardExposure = java.lang.Double.NaN;
	private double _dblRealizedCashFlow = java.lang.Double.NaN;
	private double _dblCollateralBalance = java.lang.Double.NaN;
	private org.drip.analytics.date.JulianDate _dtAnchor = null;
	private double _dblBankDefaultCloseOut = java.lang.Double.NaN;
	private double _dblCounterPartyDefaultCloseOut = java.lang.Double.NaN;

	/**
	 * Construct a Standard Instance of AlbaneseAndersenVertex
	 * 
	 * @param dtAnchor The Vertex Date Anchor
	 * @param dblForwardExposure The Forward Exposure at the Path Vertex Time Node
	 * @param dblRealizedCashFlow The Default Window Realized Cash-flow at the Path Vertex Time Node
	 * @param dblCollateralBalance The Collateral Balance at the Path Vertex Time Node
	 * @param dblHedgeError Hedge Error
	 * @param cog The Generic Close-Out Evaluator Instance
	 * 
	 * @return The Standard Instance of AlbaneseAndersenVertex
	 */

	public static AlbaneseAndersenVertex Standard (
		final org.drip.analytics.date.JulianDate dtAnchor,
		final double dblForwardExposure,
		final double dblRealizedCashFlow,
		final double dblCollateralBalance,
		final double dblHedgeError,
		final org.drip.xva.definition.CloseOutGeneral cog)
	{
		if (null == cog) return null;

		double dblUncollateralizedExposure = dblForwardExposure + dblRealizedCashFlow;

		try {
			return new AlbaneseAndersenVertex (
				dtAnchor,
				dblForwardExposure,
				dblRealizedCashFlow,
				dblCollateralBalance,
				cog.bankDefault (
					dblUncollateralizedExposure,
					dblCollateralBalance
				),
				cog.counterPartyDefault (
					dblUncollateralizedExposure,
					dblCollateralBalance
				),
				dblHedgeError
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * AlbaneseAndersenVertex Constructor
	 * 
	 * @param dtAnchor The Vertex Date Anchor
	 * @param dblForwardExposure The Forward Exposure at the Path Vertex Time Node
	 * @param dblRealizedCashFlow The Default Window Realized Cash-flow at the Path Vertex Time Node
	 * @param dblCollateralBalance The Collateral Balance at the Path Vertex Time Node
	 * @param dblBankDefaultCloseOut Close Out on Bank Default
	 * @param dblCounterPartyDefaultCloseOut Close Out on Counter Party Default
	 * @param dblHedgeError The Vertex Hedge Error
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AlbaneseAndersenVertex (
		final org.drip.analytics.date.JulianDate dtAnchor,
		final double dblForwardExposure,
		final double dblRealizedCashFlow,
		final double dblCollateralBalance,
		final double dblBankDefaultCloseOut,
		final double dblCounterPartyDefaultCloseOut,
		final double dblHedgeError)
		throws java.lang.Exception
	{
		if (null == (_dtAnchor = dtAnchor) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblForwardExposure = dblForwardExposure) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblRealizedCashFlow = dblRealizedCashFlow) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblCollateralBalance = dblCollateralBalance) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblBankDefaultCloseOut = dblBankDefaultCloseOut) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblCounterPartyDefaultCloseOut =
				dblCounterPartyDefaultCloseOut) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblHedgeError = dblHedgeError))
			throw new java.lang.Exception ("AlbaneseAndersenVertex Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Date Anchor
	 * 
	 * @return The Date Anchor
	 */

	public org.drip.analytics.date.JulianDate anchor()
	{
		return _dtAnchor;
	}

	/**
	 * Retrieve the Forward Exposure at the Path Vertex Time Node
	 * 
	 * @return The Forward Exposure at the Path Vertex Time Node
	 */

	public double forwardExposure()
	{
		return _dblForwardExposure;
	}

	/**
	 * Retrieve the Collateral Balance at the Path Vertex Time Node
	 * 
	 * @return The Collateral Balance at the Path Vertex Time Node
	 */

	public double collateralBalance()
	{
		return _dblCollateralBalance;
	}

	/**
	 * Retrieve the Default Window Realized Cash-flow at the Path Vertex Time Node
	 * 
	 * @return The Default Window Realized Cash-flow at the Path Vertex Time Node
	 */

	public double realizedCashFlow()
	{
		return _dblRealizedCashFlow;
	}

	/**
	 * Retrieve the Hedge Error
	 * 
	 * @return The Hedge Error
	 */

	public double hedgeError()
	{
		return _dblHedgeError;
	}

	/**
	 * Retrieve the Close Out on Bank Default
	 * 
	 * @return Close Out on Bank Default
	 */

	public double bankDefaultCloseOut()
	{
		return _dblBankDefaultCloseOut;
	}

	/**
	 * Retrieve the Close Out on Counter Party Default
	 * 
	 * @return Close Out on Counter Party Default
	 */

	public double counterPartyDefaultCloseOut()
	{
		return _dblCounterPartyDefaultCloseOut;
	}

	/**
	 * Retrieve the Total Collateralized Exposure at the Path Vertex Time Node
	 * 
	 * @return The Total Collateralized Exposure at the Path Vertex Time Node
	 */

	public double collateralizedExposure()
	{
		return forwardExposure() + realizedCashFlow() - collateralBalance();
	}

	/**
	 * Retrieve the Total Collateralized Exposure at the Path Vertex Time Node
	 * 
	 * @return The Total Collateralized Exposure at the Path Vertex Time Node
	 */

	public double uncollateralizedExposure()
	{
		return forwardExposure() + realizedCashFlow();
	}

	@Override public double credit()
	{
		double dblCreditExposure = collateralizedExposure();

		return 0. < dblCreditExposure ? dblCreditExposure : 0.;
	}

	@Override public double debt()
	{
		double dblDebtExposure = collateralizedExposure();

		return 0. > dblDebtExposure ? dblDebtExposure : 0.;
	}

	@Override public double funding()
	{
		double dblCreditExposure = collateralizedExposure();

		return 0. < dblCreditExposure ? dblCreditExposure : 0.;
	}
}