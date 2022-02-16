package couch.exhibition.service;

import couch.exhibition.entity.Exhibition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExhibitionService {

    @Transactional
    public static Exhibition findExhibitionById(Long exhibitionId) {
        return null;
    } // static 아님, 전시 api 작성 필요
}

