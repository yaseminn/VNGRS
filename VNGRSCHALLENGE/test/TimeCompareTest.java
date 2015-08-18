import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Assert;


public class TimeCompareTest {

	@org.junit.Test
	public void timeCompareTest() throws ParseException {
		TimeCompare time = new TimeCompare("2014-09-20", "2013-04-02", "2014-09-20");
		boolean result = time.isDateInInterval();
	    Assert.assertTrue(result);
	}

}
