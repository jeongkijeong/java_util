package com.mlog.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {
    private static Logger logger = LoggerFactory.getLogger(Utils.class);

    private static Properties properties = null;

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

    public static <T> Object jsonStrToObject(String result, Class<T> classType) {
        Object jsonObj = null;

        if (result == null) {
            return jsonObj;
        }

        try {
            Gson gson = new GsonBuilder().create();
            jsonObj = gson.fromJson(result, classType);
        } catch (Exception e) {
            logger.error("", e);
        }

        return jsonObj;
    }

    public static Map<String, Object> jsonStrToMap(String result) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        if (result == null) {
            return null;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
            };

            jsonMap = mapper.readValue(result, typeRef);
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }

        return jsonMap;
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
            System.out.println("file not exist " + path);

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

    public static String rpad(String target, int size, String pad){
        if (target == null) {
            return "";
        }

        String convert = String.format("%-" + size + "s", target).replace(" ", pad);
        return convert;
    }

    public static String lpad(String target, int size, String pad){
        if (target == null) {
            return "";
        }

        String convert = String.format("%"  + size + "s", target).replace(" ", pad);
        return convert;
    }

    public static String currentDate(String format) {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));

        return date;
    }

    public static int loadProperties(String path) {
        int result = -1;

        File file = new File(path);
        if (!file.exists()) {
            return result;
        }

        try (FileInputStream fis = new FileInputStream(path)) {
            properties = new Properties();
            properties.load(fis);

            result = 0;
        } catch (Exception e) {
            e.getStackTrace();
        }

        return result;
    }

    public static String getProterty(String key) {
        String value = null;

        if (properties != null) {
            value = (String) nvl(properties.get(key), "");
        }

        return value;
    }

    public static Object jsonFileToObject(String path) {
        String string = fileToString(path);

        Object object = null;
        try {
            Gson gson = new GsonBuilder().create();
            object = gson.fromJson(string, new HashMap<String, Object>().getClass());
        } catch (Exception e) {
            logger.error("", e);
        }

        return object;
    }

    public static Map<String, Object> jsonFileToMap(String path) {
        Map<String, Object> jsonMap = null;

        try {
            jsonMap = jsonStrToMap(fileToString(path));
        } catch (Exception e) {
            logger.error("", e);
        }

        return jsonMap;
    }

    public static Properties mapToProperties(Map<String, Object> map) {
        Properties properties = new Properties();

        for (String key : map.keySet()) {
//            properties.setProperty(key, (String) map.get(key));
            properties.setProperty(key, map.get(key).toString());
        }

        return properties;
    }

    public void mergeFile(String sourceFilePath, String targetFilePath) {
        File sourceFile = new File(sourceFilePath);
        File targetFile = new File(targetFilePath);

        try {
            String line = "";

            if (targetFile.exists() == true) {  // ???????????? ????????? ????????????.
                FileWriter fileWriter = new FileWriter(targetFile, true);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile)));
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                int count = 0;
                while ((line = bufferedReader.readLine()) != null) {
                    if (count == 0) {
                        count++;

                        continue;
                    }

                    bufferedWriter.write(line);
                    bufferedWriter.write("\n");
                }

                bufferedWriter.flush();
                bufferedReader.close();
                bufferedWriter.close();
            } else {
                sourceFile.renameTo(targetFile);
            }

            sourceFile.delete();
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    public List<Map<String, String>> matchedList (List<Map<String, String>> sourceList, List<Map<String, String>> targetList, String key) {
        List<Map<String, String>> t = sourceList.stream().filter(target -> targetList.stream().anyMatch(source -> source.get(key).equals(target.get(key)))).collect(Collectors.toList());

        return t;
    }

    public List<Map<String, String>> removedList (List<Map<String, String>> sourceList, List<Map<String, String>> targetList, String key) {
        sourceList.removeIf(target -> targetList.stream().anyMatch(source -> source.get(key).equals(target.get(key))));

        return sourceList;
    }

}
