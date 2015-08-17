import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadConfigFile {

	ReadConfigFile(){
		
	}
	
	public String getProperty(String property) throws IOException {
		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("configvngrs.properties");

			// load a properties file
			prop.load(input);

			// get the property value and print it out
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return prop.getProperty(property);
	}
}
