package mosaferTest;

import java.time.LocalDate;

public class TestData {

	String ExpectedLang = "en";
	String ExpectedNum = "+966554400000";
	String ExpectedCurrency = "SAR";
	String ExpectedValue = "false";
	int Tomorrow = LocalDate.now().plusDays(1).getDayOfMonth();
	String ExpectedDeparture = String.format("%01d", Tomorrow);
	 int DayAfterTomorrow = LocalDate.now().plusDays(2).getDayOfMonth();
	    String ExpectedReturn = String.format("%02d", DayAfterTomorrow);  
	    boolean ExpectedFinshingSearchHotel = true; 

}
