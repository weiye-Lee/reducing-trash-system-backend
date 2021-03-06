package com.work.recycle.controller.vo;

import java.util.HashMap;
import java.util.Map;

public class GarbageVo {

    private static final String PAPER = "paper";
    private static final String PLASTIC = "plastic";
    private static final String GLASS = "glass";
    private static final String METAL = "metal";
    private static final String WEAVE = "weave";
    private static final String PESTICIDE = "pesticide";
    private static final String OTHRERS = "others";
    private static final String KITCHEN = "kitchen";

    private static final String type = "纸张类";
    private static final String type1 = "塑料类";
    private static final String type2 = "玻璃类";
    private static final String type3 = "金属类";
    private static final String type4 = "纺织品";
    private static final String type5 = "金属类";
    private static final String type6 = "农药包装";
    private static final String type7 = "其他垃圾";
    private static final String type8 = "厨余垃圾";


    public static final String RECYCLE_CATEGORY = "recycleGarbage";
    public static final String UNRECYCLE_CATEGORY = "unRecycleGarbage";
    public static final String SOIL_CATEGORY = "soil";

    // 垃圾类别中英文转换map
    public static final Map<String,String> typeCHName = createTypeCHName();

    // 垃圾大类中英文转换map
    public static final Map<String,String> categoryCHName = createCategoryCHName();

    // 创建垃圾类别中英文map
    private static Map<String,String> createTypeCHName() {
        Map<String,String> map = new HashMap<>();
        map.put(PAPER,type);
        map.put(PLASTIC,type1);
        map.put(GLASS,type2);
        map.put(METAL,type3);
        map.put(WEAVE,type4);
        map.put(PESTICIDE,type6);
        map.put(OTHRERS,type7);
        map.put(KITCHEN,type8);
        return map;
    }

    // 创建垃圾大类中英文名map （e.g Map<"unRecycleGarbage","不可回收垃圾">)
    private static Map<String,String> createCategoryCHName() {
        Map<String,String> map = new HashMap<>();
        map.put(RECYCLE_CATEGORY,"可回收垃圾");
        map.put(UNRECYCLE_CATEGORY,"不可回收垃圾");
        map.put(SOIL_CATEGORY,"自利用垃圾");
        return map;
    }

    // 可回收垃圾类别数组
    public static String[] recycleTypeName = {PAPER,PLASTIC,GLASS,METAL,WEAVE};

    // 不可回收垃圾类别数组
    public static String[] UnRecycleTypeName = {METAL,PESTICIDE,OTHRERS,KITCHEN};

    public static Boolean iscategory(String category) {
        boolean bool = false;
        if (categoryCHName.get(RECYCLE_CATEGORY).equals(category)
        || categoryCHName.get(UNRECYCLE_CATEGORY).equals(category)
        || categoryCHName.get(SOIL_CATEGORY).equals(category)) {
            bool = true;
        }
        return bool;

    }
}
