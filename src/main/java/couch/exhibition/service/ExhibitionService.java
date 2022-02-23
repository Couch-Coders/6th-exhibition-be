package couch.exhibition.service;

import couch.exhibition.entity.Exhibition;
import couch.exhibition.repository.ExhibitionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class ExhibitionService {

    private final ExhibitionRepository exhibitionRepository;

    Pageable sortedByExhibitionsDescStartDateAsc =
            PageRequest.of(1, 10, Sort.by("endDate").descending());

    @Autowired
    public ExhibitionService(ExhibitionRepository exhibitionRepository) {
        this.exhibitionRepository = exhibitionRepository;
    }

    public List<Exhibition> findAllExhibitions(Pageable pageable){
        return exhibitionRepository.findAllExhibitions(pageable);
    }

    public List<Exhibition> findByKeyword(String keyword,Pageable pageable){
       return exhibitionRepository.findByKeyword(keyword, pageable);
    }

    public List<Exhibition> findByProgress(){
       return exhibitionRepository.findByProgress();
    }

    public List<Exhibition> findByCity(String city,Pageable pageable){
        return exhibitionRepository.findByCity(city, pageable);
    }

    public List<Exhibition> findByArea(String area,Pageable pageable){
        return exhibitionRepository.findByArea(area, pageable);
    }

    public List<Exhibition> findByCityAndArea(String city, String area,Pageable pageable){
        return exhibitionRepository.findByCityAndArea(city, area, pageable);
    }

    public Page<Exhibition> findAll(Pageable pageable){
        return exhibitionRepository.findAll(pageable);
    }
    public List<Exhibition> findByAllCategory(String city, String area, String keyword, Pageable pageable){
        return exhibitionRepository.findByAllCategory(city, area, keyword, pageable);
    }

    public List<Exhibition> findByAreaAndKeyword(String area, String keyword,Pageable pageable){
        return exhibitionRepository.findByAreaAndKeyword(area, keyword, pageable);
    }

    public List<Exhibition> findByCityAndKeyword(String city, String keyword,Pageable pageable){
        return exhibitionRepository.findByCityAndKeyword(city, keyword, pageable);
    }

    public Optional<Exhibition> findById(Long id){
        return exhibitionRepository.findById(id);
    }

    public List<Exhibition> findTop10ByLikeCnt(){
        return exhibitionRepository.findTop10ByOrderByLikeCntDesc();
    }

}

