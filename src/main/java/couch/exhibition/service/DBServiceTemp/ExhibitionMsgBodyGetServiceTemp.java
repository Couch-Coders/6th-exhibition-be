//package couch.exhibition.service.DBService;
//
//import lombok.extern.slf4j.Slf4j;
//import org.json.JSONObject;
//import org.json.XML;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.URL;
//import java.nio.charset.StandardCharsets;
//
//@Slf4j
//@Component
//@Transactional(noRollbackFor = RuntimeException.class)
//public class ExhibitionMsgBodyGetServiceTemp {
//
//    public JSONObject getMsgBody(URL url) {
//        BufferedReader bf;
//        try {
//            bf = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        String result;
//        try {
//            result = bf.readLine();
//            JSONObject resultJsonObject = XML.toJSONObject(result);
//            JSONObject response = (JSONObject) resultJsonObject.get("response");
//            return (JSONObject) response.get("msgBody");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}