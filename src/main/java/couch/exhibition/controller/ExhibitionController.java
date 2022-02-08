package couch.exhibition.controller;

import couch.exhibition.dto.ExhibitionDto;
import couch.exhibition.repository.ExhibitionRepository;
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

            ExhibitionDto infoObj = new ExhibitionDto((String) perforInfo.get("title"), (String) perforInfo.get("place"),(String) perforInfo.get("city"),
                    (BigDecimal) perforInfo.get("latitude"), (BigDecimal) perforInfo.get("longitude"),
                    (LocalDate) perforInfo.get("startDate"), (LocalDate) perforInfo.get("endDate"),
                    (String) perforInfo.get("contactLink"), (String) perforInfo.get("ticketPrice"),
                    (String) perforInfo.get("reservationLink"), (String) perforInfo.get("posterUrl"));

            exhibitionRepository.save(infoObj);

        } catch(Exception e) {
            e.printStackTrace();
        }

        return "redirect:/index";
    }
}
