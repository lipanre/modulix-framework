package com.modulix.framework.common.core.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lipanre
 */
public class ArrayUtil {

    public static <K, V> Map<K, V> zip(K[] keys, V[] values) {
        Map<K, V> map = new HashMap<>();
        int length = Math.min(keys.length, values.length);
        for (int i = 0; i < length; i++) {
            map.put(keys[i], values[i]);
        }
        return map;
    }

}
