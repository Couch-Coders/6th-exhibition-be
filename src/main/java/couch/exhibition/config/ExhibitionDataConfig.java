package couch.exhibition.config;

import couch.exhibition.repository.ExhibitionRepository;
import couch.exhibition.service.ExhibitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExhibitionDataConfig {

    private final ExhibitionRepository exhibitionRepository;

    @Autowired
    public ExhibitionDataConfig(ExhibitionRepository exhibitionRepository){
        this.exhibitionRepository = exhibitionRepository;
    }

    @Bean
    public ExhibitionService exhibitionService() {
        return new ExhibitionService(exhibitionRepository);
    }
}
