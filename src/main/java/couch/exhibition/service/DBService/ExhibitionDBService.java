package couch.exhibition.service.DBService;

import couch.exhibition.config.ServiceConfig.RealmUrl;
import couch.exhibition.config.ServiceConfig.ServiceKey;
import couch.exhibition.exception.DBException.DBCustomException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ExhibitionDBService {

    private final ServiceKey SERVICE_KEY;
    private final RealmUrl REALM_URL;
    private final ExhibitionGetDataService exhibitionGetDataService;
    private final ExhibitionGetMsgBodyService exhibitionGetMsgBodyService;

    public ExhibitionDBService(ServiceKey SERVICE_KEY, RealmUrl REALM_URL,
                               ExhibitionGetDataService exhibitionGetDataService, ExhibitionGetMsgBodyService exhibitionGetMsgBodyService) {
        this.SERVICE_KEY = SERVICE_KEY;
        this.REALM_URL = REALM_URL;
        this.exhibitionGetDataService = exhibitionGetDataService;
        this.exhibitionGetMsgBodyService = exhibitionGetMsgBodyService;
    }

    /*
    1. java.net.MalformedURLException (extends IOException) -> change into RuntimeException
     */
    @Scheduled(cron = "${regular.cron}")
    @Transactional
    public void load_save() {
        String serviceKey = SERVICE_KEY.getServiceKey();
        String realmUrl = REALM_URL.getRealmUrl();

        int seqtotalCount = 0;
        int pageNo = 1;
        while (true){
            URL url;
            try {
                url = new URL(realmUrl +
                        "serviceKey=" + serviceKey +
                        "&to=" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) +
                        "&realmCode=D000" +
                        "&cPage=" + pageNo
                );
            } catch (MalformedURLException e) {
                log.warn("malformed URL", e);
                throw new DBCustomException("malformed URL");
            }

            JSONObject msgBody = exhibitionGetMsgBodyService.getMsgBody(url);

            JSONArray perforList = (JSONArray) msgBody.get("perforList");

            int totalCount = (int) msgBody.get("totalCount");

            List<Integer> seqList = new ArrayList<>();
            for (int index = 0; index < perforList.length(); index++) {
                Integer sequence = (Integer) perforList.getJSONObject(index).get("seq");
                seqList.add(sequence);
            }

            exhibitionGetDataService.getDBData(seqList);

            seqtotalCount += seqList.size();

            if(seqtotalCount == totalCount) break;

            pageNo++;
        }
    }
}