package couch.exhibition.service;

import couch.exhibition.entity.Exhibition;
import couch.exhibition.repository.ExhibitionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

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

//    public List<Exhibition> findByCategory( String city,String area,String keyword) {
//
//        List<Exhibition> progressExhibition = exhibitionRepository.findByProgress();
//        List<Exhibition> list;
//        List<Exhibition> returnList = null;
//
//        if (city != null && area != null && keyword != null)
//            list= exhibitionRepository.findByAllCategory(city, area, keyword);
//        else if (city != null && area != null && keyword == null)
//            list= exhibitionRepository.findByCityAndArea(city, area);
//        else if (city != null && area == null && keyword == null)
//            list= exhibitionRepository.findByCity(city);
//        else if (city == null && area != null && keyword != null)
//            list= exhibitionRepository.findByAreaAndKeyword(area, keyword);
//        else if (city == null && area != null && keyword == null)
//            list= exhibitionRepository.findByArea(area);
//        else if (city != null && area == null && keyword != null)
//            list= exhibitionRepository.findByCityAndKeyword(city, keyword);
//        else if (city == null && area == null && keyword != null)
//            list= exhibitionRepository.findByKeyword(keyword);
//        else list= exhibitionRepository.findByProgress();
//
//        for(Exhibition exh : list){
//            if(progressExhibition.contains(exh)) returnList.add(exh);
//        }
//
//        return list;
//    }
}
