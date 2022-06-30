package couch.exhibition.service.DBService;

import couch.exhibition.config.ServiceConfig.ExhibitionUrl;
import couch.exhibition.config.ServiceConfig.ServiceKey;
import couch.exhibition.entity.Exhibition;
import couch.exhibition.exception.DBException.DBCustomException;
import couch.exhibition.repository.ExhibitionRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Slf4j
@Service
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
    1. java.lang.ClassCastException (extends RuntimeException) : when gps x,y coordinates are null.
    2. java.net.MalformedURLException (extends IOException) -> change into RuntimeException, and throw Custom Exception
     */
    @Transactional
    public void getDBData(List<Integer> seqList) {
        String serviceKey = SERVICE_KEY.getServiceKey();
        String exhibitionUrl = EXHIBITION_URL.getExhibitionUrl();

        for (Integer seq : seqList) {
            try {
                URL url = new URL(exhibitionUrl + "serviceKey=" + serviceKey + "&seq=" + seq);

                JSONObject msgBody = exhibitionGetMsgBodyService.getMsgBody(url);

                JSONObject perforInfo = (JSONObject) msgBody.get("perforInfo");
                log.info(String.valueOf(perforInfo.get("url")));

                Exhibition infoObj = new Exhibition(String.valueOf(perforInfo.get("title")),
                        String.valueOf(perforInfo.get("place")), String.valueOf(perforInfo.get("placeAddr")),
                        (BigDecimal) perforInfo.get("gpsX"), (BigDecimal) perforInfo.get("gpsY"),
                        (Integer) perforInfo.get("startDate"), (Integer) perforInfo.get("endDate"),
                        String.valueOf(perforInfo.get("url")), String.valueOf(perforInfo.get("price")),
                        String.valueOf(perforInfo.get("placeUrl")), String.valueOf(perforInfo.get("imgUrl")), 0);

                if (exhibitionRepository.findByTitle(infoObj.getTitle()).isEmpty()) {
                    exhibitionRepository.save(infoObj);
                }
            } catch (ClassCastException e) {
                log.warn("gps coordinates are null", e);
            } catch (MalformedURLException e) {
                log.warn("malformed URL", e);
                throw new DBCustomException("malformed URL");
            } catch (Exception e) {
                log.warn("Unexpected Exception", e);
            }
        }
    }
}