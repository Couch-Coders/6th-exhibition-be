package couch.exhibition.service;

import couch.exhibition.entity.Exhibition;
import couch.exhibition.repository.ExhibitionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class ExhibitionService {

    private final ExhibitionRepository exhibitionRepository;

    @Autowired
    public ExhibitionService(ExhibitionRepository exhibitionRepository) {
        this.exhibitionRepository = exhibitionRepository;
    }

    public Page<Exhibition> findByKeyword(String keyword, Pageable pageable){
       return exhibitionRepository.findByKeyword(keyword, pageable);
    }

    public Page<Exhibition> findByProgress(Pageable pageable){
       return exhibitionRepository.findByProgress(pageable);
    }

    public Page<Exhibition> findByCity(String city, Pageable pageable){
        return exhibitionRepository.findByCity(city, pageable);
    }

    public Page<Exhibition> findByArea(String area, Pageable pageable){
        return exhibitionRepository.findByArea(area, pageable);
    }

    public Page<Exhibition> findByCityAndArea(String city, String area, Pageable pageable){
        return exhibitionRepository.findByCityAndArea(city, area, pageable);
    }

    public Page<Exhibition> findAllExhibition(Pageable pageable){
        return exhibitionRepository.findAllExhibition(pageable);
    }
    public Page<Exhibition> findByAllCategory(String city, String area, String keyword, Pageable pageable){
        return exhibitionRepository.findByAllCategory(city, area, keyword, pageable);
    }

    public Page<Exhibition> findByAreaAndKeyword(String area, String keyword, Pageable pageable){
        return exhibitionRepository.findByAreaAndKeyword(area, keyword, pageable);
    }

    public Page<Exhibition> findByCityAndKeyword(String city, String keyword, Pageable pageable){
        return exhibitionRepository.findByCityAndKeyword(city, keyword, pageable);
    }

    public Optional<Exhibition> findById(Long id){
        return exhibitionRepository.findById(id);
    }

    public List<Exhibition> findTop10ByLikeCnt(){
        return exhibitionRepository.findTop10ByOrderByLikeCntDesc();
    }
}

