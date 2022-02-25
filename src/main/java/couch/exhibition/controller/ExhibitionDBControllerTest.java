package couch.exhibition.controller;

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
public class ExhibitionDBControllerTest {

    private final ExhibitionRepository exhibitionRepository;

    @Autowired
    public ExhibitionDBControllerTest(ExhibitionRepository exhibitionRepository) {
        this.exhibitionRepository = exhibitionRepository;
    }

    @Scheduled(cron = "0 0 0 * * *") // 추가 설정(매일 자정에 실행)
    public void load_save() throws IOException { // try문?

        String serviceKey = "yu7NdPRhBWZtgcD4syUEm4DB3Vp%2BEVw05S%2BpLQvDHwzaRVSNvtFn6i9kuBLVia0LULNEFp3LASuS%2B%2B3iL4yP%2BA%3D%3D"; // 처리
        URL url = new URL("http://www.culture.go.kr/openapi/rest/publicperformancedisplays/realm?" +
                "serviceKey=" + serviceKey +
                "&from=" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) +
                "&to=" + LocalDate.now().plusYears(3).format(DateTimeFormatter.ofPattern("yyyyMMdd")) +
                "&realmCode=D000");

        JSONObject msgBody = getMsgBody(url);

        JSONArray perforList = (JSONArray) msgBody.get("perforList");

        log.info(String.valueOf(perforList.getJSONObject(0)));

        List<Integer> seqList = new ArrayList<>();
        for (Object performance : perforList) {

            seqList.add((Integer) performance);
        }

        //(Integer) performance.get("seq")

        log.info("ok1");

        getDBData(seqList);

    }

    private void getDBData(List<Integer> seqList) {

        for (Integer seq : seqList) {
            try {
                String serviceKey = "yu7NdPRhBWZtgcD4syUEm4DB3Vp%2BEVw05S%2BpLQvDHwzaRVSNvtFn6i9kuBLVia0LULNEFp3LASuS%2B%2B3iL4yP%2BA%3D%3D";
                URL url = new URL("http://www.culture.go.kr/openapi/rest/publicperformancedisplays/d/?" +
                        "serviceKey=" + serviceKey + "&seq=" + seq); // seq 타입?

                JSONObject msgBody = getMsgBody(url);

                JSONObject perforInfo = (JSONObject) msgBody.get("perforInfo");

                Exhibition infoObj = new Exhibition((String) perforInfo.get("title"), (String) perforInfo.get("place"), (String) perforInfo.get("placeAddr"),
                        (BigDecimal) perforInfo.get("gpsX"), (BigDecimal) perforInfo.get("gpsY"),
                        (Integer) perforInfo.get("startDate"), (Integer) perforInfo.get("endDate"),
                        (String) perforInfo.get("url"), (String) perforInfo.get("price"),
                        (String) perforInfo.get("placeUrl"), (String) perforInfo.get("imgUrl"), 0);

                exhibitionRepository.save(infoObj);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info("ok2");
    }

    private JSONObject getMsgBody(URL url) throws IOException {
        BufferedReader bf;
        bf = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
        String result;
        result = bf.readLine();
        JSONObject resultJsonObject = XML.toJSONObject(result);

        JSONObject response = (JSONObject) resultJsonObject.get("response");
        return (JSONObject) response.get("msgBody");
    }
}

