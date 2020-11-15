package com.work.recycle.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.common.ResultCode;
import com.work.recycle.component.OrdersComponent;
import com.work.recycle.component.RequestComponent;
import com.work.recycle.entity.*;
import com.work.recycle.exception.ApiException;
import com.work.recycle.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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

    /**
     * 中转站订单列表查询
     * @return 基础订单集合 ——> 中转站需要展示的信息极少，故直接返回基础订单
     */
    public List<BaseOrder> getDTOrders() {
        int uid = requestComponent.getUid();
        return dtOrderRepository.getBaseOrdersByTransferStation(uid);
    }

    public String getDriverNameById(int id) {
        User user = userRepository.getUserById(id);
        if (user.getRole() != User.Role.DRIVER) {
            throw new ApiException(ResultCode.FORBIDDEN);
        }
        return user.getName();
    }

}
