package couch.exhibition.service.DBService;

import couch.exhibition.config.ServiceConfig.ExhibitionUrl;
import couch.exhibition.config.ServiceConfig.ServiceKey;
import couch.exhibition.entity.Exhibition;
import couch.exhibition.repository.ExhibitionRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Slf4j
@Component
public class ExhibitionGetDataService {

    private final ServiceKey SERVICE_KEY;
    private final ExhibitionUrl EXHIBITION_URL;
    private final ExhibitionRepository exhibitionRepository;
    private final ExhibitionGetMsgBodyService exhibitionGetMsgBodyService;

    public ExhibitionGetDataService(ServiceKey SERVICE_KEY, ExhibitionUrl EXHIBITION_URL,
                                    ExhibitionRepository exhibitionRepository, ExhibitionGetMsgBodyService exhibitionGetMsgBodyService) {
        this.SERVICE_KEY = SERVICE_KEY;
        this.EXHIBITION_URL = EXHIBITION_URL;
        this.exhibitionRepository = exhibitionRepository;
        this.exhibitionGetMsgBodyService = exhibitionGetMsgBodyService;
    }

    /*
    1. org.json.JSONException (extends RuntimeException) : Unclosed tag
    2. java.lang.ClassCastException (extends RuntimeException) : throw exception when gps x,y coordinates are null.
    3. java.net.MalformedURLException (extends IOException) -> change into RuntimeException
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void getDBData(List<Integer> seqList) {
        String serviceKey = SERVICE_KEY.getServiceKey();
        String exhibitionUrl = EXHIBITION_URL.getExhibitionUrl();

        for (Integer seq : seqList) {
            try {
                URL url = new URL(exhibitionUrl + "serviceKey=" + serviceKey + "&seq=" + seq);

                JSONObject msgBody = exhibitionGetMsgBodyService.getMsgBody(url);

                JSONObject perforInfo = (JSONObject) msgBody.get("perforInfo");

                Exhibition infoObj = new Exhibition((String) perforInfo.get("title"), (String) perforInfo.get("place"), (String) perforInfo.get("placeAddr"),
                        (BigDecimal) perforInfo.get("gpsX"), (BigDecimal) perforInfo.get("gpsY"),
                        (Integer) perforInfo.get("startDate"), (Integer) perforInfo.get("endDate"),
                        (String) perforInfo.get("url"), (String) perforInfo.get("price"),
                        (String) perforInfo.get("placeUrl"), (String) perforInfo.get("imgUrl"), 0);

                if (exhibitionRepository.findByTitle(infoObj.getTitle()).isEmpty()) {
                    exhibitionRepository.save(infoObj);
                }
            } catch (JSONException e) {
                log.warn("Unclosed tag", e);
            } catch (ClassCastException e) {
                log.warn("gps x, y coordinates are null", e);
            } catch (MalformedURLException e) {
                log.warn("malformed URL", e);
            }
        }
    }
}