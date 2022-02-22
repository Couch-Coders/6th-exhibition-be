package couch.exhibition.controller;

import couch.exhibition.dto.ExhibitionDto;
import couch.exhibition.dto.LikesDTO;
import couch.exhibition.dto.ReviewResponseDTO;
import couch.exhibition.entity.Exhibition;
import couch.exhibition.exception.CustomException;
import couch.exhibition.exception.ErrorCode;
import couch.exhibition.service.ExhibitionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Page<ExhibitionDto> findByCategory(@RequestParam(value = "city", required = false) String city,
                                           @RequestParam(value = "area", required = false) String area,
                                           @RequestParam(value = "keyword", required = false) String keyword,
                                           @PageableDefault(sort = "end_date", direction = Sort.Direction.ASC) Pageable pageable){

        log.info("/ 통과-1");
        Page<ExhibitionDto> progressExhibition = exhibitionService.findByProgress(pageable).map(exhibition-> new ExhibitionDto(exhibition));
        log.info("/ 통과0");
        Page<ExhibitionDto> list;
        List<ExhibitionDto> returnList = new ArrayList<>();

        log.info("/ 통과1");

        if(city != null && area != null && keyword != null){
            log.info("findByAllCategory(city, area, keyword) 통과");
            list= exhibitionService.findByAllCategory(city, area, keyword, pageable).map(exhibition-> new ExhibitionDto(exhibition));

        }
        else if(city != null && area != null && keyword == null ) {
            log.info("findByCityAndArea(city, area) 통과");
            list= exhibitionService.findByCityAndArea(city, area, pageable).map(exhibition-> new ExhibitionDto(exhibition));
        }
        else if(city != null && area == null && keyword == null) {
            log.info("findByCity(city) 통과");
            list= exhibitionService.findByCity(city, pageable).map(exhibition-> new ExhibitionDto(exhibition));
        }
        else if(city == null && area != null && keyword != null) {
            log.info("findByAreaAndKeyword(area, keyword) 통과");
            list= exhibitionService.findByAreaAndKeyword(area, keyword, pageable).map(exhibition-> new ExhibitionDto(exhibition));
        }
        else if(city == null && area != null && keyword == null) {
            log.info("exhibitionService.findByArea(area) 통과");
            list= exhibitionService.findByArea(area, pageable).map(exhibition-> new ExhibitionDto(exhibition));
        }
        else if(city != null && area == null && keyword != null){
            log.info("findByCityAndKeyword(city, keyword) 통과");
            list= exhibitionService.findByCityAndKeyword(city, keyword, pageable).map(exhibition-> new ExhibitionDto(exhibition));
        }
        else if(city == null && area == null && keyword != null) {
            log.info("findByKeyword(keyword) 통과");
            list= exhibitionService.findByKeyword(keyword, pageable).map(exhibition-> new ExhibitionDto(exhibition));
        }
        else {
            log.info("findByProgress 통과");
            list= exhibitionService.findByProgress(pageable).map(exhibition-> new ExhibitionDto(exhibition));
        }

        for(ExhibitionDto exh : progressExhibition) {
            for(ExhibitionDto listExh : list){
                if (exh.equals(listExh)) {
                    returnList.add(exh);
                }
            }
        }

        if(returnList != null) return (Page<ExhibitionDto>) returnList;
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
