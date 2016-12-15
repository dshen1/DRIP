
package org.drip.execution.nonadaptive;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * DeterministicCoordinatedVariationStatic uses the Coordinated Variation Version of the Linear Participation
 *  Rate Transaction Function as described in the "Trading Time" Model to construct an Optimal Trading
 *  Trajectory. The References are:
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
 * 		Financial Studies & (4) 631-651.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class DeterministicCoordinatedVariationStatic extends
	org.drip.execution.nonadaptive.StaticOptimalSchemeContinuous {

	/**
	 * Create the Standard DeterministicCoordinatedVariationStatic Instance
	 * 
	 * @param dblStartHoldings Trajectory Start Holdings
	 * @param dblFinishTime Trajectory Finish Time
	 * @param apep The Arithmetic Price Evolution Parameters
	 * @param dblRiskAversion The Risk Aversion Parameter
	 * 
	 * @return The DeterministicCoordinatedVariationStatic Instance
	 */

	public static final DeterministicCoordinatedVariationStatic Standard (
		final double dblStartHoldings,
		final double dblFinishTime,
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep,
		final double dblRiskAversion)
	{
		try {
			return new DeterministicCoordinatedVariationStatic (new
				org.drip.execution.strategy.OrderSpecification (dblStartHoldings, dblFinishTime), apep, new
					org.drip.execution.risk.MeanVarianceObjectiveUtility (dblRiskAversion));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private DeterministicCoordinatedVariationStatic (
		final org.drip.execution.strategy.OrderSpecification os,
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep,
		final org.drip.execution.risk.MeanVarianceObjectiveUtility mvou)
		throws java.lang.Exception
	{
		super (os, apep, mvou);
	}

	@Override public org.drip.execution.optimum.EfficientTradingTrajectory generate()
	{
		org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep =
			(org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters) priceWalkParameters();

		org.drip.execution.impact.TransactionFunction tfTemporaryExpectation =
			apep.temporaryExpectation().epochImpactFunction();

		if (!(tfTemporaryExpectation instanceof org.drip.execution.impact.TransactionFunctionLinear))
			return null;

		double dblEpochVolatility = java.lang.Double.NaN;
		org.drip.execution.impact.TransactionFunctionLinear tflTemporaryExpectation =
			(org.drip.execution.impact.TransactionFunctionLinear) tfTemporaryExpectation;

		try {
			dblEpochVolatility = apep.arithmeticPriceDynamicsSettings().epochVolatility();
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		final double dblSigma = dblEpochVolatility;

		final double dblEta = tflTemporaryExpectation.slope();

		final double dblKappa = java.lang.Math.sqrt (((org.drip.execution.risk.MeanVarianceObjectiveUtility)
			objectiveUtility()).riskAversion() * dblSigma * dblSigma / dblEta);

		final org.drip.function.definition.R1ToR1 r1ToR1VolatilityFunction =
			apep.arithmeticPriceDynamicsSettings().volatilityFunction();

		org.drip.execution.strategy.OrderSpecification os = orderSpecification();

		final double dblT = os.maxExecutionTime();

		final double dblX = os.size();

		final org.drip.function.definition.R1ToR1 r1ToR1Holdings = new org.drip.function.definition.R1ToR1
			(null) {
			@Override public double evaluate (
				final double dblTime)
				throws java.lang.Exception
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (dblTime))
					throw new java.lang.Exception
						("DeterministicCoordinatedVariationStatic::Holdings::evaluate => Invalid Inputs");

				return java.lang.Math.sinh (dblKappa * (dblT - dblTime)) / java.lang.Math.sinh (dblKappa *
					dblT) * dblX;
			}
		};

		org.drip.function.definition.R1ToR1 r1ToR1TradeRate = new org.drip.function.definition.R1ToR1 (null)
		{
			@Override public double evaluate (
				final double dblTime)
				throws java.lang.Exception
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (dblTime))
					throw new java.lang.Exception
						("DeterministicCoordinatedVariationStatic::TradeRate::evaluate => Invalid Inputs");

				org.drip.function.definition.R1ToR1 r1ToR1VarianceFunction = new
					org.drip.function.definition.R1ToR1 (null) {
					@Override public double evaluate (
						final double dblForwardTime)
						throws java.lang.Exception
					{
						double dblVolatility = r1ToR1VolatilityFunction.evaluate (dblForwardTime);

						return dblVolatility * dblVolatility;
					}
				};

				return dblKappa * dblX / java.lang.Math.tanh (dblKappa * r1ToR1VarianceFunction.integrate
					(dblTime, dblT) / dblSigma * dblSigma);
			}
		};

		final org.drip.function.definition.R1ToR1 r1ToR1TransactionCostExpectationRate = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblTime)
				throws java.lang.Exception
			{
				double dblTradeRate = r1ToR1TradeRate.evaluate (dblTime);

				if (!org.drip.quant.common.NumberUtil.IsValid (dblTradeRate))
					throw new java.lang.Exception
						("DeterministicCoordinatedVariationStatic::ExpectationRate::evaluate => Invalid Inputs");

				return dblEta * dblTradeRate * dblTradeRate;
			}
		};

		org.drip.function.definition.R1ToR1 r1ToR1TransactionCostExpectation = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblTime)
				throws java.lang.Exception
			{
				return r1ToR1TransactionCostExpectationRate.integrate (dblTime, dblT);
			}
		};

		final org.drip.function.definition.R1ToR1 r1ToR1TransactionCostVarianceRate = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblTime)
				throws java.lang.Exception
			{
				double dblHoldings = r1ToR1Holdings.evaluate (dblTime);

				return dblSigma * dblSigma * dblHoldings * dblHoldings;
			}
		};

		org.drip.function.definition.R1ToR1 r1ToR1TransactionCostVariance = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblTime)
				throws java.lang.Exception
			{
				return r1ToR1TransactionCostVarianceRate.integrate (dblTime, dblT);
			}
		};

		try {
			return new org.drip.execution.optimum.EfficientTradingTrajectoryContinuous (dblT, dblEta *
				dblKappa * dblX * dblX / java.lang.Math.tanh (dblKappa * dblT),
					r1ToR1TransactionCostExpectation.evaluate (0.), 1. / dblKappa, r1ToR1Holdings,
						r1ToR1TradeRate, r1ToR1TransactionCostExpectation,
							r1ToR1TransactionCostVariance);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
