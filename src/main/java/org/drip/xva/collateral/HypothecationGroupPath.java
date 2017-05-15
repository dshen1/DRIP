
package org.drip.xva.collateral;

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
 * HypothecationGroupPath accumulates the Vertex Realizations of the Sequence in a Single Path Projection Run
 *  along the Granularity of a Regular Collateral Hypothecation Group. The References are:
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

public class HypothecationGroupPath {
	private org.drip.xva.collateral.HypothecationGroupVertexRegular[] _aHGVR = null;

	/**
	 * HypothecationGroupPath Constructor
	 * 
	 * @param aHGVR The Array of Collateral Hypothecation Group Trajectory Vertexes
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public HypothecationGroupPath (
		final org.drip.xva.collateral.HypothecationGroupVertexRegular[] aHGVR)
		throws java.lang.Exception
	{
		if (null == (_aHGVR = aHGVR))
			throw new java.lang.Exception ("HypothecationGroupPathRegular Constructor => Invalid Inputs");

		int iNumPath = _aHGVR.length;

		if (1 >= iNumPath)
			throw new java.lang.Exception ("HypothecationGroupPathRegular Constructor => Invalid Inputs");

		for (int i = 0; i < iNumPath; ++i) {
			if (null == _aHGVR[i])
				throw new java.lang.Exception ("HypothecationGroupPathRegular Constructor => Invalid Inputs");

			if (0 != i && _aHGVR[i - 1].anchor().julian() >= _aHGVR[i].anchor().julian())
				throw new java.lang.Exception ("HypothecationGroupPathRegular Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Array of Netting Group Trajectory Vertexes
	 * 
	 * @return The Array of Netting Group Trajectory Vertexes
	 */

	public org.drip.xva.collateral.HypothecationGroupVertexRegular[] vertexes()
	{
		return _aHGVR;
	}

	/**
	 * Retrieve the Array of the Vertex Anchor Dates
	 * 
	 * @return The Array of the Vertex Anchor Dates
	 */

	public org.drip.analytics.date.JulianDate[] anchors()
	{
		int iNumVertex = _aHGVR.length;
		org.drip.analytics.date.JulianDate[] adtVertex = new org.drip.analytics.date.JulianDate[iNumVertex];

		for (int i = 0; i < iNumVertex; ++i)
			adtVertex[i] = _aHGVR[i].anchor();

		return adtVertex;
	}

	/**
	 * Retrieve the Array of Collateralized Exposures
	 * 
	 * @return The Array of Collateralized Exposures
	 */

	public double[] collateralizedExposure()
	{
		int iNumVertex = _aHGVR.length;
		double[] adblCollateralizedExposure = new double[iNumVertex];

		for (int i = 0; i < iNumVertex; ++i)
			adblCollateralizedExposure[i] = _aHGVR[i].collateralizedExposure();

		return adblCollateralizedExposure;
	}

	/**
	 * Retrieve the Array of Uncollateralized Exposures
	 * 
	 * @return The Array of Uncollateralized Exposures
	 */

	public double[] uncollateralizedExposure()
	{
		int iNumVertex = _aHGVR.length;
		double[] adblUncollateralizedExposure = new double[iNumVertex];

		for (int i = 0; i < iNumVertex; ++i)
			adblUncollateralizedExposure[i] = _aHGVR[i].uncollateralizedExposure();

		return adblUncollateralizedExposure;
	}

	/**
	 * Retrieve the Array of Collateral Balances
	 * 
	 * @return The Array of Collateral Balances
	 */

	public double[] collateralBalance()
	{
		int iNumVertex = _aHGVR.length;
		double[] adblCollateralizedBalance = new double[iNumVertex];

		for (int i = 0; i < iNumVertex; ++i)
			adblCollateralizedBalance[i] = _aHGVR[i].collateralBalance();

		return adblCollateralizedBalance;
	}
}
