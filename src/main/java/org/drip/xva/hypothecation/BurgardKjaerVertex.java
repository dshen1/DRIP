
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
 * BurgardKjaerVertex holds the Close Out Based Vertex Exposures of a Projected Path of a Simulation Run of a
 *  Collateral Hypothecation Group using the Generalized Burgard Kjaer (2013) Scheme. The References are:
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

public class BurgardKjaerVertex {
	private org.drip.analytics.date.JulianDate _dtAnchor = null;
	private org.drip.xva.derivative.ReplicationPortfolioVertexBank _rpvb = null;
	private org.drip.xva.hypothecation.CollateralGroupVertexCloseOut _cgvco = null;
	private org.drip.xva.hypothecation.CollateralGroupVertexExposureRaw _cgver = null;
	private org.drip.xva.hypothecation.BurgardKjaerVertexExposureAttribution _cgvea = null;

	/**
	 * BurgardKjaerVertex Constructor
	 * 
	 * @param dtAnchor The Vertex Date Anchor
	 * @param cgver The Collateral Group Vertex Exposure Raw
	 * @param cgvea The Collateral Group Vertex Exposure Attribution
	 * @param cgvco The Collateral Group Vertex Close Out Instance
	 * @param rpvb The Bank Replication Portfolio Vertex Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BurgardKjaerVertex (
		final org.drip.analytics.date.JulianDate dtAnchor,
		final org.drip.xva.hypothecation.CollateralGroupVertexExposureRaw cgver,
		final org.drip.xva.hypothecation.BurgardKjaerVertexExposureAttribution cgvea,
		final org.drip.xva.hypothecation.CollateralGroupVertexCloseOut cgvco,
		final org.drip.xva.derivative.ReplicationPortfolioVertexBank rpvb)
		throws java.lang.Exception
	{
		if (null == (_dtAnchor = dtAnchor) || null == (_cgver = cgver) || null == (_cgvea = cgvea) || null ==
			(_cgvco = cgvco) || null == (_rpvb = rpvb))
			throw new java.lang.Exception ("BurgardKjaerVertex Constructor => Invalid Inputs");
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

	public double exposure()
	{
		return _cgver.uncollateralized();
	}

	/**
	 * Retrieve the Collateral Balance at the Path Vertex Time Node
	 * 
	 * @return The Collateral Balance at the Path Vertex Time Node
	 */

	public double collateralBalance()
	{
		return _cgvea.collateralBalance();
	}

	/**
	 * Retrieve the Default Window Realized Cash-flow at the Path Vertex Time Node
	 * 
	 * @return The Default Window Realized Cash-flow at the Path Vertex Time Node
	 */

	public double realizedCashFlow()
	{
		return _cgver.realizedCashFlow();
	}

	/**
	 * Retrieve the Close Out on Bank Default
	 * 
	 * @return Close Out on Bank Default
	 */

	public double bankDefaultCloseOut()
	{
		return _cgvco.bank();
	}

	/**
	 * Retrieve the Close Out on Counter Party Default
	 * 
	 * @return Close Out on Counter Party Default
	 */

	public double counterPartyDefaultCloseOut()
	{
		return _cgvco.counterParty();
	}

	/**
	 * Retrieve the Total Collateralized Exposure at the Path Vertex Time Node
	 * 
	 * @return The Total Collateralized Exposure at the Path Vertex Time Node
	 */

	public double collateralizedExposure()
	{
		return _cgver.gross() - _cgvea.collateralBalance();
	}

	/**
	 * Retrieve the Total Collateralized Exposure at the Path Vertex Time Node
	 * 
	 * @return The Total Collateralized Exposure at the Path Vertex Time Node
	 */

	public double uncollateralizedExposure()
	{
		return _cgver.gross();
	}

	/**
	 * Retrieve the Credit Exposure at the Path Vertex Time Node
	 * 
	 * @return The Credit Exposure at the Path Vertex Time Node
	 */

	public double creditExposure()
	{
		return _cgvea.credit();
	}

	/**
	 * Retrieve the Debt Exposure at the Path Vertex Time Node
	 * 
	 * @return The Debt Exposure at the Path Vertex Time Node
	 */

	public double debtExposure()
	{
		return _cgvea.debt();
	}

	/**
	 * Retrieve the Funding Exposure at the Path Vertex Time Node
	 * 
	 * @return The Funding Exposure at the Path Vertex Time Node
	 */

	public double fundingExposure()
	{
		return _cgvea.funding();
	}

	/**
	 * Retrieve the Hedge Error
	 * 
	 * @return The Hedge Error
	 */

	public double hedgeError()
	{
		return _cgvea.funding();
	}

	/**
	 * Retrieve the Bank Replication Potrfolio Instance
	 * 
	 * @return The Bank Replication Potrfolio Instance
	 */

	public org.drip.xva.derivative.ReplicationPortfolioVertexBank bankReplicationPortfolio()
	{
		return _rpvb;
	}
}
