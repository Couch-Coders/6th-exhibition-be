package couch.exhibition.service;

import couch.exhibition.entity.Exhibition;
import couch.exhibition.repository.ExhibitionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExhibitionService {

    private ExhibitionRepository exhibitionRepository;

    public ExhibitionService(ExhibitionRepository exhibitionRepository) {
        this.exhibitionRepository = exhibitionRepository;
    }

    @Transactional
    public Exhibition findExhibitionById(Long exhibitionId) {
        return exhibitionRepository.getById(exhibitionId);
    } // static 아님, 전시 api 작성 필요
}

