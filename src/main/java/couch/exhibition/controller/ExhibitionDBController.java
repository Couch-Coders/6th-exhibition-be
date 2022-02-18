package couch.exhibition.controller;

import couch.exhibition.entity.Exhibition;
import couch.exhibition.repository.ExhibitionDBRepository;
import couch.exhibition.repository.ExhibitionRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.XML;
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

@Slf4j
@Controller
public class ExhibitionDBController {

    private final ExhibitionDBRepository exhibitionDBRepository;

    @Autowired
    public ExhibitionDBController(ExhibitionDBRepository exhibitionDBRepository) {
        this.exhibitionDBRepository = exhibitionDBRepository;
    }

    @GetMapping("/api")
    public String index(){
        return "index";
    }

    @PostMapping("/api")
    public String load_save(@RequestParam("seq") String seq){
        String result = "";

        try {
            String serviceKey = "yu7NdPRhBWZtgcD4syUEm4DB3Vp%2BEVw05S%2BpLQvDHwzaRVSNvtFn6i9kuBLVia0LULNEFp3LASuS%2B%2B3iL4yP%2BA%3D%3D";
            URL url = new URL("http://www.culture.go.kr/openapi/rest/publicperformancedisplays/d/?" +
                                    "serviceKey=" + serviceKey + "&seq=" + seq);

            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            result = bf.readLine();
            JSONObject resultJsonObject = XML.toJSONObject(result);

            JSONObject response = (JSONObject) resultJsonObject.get("response");
            JSONObject msgBody = (JSONObject)response.get("msgBody");

            JSONObject perforInfo = (JSONObject)msgBody.get("perforInfo");

            Exhibition infoObj = new Exhibition((String) perforInfo.get("title"), (String) perforInfo.get("place"),(String) perforInfo.get("placeAddr"),
                    (BigDecimal) perforInfo.get("gpsX"), (BigDecimal) perforInfo.get("gpsY"),
                    (Integer) perforInfo.get("startDate"), (Integer) perforInfo.get("endDate"),
                    (String) perforInfo.get("url"), (String) perforInfo.get("price"),
                    (String) perforInfo.get("placeUrl"), (String) perforInfo.get("imgUrl"), 0);

            exhibitionDBRepository.save(infoObj);

        } catch(Exception e) {
            e.printStackTrace();
        }

        return "redirect:/index";
    }
}
