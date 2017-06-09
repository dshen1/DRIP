
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
 * LatentStatePath holds the Vertex Realizations of the Latent States of the Reference Universe along an
 * 	Evolution Path. The References are:
 *  
 *  - Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  	Re-Hypothecation Option, eSSRN, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955.
 *  
 *  - Burgard, C., and M. Kjaer (2013): Funding Costs, Funding Strategies, Risk, 23 (12) 82-87.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 *  
 *  - Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting, eSSRN,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class LatentStatePath {
	private org.drip.xva.universe.LatentStateVertex[] _aLSV = null;

	/**
	 * LatentStatePath Constructor
	 * 
	 * @param aLSV Array of Latent State Vertexes
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public LatentStatePath (
		final org.drip.xva.universe.LatentStateVertex[] aLSV)
		throws java.lang.Exception
	{
		if (null == (_aLSV = aLSV))
			throw new java.lang.Exception ("LatentStatePath Constructor => Invalid Inputs");

		int iNumVertex = _aLSV.length;

		if (0 == iNumVertex) throw new java.lang.Exception ("LatentStatePath Constructor => Invalid Inputs");

		for (int i = 0; i < iNumVertex; ++i) {
			if (null == _aLSV[i])
				throw new java.lang.Exception ("LatentStatePath Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Path Time Vertexes
	 * 
	 * @return The Path Time Vertexes
	 */

	public double[] vertex()
	{
		int iNumVertex = _aLSV.length;
		double[] adblTimeVertex = new double[iNumVertex];

		for (int iVertex = 0; iVertex < iNumVertex; ++iVertex)
			adblTimeVertex[iVertex] = _aLSV[iVertex].time();

		return adblTimeVertex;
	}

	/**
	 * Retrieve the Array of Latent State Vertex Nodes
	 * 
	 * @return Array of Latent State Vertex Nodes
	 */

	public org.drip.xva.universe.LatentStateVertex[] nodes()
	{
		return _aLSV;
	}
}
