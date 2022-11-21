package com.mlog.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Utils {

    public static String replace(String target, List<Map<String, String>> variableList) {
        if (target == null || variableList == null)
            return null;

        String KEY = "KEY";
        String VAL = "VAL";

        String replaced = target;

        for (Map<String, String> m : variableList) {
            String key = m.get(KEY);
            String val = m.get(VAL);

            if (key == null || val == null)
                continue;

            replaced = replaced.replace(key, val);
        }

        return replaced;
    }

    public static List<String> split(String target, int size) {
        List<String> list = new ArrayList<>();

        int len = target.length();
        if (len > size) {
            int stt = 0;
            int end = stt + size;

            String sb;

            boolean flag = true;

            while (flag) {
                if (end >= len) {
                    flag = false;
                    end = len;
                }

                sb = target.substring(stt, end);
                list.add(sb);

                stt = end;
                end = stt + size;
            }
        } else {
            list.add(target);
        }

        return list;
    }
}
