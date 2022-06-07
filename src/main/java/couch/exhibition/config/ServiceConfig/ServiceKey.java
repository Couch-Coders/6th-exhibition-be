package couch.exhibition.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ServiceKey {

    @Value("${serviceKey}")
    public String serviceKey;
}
