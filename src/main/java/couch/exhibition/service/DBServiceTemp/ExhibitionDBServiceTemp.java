//package couch.exhibition.service.DBServiceTemp;
//
//import couch.exhibition.config.ServiceConfig.ExhibitionUrl;
//import couch.exhibition.config.ServiceConfig.RealmUrl;
//import couch.exhibition.config.ServiceConfig.ServiceKey;
//import couch.exhibition.entity.Exhibition;
//import couch.exhibition.exception.DBException.DBCustomException;
//import couch.exhibition.repository.ExhibitionRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.json.XML;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.math.BigDecimal;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.nio.charset.StandardCharsets;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//
//@Slf4j
//@Service
//public class ExhibitionDBServiceTemp {
//
//    private final ServiceKey SERVICE_KEY;
//    private final RealmUrl REALM_URL;
//    private final ExhibitionUrl EXHIBITION_URL;
//    private final ExhibitionRepository exhibitionRepository;
//
//    public ExhibitionDBServiceTemp(ServiceKey SERVICE_KEY, RealmUrl REALM_URL,
//                                   ExhibitionUrl EXHIBITION_URL, ExhibitionRepository exhibitionRepository) {
//        this.SERVICE_KEY = SERVICE_KEY;
//        this.REALM_URL = REALM_URL;
//        this.EXHIBITION_URL = EXHIBITION_URL;
//        this.exhibitionRepository = exhibitionRepository;
//    }
//
//    @Transactional
//    @Scheduled(cron = "${regular.cron}", zone = "Asia/Seoul")
//    public void load_save() {
//        String serviceKey = SERVICE_KEY.getServiceKey();
//        String realmUrl = REALM_URL.getRealmUrl();
//
//        int seqtotalCount = 0;
//        int pageNo = 1;
//        while (true){
//            URL url;
//            try {
//                url = new URL(realmUrl +
//                        "serviceKey=" + serviceKey +
//                        "&to=" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) +
//                        "&realmCode=D000" +
//                        "&cPage=" + pageNo
//                );
//            } catch (MalformedURLException e) {
//                log.warn("malformed URL", e);
//                throw new DBCustomException("malformed URL");
//            }
//
//            JSONObject msgBody = getMsgBody(url);
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
//            getDBData(seqList);
//
//            seqtotalCount += seqList.size();
//
//            if(seqtotalCount == totalCount) break;
//
//            pageNo++;
//        }
//    }
//
//    private void getDBData(List<Integer> seqList) {
//        String serviceKey = SERVICE_KEY.getServiceKey();
//        String exhibitionUrl = EXHIBITION_URL.getExhibitionUrl();
//
//        for (Integer seq : seqList) {
//            try {
//                URL url = new URL(exhibitionUrl + "serviceKey=" + serviceKey + "&seq=" + seq);
//
//                JSONObject msgBody = getMsgBody(url);
//
//                JSONObject perforInfo = (JSONObject) msgBody.get("perforInfo");
//                log.info(String.valueOf(perforInfo.get("url")));
//
//                Exhibition infoObj = new Exhibition(String.valueOf(perforInfo.get("title")),
//                        String.valueOf(perforInfo.get("place")), String.valueOf(perforInfo.get("placeAddr")),
//                        (BigDecimal) perforInfo.get("gpsX"), (BigDecimal) perforInfo.get("gpsY"),
//                        (Integer) perforInfo.get("startDate"), (Integer) perforInfo.get("endDate"),
//                        String.valueOf(perforInfo.get("url")), String.valueOf(perforInfo.get("price")),
//                        String.valueOf(perforInfo.get("placeUrl")), String.valueOf(perforInfo.get("imgUrl")), 0);
//
//                if (exhibitionRepository.findByTitle(infoObj.getTitle()).isEmpty()) {
//                    exhibitionRepository.save(infoObj);
//                }
//            } catch (ClassCastException e) {
//                log.warn("gps coordinates are null", e);
//            } catch (MalformedURLException e) {
//                log.warn("malformed URL", e);
//                throw new DBCustomException("malformed URL");
//            } catch (Exception e) {
//                log.warn("Unexpected Exception", e);
//            }
//        }
//    }
//
//    private JSONObject getMsgBody(URL url) {
//        BufferedReader bf;
//        StringBuilder resultBuilder = new StringBuilder();
//
//        try {
//            bf = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), StandardCharsets.UTF_8));
//            String temp = bf.readLine();
//            while (temp != null) {
//                resultBuilder.append(temp);
//                temp = bf.readLine();
//            }
//        } catch (IOException e) {
//            log.warn("URL IOException", e);
//            throw new DBCustomException("URL IOException");
//        }
//
//        String result = resultBuilder.toString();
////        log.info(result);
//        JSONObject resultJsonObject = XML.toJSONObject(result);
//
//        JSONObject response = (JSONObject) resultJsonObject.get("response");
//        return (JSONObject) response.get("msgBody");
//    }
//}