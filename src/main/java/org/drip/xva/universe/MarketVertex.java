
package org.drip.xva.universe;

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
 * MarketVertex holds the Market Realizations at a Trajectory Vertex. The References are:
 *  
 *  - Burgard, C., and M. Kjaer (2013): Funding Strategies, Funding Costs, Risk, 24 (12) 82-87.
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

public class MarketVertex {
	private double _dblCSASpread = java.lang.Double.NaN;
	private org.drip.analytics.date.JulianDate _dtAnchor = null;
	private double _dblOvernightPolicyIndex = java.lang.Double.NaN;
	private org.drip.xva.universe.EntityMarketVertex _envBank = null;
	private org.drip.xva.universe.EntityMarketVertex _envCounterParty = null;

	/**
	 * Construct a Standard Instance of the MarketVertex
	 * 
	 * @param dtAnchor The Vertex Date Anchor
	 * @param dblOvernightPolicyIndex The Realized Overnight Policy Index Rate
	 * @param dblBankSurvival The Realized Bank Survival Rate
	 * @param dblBankRecovery The Realized Bank Recovery Rate
	 * @param dblBankFundingSpread The Bank Funding Spread
	 * @param dblCounterPartySurvival The Realized Counter Party Survival Rate
	 * @param dblCounterPartyRecovery The Realized Counter Party Recovery Rate
	 * 
	 * @return The Standard Instance of MarketVertex
	 */

	public static final MarketVertex Standard (
		final org.drip.analytics.date.JulianDate dtAnchor,
		final double dblOvernightPolicyIndex,
		final double dblBankSurvival,
		final double dblBankRecovery,
		final double dblBankFundingSpread,
		final double dblCounterPartySurvival,
		final double dblCounterPartyRecovery)
	{
		try {
			return new MarketVertex (
				dtAnchor,
				dblOvernightPolicyIndex,
				0.,
				new org.drip.xva.universe.EntityMarketVertex (
					dblBankSurvival,
					dblBankRecovery,
					dblBankFundingSpread / (1. - dblBankRecovery),
					dblBankFundingSpread
				),
				new org.drip.xva.universe.EntityMarketVertex (
					dblCounterPartySurvival,
					dblCounterPartyRecovery,
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
	 * MarketVertex Constructor
	 * 
	 * @param dtAnchor The Vertex Date Anchor
	 * @param dblOvernightPolicyIndex The Realized Overnight Policy Index Rate
	 * @param dblCSASpread The Realized CSA Spread
	 * @param envBank Bank Entity Market Vertex Instance
	 * @param envCounterParty Counter Party Market Vertex Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MarketVertex (
		final org.drip.analytics.date.JulianDate dtAnchor,
		final double dblOvernightPolicyIndex,
		final double dblCSASpread,
		final org.drip.xva.universe.EntityMarketVertex envBank,
		final org.drip.xva.universe.EntityMarketVertex envCounterParty)
		throws java.lang.Exception
	{
		if (null == (_dtAnchor = dtAnchor) || !org.drip.quant.common.NumberUtil.IsValid
			(_dblOvernightPolicyIndex = dblOvernightPolicyIndex) || !org.drip.quant.common.NumberUtil.IsValid
				(_dblCSASpread = dblCSASpread) || null == (_envBank = envBank) || null == (_envCounterParty =
					envCounterParty))
			throw new java.lang.Exception ("MarketVertex Constructor => Invalid Inputs");
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
	 * Retrieve the Realized Overnight Policy Index Rate
	 * 
	 * @return The Realized Overnight Policy Index Rate
	 */

	public double overnightPolicyIndex()
	{
		return _dblOvernightPolicyIndex;
	}

	/**
	 * Retrieve the Realized CSA Spread Rate
	 * 
	 * @return The Realized CSA Spread Rate
	 */

	public double csaSpread()
	{
		return _dblCSASpread;
	}

	/**
	 * Retrieve the Realized CSA Rate
	 * 
	 * @return The Realized CSA Rate
	 */

	public double csa()
	{
		return _dblOvernightPolicyIndex * _dblCSASpread;
	}

	/**
	 * Retrieve the Realized Bank Market Vertex
	 * 
	 * @return The Realized Bank Market Vertex
	 */

	public org.drip.xva.universe.EntityMarketVertex bankMarket()
	{
		return _envBank;
	}

	/**
	 * Retrieve the Realized Counter Party Market Vertex
	 * 
	 * @return The Realized Counter Party Market Vertex
	 */

	public org.drip.xva.universe.EntityMarketVertex counterPartyMarket()
	{
		return _envCounterParty;
	}

	/**
	 * Retrieve the Realized Bank Hazard Rate
	 * 
	 * @return The Realized Bank Hazard Rate
	 */

	public double bankHazard()
	{
		return _envBank.hazard();
	}

	/**
	 * Retrieve the Realized Bank Survival Probability
	 * 
	 * @return The Realized Bank Survival Probability
	 */

	public double bankSurvival()
	{
		return _envBank.survival();
	}

	/**
	 * Retrieve the Realized Bank Recovery Numeraire
	 * 
	 * @return The Realized Bank Recovery Numeraire
	 */

	public double bankRecovery()
	{
		return _envBank.recovery();
	}

	/**
	 * Retrieve the Realized Bank Funding Spread
	 * 
	 * @return The Realized Bank Funding Spread
	 */

	public double bankFundingSpread()
	{
		return _envBank.fundingSpread();
	}

	/**
	 * Retrieve the Realized Counter Party Survival Numeraire
	 * 
	 * @return The Realized Counter Party Survival Numeraire
	 */

	public double counterPartySurvival()
	{
		return _envCounterParty.survival();
	}

	/**
	 * Retrieve the Realized Counter Party Recovery Rate
	 * 
	 * @return The Realized Counter Party Recovery Rate
	 */

	public double counterPartyRecovery()
	{
		return _envCounterParty.recovery();
	}
}
