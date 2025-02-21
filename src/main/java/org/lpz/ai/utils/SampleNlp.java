package org.lpz.ai.utils;


import com.baidu.aip.nlp.AipNlp;
import org.json.JSONObject;

public class SampleNlp {
    //设置APPID/AK/SK
    public static final String APP_ID = "117546556";
    public static final String API_KEY = "mh8ELYdpYNPVDcmCm1hUUuE3";
    public static final String SECRET_KEY = "vvHiPGaA0VQysHs2L6JTxKeQnpW5nRey";

    public static void main(String[] args) {
        // 初始化一个AipNlp
        AipNlp client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);
        // 调用接口
        String text = "百度是一家人工只能公司";
        JSONObject res = client.ecnet(text, null);
        System.out.println(res.toString(2));

    }
}
