package org.yakushev.shopwebapp.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class MergeableBean {
    public <T> T merge(T local, T remote) throws IllegalAccessException, InstantiationException,
            NoSuchMethodException, InvocationTargetException {

        Class<?> clazz = local.getClass();
        Object merged = clazz.getDeclaredConstructor().newInstance();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            Object localValue = field.get(local);
            Object remoteValue = field.get(remote);

            if (localValue != null) {
                switch (localValue.getClass().getSimpleName()) {
                    case "Default":
                    case "Detail":
                        field.set(merged, this.merge(localValue, remoteValue));
                        break;
                    default:
                        field.set(merged, (remoteValue != null) ? remoteValue : localValue);
                }
            }
        }

        return (T) merged;
    }
}
