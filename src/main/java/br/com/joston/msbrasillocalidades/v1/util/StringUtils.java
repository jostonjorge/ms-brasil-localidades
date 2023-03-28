package br.com.joston.msbrasillocalidades.v1.util;

public class StringUtils {
    public static String onlyNumbers(String characters){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i < characters.length();i++){
            char character = characters.charAt(i);
            if(Character.isDigit(character)){
                stringBuilder.append(character);
            }
        }
        return stringBuilder.toString();
    }
}
