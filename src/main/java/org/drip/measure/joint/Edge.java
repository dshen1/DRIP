
package org.drip.measure.joint;

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
 * Edge implements the Deterministic and the Stochastic Components of a Joint R^1 Random Increment. The
 *  References are:
 * 
 * 	- Almgren, R. F., and N. Chriss (2000): Optimal Execution of Portfolio Transactions, Journal of Risk 3
 * 		(2) 5-39.
 *
 * 	- Almgren, R. F. (2009): Optimal Trading in a Dynamic Market
 * 		https://www.math.nyu.edu/financial_mathematics/content/02_financial/2009-2.pdf.
 *
 * 	- Almgren, R. F. (2012): Optimal Trading with Stochastic Liquidity and Volatility, SIAM Journal of
 * 		Financial Mathematics  3 (1) 163-181.
 * 
 * 	- Geman, H., D. B. Madan, and M. Yor (2001): Time Changes for Levy Processes, Mathematical Finance 11 (1)
 * 		79-96.
 * 
 * 	- Jones, C. M., G. Kaul, and M. L. Lipson (1994): Transactions, Volume, and Volatility, Review of
 * 		Financial Studies 7 (4) 631-651.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class Edge {
	private org.drip.measure.realization.JumpDiffusionEdge[] _aMLR = null;

	/**
	 * Edge Constructor
	 * 
	 * @param aMLR Array of the Marginal Level Realizations
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Edge (
		final org.drip.measure.realization.JumpDiffusionEdge[] aMLR)
		throws java.lang.Exception
	{
		if (null == (_aMLR = aMLR)) throw new java.lang.Exception ("Edge Constructor => Invalid Inputs");

		int iNumVariate = _aMLR.length;

		if (0 == iNumVariate) throw new java.lang.Exception ("Edge Constructor => Invalid Inputs");

		for (int i = 0; i < iNumVariate; ++i) {
			if (null == _aMLR[i]) throw new java.lang.Exception ("Edge Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Array of the Marginal Level Realizations
	 * 
	 * @return The Array of the Marginal Level Realizations
	 */

	public org.drip.measure.realization.JumpDiffusionEdge[] marginal()
	{
		return _aMLR;
	}
}
