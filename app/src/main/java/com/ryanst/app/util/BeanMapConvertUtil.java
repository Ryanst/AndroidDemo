package com.ryanst.app.util;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kevin on 16/3/10.
 */
public class BeanMapConvertUtil {
    /**
     * 转换对象到map
     *
     * @param object
     * @return
     */
    public static Map<String, Object> convertObjToMap(Object object, String token) {
        Map<String, Object> map = new HashMap<>();
        if (object == null) {
            return null;
        }
        Field[] fields = object.getClass().getSuperclass().getDeclaredFields();
        Field[] ff = object.getClass().getDeclaredFields();
        try {
            for (int i = 0; i < fields.length; i++) {
                Field field = object.getClass().getSuperclass().getDeclaredField(fields[i].getName());
                field.setAccessible(true);
                Annotation[] annotations = field.getDeclaredAnnotations();
                String key = "";
                for (Annotation annotation : annotations) {
                    if (annotation instanceof SerializedName) {
                        key = ((SerializedName) annotation).value();
                    }
                }
                if (TextUtils.isEmpty(key)) {
                    key = fields[i].getName();
                }
                Object o = field.get(object);
                map.put(key, o);
            }
            for (int i = 0; i < ff.length; i++) {
                Field field = object.getClass().getDeclaredField(ff[i].getName());
                field.setAccessible(true);
                Annotation[] annotations = field.getDeclaredAnnotations();
                String key = "";
                for (Annotation annotation : annotations) {
                    if (annotation instanceof SerializedName) {
                        key = ((SerializedName) annotation).value();
                    }
                }
                if (TextUtils.isEmpty(key)) {
                    key = ff[i].getName();
                }
                Object o = field.get(object);
                map.put(key, o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.remove("token");
        map.put("token", token);
        Log.d(".......", "convert success");
        return map;
    }


}
