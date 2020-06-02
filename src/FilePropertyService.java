import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class FilePropertyService implements PropertyService {

    private Properties properties = new Properties();

    public FilePropertyService() {

        try {
            properties.load(new FileReader("prop.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getByName(String propertyName) {
        return properties.getProperty(propertyName);
    }
}
