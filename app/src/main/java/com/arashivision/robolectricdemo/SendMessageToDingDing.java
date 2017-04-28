package com.arashivision.robolectricdemo;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/4/28 14:54.
 */

public class SendMessageToDingDing {

    public static String WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=b90ec59a860a3c96f29ff932fe05173f7e52093a3f3a8df9a155960009322e07";

    public static void main(String[] args) {

//        HttpClient httpclient = HttpClients.createDefault();
//
//        HttpPost post = new HttpPost(WEBHOOK_TOKEN);
//        post.addHeader("Content-Type", "application/json; charset=utf-8");
//
//        JSONObject jsonObj = new JSONObject();
//        try {
//            jsonObj.put("msgtype", "text");
//
//            JSONObject textObj = new JSONObject();
//            String content = "from gradle";
//            textObj.put("content", content);
//            jsonObj.put("text", textObj);
//
//            JSONObject atObj = new JSONObject();
//            boolean isAtAll = true;
//            atObj.put("isAtAll", isAtAll);
//            jsonObj.put("at", atObj);
//
//            String textMsg = jsonObj.toString();
//
//            StringEntity stringEntity = new StringEntity(textMsg, "utf-8");
//            post.setEntity(stringEntity);
//            HttpResponse response = http.execute(post);
//            if (response.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
//                def result = org.apache.http.util.EntityUtils.toString(response.getEntity());
//                println(result);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }
}
