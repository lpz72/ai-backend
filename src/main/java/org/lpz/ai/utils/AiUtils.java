package org.lpz.ai.utils;

import com.baidu.aip.imageclassify.AipImageClassify;
import com.baidu.aip.nlp.AipNlp;
import com.baidu.aip.ocr.AipOcr;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

public class AiUtils {

    public static String word(MultipartFile file) throws IOException {
        //设置APPID/AK/SK
        String APP_ID = "117546200";
        String API_KEY = "Q0bfYoc8XO2AHPOEjDIf2hb8";
        String SECRET_KEY = "XWimCqDaIGuIyqgs5sByFZkVmO12shfr";
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 调用接口
//        String path = "C:\\Users\\lenovo\\Desktop\\test.png";
        JSONObject res = client.basicGeneral(file.getBytes(), new HashMap<String, String>());
        System.out.println(res.toString(2));
        JSONArray wordsResult = res.getJSONArray("words_result");
        StringBuffer result = new StringBuffer();
        for (int i = 0;i < wordsResult.length();i ++){
            result.append(wordsResult.getJSONObject(i).get("words")).append("\n");
        }

        return result.toString();
    }

    public static String img(MultipartFile file) throws IOException {
        //设置APPID/AK/SK
        String APP_ID = "117546477";
        String API_KEY = "bXQvaMuXlTJaIu9QM2YpYR1J";
        String SECRET_KEY = "F5NF9Gq3Yn1Kb4tXE63tq9Ys9LS7gX5Q";
        // 初始化一个AipImageClassify
        AipImageClassify client = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);
        // 调用接口
//        String path = "C:\\Users\\lenovo\\Desktop\\fruit.jpg";
        JSONObject res = client.advancedGeneral(file.getBytes(), new HashMap<String, String>());
        JSONArray jsonArray = res.getJSONArray("result");
        StringBuffer result = new StringBuffer();
//        System.out.println(res.toString(2));
        for (int i = 0;i < jsonArray.length();i ++){
            JSONObject object = jsonArray.getJSONObject(i);
            result.append("这张图有" + String.format("%.3f",object.getDouble("score") * 100) + "%的可能是" + object.get("root") + "-" + object.get("keyword")).append("\n");
        }
        return result.toString();
    }

    public static String nlp(String text) {
        //设置APPID/AK/SK
        String APP_ID = "117546556";
        String API_KEY = "mh8ELYdpYNPVDcmCm1hUUuE3";
        String SECRET_KEY = "vvHiPGaA0VQysHs2L6JTxKeQnpW5nRey";
        // 初始化一个AipNlp
        AipNlp client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);
        // 调用接口
        JSONObject res = client.ecnet(text, null);
//        System.out.println(res.toString(2));
        return res.getJSONObject("item").get("correct_query").toString();
    }
}
