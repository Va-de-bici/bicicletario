/*package vadebici.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Segredos {
    private Segredos(){}
    private static Properties properties;
    private static final String EMAIL_API_KEY = "EMAIL_API_KEY";
    private static final String EMAIL_DOMAIN = "EMAIL_DOMAIN";

    static {
        String pathName = "segredos.properties";
        Path path = Paths.get(pathName);
        if (Files.exists(path)) {
            try {
                FileInputStream fileInputStream = new FileInputStream(pathName);
                properties = new Properties();
                properties.load(fileInputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String getEmailApiKey() {
        String emailApiKey = System.getenv(EMAIL_API_KEY);
        if (emailApiKey == null) emailApiKey = properties.getProperty(EMAIL_API_KEY);
        return emailApiKey;
    }

    public static String getEmailDomainName() {
        String emailDomainName = System.getenv(EMAIL_DOMAIN);
        if (emailDomainName == null) emailDomainName = properties.getProperty(EMAIL_DOMAIN);
        return emailDomainName;
    }

}

*/