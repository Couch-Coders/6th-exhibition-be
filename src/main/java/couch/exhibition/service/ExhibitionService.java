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

    public List<Exhibition> findAllExhibitions(int today, Pageable pageable){
        return exhibitionRepository.findAllExhibitions(today, pageable);
    }

    public List<Exhibition> findByKeyword(String keyword,int today, Pageable pageable){
       return exhibitionRepository.findByKeyword(keyword, today, pageable);
    }

    public List<Exhibition> findByCity(String city,int today,Pageable pageable){
        return exhibitionRepository.findByCity(city, today, pageable);
    }

    public List<Exhibition> findByArea(String area,int today,Pageable pageable){
        return exhibitionRepository.findByArea(area,today, pageable);
    }

    public List<Exhibition> findByCityAndArea(String city, String area,int today,Pageable pageable){
        return exhibitionRepository.findByCityAndArea(city, area, today,pageable);
    }

    public Page<Exhibition> findAll(Pageable pageable){
        return exhibitionRepository.findAll(pageable);
    }
    public List<Exhibition> findByAllCategory(String city, String area, String keyword, int today,Pageable pageable){
        return exhibitionRepository.findByAllCategory(city, area, keyword, today,pageable);
    }

    public List<Exhibition> findByAreaAndKeyword(String area, String keyword,int today,Pageable pageable){
        return exhibitionRepository.findByAreaAndKeyword(area, keyword, today,pageable);
    }

    public List<Exhibition> findByCityAndKeyword(String city, String keyword,int today,Pageable pageable){
        return exhibitionRepository.findByCityAndKeyword(city, keyword, today,pageable);
    }

    public Optional<Exhibition> findById(Long id){
        return exhibitionRepository.findById(id);
    }

    public List<Exhibition> findTop10ByLikeCnt(){
        return exhibitionRepository.findTop10ByOrderByLikeCntDesc();
    }

}

