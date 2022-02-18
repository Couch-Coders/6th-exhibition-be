package couch.exhibition.service;

import couch.exhibition.entity.Exhibition;
import couch.exhibition.repository.ExhibitionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Service
public class ExhibitionService {

    private final ExhibitionRepository exhibitionRepository;

    @Autowired
    public ExhibitionService(ExhibitionRepository exhibitionRepository) {
        this.exhibitionRepository = exhibitionRepository;
    }

    public List<Exhibition> findByKeyword(String keyword){
       return exhibitionRepository.findByKeyword(keyword);
    }

    public List<Exhibition> findByProgress(){
       return exhibitionRepository.findByProgress();
    }

    public List<Exhibition> findByCity(String city){
        return exhibitionRepository.findByCity(city);
    }

    public List<Exhibition> findByArea(String area){
        return exhibitionRepository.findByArea(area);
    }

    public List<Exhibition> findByCityAndArea(String city, String area){
        return exhibitionRepository.findByCityAndArea(city, area);
    }

    public List<Exhibition> findAllExhibition(){
        return exhibitionRepository.findAllExhibition();
    }
    public List<Exhibition> findByAllCategory(String city, String area, String keyword){
        return exhibitionRepository.findByAllCategory(city, area, keyword);
    }

    public List<Exhibition> findByAreaAndKeyword(String area, String keyword){
        return exhibitionRepository.findByAreaAndKeyword(area, keyword);
    }

    public List<Exhibition> findByCityAndKeyword(String city, String keyword){
        return exhibitionRepository.findByCityAndKeyword(city, keyword);
    }

    public Optional<Exhibition> findById(Long id){
        return exhibitionRepository.findById(id);
    }
}
