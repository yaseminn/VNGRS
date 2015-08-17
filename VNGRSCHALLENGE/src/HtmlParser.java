import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

public class HtmlParser {

	/*
	 * Taking datas given date intervals and saving to WeatherInfo object type.
	 */
	public static List<WeatherInfo> parseHtmlToWeatherInfo(String urlPath,
			String date1, String date2) throws IOException, ParseException {

		List<String> urls = getUrls(urlPath);
		TimeCompare timeComparer = null;
		String value = null;
		String resultDate = null;
		
		Iterator<String> iter = urls.iterator();
		while (iter.hasNext()) {
			value = iter.next();
			resultDate = DateMatcher(value);
			timeComparer = new TimeCompare(resultDate, date1, date2);

			if (!(timeComparer.isDateInInterval())) {
				iter.remove();
			}
		}

		List<String> urlList = extractUrls(urls);
		List<WeatherInfo> weathersList = new ArrayList<>();

		for (String url : urlList) {
			System.out.println("--------- " + url);
			
			weathersList.add(new WeatherInfo(DateMatcher(url),
					getAttributes(getUrlsContent(url), "min"), getAttributes(
							getUrlsContent(url), "max"), getAttributes(
							getUrlsContent(url), "mean")));
			
		}

		for (WeatherInfo weather : weathersList) {
			System.out.println(weather.toString());
		}

		return weathersList;
	}

	/*
	 * Getting information from given url
	 */
	public static List<String> getUrls(String path) throws IOException {

		URL url = new URL(path);
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
		int begin = body.indexOf("<body>");
		int finish = body.indexOf("</body>");

		String[] splittedBody = body
				.substring(begin + "<body>".length(), finish).trim()
				.split("\n");

		System.out.println("Url listesi alındı");
		return new ArrayList<String>(Arrays.asList(splittedBody));
	}

	/*
	 * Taking information from given redirectted url
	 */
	public static String getUrlsContent(String path) throws IOException {

		URL url = new URL(path);
		URLConnection con = url.openConnection();
		String redirect = con.getHeaderField("Location");
		if (redirect != null) {
			con = new URL(redirect).openConnection();
		}
		InputStream in = con.getInputStream();

		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);

		if(!body.equalsIgnoreCase(null)){
			System.out.println("Url içeriği alındı.");
		}
		
		return body;
	}

	/*
	 * Searching dates formats from given string
	 */
	public static String DateMatcher(String url) {

		Pattern datePattern = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})");

		String sResult = null;
		Matcher dateMatcher = datePattern.matcher(url);
		if (dateMatcher.find()) {

			sResult = dateMatcher.group(1) + "-" + dateMatcher.group(2) + "-"
					+ dateMatcher.group(3);
		}

		return sResult;
	}

	/*
	 * Taking inside urls in given urlList
	 */
	public static List<String> extractUrls(List<String> urls) {

		List<String> result = new ArrayList<>();
		String urlPattern = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
		Pattern p = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
		Matcher m = null;

		for (String url : urls) {
			m = p.matcher(url);
			if (m.find()) {
				result.add((url.substring(m.start(0), m.end(0))));
			}
			System.out.println(url);
		}

		return result;
	}

	public static String getAttributes(String htmlContent, String searchText) {
		String result  = htmlContent.split("<b>" + searchText + "</b>")[1]
				.split("<span>")[1].replaceAll("\\D+", "");
		
		System.out.println(htmlContent +" - " +searchText +":" +result  );
		
		return result;

	}

}
