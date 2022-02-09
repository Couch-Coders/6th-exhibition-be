package couch.exhibition.controller;

import couch.exhibition.entity.Exhibition;
import couch.exhibition.repository.ExhibitionRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
@Slf4j
@Controller
public class ExhibitionController {

    @Autowired
    private ExhibitionRepository exhibitionRepository;

    @GetMapping("/api")
    public String index(){
        return "index";
    }

    @PostMapping("/api")
    public String load_save(@RequestParam("seq") String seq){
        String result;
        System.out.println("통과1");
        try {
            String serviceKey = "cqhL2N3Az%2BqDsTnmP5D0sUfmO7xujUBqG5gPWPxF7Ivv6eaIzWZtNrCBlyboVKnzjNY6gQviShM6JiC0DzKiGQ%3D%3D";
            URL url = new URL("http://www.culture.go.kr/openapi/rest/publicperformancedisplays/d/?" +
                                    "serviceKey=" + serviceKey + "&seq=" + seq);

            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            result = bf.readLine();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(result);

            JSONObject response = (JSONObject)jsonObject.get("response");
            JSONObject msgBody = (JSONObject)response.get("msgBody");

            JSONObject perforInfo = (JSONObject)msgBody.get("perforInfo");

            Exhibition infoObj = new Exhibition((String) perforInfo.get("title"), (String) perforInfo.get("place"),(String) perforInfo.get("placeAddr"),
                    (BigDecimal) perforInfo.get("latitude"), (BigDecimal) perforInfo.get("longitude"),
                    (LocalDate) perforInfo.get("startDate"), (LocalDate) perforInfo.get("endDate"),
                    (String) perforInfo.get("placeUrl"), (String) perforInfo.get("price"),
                    (String) perforInfo.get("url"), (String) perforInfo.get("imgUrl"));

            System.out.println("통과2");
            exhibitionRepository.save(infoObj);

        } catch(Exception e) {
            e.printStackTrace();
        }

        return "dataSaveSuccess";
    }
}