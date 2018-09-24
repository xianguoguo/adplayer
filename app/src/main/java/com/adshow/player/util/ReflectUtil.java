package com.adshow.player.util;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by baina on 18-2-26.
 * java反射工具类
 */

public class ReflectUtil {

    private static final String TAG = "ReflectUtil1";

    /**
     * 执行某静态方法，如果此类中没找到则从父类中找
     *
     * @param c
     * @param methodName
     * @param parameterTypes
     * @param args
     * @return
     */
    public static Object invokeStatic(Class c, String methodName, Class<?>[] parameterTypes, Object[] args) {
        if (c == null)
            return null;
        try {
            Method m = c.getDeclaredMethod(methodName, parameterTypes);
            m.setAccessible(true);
            return m.invoke(null, args);
        } catch (NoSuchMethodException e) {
            c = c.getSuperclass();
        } catch (Exception e) {
            Log.e(TAG, "invoke-->methodName=" + methodName, e);
        }
        return null;
    }

    /**
     * 执行某方法，如果此类中没找到则从父类中找
     *
     * @param owner
     * @param methodName
     * @param parameterTypes
     * @param args
     * @return
     */
    public static Object invoke(Object owner, String methodName, Class<?>[] parameterTypes, Object[] args) {
        if (owner == null)
            return null;
        Class<?> c = owner.getClass();
        while (c != null) {
            try {
                Method m = c.getDeclaredMethod(methodName, parameterTypes);
                m.setAccessible(true);
                return m.invoke(owner, args);
            } catch (NoSuchMethodException e) {
                c = c.getSuperclass();
            } catch (Exception e) {
                Log.e(TAG, "invoke-->methodName=" + methodName, e);
                break;
            }
        }
        return null;
    }

    /**
     * 设置某个对象的父类私有属性值,从此类一直沿着父类找
     *
     * @param owner
     * @param fieldName
     * @param value
     * @return
     */
    public static boolean setPrivateFieldValue(Object owner, String fieldName,
                                               Object value) {
        Class<?> c = owner.getClass();
        while (c != null) {
            try {
                Field field = c.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(owner, value);
                return true;
            } catch (NoSuchFieldException e) {
                c = c.getSuperclass();
            } catch (Exception e) {
                Log.e("app", "field-->fieldName=" + fieldName, e);
                break;
            }
        }
        return false;
    }


    /**
     * 获取私有成员的值，查找继承体系中的所有父类
     *
     * @param owner     本类的实例
     * @param fieldName 查找的成员方法
     * @return
     */
    public static Object getPrivateFiledValue(Object owner, String fieldName) {
        Class<?> c = owner.getClass();
        while (c != null) {
            try {
                Field field = c.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(owner);
            } catch (NoSuchFieldException e) {
                c = c.getSuperclass();
            } catch (Exception e) {
                Log.e("app", "field-->fieldName=" + fieldName, e);
                break;
            }
        }
        return null;
    }

    /**
     * 根据包名以及类名，实例化一个类
     *
     * @param className
     * @param types
     * @param values
     * @return
     */
    public static Object newInstance(String className, Class<?>[] types, Object[] values) {
        try {
            Class<?> tmpClass = Class.forName(className);
            Object o = tmpClass.getConstructor(types).newInstance(values);
            return o;
        } catch (Exception e) {
            Log.e("app", "newInstance " + className + " error", e);
        }
        return null;
    }
}
