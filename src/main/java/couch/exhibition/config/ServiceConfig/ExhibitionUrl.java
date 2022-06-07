package couch.exhibition.config.ServiceConfig;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ExhibitionUrl {

    @Value("${exhibitionUrl}")
    public String exhibitionUrl;
}
