
package org.drip.sample.almgren;

import org.drip.execution.dynamics.Almgren2003Parameters;
import org.drip.execution.generator.Almgren2003TrajectoryScheme;
import org.drip.execution.impact.*;
import org.drip.execution.optimum.Almgren2003TradingTrajectory;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for fixed income analysts and developers -
 * 		http://www.credit-trader.org/Begin.html
 * 
 *  DRIP is a free, full featured, fixed income rates, credit, and FX analytics library with a focus towards
 *  	pricing/valuation, risk, and market making.
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
 * PowerLawOptimalTrajectory sketches out the Optimal Trajectories for 3 different values of k - representing
 * 	Concave, Linear, and Convex Power's respectively. The References are:
 * 
 * 	- Almgren, R., and N. Chriss (1999): Value under Liquidation, Risk 12 (12).
 * 
 * 	- Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions, Journal of Risk 3 (2)
 * 		5-39.
 * 
 * 	- Almgren, R. (2003): Optimal Execution with Nonlinear Impact Functions and Trading-Enhanced Risk,
 * 		Applied Mathematical Finance 10 (1) 1-18.
 * 
 * 	- Almgren, R., and N. Chriss (2003): Bidding Principles, Risk 97-102.
 * 
 * 	- Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs, Journal of Financial Markets,
 * 		1, 1-50.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PowerLawOptimalTrajectory {

	private static final void RiskAversionRun (
		final double dblLambda)
		throws Exception
	{
		double dblGamma = 0.;
		double dblHRef = 0.50;
		double dblVRef = 100000.;
		double dblDrift = 0.;
		double dblVolatility = 1.;
		double dblSerialCorrelation = 0.;
		double dblX = 100000.;
		double dblFinishTime = 10.;
		int iNumInterval = 10;

		double[] adblK = new double[] {
			0.25,
			0.50,
			0.75,
			1.00,
			1.25,
			1.50,
			1.75,
			2.00,
			2.25,
			2.50,
			2.75,
			3.00
		};

		System.out.println ("\n\t|------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|\tPOWER LAW OPTIMAL TRAJECTORY; RISK TOLERANCE (thousands) => " + FormatUtil.FormatDouble (1. / dblLambda, 1, 0, 1.e-03));

		System.out.println ("\t|");

		System.out.println ("\t|\t\tL -> R:");

		System.out.println ("\t|\t\t\tTime Node Trajectory Realization (Percent)");

		System.out.println ("\t|\t\t\tCharacteristic Time (Days)");

		System.out.println ("\t|\t\t\tMaximum Execution Time (Days)");

		System.out.println ("\t|\t\t\tTransaction Cost Expectation (Thousands)");

		System.out.println ("\t|\t\t\tTransaction Cost Variance (Thousands)");

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------------||");

		for (int i = 0; i < adblK.length; ++i) {
			double dblEta = dblHRef / java.lang.Math.pow (dblVRef, adblK[i]);

			Almgren2003Parameters a2003pe = new Almgren2003Parameters (
				dblDrift,
				dblVolatility,
				dblSerialCorrelation,
				new ParticipationRateLinear (
					0.,
					dblGamma
				),
				new ParticipationRatePower (
					dblEta,
					adblK[i]
				)
			);

			Almgren2003TrajectoryScheme a2003ts = Almgren2003TrajectoryScheme.Standard (
				dblX,
				dblFinishTime,
				iNumInterval,
				a2003pe,
				dblLambda
			);

			Almgren2003TradingTrajectory a2003tt = (Almgren2003TradingTrajectory) a2003ts.generate();

			double[] adblExecutionTime = a2003tt.executionTimeNode();

			if (0 == i) {
				String strExecutionTime = "\t|          |  ";

				for (int j = 0; j < adblExecutionTime.length; ++j)
					strExecutionTime = strExecutionTime + "   " + FormatUtil.FormatDouble (adblExecutionTime[j], 1, 2, 1.);

				System.out.println (strExecutionTime);

				System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------------||");
			}

			double[] adblHoldings = a2003tt.holdings();

			String strHoldings = "\t| k =" + FormatUtil.FormatDouble (adblK[i], 1, 2, 1.) + " | ";

			for (int j = 0; j < adblHoldings.length; ++j)
				strHoldings = strHoldings + "  " + FormatUtil.FormatDouble (adblHoldings[j] / dblX, 2, 2, 100.);

			double dblMaxExecutionTime = a2003tt.maxExecutionTime();

			System.out.println (
				strHoldings + " | " +
				FormatUtil.FormatDouble (a2003tt.characteristicTime(), 2, 1, 1.) + " | " +
				FormatUtil.FormatDouble (Double.isNaN (dblMaxExecutionTime) ? 0. : dblMaxExecutionTime, 2, 1, 1.) + " | " +
				FormatUtil.FormatDouble (a2003tt.transactionCostExpectation(), 3, 0, 1.e-03) + " | " +
				FormatUtil.FormatDouble (Math.sqrt (a2003tt.transactionCostVariance()), 3, 0, 1.e-03) + " ||"
			);
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------------||");
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double[] adblLambda = new double[] {
			1.e-04,
			5.e-06,
			5.e-07
		};

		for (double dblLambda : adblLambda)
			RiskAversionRun (dblLambda);
	}
}
