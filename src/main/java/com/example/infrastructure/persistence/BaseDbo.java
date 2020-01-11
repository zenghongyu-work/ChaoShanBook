package com.example.infrastructure.persistence;

import org.springframework.beans.BeanUtils;

public class BaseDbo {
    public <T> T toModule(Class<T> clazz) {
        T target = null;
        try {
            target = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        BeanUtils.copyProperties(this, target);
        return target;
    }

    public static <T> T fromModule(Object t, Class<T> clazz) {
        T target = null;
        try {
            target = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        BeanUtils.copyProperties(t, target);
        return target;
    }
}
