package couch.exhibition.service;

import couch.exhibition.config.ServiceKey;
import couch.exhibition.entity.Exhibition;
import couch.exhibition.repository.ExhibitionRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ExhibitionDBService {

    private final ServiceKey SERVICE_KEY;
    private final ExhibitionRepository exhibitionRepository;

    @Autowired
    public ExhibitionDBService(ServiceKey SERVICE_KEY, ExhibitionRepository exhibitionRepository) {
        this.SERVICE_KEY = SERVICE_KEY;
        this.exhibitionRepository = exhibitionRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void load_save() throws IOException {
        String serviceKey = SERVICE_KEY.getServiceKey();
        int seqtotalCount = 0;
        int pageNo = 1;
        while (true){
            URL url = new URL("http://www.culture.go.kr/openapi/rest/publicperformancedisplays/realm?" +
                    "serviceKey=" + serviceKey +
                    "&to=" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) +
                    "&realmCode=D000" +
                    "&cPage=" + pageNo
            );

            JSONObject msgBody = getMsgBody(url);

            JSONArray perforList = (JSONArray) msgBody.get("perforList");

            int totalCount = (int) msgBody.get("totalCount");

            List<Integer> seqList = new ArrayList<>();
            for (int index = 0; index < perforList.length(); index++) {
                Integer sequence = (Integer) perforList.getJSONObject(index).get("seq");
                seqList.add(sequence);
            }

            getDBData(seqList);

            seqtotalCount += seqList.size();

            if(seqtotalCount == totalCount) break;

            pageNo++;
        }
    }

    private void getDBData(List<Integer> seqList) {
        String serviceKey = SERVICE_KEY.getServiceKey();
        for (Integer seq : seqList) {
            try {
                URL url = new URL("http://www.culture.go.kr/openapi/rest/publicperformancedisplays/d/?" +
                        "serviceKey=" + serviceKey + "&seq=" + seq); // seq 타입?

                JSONObject msgBody = getMsgBody(url);

                JSONObject perforInfo = (JSONObject) msgBody.get("perforInfo");

                Exhibition infoObj = new Exhibition((String) perforInfo.get("title"), (String) perforInfo.get("place"), (String) perforInfo.get("placeAddr"),
                        (BigDecimal) perforInfo.get("gpsX"), (BigDecimal) perforInfo.get("gpsY"),
                        (Integer) perforInfo.get("startDate"), (Integer) perforInfo.get("endDate"),
                        (String) perforInfo.get("url"), (String) perforInfo.get("price"),
                        (String) perforInfo.get("placeUrl"), (String) perforInfo.get("imgUrl"), 0);

                if (exhibitionRepository.findByTitle(infoObj.getTitle()).isEmpty()) {
                    exhibitionRepository.save(infoObj);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        log.info("DB save completed");
    }

    private JSONObject getMsgBody(URL url) throws IOException {
        BufferedReader bf;
        bf = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
        String result;
        result = bf.readLine();
//        log.info(result);
        JSONObject resultJsonObject = XML.toJSONObject(result);

        JSONObject response = (JSONObject) resultJsonObject.get("response");
        return (JSONObject) response.get("msgBody");
    }
}

