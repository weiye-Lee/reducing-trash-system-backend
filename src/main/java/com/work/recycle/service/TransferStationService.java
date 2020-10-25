package com.work.recycle.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.common.ResultCode;
import com.work.recycle.component.OrdersComponent;
import com.work.recycle.component.RequestComponent;
import com.work.recycle.entity.*;
import com.work.recycle.exception.ApiException;
import com.work.recycle.repository.*;
import com.work.recycle.utils.SwitchUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
@Slf4j
public class TransferStationService {
    @Autowired
    private OrdersComponent ordersComponent;
    @Autowired
    private DTOrderRepository dtOrderRepository;
    @Autowired
    private TransferStationRepository transferStationRepository;
    @Autowired
    private RequestComponent requestComponent;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private BaseOrderRepository baseOrderRepository;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private UserRepository userRepository;

    // 中转站添加 司机-中转站订单
    public void addDTOrder(BaseOrder baseOrder, List<GarbageChoose> garbageChooses, int id) {
        int uid = requestComponent.getUid();
        TransferStation transferStation = transferStationRepository.getTransferStationById(uid);
        DTOrder dtOrder = new DTOrder();
        Driver driver = driverRepository.getDriverById(id);
        if (driver == null) {
            throw new ApiException(ResultCode.VALIDATE_FAILED);
        }
        dtOrder.setBaseOrder(baseOrder);
        dtOrder.setDriver(driver);
        dtOrder.setTransferStation(transferStation);
        dtOrderRepository.save(dtOrder);
        ordersComponent.addBaseOrderGarbageList(baseOrder,garbageChooses);
    }

    // TODO 2020/10/22 : 重构中转站获得订单记录（暂时不清楚要返回什么类型）
    public List<DTOrder> getDTOrders() {
        int uid = requestComponent.getUid();
        return dtOrderRepository.getDTOrdersByTransferStation(uid);
    }

}
