package com.work.recycle.utils;

import com.work.recycle.controller.vo.AddressVo;
import com.work.recycle.controller.vo.IndexOrderVo;
import com.work.recycle.entity.BaseOrder;
import com.work.recycle.entity.GarbageChoose;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VoUtil {

    /**
     * 根据给定的垃圾选择集合，构造msg，用来为前端订单列表显示 构造数据
     * @param garbageChooses the garbageChoose list
     * @return (String) 长度不超过13的字符串
     */
    private static String getMsg(List<GarbageChoose> garbageChooses) {
        Iterator<GarbageChoose> iterator = garbageChooses.iterator();
        StringBuilder msg = new StringBuilder();
        while (iterator.hasNext()) {
            GarbageChoose garbageChoose = iterator.next();
            msg.append(garbageChoose.getGarbage().getName()).append("*").append(garbageChoose.getAmount());
            if (msg.length() > 10) {
                break;
            }
            msg.append(",");
        }

        if (msg.length() > 10) {
            msg.delete(10,msg.length());
        }
        msg.append("...");
        return msg.toString();
    }

    /**
     * 根据一条基础订单（baseOrder),生成一条订单vo
     * @param baseOrder the baseOrder
     * @return 订单Vo
     */
    public static IndexOrderVo constructIndexOrder(BaseOrder baseOrder) {
            IndexOrderVo indexOrderVo = new IndexOrderVo();
            indexOrderVo.setProvince(baseOrder.getProvince());
            indexOrderVo.setCity(baseOrder.getCity());
            indexOrderVo.setArea(baseOrder.getArea());
            indexOrderVo.setStreet(baseOrder.getStreet());
            indexOrderVo.setVillage(baseOrder.getVillage());
            indexOrderVo.setAddress(baseOrder.getAddress());
            indexOrderVo.setId(baseOrder.getId());
            indexOrderVo.setMsg(getMsg(baseOrder.getGarbageChooses()));
            indexOrderVo.setDate(baseOrder.getInsertTime());
            return indexOrderVo;
    }

    public static String getAddresslevel(AddressVo addressVo) {
        // 从 村 -> 街道 -> 区 -> 市 -> 省 依次判空，如果区域级别小，则按照小的查询
        if (addressVo == null) {
            return AddressVo.NONE;
        } else if (addressVo.getVillage() != null) {
            return AddressVo.VILLAGE;
        } else if (addressVo.getStreet() != null) {
            return AddressVo.STREET;
        } else if (addressVo.getArea() != null) {
            return AddressVo.AREA;
        } else if (addressVo.getCity() != null) {
            return AddressVo.CITY;
        } else if (addressVo.getProvince() != null) {
            return AddressVo.PROVINCE;
        } else {
            return AddressVo.NONE;
        }
    }
}
