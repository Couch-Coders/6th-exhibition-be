package couch.exhibition.service;

import couch.exhibition.repository.ExhibitionRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class ExhibitionService {

    public final ExhibitionRepository exhibitionRepository;

    public ExhibitionService(ExhibitionRepository exhibitionRepository) {
        this.exhibitionRepository = exhibitionRepository;
    }
}
