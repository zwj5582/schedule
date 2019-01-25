package org.zhong.zschedule;

import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public static boolean valid(Boolean bool) {
        if (bool != null && bool) {
            return true;
        }
        return false;
    }

    public static boolean valid(String string) {
        if (string != null && !"".equals(string.trim())) {
            return true;
        }
        return false;
    }

    public static boolean valid(Integer integer) {
        if (integer != null && integer > 0) {
            return true;
        }
        return false;
    }

    public static boolean valid(Object[] objects) {
        if (objects != null && objects.length > 0) {
            return true;
        }
        return false;
    }

    public static boolean valid(Collection<Object> collection) {
        if (collection != null && !collection.isEmpty()) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public static boolean valid(Object object) {
        if (object != null) {
            if (object instanceof Object[]) {
                return valid((Object[]) object);
            } else if (object instanceof Collection<?>) {
                return valid((Collection<Object>) object);
            } else if (object instanceof String) {
                return valid((String) object);
            } else if (object instanceof Integer) {
                return valid((Integer) object);
            } else if (object instanceof Boolean) {
                return valid((Boolean) object);
            }
            return true;
        }
        return false;
    }

    public static <T> T getFirst(List<T> list) {
        if (valid(list)) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public static String idcardMask(String string) {
        if (string != null && string.length() > 10) {
            StringBuffer sb = new StringBuffer(string.substring(0, 6));
            for (int i = 0; i < string.length() - 10; i++) {
                sb.append("*");
            }
            sb.append(string.substring(string.length() - 4, string.length()));
            string = sb.toString();
        }
        return string;
    }

    public static String bcardMask(String string) {
        if (string != null && string.length() >= 4) {
            string = string.substring(0, string.length() - 4) + "****";
        }
        return string;
    }

    public static Float getFloat0(Float value) {

        return valid(value) ? value : 0f;
    }

    public static Float getFloat0(Object value) {

        return valid(value) ? Float.valueOf(value.toString()) : 0f;
    }

    public static Float getFloat1(Float value) {

        return valid(value) ? value : 1f;
    }

    public static String getString(Object value) {
        return valid(value) ? value.toString() : "";
    }

    public static Integer getInteger(Object value) {
        return valid(value) ? (Integer) value : 0;
    }

    public static String format2dec(Object value) {
        if (!valid(value)) {
            return "";
        }
        if (Double.valueOf(value.toString()) == 0) {
            // return "0.00";
        }
        DecimalFormat df1 = new DecimalFormat("0.00");
        return df1.format((Double.valueOf(value.toString())));
    }

    public static String format3dec(String value1, String value2, String value3) {
        value1 = Util.valid(value1) ? value1 : "0";
        value2 = Util.valid(value2) ? value2 : "0";
        value3 = Util.valid(value3) ? value3 : "0";
        Double value = Double.parseDouble(value1) + Double.parseDouble(value2)
                - Double.parseDouble(value3);
        if (!valid(value)) {
            return "";
        }
        if (Double.valueOf(value.toString()) == 0) {
            // return "0.00";
        }
        DecimalFormat df1 = new DecimalFormat("0.00");
        return df1.format((Double.valueOf(value.toString())));
    }

    public static Map jsonToMap(String json) {
        JSONObject jasonObject = JSONObject.fromObject(json);
        Map map = (Map) jasonObject;
        Iterator<Entry<Object, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Entry<Object, Object> itEntry = it.next();
            String value = itEntry.getKey().toString();
            if ("null".equals(value) || "".equals(value)) {
                it.remove();
            }

        }
        return map;
    }

    public static String convert(String utfString) {
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;

        while ((i = utfString.indexOf("\\u", pos)) != -1) {
            sb.append(utfString.substring(pos, i));
            if (i + 5 < utfString.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(
                        utfString.substring(i + 2, i + 6), 16));
            }
        }

        return sb.toString();
    }

    public static String clearSpecialChars(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static String ToDBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);

            }
        }
        String returnString = new String(c);

        return returnString;
    }

    public static String buildFileName(String fileName) throws UnsupportedEncodingException {
        String downFileName = fileName;
        if (Util.isFirefox()) {
            downFileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
        } else {
            downFileName = URLEncoder.encode(fileName, "UTF-8");
        }
        return downFileName;
    }

    public static boolean isFirefox() {
        HttpServletRequest request = ServletActionContext.getRequest();
        if ("FF".equals(getBrowser())) {
            return true;
        }
        return false;
    }

    public static String getBrowser() {
        HttpServletRequest request = ServletActionContext.getRequest();
        return getBrowser(request);
    }

    private static String getBrowser(HttpServletRequest request) {
        String UserAgent = request.getHeader("USER-AGENT").toLowerCase();
        if (UserAgent != null) {
            if (UserAgent.indexOf("msie") >= 0)
                return "IE";
            if (UserAgent.indexOf("firefox") >= 0)
                return "FF";
            if (UserAgent.indexOf("safari") >= 0)
                return "SF";
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(ToDBC(clearSpecialChars("5ＣＢＤ")));
    }

    public static String swapString(String str) {
        if (!Util.valid(str)) {
            return "";
        }
        String res = "";
        int len = str.length();
        res = str.substring(len - 1, len) + str.substring(1, len - 1) + str.substring(0, 1);
        return res;
    }

    /**
     * 分站名字 不规范处理
     * 去掉多余前缀
     * @param departName 要处理的分站名
     * @return
     */
    public static String dealDepartName(String departName) {
        if(departName.startsWith("5")){
           departName= departName.substring(departName.indexOf("5")+1);
        }else if(departName.contains("-")){
            departName=departName.substring(departName.lastIndexOf("-")+1);
        }else if(departName.contains("－")){
            departName=departName.substring(departName.lastIndexOf("－")+1);
        }
        return departName.trim();
    }

    public static Map<String,Object> paramMap(){
        return new HashMap<String,Object>();
    }

    public static Integer getInteger0(Object value) {
        return valid(value) ? Integer.valueOf(value.toString()) : 0;
    }
}