package com.work.recycle.component;

import com.work.recycle.common.ResultCode;
import com.work.recycle.controller.vo.AddressVo;
import com.work.recycle.controller.vo.IndexOrderVo;
import com.work.recycle.controller.vo.SiftOrderVo;
import com.work.recycle.exception.ApiException;
import com.work.recycle.utils.VoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SiftOrdersComponent {

    /**
     * 根据时间和位置过滤订单列表
     *
     * @param indexOrderVos 订单列表集合
     * @param siftOrderVo   过滤控制条件
     * @return 过滤后的订单列表集合
     */
    public List<IndexOrderVo> siftOrders(List<IndexOrderVo> indexOrderVos, SiftOrderVo siftOrderVo) {
        // 判空 ，控制因子为空，则说明不需要过滤
        if (indexOrderVos == null || siftOrderVo == null) {
            return indexOrderVos;
        }
        log.warn(siftOrderVo.getCol());
        switch (siftOrderVo.getCol()) {
            case SiftOrderVo.A:
                indexOrderVos = filterByAddress(indexOrderVos, siftOrderVo);
                break;
            case SiftOrderVo.T:
                indexOrderVos = filterByTime(indexOrderVos, siftOrderVo);
                break;
            case SiftOrderVo.T_A:
                indexOrderVos = filterByTimeAndAddress(indexOrderVos, siftOrderVo);
                break;
        }
        return indexOrderVos;
    }

    private static List<IndexOrderVo> siftOrdersByAddress(List<IndexOrderVo> indexOrderVos, AddressVo addressVo) {
        log.warn(VoUtil.getAddresslevel(addressVo));
        switch (VoUtil.getAddresslevel(addressVo)) {
            case AddressVo.VILLAGE:
                indexOrderVos = indexOrderVos.stream()
                        .filter(indexOrderVo -> indexOrderVo.getVillage().equals(addressVo.getVillage()))
                        .collect(Collectors.toList());
                break;
            case AddressVo.STREET:
                indexOrderVos = indexOrderVos.stream()
                        .filter(indexOrderVo -> indexOrderVo.getStreet().equals(addressVo.getStreet()))
                        .collect(Collectors.toList());
                break;
            case AddressVo.AREA:
                indexOrderVos = indexOrderVos.stream()
                        .filter(indexOrderVo -> indexOrderVo.getArea().equals(addressVo.getArea()))
                        .collect(Collectors.toList());
                break;
            case AddressVo.CITY:
                indexOrderVos = indexOrderVos.stream()
                        .filter(indexOrderVo -> indexOrderVo.getCity().equals(addressVo.getCity()))
                        .collect(Collectors.toList());
                break;
            case AddressVo.PROVINCE:
                indexOrderVos = indexOrderVos.stream()
                        .filter(indexOrderVo -> indexOrderVo.getProvince().equals(addressVo.getProvince()))
                        .collect(Collectors.toList());
                break;
        }
        return indexOrderVos;
    }

    private static List<IndexOrderVo> filterByAddress(List<IndexOrderVo> indexOrderVos, SiftOrderVo siftOrderVo) {
        if (siftOrderVo.getAddressVo() == null) throw new ApiException(ResultCode.RESOURCE_NOT_FOUND);
        return siftOrdersByAddress(indexOrderVos, siftOrderVo.getAddressVo());
    }

    private static List<IndexOrderVo> filterByTime(List<IndexOrderVo> indexOrderVos, SiftOrderVo siftOrderVo) {
        if (siftOrderVo.getStartTime() == null && siftOrderVo.getEndTime() == null)
            throw new ApiException(ResultCode.RESOURCE_NOT_FOUND);
        log.warn("{}",siftOrderVo.getEndTime());
        log.warn("{}",siftOrderVo.getStartTime());
        return indexOrderVos.stream()
                .filter(indexOrderVo -> indexOrderVo.getDate().compareTo(siftOrderVo.getEndTime()) < 0)
                .filter(indexOrderVo -> indexOrderVo.getDate().compareTo(siftOrderVo.getStartTime()) > 0)
                .collect(Collectors.toList());
    }

    private static List<IndexOrderVo> filterByTimeAndAddress(List<IndexOrderVo> indexOrderVos, SiftOrderVo siftOrderVo) {
        if ((siftOrderVo.getStartTime() == null && siftOrderVo.getEndTime() == null) || siftOrderVo.getAddressVo() == null)
            throw new ApiException(ResultCode.RESOURCE_NOT_FOUND);
        indexOrderVos = indexOrderVos.stream()
                .filter(indexOrderVo -> indexOrderVo.getDate().compareTo(siftOrderVo.getEndTime()) < 0)
                .filter(indexOrderVo -> indexOrderVo.getDate().compareTo(siftOrderVo.getStartTime()) > 0)
                .collect(Collectors.toList());
        return siftOrdersByAddress(indexOrderVos, siftOrderVo.getAddressVo());
    }
}
