import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import scala.Tuple2;

public class MonthlyAverageTest {

	private transient JavaSparkContext sc;

	@Before
	public void setUp() {
		sc = new JavaSparkContext("local", "MonthlyAverageTest");
	}

	@After
	public void tearDown() {
		sc.stop();
		sc = null;
	}

	@Test
	public void readCsvTest() {
		String testCsv = "test.csv";
		JavaPairRDD<String, List<Double>> weatherLogs = MonthlyAverage.readCsv(
				sc, testCsv);
		
		String resultStr = "2014-05";
		List<Double> resultList = Arrays.asList(11.0, 13.0, 12.0);

		Assert.assertEquals(resultStr,  weatherLogs.keys().collect().get(0).toString());
		Assert.assertEquals(resultList.toString(),   weatherLogs.values().collect().get(0).toString());
	}
	
	@Test
	public void sumListTest(){
		String testCsv = "testPairs.csv";
		JavaPairRDD<String, List<Double>> weatherLogs = MonthlyAverage.readCsv(
				sc, testCsv);
		String resultStr = "2014-05";
		List<Double> resultList = Arrays.asList(22.0, 24.0, 22.0);
		
		JavaPairRDD<String, List<Double>> sumList = MonthlyAverage.sumList(weatherLogs);

		Assert.assertEquals(resultStr,  sumList.keys().collect().get(0).toString());
		Assert.assertEquals(resultList.toString(), sumList.values().collect().get(0).toString());
	}

	@Test
	public void averageListTest(){
		String testCsv = "testPairs.csv";
		String resultStr = "2014-05";
		List<Double> resultList = Arrays.asList(11.0, 12.0, 11.0);
		
		JavaPairRDD<String, List<Double>> weatherLogs = MonthlyAverage.readCsv(
				sc, testCsv);
		JavaPairRDD<String, List<Double>> sumList = MonthlyAverage.sumList(weatherLogs);
		Map<String, Object> keyCount = weatherLogs.countByKey();
		
		JavaPairRDD<String, List<Double>> averageList = MonthlyAverage.averageList(sumList,keyCount);

		Assert.assertEquals(resultStr,  averageList.keys().collect().get(0).toString());
		Assert.assertEquals(resultList.toString(), averageList.values().collect().get(0).toString());
 
	}
}
