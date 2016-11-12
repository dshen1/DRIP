
package org.drip.sample.athl;

import org.drip.execution.athl.*;
import org.drip.execution.parameters.AssetFlowSettings;
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
 * EquityMarketImpactIBM demonstrates the Reconciliation of the Equity Market Impact with that determined
 *  empirically by Almgren, Thum, Hauptmann, and Li (2005), using the Parameterization of Almgren (2003) for
 *  IBM. The References are:
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
 * 	- Almgren, R., C. Thum, E. Hauptmann, and H. Li (2005): Equity Market Impact, Risk 18 (7) 57-62.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EquityMarketImpactIBM {

	private static final void TemporaryImpactReconciler (
		final TemporaryImpact athlTemporary,
		final double dblTradeSize,
		final double dblTime,
		final double dblNormalizedTemporaryImpactReconciler,
		final double dblDenormalizedTemporaryImpactReconciler,
		final double dblDenormalizedPermanentImpact,
		final double dblRealizedImpactReconciler)
		throws Exception
	{
		double dblNormalizedTemporaryImpact = athlTemporary.evaluate (
			dblTradeSize / (athlTemporary.assetFlowParameters().averageDailyVolume() * dblTime)
		);

		double dblDenormalizedTemporaryImpact = athlTemporary.evaluate (
			dblTradeSize,
			dblTime
		);

		System.out.println (
			"\t| " +
			FormatUtil.FormatDouble (dblTime, 1, 1, 1.) + " | " +
			FormatUtil.FormatDouble (dblNormalizedTemporaryImpact, 1, 3, 1.) + " | " +
			FormatUtil.FormatDouble (dblNormalizedTemporaryImpactReconciler, 1, 3, 1.) + " || " +
			FormatUtil.FormatDouble (dblDenormalizedTemporaryImpact, 2, 0, 100.) + " | " +
			FormatUtil.FormatDouble (dblDenormalizedTemporaryImpactReconciler, 2, 0, 1.) + " ||" +
			FormatUtil.FormatDouble (dblDenormalizedPermanentImpact + dblDenormalizedTemporaryImpact, 2, 0, 100.) + " | " +
			FormatUtil.FormatDouble (dblRealizedImpactReconciler, 2, 0, 1.) + " ||"
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String strAssetName = "IBM";
		double dblAverageDailyVolume = 6561000.;
		double dblSharesOutstanding = 1728000000.;
		double dblDailyVolatility = 1.57;
		double dblTradeSize = 656100.;
		double dblTradeTime = 1.;

		double dblInverseTurnoverReconciler = 263.374;
		double dblNormalizedTradeSizeReconciler = 0.1;
		double dblNormalizedPermanentImpactReconciler = 0.126;
		double dblDenormalizedPermanentImpactReconciler = 19.86;

		double[] adblTime = new double[] {
			0.1,
			0.2,
			0.5
		};

		double[] adblNormalizedTemporaryImpactReconciler = new double[] {
			0.142,
			0.094,
			0.054
		};

		double[] adblDenormalizedTemporaryImpactReconciler = new double[] {
			22,
			15,
			8
		};

		double[] adblRealizedImpactReconciler = new double[] {
			32,
			25,
			18
		};

		AssetFlowSettings afp = new AssetFlowSettings (
			strAssetName,
			dblAverageDailyVolume,
			dblSharesOutstanding,
			dblDailyVolatility
		);

		TemporaryImpact athlTemporary = new TemporaryImpact (afp);

		PermanentImpactNoArbitrage athlPermanent = new PermanentImpactNoArbitrage (afp);

		double dblDenormalizedPermanentImpact = athlPermanent.evaluate (dblTradeSize, dblTradeTime);

		double dblNormalizedPermanentImpact = athlPermanent.evaluate (
			dblTradeSize / (afp.averageDailyVolume() * dblTradeTime)
		);

		System.out.println();

		System.out.println ("\t|-------------------------------------------||");

		System.out.println ("\t|  Asset                   =>  " + strAssetName);

		System.out.println ("\t|  Average Daily Volume    => " + FormatUtil.FormatDouble (dblAverageDailyVolume, 1, 0, 1.));

		System.out.println ("\t|  Shares Outstanding      => " + FormatUtil.FormatDouble (dblSharesOutstanding, 1, 0, 1.));

		System.out.println ("\t|  Daily Volatility        => " + FormatUtil.FormatDouble (dblDailyVolatility, 1, 2, 1.) + "%");

		System.out.println ("\t|  Trade Size              => " + FormatUtil.FormatDouble (dblTradeSize, 1, 0, 1.));

		System.out.println ("\t|-------------------------------------------||\n");

		System.out.println ("\t|--------------------------------------------------------||");

		System.out.println ("\t|  ALMGREN, THUM, HAUPTMANN, and LI (2005) PERM. RECON   ||");

		System.out.println ("\t|--------------------------------------------------------||");

		System.out.println (
			"\t|  Inverse Turn-over              => " +
			FormatUtil.FormatDouble (afp.inverseTurnover(), 3, 3, 1.) + " | " +
			FormatUtil.FormatDouble (dblInverseTurnoverReconciler, 3, 3, 1.) + " ||"
		);

		System.out.println (
			"\t|  Normalized Trade Size          => " +
			FormatUtil.FormatDouble (afp.normalizeTradeSize (dblTradeSize, dblTradeTime), 3, 3, 1.) + " | " +
			FormatUtil.FormatDouble (dblNormalizedTradeSizeReconciler, 3, 3, 1.) + " ||"
		);

		System.out.println (
			"\t|  Normalized Permanent Impact    => " +
			FormatUtil.FormatDouble (2. * dblNormalizedPermanentImpact, 3, 3, 1.) + " | " +
			FormatUtil.FormatDouble (dblNormalizedPermanentImpactReconciler, 3, 3, 1.) + " ||"
		);

		System.out.println (
			"\t|  De-normalized Permanent Impact => " +
			FormatUtil.FormatDouble (2. * dblDenormalizedPermanentImpact, 3, 3, 100.) + " | " +
			FormatUtil.FormatDouble (dblDenormalizedPermanentImpactReconciler, 3, 3, 1.) + " ||"
		);

		System.out.println ("\t|--------------------------------------------------------||\n");

		System.out.println ("\t|-------------------------------------------------||");

		System.out.println ("\t|    TEMPORARY IMPACT PARAMETERS RECONCILIATION   ||");

		System.out.println ("\t|-------------------------------------------------||");

		System.out.println ("\t|        L -> R:                                  ||");

		System.out.println ("\t|                - Time                           ||");

		System.out.println ("\t|                - Normalized K (Computed)        ||");

		System.out.println ("\t|                - Normalized K (Reconciler)      ||");

		System.out.println ("\t|                - De-normalized K (Computed)     ||");

		System.out.println ("\t|                - De-normalized K (Reconciler)   ||");

		System.out.println ("\t|                - De-normalized J (Computed)     ||");

		System.out.println ("\t|                - De-normalized J (Reconciler)   ||");

		System.out.println ("\t|-------------------------------------------------||");

		for (int i = 0; i < adblTime.length; ++i)
			TemporaryImpactReconciler (
				athlTemporary,
				dblTradeSize,
				adblTime[i],
				adblNormalizedTemporaryImpactReconciler[i],
				adblDenormalizedTemporaryImpactReconciler[i],
				dblDenormalizedPermanentImpact,
				adblRealizedImpactReconciler[i]
			);

		System.out.println ("\t|-------------------------------------------------||\n");
	}
}
