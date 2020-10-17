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

    private static final String type = "纸张类";
    private static final String type1 = "塑料类";
    private static final String type2 = "玻璃类";
    private static final String type3 = "金属类";
    private static final String type4 = "纺织品";
    private static final String type5 = "金属类";
    private static final String type6 = "农药包装";


    public static final String RECYCLE_CATEGORY = "recycleGarbage";
    public static final String UNRECYCLE_CATEGORY = "unRecycleGarbage";
    public static final String SOIL_CATEGORY = "soil";

    public static final Map<String,String> typeCHName = createTypeCHName();

    public static final Map<String,String> categoryCHName = createCategoryCHName();

    private static Map<String,String> createTypeCHName() {
        Map<String,String> map = new HashMap<>();
        map.put(PAPER,type);
        map.put(PLASTIC,type1);
        map.put(GLASS,type2);
        map.put(METAL,type3);
        map.put(WEAVE,type4);
        map.put(PESTICIDE,type6);
        return map;
    }

    private static Map<String,String> createCategoryCHName() {
        Map<String,String> map = new HashMap<>();
        map.put(RECYCLE_CATEGORY,"可回收垃圾");
        map.put(UNRECYCLE_CATEGORY,"不可回收垃圾");
        map.put(SOIL_CATEGORY,"煤渣、灰土");
        return map;
    }

    public static String[] recycleTypeName = {PAPER,PLASTIC,GLASS,METAL,WEAVE};

    public static String[] UnRecycleTypeName = {METAL,PESTICIDE};


}
