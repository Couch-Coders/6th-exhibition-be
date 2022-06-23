package couch.exhibition.service.DBService;

import couch.exhibition.exception.DBException.DBCustomException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class ExhibitionGetMsgBodyService {

    /*
    1. java.io.IOException -> change into RuntimeException, and throw Custom Exception
     */
    @Transactional
    public JSONObject getMsgBody(URL url) {
        BufferedReader bf;
        StringBuilder resultBuilder = new StringBuilder();

        try {
            bf = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), StandardCharsets.UTF_8));
            String temp = bf.readLine();
            while (temp != null) {
                resultBuilder.append(temp);
                temp = bf.readLine();
            }
        } catch (IOException e) {
            log.warn("URL IOException", e);
            throw new DBCustomException("URL IOException");
        }

        String result = resultBuilder.toString();
//        log.info(result);
        JSONObject resultJsonObject = XML.toJSONObject(result);

        JSONObject response = (JSONObject) resultJsonObject.get("response");
        return (JSONObject) response.get("msgBody");
    }
}