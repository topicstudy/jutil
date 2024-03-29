package cn.topicstudy.jutil.area.division;

/**
 * 中国省份 33个
 */
public enum ProvinceEnum {
    BEI_JING("11", "北京市"),
    TIAN_JING("12", "天津市"),
    HE_BEI("13", "河北省"),
    SHAN_XI_1("14", "山西省"),// 1:第一声
    NEI_MEN_GU("15", "内蒙古自治区"),
    LIAO_NING("21", "辽宁省"),
    JI_LING("22", "吉林省"),
    HEI_LONG_JIANG("23", "黑龙江省"),
    SHANG_HAI("31", "上海市"),
    JIANG_SU("32", "江苏省"),
    ZHE_JIANG("33", "浙江省"),
    AN_HUI("34", "安徽省"),
    FU_JIAN("35", "福建省"),
    JIANG_XI("36", "江西省"),
    SHAN_DONG("37", "山东省"),
    HE_NAN("41", "河南省"),
    HU_BEI("42", "湖北省"),
    HU_NAN("43", "湖南省"),
    GUANG_DONG("44", "广东省"),
    GUANG_XI("45", "广西壮族自治区"),
    HAI_NAN("46", "海南省"),
    CHONG_QING("50", "重庆市"),
    SI_CHUAN("51", "四川省"),
    GUI_ZHOU("52", "贵州省"),
    YUN_NAN("53", "云南省"),
    XI_ZANG("54", "西藏自治区"),
    SHAN_XI_2("61", "陕西省"),
    GAN_SU("62", "甘肃省"),
    QING_HAI("63", "青海省"),
    LING_XIA("64", "宁夏回族自治区"),
    XING_JIANG("65", "新疆维吾尔自治区"),
    TAI_WAN("71", "台湾省"),
    XIANG_GANG("81", "香港特别行政区"),
    AO_MEN("82", "澳门特别行政区");

    private String code;
    private String name;

    ProvinceEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }


    /**
     * 根据省代码获取省
     * 充血写法
     */
    public static ProvinceEnum getProvinceByCode(String code) {
        ProvinceEnum[] provinceEnums = ProvinceEnum.values();
        for (ProvinceEnum provinceEnum : provinceEnums) {
            if (provinceEnum.getCode().equals(code)) return provinceEnum;
        }
        throw new RuntimeException("省代码 %s 不存在".format(code));
    }
}
