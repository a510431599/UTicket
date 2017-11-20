package sample;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    public Map jsonToMap(String json){
        HashMap<String, Map> hashMap = new HashMap<>();
        Map<String ,Map> map = JSON.parseObject(json, Map.class);
        for (String s : map.keySet()) {
            Map map1 = map.get(s);
            Map o = (Map) map1.get(s);
        }
        return null;
    }
}
