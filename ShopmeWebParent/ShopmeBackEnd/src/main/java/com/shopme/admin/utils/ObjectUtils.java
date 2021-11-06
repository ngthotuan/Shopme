package com.shopme.admin.utils;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class ObjectUtils {
    public static Object getValue(Object obj, String fieldName) {
        Class<?> clazz = obj.getClass();
        Field field = ReflectionUtils.findField(clazz, fieldName);
        if (field != null) {
            field.setAccessible(true);
//                    ReflectionUtils.makeAccessible(field);
            try {
                return field.get(obj);
            } catch (IllegalAccessException e) {
                System.err.printf("Get field data of %s error", fieldName);
                return null;
            }
        } else {
            System.err.println("Could not found field " + fieldName);
            return null;
        }
    }

    public static String getStringValue(Object obj, String fieldName) {
        Object value = getValue(obj, fieldName);
        if (value != null) {
            return value.toString();
        } else {
            return null;
        }
    }
}
