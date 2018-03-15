package ma.najeh.ibnouzouhr.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by youssef on 12/26/17.
 */
public class ObjectUtil {
    public static Map<String,Object> makeMap(String[] propertyNames, Object[] state) {
        Map<String ,Object> objectMap =new HashMap<>();
        for (int i=0;i<propertyNames.length;i++){
            objectMap.put(propertyNames[i],state[i]);
        }
        return objectMap;
    }
}
