package org.lpz.ai.utils;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 文字识别
 */
public class SampleWord {
    //设置APPID/AK/SK
    public static final String APP_ID = "117546200";
    public static final String API_KEY = "Q0bfYoc8XO2AHPOEjDIf2hb8";
    public static final String SECRET_KEY = "XWimCqDaIGuIyqgs5sByFZkVmO12shfr";

    public static void main(String[] args) {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 调用接口
        String path = "C:\\Users\\lenovo\\Desktop\\test.png";
        JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
        System.out.println(res.toString(2));
        
    }
}
