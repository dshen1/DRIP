
package org.drip.xva.definition;

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
 * SpreadIntensity holds the Funding Spreads and the Bank/Counter Party Intensities, as laid out in Burgard
 *  and Kjaer (2014). The References are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Cesari, G., J. Aquilina, N. Charpillon, X. Filipovic, G. Lee, and L. Manda (2009): Modeling, Pricing,
 *  	and Hedging Counter-party Credit Exposure - A Technical Guide, Springer Finance, New York.
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

public class SpreadIntensity {
	private double[] _adblCounterPartyDefaultIntensity = null;
	private double _dblBankSeniorFundingSpread = java.lang.Double.NaN;
	private double _dblBankSeniorDefaultIntensity = java.lang.Double.NaN;
	private double _dblBankSubordinateFundingSpread = java.lang.Double.NaN;
	private double _dblBankSubordinateDefaultIntensity = java.lang.Double.NaN;

	/**
	 * Construct a Standard SpreadIntensity Instance
	 * 
	 * @param dblBankSeniorFundingSpread The Bank Senior Funding Spread
	 * @param dblBankSeniorDefaultIntensity The Bank Senior Funding Default Intensity
	 * @param adblCounterPartyDefaultIntensity Array of Counter Party Default Intensity
	 * 
	 * @return The Standard SpreadIntensity Instance
	 */

	public static final SpreadIntensity Standard (
		final double dblBankSeniorFundingSpread,
		final double dblBankSeniorDefaultIntensity,
		final double[] adblCounterPartyDefaultIntensity)
		throws java.lang.Exception
	{
		try {
			return new SpreadIntensity (dblBankSeniorFundingSpread, dblBankSeniorDefaultIntensity,
				java.lang.Double.NaN, java.lang.Double.NaN, adblCounterPartyDefaultIntensity);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * SpreadIntensity Constructor
	 * 
	 * @param dblBankSeniorFundingSpread The Bank Senior Funding Spread
	 * @param dblBankSeniorDefaultIntensity The Bank Senior Funding Default Intensity
	 * @param dblBankSubordinateFundingSpread The Bank Subordinate Funding Spread
	 * @param dblBankSubordinateDefaultIntensity The Bank Subordinate Funding Default Intensity
	 * @param adblCounterPartyDefaultIntensity Array of Counter Party Default Intensity
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	private SpreadIntensity (
		final double dblBankSeniorFundingSpread,
		final double dblBankSeniorDefaultIntensity,
		final double dblBankSubordinateFundingSpread,
		final double dblBankSubordinateDefaultIntensity,
		final double[] adblCounterPartyDefaultIntensity)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblBankSeniorFundingSpread =
			dblBankSeniorFundingSpread) || !org.drip.quant.common.NumberUtil.IsValid
				(_dblBankSeniorDefaultIntensity = dblBankSeniorDefaultIntensity) || null ==
					(_adblCounterPartyDefaultIntensity = adblCounterPartyDefaultIntensity) || 0 ==
						_adblCounterPartyDefaultIntensity.length || !org.drip.quant.common.NumberUtil.IsValid
							(_adblCounterPartyDefaultIntensity))
			throw new java.lang.Exception ("SpreadIntensity Constructor => Invalid Inputs");

		_dblBankSubordinateFundingSpread = dblBankSubordinateFundingSpread;
		_dblBankSubordinateDefaultIntensity = dblBankSubordinateDefaultIntensity;
	}

	/**
	 * Retrieve the Bank Senior Funding Spread
	 * 
	 * @return The Bank Senior Funding Spread
	 */

	public double bankSeniorFundingSpread()
	{
		return _dblBankSeniorFundingSpread;
	}

	/**
	 * Retrieve the Bank Senior Default Intensity
	 * 
	 * @return The Bank Senior Default Intensity
	 */

	public double bankSeniorDefaultIntensity()
	{
		return _dblBankSeniorDefaultIntensity;
	}

	/**
	 * Retrieve the Bank Subordinate Funding Spread
	 * 
	 * @return The Bank Subordinate Funding Spread
	 */

	public double bankSubordinateFundingSpread()
	{
		return _dblBankSubordinateFundingSpread;
	}

	/**
	 * Retrieve the Bank Subordinate Default Intensity
	 * 
	 * @return The Bank Subordinate Default Intensity
	 */

	public double bankSubordinateDefaultIntensity()
	{
		return _dblBankSubordinateDefaultIntensity;
	}

	/**
	 * Retrieve the Array of Counter Party Default Intensities
	 * 
	 * @return The Array of Counter Party Default Intensities
	 */

	public double[] counterPartyDefaultIntensity()
	{
		return _adblCounterPartyDefaultIntensity;
	}
}
