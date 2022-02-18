package couch.exhibition.controller;

import couch.exhibition.entity.Exhibition;
import couch.exhibition.exception.CustomException;
import couch.exhibition.exception.ErrorCode;
import couch.exhibition.service.ExhibitionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/exhibitions")
public class ExhibitionController {

    private final ExhibitionService exhibitionService;

    @Autowired
    public ExhibitionController(ExhibitionService exhibitionService) {
        this.exhibitionService = exhibitionService;
    }

    @GetMapping("/search1")
    public List<Exhibition> findByKeyword(@RequestParam("keyword") String keyword){
        log.info("통과");
        return exhibitionService.findByKeyword(keyword);
    }

    @GetMapping("/search2")
    public List<Exhibition> findByProgress(){
        return exhibitionService.findByProgress();
    }

    @GetMapping("/search3")
    public List<Exhibition> findByCity(@RequestParam("city") String city){
        return exhibitionService.findByCity(city);
    }

    @GetMapping("/search4")
    public List<Exhibition> findByArea(@RequestParam("area") String area){
        return exhibitionService.findByArea(area);
    }
//
//    @GetMapping("/search5")
//    public List<Exhibition> findByCityAndArea(@RequestParam(value = "city", required = false) String city, @RequestParam(value = "area", required = false) String area){
//
//        if(city == null) return exhibitionService.findByArea(area);
//        else if(area == null) return exhibitionService.findByCity(city);
//        else if(city == null && area == null) return exhibitionService.findAllExhibition();
//
//        return exhibitionService.findByCityAndArea(city, area);
//    }
//
//    @GetMapping("/")
//    List<Exhibition> findAllExhibition(){
//        return exhibitionService.findAllExhibition();
//    }

    @GetMapping("")
    public List<Exhibition> findByCategory(@RequestParam(value = "city", required = false) String city,
                                    @RequestParam(value = "area", required = false) String area,
                                    @RequestParam(value = "keyword", required = false) String keyword){

        List<Exhibition> progressExhibition = exhibitionService.findByProgress();
        List<Exhibition> list;
        List<Exhibition> returnList = new ArrayList<>();

        log.info("/ 통과1");

        if(city != null && area != null && keyword != null){
            log.info("findByAllCategory(city, area, keyword) 통과");
            list= exhibitionService.findByAllCategory(city, area, keyword);

        }
        else if(city != null && area != null && keyword == null ) {
            log.info("findByCityAndArea(city, area) 통과");
            list= exhibitionService.findByCityAndArea(city, area);
        }
        else if(city != null && area == null && keyword == null) {
            log.info("findByCity(city) 통과");
            list= exhibitionService.findByCity(city);
        }
        else if(city == null && area != null && keyword != null) {
            log.info("findByAreaAndKeyword(area, keyword) 통과");
            list= exhibitionService.findByAreaAndKeyword(area, keyword);
        }
        else if(city == null && area != null && keyword == null) {
            log.info("exhibitionService.findByArea(area) 통과");
            list= exhibitionService.findByArea(area);
        }
        else if(city != null && area == null && keyword != null){
            log.info("findByCityAndKeyword(city, keyword) 통과");
            list= exhibitionService.findByCityAndKeyword(city, keyword);
        }
        else if(city == null && area == null && keyword != null) {
            log.info("findByKeyword(keyword) 통과");
            list= exhibitionService.findByKeyword(keyword);
        }
        else {
            log.info("findByProgress 통과");
            list= exhibitionService.findByProgress();
        }

        for(Exhibition exh : list){
            if(progressExhibition.contains(exh)){
                returnList.add(exh);
            }
        }

        if(returnList != null) return returnList;
        else throw new CustomException(ErrorCode.NOT_FOUND_EXHIBITION);

    }


}
