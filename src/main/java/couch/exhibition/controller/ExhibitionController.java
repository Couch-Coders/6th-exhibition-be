package couch.exhibition.controller;

import couch.exhibition.entity.Exhibition;
import couch.exhibition.exception.CustomException;
import couch.exhibition.exception.ErrorCode;
import couch.exhibition.service.ExhibitionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Api(tags = {"Exhibition search service"})
@Slf4j
@RestController
@RequestMapping("/exhibitions/search")
public class ExhibitionController {

    private final ExhibitionService exhibitionService;

    @Autowired
    public ExhibitionController(ExhibitionService exhibitionService) {
        this.exhibitionService = exhibitionService;
    }

    @ApiOperation(value = "Exhibition Search", notes ="다양한 카테고리로 검색 가능")
    @GetMapping("")
    public List<Exhibition> findByCategory(@RequestParam(value = "city", required = false) String city,
                                           @RequestParam(value = "area", required = false) String area,
                                           @RequestParam(value = "keyword", required = false) String keyword,
                                           @RequestParam(value = "today", required = false) String today,
                                           @PageableDefault(size = 100, sort = "startDate", direction = Sort.Direction.DESC) Pageable pageable){


       today  = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
       int todayToInt = Integer.parseInt(today);

        List<Exhibition> list = new ArrayList<>();

        if(city.equals("전체") && keyword == null){
            list = exhibitionService.findAllExhibitions(todayToInt, pageable);
        }
        else if(city.equals("전체") && keyword != null){
            list = exhibitionService.findByKeyword(keyword, todayToInt, pageable);
        }
        else if(area.equals("전체") && keyword == null){
            list = exhibitionService.findByCity(city,todayToInt, pageable);
        }
        else if(area.equals("전체") && keyword != null){
            list = exhibitionService.findByCityAndKeyword(city, keyword, todayToInt, pageable);
        }
        else if(area != null && keyword != null){
            log.info("findByAllCategory(city, area, keyword) 통과");
            list= exhibitionService.findByAllCategory(city, area, keyword, todayToInt, pageable);
        }
        else if(area != null && keyword == null) {
            log.info("findByCityAndArea(city, area) 통과");
            list= exhibitionService.findByCityAndArea(city, area, todayToInt, pageable);
        }
//        else if(keyword == null) {
//            log.info("findByCity(city) 통과");
//            list= exhibitionService.findByCity(city, todayToInt,pageable);
//        }
//        else {
//            log.info("findByCityAndKeyword(city, keyword) 통과");
//            list= exhibitionService.findByCityAndKeyword(city, keyword,todayToInt, pageable);
//        }

        if(list != null) return list;
        else throw new CustomException(ErrorCode.NOT_FOUND_EXHIBITION);
    }

    @ApiOperation(value = "Exhibition ID search", notes = "전시 ID로 전시회 검색")
    @GetMapping("/{exhibitionId}")
    public Optional<Exhibition> exhibitionDetails(@PathVariable Long exhibitionId) {
        return exhibitionService.findById(exhibitionId);
    }

    @ApiOperation(value = "Exhibition likes count Top10", notes ="좋아요 수에 따른 전시회 TOP10 조회")
    @GetMapping("/top10")
    public List<Exhibition> exhibitionsTop10(@RequestParam(value = "today", required = false) String today){
        today  = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int todayToInt = Integer.parseInt(today);
        List<Exhibition> ExhibitionList= exhibitionService.findTop10ByLikeCnt(todayToInt);

        List<Exhibition> Exhibition10List = new ArrayList<>();
        for(int i=0; i<10; i++){
            Exhibition10List.add(ExhibitionList.get(i));
        }

        return Exhibition10List;
    }
}
