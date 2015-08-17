
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WeatherCSVWriter {
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";

	public void writeCsv(List<WeatherInfo> weathersList, String fileName) {

		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter(fileName);

			// Write a new student object list to the CSV file
			for (WeatherInfo weather : weathersList) {
				fileWriter.append(String.valueOf(weather.getDate()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(weather.getMin());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(weather.getMax());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(weather.getMean());
				fileWriter.append(NEW_LINE_SEPARATOR);
			}

			System.out.println("CSV file was created successfully !!!");

		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out
						.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}
	}

}
