package couch.exhibition.service.DBService;

import couch.exhibition.exception.DBException.DBCustomException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class ExhibitionGetMsgBodyService {

    /*
    1. url.openStream() -> java.io.IOException -> change into RuntimeException
    2. bf.readLine() -> IOException -> change into RuntimeException
    3. org.json.JSONException (extends RuntimeException) : Unclosed tag
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public JSONObject getMsgBody(URL url) {
        BufferedReader bf;
        try {
            bf = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.warn("URL IOException", e);
            throw new DBCustomException("URL IOException");
        }

        String result = null;
        try {
            result = bf.readLine();
        } catch (IOException e) {
            log.warn("bf IOException", e);
            throw new DBCustomException("bf IOException");
        } catch (JSONException e) {
            log.warn("Unclosed tag", e);
        }

        JSONObject resultJsonObject = XML.toJSONObject(result);
        JSONObject response = (JSONObject) resultJsonObject.get("response");
        return (JSONObject) response.get("msgBody");
    }
}