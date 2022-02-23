package couch.exhibition.controller;

import couch.exhibition.dto.ExhibitionDto;
import couch.exhibition.entity.Exhibition;
import couch.exhibition.exception.CustomException;
import couch.exhibition.exception.ErrorCode;
import couch.exhibition.service.ExhibitionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;
import java.util.*;
import java.util.function.Function;

@Slf4j
@RestController
@RequestMapping("exhibitions/search")
public class ExhibitionController {

    private final ExhibitionService exhibitionService;

    @Autowired
    public ExhibitionController(ExhibitionService exhibitionService) {
        this.exhibitionService = exhibitionService;
    }

    @GetMapping("")
    public List<Exhibition> findByCategory(@RequestParam(value = "city", required = false) String city,
                                           @RequestParam(value = "area", required = false) String area,
                                           @RequestParam(value = "keyword", required = false) String keyword,
                                           @PageableDefault(size = 10, sort = "startDate", direction = Sort.Direction.DESC) Pageable pageable){

        Pageable pageable2 =
                PageRequest.of(0, 10, Sort.by("start_date").descending());


        log.info("/ 통과-1");
        List<Exhibition> progressExhibition = exhibitionService.findByProgress(pageable2);
        log.info("/ 통과0");
        List<Exhibition> list;
        List<Exhibition> returnList = new ArrayList<>();

        log.info("/ 통과1");

        if(city != null && area != null && keyword != null){
            log.info("findByAllCategory(city, area, keyword) 통과");
            list= exhibitionService.findByAllCategory(city, area, keyword, pageable);

        }
        else if(city != null && area != null && keyword == null ) {
            log.info("findByCityAndArea(city, area) 통과");
            list= exhibitionService.findByCityAndArea(city, area, pageable);
        }
        else if(city != null && area == null && keyword == null) {
            log.info("findByCity(city) 통과");
            list= exhibitionService.findByCity(city, pageable);
        }
        else if(city == null && area != null && keyword != null) {
            log.info("findByAreaAndKeyword(area, keyword) 통과");
            list= exhibitionService.findByAreaAndKeyword(area, keyword, pageable);
        }
        else if(city == null && area != null && keyword == null) {
            log.info("exhibitionService.findByArea(area) 통과");
            list= exhibitionService.findByArea(area, pageable);
        }
        else if(city != null && area == null && keyword != null){
            log.info("findByCityAndKeyword(city, keyword) 통과");
            list= exhibitionService.findByCityAndKeyword(city, keyword, pageable);
        }
        else if(city == null && area == null && keyword != null) {
            log.info("findByKeyword(keyword) 통과");
            list= exhibitionService.findByKeyword(keyword, pageable);
        }
        else {
            log.info("findAllExhibitions 통과");
            list = exhibitionService.findAllExhibitions(pageable);
        }

//        for(Exhibition exh : list){
//            if(progressExhibition.contains(exh)){
//                returnList.add(exh);
//            }
//        }

        if(list != null) return list;
        else throw new CustomException(ErrorCode.NOT_FOUND_EXHIBITION);
    }

    @GetMapping("/{exhibitionId}")
    public Optional<Exhibition> exhibitionDetails(@PathVariable Long exhibitionId) {
        return exhibitionService.findById(exhibitionId);
    }

    @GetMapping("/top10")
    public List<Exhibition> exhibitionsTop10(){
        return exhibitionService.findTop10ByLikeCnt();
    }
}
