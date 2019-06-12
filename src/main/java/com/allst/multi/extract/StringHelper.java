package com.allst.multi.extract;

import java.util.ArrayList;
import java.util.List;

/**
 * @author June
 * @version 1.0
 * @date 2018-06-27
 */
public class StringHelper {

    public static String joinString(String... args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]);
        }
        return sb.toString();
    }

    public static String stringRightFile(String strTemp, int iLength, String strFile) {
        StringBuilder sbCommand = new StringBuilder();
        if (strTemp == null) {
            strTemp = "";
        }
        strTemp = strTemp.trim();
        if (strTemp.length() < iLength) {
            for (int i = 0; i < strTemp.length(); i++) {
                sbCommand.append(strTemp.charAt(i));
            }
            for (int j = 0; j < iLength - strTemp.length(); j++) {
                sbCommand.append(strFile);
            }
        } else if (strTemp.length() > iLength) {
            int iPont = strTemp.length() - iLength;
            sbCommand.append(strTemp.substring(iPont, strTemp.length()));
        } else if (strTemp.length() == iLength) {
            sbCommand.append(strTemp);
        }
        return sbCommand.toString();
    }

    public static String[] stringDiffer(String[] minuend,String[] subtractor){
        List<String> results = new ArrayList<>();
        // 减数
        List<String> subLists = new ArrayList<>();
        //被减数
        List<String> minLists = new ArrayList<>();
        for(String s:subtractor){
            subLists.add(s);
        }
        for(String s:minuend){
            minLists.add(s);
        }
        for(String s:minLists){
            if(!subLists.contains(s)){
                results.add(s);
            }
        }
        return results.toArray(new String[results.size()]);
    }
}
