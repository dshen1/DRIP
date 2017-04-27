
package org.drip.analytics.holset;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*
 *    GENERATED on Fri Jan 11 19:54:07 EST 2013 ---- DO NOT DELETE
 */

/*!
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
 *
 * This file is part of CreditAnalytics, a free-software/open-source library for
 *		fixed income analysts and developers - http://www.credit-trader.org
 *
 * CreditAnalytics is a free, full featured, fixed income credit analytics library, developed with a special focus
 * 		towards the needs of the bonds and credit products community.
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

public class IEPHoliday implements org.drip.analytics.holset.LocationHoliday {
	public IEPHoliday()
	{
	}

	public java.lang.String getHolidayLoc()
	{
		return "IEP";
	}

	public org.drip.analytics.eventday.Locale getHolidaySet()
	{
		org.drip.analytics.eventday.Locale lh = new
			org.drip.analytics.eventday.Locale();

		lh.addStaticHoliday ("01-JAN-1998", "New Years Day");

		lh.addStaticHoliday ("17-MAR-1998", "St. Patricks Day");

		lh.addStaticHoliday ("10-APR-1998", "Good Friday");

		lh.addStaticHoliday ("13-APR-1998", "Easter Monday");

		lh.addStaticHoliday ("04-MAY-1998", "Labour Day");

		lh.addStaticHoliday ("01-JUN-1998", "Spring Bank Holiday");

		lh.addStaticHoliday ("03-AUG-1998", "Summer Bank Holiday");

		lh.addStaticHoliday ("26-OCT-1998", "Autumn Bank Holiday");

		lh.addStaticHoliday ("25-DEC-1998", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-1998", "St. Stephens Day Observed");

		lh.addStaticHoliday ("29-DEC-1998", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-1999", "New Years Day");

		lh.addStaticHoliday ("17-MAR-1999", "St. Patricks Day");

		lh.addStaticHoliday ("02-APR-1999", "Good Friday");

		lh.addStaticHoliday ("05-APR-1999", "Easter Monday");

		lh.addStaticHoliday ("03-MAY-1999", "Labour Day");

		lh.addStaticHoliday ("07-JUN-1999", "Spring Bank Holiday");

		lh.addStaticHoliday ("02-AUG-1999", "Summer Bank Holiday");

		lh.addStaticHoliday ("25-OCT-1999", "Autumn Bank Holiday");

		lh.addStaticHoliday ("27-DEC-1999", "Christmas Day Observed");

		lh.addStaticHoliday ("28-DEC-1999", "St. Stephens Day Observed");

		lh.addStaticHoliday ("29-DEC-1999", "Bank Holiday");

		lh.addStaticHoliday ("03-JAN-2000", "New Years Day Observed");

		lh.addStaticHoliday ("17-MAR-2000", "St. Patricks Day");

		lh.addStaticHoliday ("21-APR-2000", "Good Friday");

		lh.addStaticHoliday ("24-APR-2000", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2000", "Labour Day");

		lh.addStaticHoliday ("05-JUN-2000", "Spring Bank Holiday");

		lh.addStaticHoliday ("07-AUG-2000", "Summer Bank Holiday");

		lh.addStaticHoliday ("30-OCT-2000", "Autumn Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2000", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2000", "St. Stephens Day");

		lh.addStaticHoliday ("27-DEC-2000", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2001", "New Years Day");

		lh.addStaticHoliday ("19-MAR-2001", "St. Patricks Day Observed");

		lh.addStaticHoliday ("13-APR-2001", "Good Friday");

		lh.addStaticHoliday ("16-APR-2001", "Easter Monday");

		lh.addStaticHoliday ("07-MAY-2001", "Labour Day");

		lh.addStaticHoliday ("04-JUN-2001", "Spring Bank Holiday");

		lh.addStaticHoliday ("06-AUG-2001", "Summer Bank Holiday");

		lh.addStaticHoliday ("29-OCT-2001", "Autumn Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2001", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2001", "St. Stephens Day");

		lh.addStaticHoliday ("27-DEC-2001", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2002", "New Years Day");

		lh.addStaticHoliday ("18-MAR-2002", "St. Patricks Day Observed");

		lh.addStaticHoliday ("29-MAR-2002", "Good Friday");

		lh.addStaticHoliday ("01-APR-2002", "Easter Monday");

		lh.addStaticHoliday ("06-MAY-2002", "Labour Day");

		lh.addStaticHoliday ("03-JUN-2002", "Spring Bank Holiday");

		lh.addStaticHoliday ("05-AUG-2002", "Summer Bank Holiday");

		lh.addStaticHoliday ("28-OCT-2002", "Autumn Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2002", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2002", "St. Stephens Day");

		lh.addStaticHoliday ("27-DEC-2002", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2003", "New Years Day");

		lh.addStaticHoliday ("17-MAR-2003", "St. Patricks Day");

		lh.addStaticHoliday ("18-APR-2003", "Good Friday");

		lh.addStaticHoliday ("21-APR-2003", "Easter Monday");

		lh.addStaticHoliday ("05-MAY-2003", "Labour Day");

		lh.addStaticHoliday ("02-JUN-2003", "Spring Bank Holiday");

		lh.addStaticHoliday ("04-AUG-2003", "Summer Bank Holiday");

		lh.addStaticHoliday ("27-OCT-2003", "Autumn Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2003", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2003", "St. Stephens Day");

		lh.addStaticHoliday ("29-DEC-2003", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2004", "New Years Day");

		lh.addStaticHoliday ("17-MAR-2004", "St. Patricks Day");

		lh.addStaticHoliday ("09-APR-2004", "Good Friday");

		lh.addStaticHoliday ("12-APR-2004", "Easter Monday");

		lh.addStaticHoliday ("03-MAY-2004", "Labour Day");

		lh.addStaticHoliday ("07-JUN-2004", "Spring Bank Holiday");

		lh.addStaticHoliday ("02-AUG-2004", "Summer Bank Holiday");

		lh.addStaticHoliday ("25-OCT-2004", "Autumn Bank Holiday");

		lh.addStaticHoliday ("27-DEC-2004", "Christmas Day Observed");

		lh.addStaticHoliday ("28-DEC-2004", "St. Stephens Day Observed");

		lh.addStaticHoliday ("29-DEC-2004", "Bank Holiday");

		lh.addStaticHoliday ("03-JAN-2005", "New Years Day Observed");

		lh.addStaticHoliday ("17-MAR-2005", "St. Patricks Day");

		lh.addStaticHoliday ("25-MAR-2005", "Good Friday");

		lh.addStaticHoliday ("28-MAR-2005", "Easter Monday");

		lh.addStaticHoliday ("02-MAY-2005", "Labour Day");

		lh.addStaticHoliday ("06-JUN-2005", "Spring Bank Holiday");

		lh.addStaticHoliday ("01-AUG-2005", "Summer Bank Holiday");

		lh.addStaticHoliday ("31-OCT-2005", "Autumn Bank Holiday");

		lh.addStaticHoliday ("26-DEC-2005", "St. Stephens Day");

		lh.addStaticHoliday ("27-DEC-2005", "Christmas Day Observed");

		lh.addStaticHoliday ("28-DEC-2005", "Bank Holiday");

		lh.addStaticHoliday ("02-JAN-2006", "New Years Day Observed");

		lh.addStaticHoliday ("17-MAR-2006", "St. Patricks Day");

		lh.addStaticHoliday ("14-APR-2006", "Good Friday");

		lh.addStaticHoliday ("17-APR-2006", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2006", "Labour Day");

		lh.addStaticHoliday ("05-JUN-2006", "Spring Bank Holiday");

		lh.addStaticHoliday ("07-AUG-2006", "Summer Bank Holiday");

		lh.addStaticHoliday ("30-OCT-2006", "Autumn Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2006", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2006", "St. Stephens Day");

		lh.addStaticHoliday ("27-DEC-2006", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2007", "New Years Day");

		lh.addStaticHoliday ("19-MAR-2007", "St. Patricks Day Observed");

		lh.addStaticHoliday ("06-APR-2007", "Good Friday");

		lh.addStaticHoliday ("09-APR-2007", "Easter Monday");

		lh.addStaticHoliday ("07-MAY-2007", "Labour Day");

		lh.addStaticHoliday ("04-JUN-2007", "Spring Bank Holiday");

		lh.addStaticHoliday ("06-AUG-2007", "Summer Bank Holiday");

		lh.addStaticHoliday ("29-OCT-2007", "Autumn Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2007", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2007", "St. Stephens Day");

		lh.addStaticHoliday ("27-DEC-2007", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2008", "New Years Day");

		lh.addStaticHoliday ("17-MAR-2008", "St. Patricks Day");

		lh.addStaticHoliday ("21-MAR-2008", "Good Friday");

		lh.addStaticHoliday ("24-MAR-2008", "Easter Monday");

		lh.addStaticHoliday ("05-MAY-2008", "Labour Day");

		lh.addStaticHoliday ("02-JUN-2008", "Spring Bank Holiday");

		lh.addStaticHoliday ("04-AUG-2008", "Summer Bank Holiday");

		lh.addStaticHoliday ("27-OCT-2008", "Autumn Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2008", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2008", "St. Stephens Day");

		lh.addStaticHoliday ("29-DEC-2008", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2009", "New Years Day");

		lh.addStaticHoliday ("17-MAR-2009", "St. Patricks Day");

		lh.addStaticHoliday ("10-APR-2009", "Good Friday");

		lh.addStaticHoliday ("13-APR-2009", "Easter Monday");

		lh.addStaticHoliday ("04-MAY-2009", "Labour Day");

		lh.addStaticHoliday ("01-JUN-2009", "Spring Bank Holiday");

		lh.addStaticHoliday ("03-AUG-2009", "Summer Bank Holiday");

		lh.addStaticHoliday ("26-OCT-2009", "Autumn Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2009", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-2009", "St. Stephens Day Observed");

		lh.addStaticHoliday ("29-DEC-2009", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2010", "New Years Day");

		lh.addStaticHoliday ("17-MAR-2010", "St. Patricks Day");

		lh.addStaticHoliday ("02-APR-2010", "Good Friday");

		lh.addStaticHoliday ("05-APR-2010", "Easter Monday");

		lh.addStaticHoliday ("03-MAY-2010", "Labour Day");

		lh.addStaticHoliday ("07-JUN-2010", "Spring Bank Holiday");

		lh.addStaticHoliday ("02-AUG-2010", "Summer Bank Holiday");

		lh.addStaticHoliday ("25-OCT-2010", "Autumn Bank Holiday");

		lh.addStaticHoliday ("27-DEC-2010", "Christmas Day Observed");

		lh.addStaticHoliday ("28-DEC-2010", "St. Stephens Day Observed");

		lh.addStaticHoliday ("29-DEC-2010", "Bank Holiday");

		lh.addStaticHoliday ("03-JAN-2011", "New Years Day Observed");

		lh.addStaticHoliday ("17-MAR-2011", "St. Patricks Day");

		lh.addStaticHoliday ("22-APR-2011", "Good Friday");

		lh.addStaticHoliday ("25-APR-2011", "Easter Monday");

		lh.addStaticHoliday ("02-MAY-2011", "Labour Day");

		lh.addStaticHoliday ("06-JUN-2011", "Spring Bank Holiday");

		lh.addStaticHoliday ("01-AUG-2011", "Summer Bank Holiday");

		lh.addStaticHoliday ("31-OCT-2011", "Autumn Bank Holiday");

		lh.addStaticHoliday ("26-DEC-2011", "St. Stephens Day");

		lh.addStaticHoliday ("27-DEC-2011", "Christmas Day Observed");

		lh.addStaticHoliday ("28-DEC-2011", "Bank Holiday");

		lh.addStaticHoliday ("02-JAN-2012", "New Years Day Observed");

		lh.addStaticHoliday ("19-MAR-2012", "St. Patricks Day Observed");

		lh.addStaticHoliday ("06-APR-2012", "Good Friday");

		lh.addStaticHoliday ("09-APR-2012", "Easter Monday");

		lh.addStaticHoliday ("07-MAY-2012", "Labour Day");

		lh.addStaticHoliday ("04-JUN-2012", "Spring Bank Holiday");

		lh.addStaticHoliday ("06-AUG-2012", "Summer Bank Holiday");

		lh.addStaticHoliday ("29-OCT-2012", "Autumn Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2012", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2012", "St. Stephens Day");

		lh.addStaticHoliday ("27-DEC-2012", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2013", "New Years Day");

		lh.addStaticHoliday ("18-MAR-2013", "St. Patricks Day Observed");

		lh.addStaticHoliday ("29-MAR-2013", "Good Friday");

		lh.addStaticHoliday ("01-APR-2013", "Easter Monday");

		lh.addStaticHoliday ("06-MAY-2013", "Labour Day");

		lh.addStaticHoliday ("03-JUN-2013", "Spring Bank Holiday");

		lh.addStaticHoliday ("05-AUG-2013", "Summer Bank Holiday");

		lh.addStaticHoliday ("28-OCT-2013", "Autumn Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2013", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2013", "St. Stephens Day");

		lh.addStaticHoliday ("27-DEC-2013", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2014", "New Years Day");

		lh.addStaticHoliday ("17-MAR-2014", "St. Patricks Day");

		lh.addStaticHoliday ("18-APR-2014", "Good Friday");

		lh.addStaticHoliday ("21-APR-2014", "Easter Monday");

		lh.addStaticHoliday ("05-MAY-2014", "Labour Day");

		lh.addStaticHoliday ("02-JUN-2014", "Spring Bank Holiday");

		lh.addStaticHoliday ("04-AUG-2014", "Summer Bank Holiday");

		lh.addStaticHoliday ("27-OCT-2014", "Autumn Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2014", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2014", "St. Stephens Day");

		lh.addStaticHoliday ("29-DEC-2014", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2015", "New Years Day");

		lh.addStaticHoliday ("17-MAR-2015", "St. Patricks Day");

		lh.addStaticHoliday ("03-APR-2015", "Good Friday");

		lh.addStaticHoliday ("06-APR-2015", "Easter Monday");

		lh.addStaticHoliday ("04-MAY-2015", "Labour Day");

		lh.addStaticHoliday ("01-JUN-2015", "Spring Bank Holiday");

		lh.addStaticHoliday ("03-AUG-2015", "Summer Bank Holiday");

		lh.addStaticHoliday ("26-OCT-2015", "Autumn Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2015", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-2015", "St. Stephens Day Observed");

		lh.addStaticHoliday ("29-DEC-2015", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2016", "New Years Day");

		lh.addStaticHoliday ("17-MAR-2016", "St. Patricks Day");

		lh.addStaticHoliday ("25-MAR-2016", "Good Friday");

		lh.addStaticHoliday ("28-MAR-2016", "Easter Monday");

		lh.addStaticHoliday ("02-MAY-2016", "Labour Day");

		lh.addStaticHoliday ("06-JUN-2016", "Spring Bank Holiday");

		lh.addStaticHoliday ("01-AUG-2016", "Summer Bank Holiday");

		lh.addStaticHoliday ("31-OCT-2016", "Autumn Bank Holiday");

		lh.addStaticHoliday ("26-DEC-2016", "St. Stephens Day");

		lh.addStaticHoliday ("27-DEC-2016", "Christmas Day Observed");

		lh.addStaticHoliday ("28-DEC-2016", "Bank Holiday");

		lh.addStaticHoliday ("02-JAN-2017", "New Years Day Observed");

		lh.addStaticHoliday ("17-MAR-2017", "St. Patricks Day");

		lh.addStaticHoliday ("14-APR-2017", "Good Friday");

		lh.addStaticHoliday ("17-APR-2017", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2017", "Labour Day");

		lh.addStaticHoliday ("05-JUN-2017", "Spring Bank Holiday");

		lh.addStaticHoliday ("07-AUG-2017", "Summer Bank Holiday");

		lh.addStaticHoliday ("30-OCT-2017", "Autumn Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2017", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2017", "St. Stephens Day");

		lh.addStaticHoliday ("27-DEC-2017", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2018", "New Years Day");

		lh.addStaticHoliday ("19-MAR-2018", "St. Patricks Day Observed");

		lh.addStaticHoliday ("30-MAR-2018", "Good Friday");

		lh.addStaticHoliday ("02-APR-2018", "Easter Monday");

		lh.addStaticHoliday ("07-MAY-2018", "Labour Day");

		lh.addStaticHoliday ("04-JUN-2018", "Spring Bank Holiday");

		lh.addStaticHoliday ("06-AUG-2018", "Summer Bank Holiday");

		lh.addStaticHoliday ("29-OCT-2018", "Autumn Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2018", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2018", "St. Stephens Day");

		lh.addStaticHoliday ("27-DEC-2018", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2019", "New Years Day");

		lh.addStaticHoliday ("18-MAR-2019", "St. Patricks Day Observed");

		lh.addStaticHoliday ("19-APR-2019", "Good Friday");

		lh.addStaticHoliday ("22-APR-2019", "Easter Monday");

		lh.addStaticHoliday ("06-MAY-2019", "Labour Day");

		lh.addStaticHoliday ("03-JUN-2019", "Spring Bank Holiday");

		lh.addStaticHoliday ("05-AUG-2019", "Summer Bank Holiday");

		lh.addStaticHoliday ("28-OCT-2019", "Autumn Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2019", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2019", "St. Stephens Day");

		lh.addStaticHoliday ("27-DEC-2019", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2020", "New Years Day");

		lh.addStaticHoliday ("17-MAR-2020", "St. Patricks Day");

		lh.addStaticHoliday ("10-APR-2020", "Good Friday");

		lh.addStaticHoliday ("13-APR-2020", "Easter Monday");

		lh.addStaticHoliday ("04-MAY-2020", "Labour Day");

		lh.addStaticHoliday ("01-JUN-2020", "Spring Bank Holiday");

		lh.addStaticHoliday ("03-AUG-2020", "Summer Bank Holiday");

		lh.addStaticHoliday ("26-OCT-2020", "Autumn Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2020", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-2020", "St. Stephens Day Observed");

		lh.addStaticHoliday ("29-DEC-2020", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2021", "New Years Day");

		lh.addStaticHoliday ("17-MAR-2021", "St. Patricks Day");

		lh.addStaticHoliday ("02-APR-2021", "Good Friday");

		lh.addStaticHoliday ("05-APR-2021", "Easter Monday");

		lh.addStaticHoliday ("03-MAY-2021", "Labour Day");

		lh.addStaticHoliday ("07-JUN-2021", "Spring Bank Holiday");

		lh.addStaticHoliday ("02-AUG-2021", "Summer Bank Holiday");

		lh.addStaticHoliday ("25-OCT-2021", "Autumn Bank Holiday");

		lh.addStaticHoliday ("27-DEC-2021", "Christmas Day Observed");

		lh.addStaticHoliday ("28-DEC-2021", "St. Stephens Day Observed");

		lh.addStaticHoliday ("29-DEC-2021", "Bank Holiday");

		lh.addStaticHoliday ("03-JAN-2022", "New Years Day Observed");

		lh.addStaticHoliday ("17-MAR-2022", "St. Patricks Day");

		lh.addStaticHoliday ("15-APR-2022", "Good Friday");

		lh.addStaticHoliday ("18-APR-2022", "Easter Monday");

		lh.addStaticHoliday ("02-MAY-2022", "Labour Day");

		lh.addStaticHoliday ("06-JUN-2022", "Spring Bank Holiday");

		lh.addStaticHoliday ("01-AUG-2022", "Summer Bank Holiday");

		lh.addStaticHoliday ("31-OCT-2022", "Autumn Bank Holiday");

		lh.addStaticHoliday ("26-DEC-2022", "St. Stephens Day");

		lh.addStaticHoliday ("27-DEC-2022", "Christmas Day Observed");

		lh.addStaticHoliday ("28-DEC-2022", "Bank Holiday");

		lh.addStaticHoliday ("02-JAN-2023", "New Years Day Observed");

		lh.addStaticHoliday ("17-MAR-2023", "St. Patricks Day");

		lh.addStaticHoliday ("07-APR-2023", "Good Friday");

		lh.addStaticHoliday ("10-APR-2023", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2023", "Labour Day");

		lh.addStaticHoliday ("05-JUN-2023", "Spring Bank Holiday");

		lh.addStaticHoliday ("07-AUG-2023", "Summer Bank Holiday");

		lh.addStaticHoliday ("30-OCT-2023", "Autumn Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2023", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2023", "St. Stephens Day");

		lh.addStaticHoliday ("27-DEC-2023", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2024", "New Years Day");

		lh.addStaticHoliday ("18-MAR-2024", "St. Patricks Day Observed");

		lh.addStaticHoliday ("29-MAR-2024", "Good Friday");

		lh.addStaticHoliday ("01-APR-2024", "Easter Monday");

		lh.addStaticHoliday ("06-MAY-2024", "Labour Day");

		lh.addStaticHoliday ("03-JUN-2024", "Spring Bank Holiday");

		lh.addStaticHoliday ("05-AUG-2024", "Summer Bank Holiday");

		lh.addStaticHoliday ("28-OCT-2024", "Autumn Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2024", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2024", "St. Stephens Day");

		lh.addStaticHoliday ("27-DEC-2024", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2025", "New Years Day");

		lh.addStaticHoliday ("17-MAR-2025", "St. Patricks Day");

		lh.addStaticHoliday ("18-APR-2025", "Good Friday");

		lh.addStaticHoliday ("21-APR-2025", "Easter Monday");

		lh.addStaticHoliday ("05-MAY-2025", "Labour Day");

		lh.addStaticHoliday ("02-JUN-2025", "Spring Bank Holiday");

		lh.addStaticHoliday ("04-AUG-2025", "Summer Bank Holiday");

		lh.addStaticHoliday ("27-OCT-2025", "Autumn Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2025", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2025", "St. Stephens Day");

		lh.addStaticHoliday ("29-DEC-2025", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2026", "New Years Day");

		lh.addStaticHoliday ("17-MAR-2026", "St. Patricks Day");

		lh.addStaticHoliday ("03-APR-2026", "Good Friday");

		lh.addStaticHoliday ("06-APR-2026", "Easter Monday");

		lh.addStaticHoliday ("04-MAY-2026", "Labour Day");

		lh.addStaticHoliday ("01-JUN-2026", "Spring Bank Holiday");

		lh.addStaticHoliday ("03-AUG-2026", "Summer Bank Holiday");

		lh.addStaticHoliday ("26-OCT-2026", "Autumn Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2026", "Christmas Day");

		lh.addStaticHoliday ("28-DEC-2026", "St. Stephens Day Observed");

		lh.addStaticHoliday ("29-DEC-2026", "Bank Holiday");

		lh.addStaticHoliday ("01-JAN-2027", "New Years Day");

		lh.addStaticHoliday ("17-MAR-2027", "St. Patricks Day");

		lh.addStaticHoliday ("26-MAR-2027", "Good Friday");

		lh.addStaticHoliday ("29-MAR-2027", "Easter Monday");

		lh.addStaticHoliday ("03-MAY-2027", "Labour Day");

		lh.addStaticHoliday ("07-JUN-2027", "Spring Bank Holiday");

		lh.addStaticHoliday ("02-AUG-2027", "Summer Bank Holiday");

		lh.addStaticHoliday ("25-OCT-2027", "Autumn Bank Holiday");

		lh.addStaticHoliday ("27-DEC-2027", "Christmas Day Observed");

		lh.addStaticHoliday ("28-DEC-2027", "St. Stephens Day Observed");

		lh.addStaticHoliday ("29-DEC-2027", "Bank Holiday");

		lh.addStaticHoliday ("03-JAN-2028", "New Years Day Observed");

		lh.addStaticHoliday ("17-MAR-2028", "St. Patricks Day");

		lh.addStaticHoliday ("14-APR-2028", "Good Friday");

		lh.addStaticHoliday ("17-APR-2028", "Easter Monday");

		lh.addStaticHoliday ("01-MAY-2028", "Labour Day");

		lh.addStaticHoliday ("05-JUN-2028", "Spring Bank Holiday");

		lh.addStaticHoliday ("07-AUG-2028", "Summer Bank Holiday");

		lh.addStaticHoliday ("30-OCT-2028", "Autumn Bank Holiday");

		lh.addStaticHoliday ("25-DEC-2028", "Christmas Day");

		lh.addStaticHoliday ("26-DEC-2028", "St. Stephens Day");

		lh.addStaticHoliday ("27-DEC-2028", "Bank Holiday");

		lh.addStandardWeekend();

		return lh;
	}
}
