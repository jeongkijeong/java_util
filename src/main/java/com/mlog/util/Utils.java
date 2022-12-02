package com.mlog.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
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

    public static String objectToJsonStr(Object object) {
        if (object == null) {
            return null;
        }

        String result = null;
        try {
            Gson gson = new GsonBuilder().create();
            result = gson.toJson(object);
        } catch (Exception e) {
            e.getStackTrace();
        }

        return result;
    }

    public static Object jsonStrToObject(String result) {
        if (result == null) {
            return null;
        }

        Object object = null;
        try {
            Gson gson = new GsonBuilder().create();
            object = gson.fromJson(result, new HashMap<String, Object>().getClass());
        } catch (Exception e) {
            e.getStackTrace();
        }

        return object;
    }

    public static int getProcessId() {
        int processId = -1;

        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (name != null) {
            processId = Integer.valueOf(name.split("\\@")[0]);
        }

        return processId;
    }

    public static String fileToString(String path) {
        String result = "";

        File file = new File(path);
        if (!file.exists()) {
            return result;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file));) {
            StringBuilder sb = new StringBuilder();

            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }

            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    public static String nvl(String target, String replace) {
        if (target == null) {
            return replace;
        }

        return target;
    }

    public static Object nvl(Object target, Object replace) {
        if (target == null) {
            return replace;
        }

        return target;
    }

    public static String nvl2(String target, String replace1, String replace2) {
        if (target == null) {
            return replace1;
        }

        return replace2;
    }

    public static Object nvl2(Object target, Object replace1, Object replace2) {
        if (target == null) {
            return replace1;
        }

        return replace2;
    }
}
