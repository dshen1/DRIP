
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
 * CloseOutGeneral exposes the General Close Out Amounts to be applied to the MTM Exposure at the
 *  Bank/Counter Party Default. The References are:
 *  
 *  - Burgard, C., and M. Kjaer (2013): Funding Strategies, Funding Costs, Risk, 24 (12) 82-87.
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Cesari, G., J. Aquilina, N. Charpillon, X. Filipovic, G. Lee, and L. Manda (2009): Modeling, Pricing,
 *  	and Hedging Counter-party Credit Exposure - A Technical Guide, Springer Finance, New York.
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk, Risk 20 (2) 86-90.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public abstract class CloseOutGeneral {

	private static final double[] ZeroCollateral (
		final double[] adblExposure)
	{
		if (null == adblExposure) return null;

		int iNumCounterPartyGroup = adblExposure.length;
		double[] adblCounterPartyGroupCollateral = 0 == iNumCounterPartyGroup ? null : new
			double[iNumCounterPartyGroup];

		if (0 == iNumCounterPartyGroup) return null;

		for (int i = 0; i < iNumCounterPartyGroup; ++i)
			adblCounterPartyGroupCollateral[i] = 0.;

		return adblCounterPartyGroupCollateral;
	}

	/**
	 * Retrieve the Close-out Array from the Exposure on Bank Default
	 * 
	 * @param adblUncollateralizedExposure Array of the Counter Party Group Uncollateralized Exposures
	 * @param adblCollateralAmount Array of the Counter Party Group Collateral Amounts
	 * 
	 * @return The Close-out Array from the Exposure on Bank Default
	 */

	public abstract double[] bankDefault (
		final double[] adblUncollateralizedExposure,
		final double[] adblCollateralAmount);

	/**
	 * Retrieve the Gross Close-out from the Exposure on Bank Default
	 * 
	 * @param adblUncollateralizedExposure Array of the Counter Party Group Uncollateralized Exposures
	 * @param adblCollateralAmount Array of the Counter Party Group Collateral Amounts
	 * 
	 * @return The Gross Close-out from the Exposure on Bank Default
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public abstract double bankDefaultGross (
		final double[] adblUncollateralizedExposure,
		final double[] adblCollateralAmount)
		throws java.lang.Exception;

	/**
	 * Retrieve the Close-out from the Exposure on a specific Counter Party Default
	 * 
	 * @param iCounterPartyGroupIndex The Counter Party Group Index
	 * @param adblUncollateralizedExposure Array of the Counter Party Group Uncollateralized Exposures
	 * @param adblCollateralAmount Array of the Counter Party Group Collateral Amounts
	 * 
	 * @return The Close-out from the Exposure on a specific Counter Party Default
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public abstract double counterPartyDefault (
		final int iCounterPartyGroupIndex,
		final double[] adblUncollateralizedExposure,
		final double[] adblCollateralAmount)
		throws java.lang.Exception;

	/**
	 * Retrieve the Close-out Array from the Exposure on Bank Default
	 * 
	 * @param adblUncollateralizedExposure Array of the Counter Party Group Uncollateralized Exposures
	 * 
	 * @return The Close-out Array from the Exposure on Bank Default
	 */

	public double[] bankDefault (
		final double[] adblUncollateralizedExposure)
	{
		return bankDefault (adblUncollateralizedExposure, ZeroCollateral (adblUncollateralizedExposure));
	}

	/**
	 * Retrieve the Gross Close-out from the Exposure on Bank Default
	 * 
	 * @param adblExposure Array of the Counter Party Group Exposures
	 * 
	 * @return The Gross Close-out from the Exposure on Bank Default
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double bankDefaultGross (
		final double[] adblExposure)
		throws java.lang.Exception
	{
		return bankDefaultGross (adblExposure, ZeroCollateral (adblExposure));
	}

	/**
	 * Retrieve the Close-out from the Exposure on specific Counter Party Default
	 * 
	 * @param iCounterPartyGroupIndex The Counter Party Group Index
	 * @param adblExposure Array of the Counter Party Group Exposures
	 * 
	 * @return The Close-out from the Exposure on specific Counter Party Default
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double counterPartyDefault (
		final int iCounterPartyGroupIndex,
		final double[] adblExposure)
		throws java.lang.Exception
	{
		return counterPartyDefault (iCounterPartyGroupIndex, adblExposure, ZeroCollateral (adblExposure));
	}
}
