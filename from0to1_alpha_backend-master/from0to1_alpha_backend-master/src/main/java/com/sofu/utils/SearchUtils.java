package com.sofu.utils;

public class SearchUtils {

    /**
     * 根据搜索特殊字符串
     * @param str
     * @return 取不到返回null
     */
    public static String specialStr(String str){
        Integer index=str.indexOf("%");
        Integer index1=str.indexOf("_");
        Integer index2=str.indexOf("/");
        StringBuffer stringBuffer = new StringBuffer(str);
        if(index!=-1) {
            stringBuffer.insert(index, "\\");
        }
        if(index1!=-1) {
            stringBuffer.insert(index1, "\\");
        }
        if(index2!=-1) {
            stringBuffer.insert(index2, "\\");
        }
        return stringBuffer.toString();
    }

    /**
     * 处理不连续关键字查询
     * @param str
     * @return
     */
    public static String specialStrKeyword(String str){
        if(str==null||str==""){
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer(str);
        int length=str.length();
        for (int i = 0; i <length; i++) {
            char chari=stringBuffer.charAt(i);
            if(i==0){
                if(chari=='%'||chari=='_'||chari=='\\'){
                    stringBuffer.insert(i, "\\");
                    i++;
                    length++;
                }
            }
            else{
                if(chari=='%'||chari=='_'||chari=='\\'){
                    stringBuffer.insert(i, "%\\");
                    i+=2;
                    length+=2;
                }
                else{
                    stringBuffer.insert(i, "%");
                    i++;
                    length++;
                }
            }
        }
        return stringBuffer.toString();
    }

    /**
     * 处理多个关键字查询
     * @param str
     * @return
     */
    public static String conactString(String str) {
        String outString="";
        String[] subString=str.split("\\s+");
        for(int i = 0; i < subString.length ; i++) {
            //最后不能拼接|符号
            if(i != (subString.length-1)) {
                outString += subString[i] + "|";
            }
            else {
                outString += subString[i];
            }
        }
        return outString;
    }

}
