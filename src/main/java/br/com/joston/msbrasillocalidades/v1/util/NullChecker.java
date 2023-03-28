package br.com.joston.msbrasillocalidades.v1.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NullChecker {
    public static boolean allGettersIsNull(Object target){
        Field[] fields = target.getClass().getDeclaredFields();
        List<String> methodNames = Arrays.stream(target.getClass().getDeclaredMethods()).map(Method::getName).collect(Collectors.toList());

        for(Field field:fields){
            String fieldName = field.getName();
            String getterMethod = "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
            if(methodNames.contains(getterMethod)){
                try {
                    if(target.getClass().getMethod(getterMethod).invoke(target) != null){
                        return false;
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return true;
    }
}
