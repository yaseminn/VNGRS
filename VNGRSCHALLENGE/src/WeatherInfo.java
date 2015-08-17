

public class WeatherInfo {
	private String date;
	private String min;
	private String max;
	private String mean;

	public WeatherInfo(String date, String min, String max, String mean) {
		this.date = date;
		this.min = min;
		this.max = max;
		this.mean = mean;
	}

	public String getDate() {
		return date;
	}

	public String getMax() {
		return max;
	}

	public String getMin() {
		return min;
	}

	public String getMean() {
		return mean;
	}

	@Override
	public String toString() {
		return date + " min: " + min + " max: " + max + " mean: " + mean;
	}
}
