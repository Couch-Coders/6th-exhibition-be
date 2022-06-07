//package couch.exhibition.service.DBService;
//
//import couch.exhibition.config.ServiceConfig.RealmUrl;
//import couch.exhibition.config.ServiceConfig.ServiceKey;
//import lombok.extern.slf4j.Slf4j;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.net.URL;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//
//@Slf4j
//@Component
//@Transactional(noRollbackFor = Exception.class)
//public class ExhibitionDBServiceTemp {
//
//    private final ServiceKey SERVICE_KEY;
//    private final RealmUrl REALM_URL;
//    private final ExhibitionDataGetServiceTemp exhibitionDataGetService;
//    private final ExhibitionMsgBodyGetServiceTemp exhibitionMsgBodyGetService;
//
//    public ExhibitionDBServiceTemp(ServiceKey SERVICE_KEY, RealmUrl REALM_URL,
//                                   ExhibitionDataGetServiceTemp exhibitionDataGetService, ExhibitionMsgBodyGetServiceTemp exhibitionMsgBodyGetService) {
//        this.SERVICE_KEY = SERVICE_KEY;
//        this.REALM_URL = REALM_URL;
//        this.exhibitionDataGetService = exhibitionDataGetService;
//        this.exhibitionMsgBodyGetService = exhibitionMsgBodyGetService;
//    }
//
//    @Scheduled(cron = "${regular.cron}")
//    public void load_save() {
//        String serviceKey = SERVICE_KEY.getServiceKey();
//        String realmUrl = REALM_URL.getRealmUrl();
//
//        int seqtotalCount = 0;
//        int pageNo = 1;
//        while (true){
//            URL url = null;
//            try {
//                url = new URL(realmUrl +
//                        "serviceKey=" + serviceKey +
//                        "&to=" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) +
//                        "&realmCode=D000" +
//                        "&cPage=" + pageNo
//                );
//            } catch (Exception e) {
//                log.info("Exception", e);
//            }
//
//            JSONObject msgBody = exhibitionMsgBodyGetService.getMsgBody(url);
//
//            JSONArray perforList = (JSONArray) msgBody.get("perforList");
//
//            int totalCount = (int) msgBody.get("totalCount");
//
//            List<Integer> seqList = new ArrayList<>();
//            for (int index = 0; index < perforList.length(); index++) {
//                Integer sequence = (Integer) perforList.getJSONObject(index).get("seq");
//                seqList.add(sequence);
//            }
//
//            exhibitionDataGetService.getDBData(seqList);
//
//            seqtotalCount += seqList.size();
//
//            if(seqtotalCount == totalCount) break;
//
//            pageNo++;
//        }
//    }
//}
//
