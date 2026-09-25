package cn.topicstudy.jutil.basic.number;

import cn.topicstudy.jutil.basic.text.StringUtil;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 专题学习大师 topicstudy.cn 2021/10/15
 */
public class NumberUtil {
    public static Map<String, String> chineseUppercaseMap() {
        Map<String, String> map = new HashMap();
        map.put("1", "壹");
        map.put("2", "贰");
        map.put("3", "叁");
        map.put("4", "肆");
        map.put("5", "伍");
        map.put("6", "陆");
        map.put("7", "柒");
        map.put("8", "捌");
        map.put("9", "玖");
        map.put("10", "拾");
        map.put("0", "零");
        map.put("百", "佰");
        map.put("千", "仟");
        map.put("万", "万");
        map.put("亿", "亿");
        map.put("元", "圆");
        return map;
    }

    /**
     * 获取钱数的大写
     *
     * @param money 钱数，如“123.45”、“38500”
     */
    public static String getMoneyUppercase(String money) {
        if (StringUtil.isBlank(money)) return "--";
        if (money.endsWith(".")) money = money.substring(0, money.length() - 1);

        boolean isInt;// 是否是整数
        String intSegment;// 整数部分
        String decimalSegment;// 小数部分
        String intRes = "";// 整数部分大写结果
        String decimalRes = "";// 小数部分大写结果
        // 单位, 万和亿位是0时，大写是写万、亿而非零; 元位是0也是写圆，而非零
        String[] intUnits = {"圆", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟"};
        final int supportIntLength = 12;// 最大可转换千亿,即整数部分12位
        final int supportDecimalLength = 2;// 最小可转换分，即小数部分2位
        Map<String, String> uppercaseMap = chineseUppercaseMap();

        // 判断是否是整数
        if (money.contains(".")) {
            String[] array = money.split("[.]");
            intSegment = array[0];
            decimalSegment = array[1];
            isInt = false;
        } else {
            intSegment = money;
            decimalSegment = "";
            isInt = true;
        }
        intSegment = StringUtil.reverse(intSegment);

        // 判断是否超出范围
        if (intSegment.length() > supportIntLength) throw new RuntimeException("最大可转换千亿,即整数部分12位");
        if (!isInt && decimalSegment.length() > supportDecimalLength)
            throw new RuntimeException("最小可转换分，即小数部分2位");

        // 整数部分转大写
        for (int i = 0; i < intSegment.length(); i++) {
            String n = intSegment.charAt(i) + "";
            String unit = intUnits[i];
            if ("0".equals(n)) {
                if ("万".equals(unit) || "亿".equals(unit)) {
                    // 万位或亿位是0，不写零而写万、亿
                    intRes = unit + intRes;
                } else if ("圆".equals(unit)) {
                    // 元位是0，如果整数部分只有零，大写写零圆，否则写圆
                    intRes = Integer.parseInt(intSegment) == 0 ? "零圆" : "圆";
                } else {
                    // 多个0大写时只写1个零,但整数部分如果多个0结尾，只写一个圆
                    intRes = (intRes.startsWith("零") || intRes.startsWith("圆") ? "" : "零") + intRes;
                }
            } else {
                intRes = uppercaseMap.get(n) + unit + intRes;
            }
        }

        // 小数部分转大写，小数部分最多2位，所以可以不用循环
        if (!isInt) {
            if (decimalSegment.length() == 1) {
                String n = decimalSegment.charAt(0) + "";
                if (!"0".equals(n)) decimalRes = uppercaseMap.get(n) + "角";
            } else if (decimalSegment.length() == 2) {
                String n1 = decimalSegment.charAt(0) + "";
                String n2 = decimalSegment.charAt(1) + "";
                decimalRes = ("0".equals(n1) ? "零" : uppercaseMap.get(n1) + "角") + ("0".equals(n2) ? "" : uppercaseMap.get(n2) + "分");
            }
        }

        return isInt ? "人民币" + intRes + "整" : "人民币" + intRes + decimalRes;
    }


    public static String formatIntegerWithSuffix(Integer a) {
        if (a == null) {
            return "";
        } else if (a < 1000) {
            return a.toString();
        } else if (a < 100_0000) {
            return keepOne(a / 1000.0) + "K";
        } else {
            return keepOne(a / 100_0000.0) + "M";
        }
    }

    private static String keepOne(double a) {
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        String s = decimalFormat.format(a);
        if (s.endsWith(".0")) {
            return s.substring(0, s.indexOf(".0"));
        }
        return s;
    }
}
