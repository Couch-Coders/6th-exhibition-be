//package couch.exhibition.service.DBService;
//
//import couch.exhibition.config.ServiceConfig.ExhibitionUrl;
//import couch.exhibition.config.ServiceConfig.ServiceKey;
//import couch.exhibition.entity.Exhibition;
//import couch.exhibition.repository.ExhibitionRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.json.JSONObject;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.net.URL;
//import java.util.List;
//
//@Slf4j
//@Component
//@Transactional(noRollbackFor = Exception.class)
//public class ExhibitionDataGetServiceTemp {
//
//    private final ServiceKey SERVICE_KEY;
//    private final ExhibitionUrl EXHIBITION_URL;
//    private final ExhibitionRepository exhibitionRepository;
//    private final ExhibitionMsgBodyGetServiceTemp exhibitionMsgBodyGetService;
//
//    public ExhibitionDataGetServiceTemp(ServiceKey SERVICE_KEY, ExhibitionUrl EXHIBITION_URL,
//                                        ExhibitionRepository exhibitionRepository, ExhibitionMsgBodyGetServiceTemp exhibitionMsgBodyGetService) {
//        this.SERVICE_KEY = SERVICE_KEY;
//        this.EXHIBITION_URL = EXHIBITION_URL;
//        this.exhibitionRepository = exhibitionRepository;
//        this.exhibitionMsgBodyGetService = exhibitionMsgBodyGetService;
//    }
//
//    public void getDBData(List<Integer> seqList) {
//        String serviceKey = SERVICE_KEY.getServiceKey();
//        String exhibitionUrl = EXHIBITION_URL.getExhibitionUrl();
//
//        for (Integer seq : seqList) {
//            try {
//                URL url = new URL(exhibitionUrl + "serviceKey=" + serviceKey + "&seq=" + seq);
//
//                JSONObject msgBody = exhibitionMsgBodyGetService.getMsgBody(url);
//
//                JSONObject perforInfo = (JSONObject) msgBody.get("perforInfo");
//
//                Exhibition infoObj = new Exhibition((String) perforInfo.get("title"), (String) perforInfo.get("place"), (String) perforInfo.get("placeAddr"),
//                        (BigDecimal) perforInfo.get("gpsX"), (BigDecimal) perforInfo.get("gpsY"),
//                        (Integer) perforInfo.get("startDate"), (Integer) perforInfo.get("endDate"),
//                        (String) perforInfo.get("url"), (String) perforInfo.get("price"),
//                        (String) perforInfo.get("placeUrl"), (String) perforInfo.get("imgUrl"), 0);
//
//                if (exhibitionRepository.findByTitle(infoObj.getTitle()).isEmpty()) {
//                    exhibitionRepository.save(infoObj);
//                }
//            } catch (Exception e) {  // for no rollback
//                log.info("Exception", e);
//            }
//        }
//    }
//}
