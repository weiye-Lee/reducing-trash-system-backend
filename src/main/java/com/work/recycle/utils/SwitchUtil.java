package com.work.recycle.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OrdersComponent组件switch方法统一识别符
 */
@Data
@NoArgsConstructor
public class SwitchUtil {
    public static final String CDORDER = "CDOrder";
    public static final String FCORDER = "CFOrder";
    public static final String CRORDER = "CROrder";
    public static final String DTORDER = "DTOrder";

}
