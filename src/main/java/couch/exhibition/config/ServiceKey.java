package couch.exhibition.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceKey {

    @Value("${service-key}")
    private String key;

    private static ServiceKey instance = new ServiceKey();

    private ServiceKey() {
    }

    public static ServiceKey getInstance() {
        if (instance == null) {
            instance = new ServiceKey();
        }
        return instance;
    }
}
