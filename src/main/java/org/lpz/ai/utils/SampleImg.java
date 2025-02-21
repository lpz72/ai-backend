package org.lpz.ai.utils;


import com.baidu.aip.imageclassify.AipImageClassify;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 图片识别
 */
public class SampleImg {
    //设置APPID/AK/SK
    public static final String APP_ID = "117546477";
    public static final String API_KEY = "bXQvaMuXlTJaIu9QM2YpYR1J";
    public static final String SECRET_KEY = "F5NF9Gq3Yn1Kb4tXE63tq9Ys9LS7gX5Q";

    public static void main(String[] args) {
        // 初始化一个AipImageClassify
        AipImageClassify client = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);
        // 调用接口
        String path = "C:\\Users\\lenovo\\Desktop\\fruit.jpg";
        JSONObject res = client.advancedGeneral(path, new HashMap<String, String>());
        System.out.println(res.toString(2));

    }
}
