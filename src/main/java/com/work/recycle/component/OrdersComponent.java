package com.work.recycle.component;

import com.work.recycle.common.ResultCode;
import com.work.recycle.entity.*;
import com.work.recycle.exception.ApiException;
import com.work.recycle.exception.Asserts;
import com.work.recycle.repository.*;
import com.work.recycle.utils.SwitchUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;


/**
 * 插入，接收，审核订单统一处理组件
 * 接下来可以用泛型去简化代码
 */
@Component
@Slf4j
public class OrdersComponent {
    @Autowired
    private FarmerRepository farmerRepository;
    @Autowired
    private FCOrderRepository fcOrderRepository;
    @Autowired
    private GarbageRepository garbageRepository;
    @Autowired
    private GarbageChooseRepository chooseRepository;
    @Autowired
    private CleanerRepository cleanerRepository;
    @Autowired
    private CDOrderRepository cdOrderRepository;
    @Autowired
    private TransferStationRepository transferStationRepository;
    @Autowired
    private DTOrderRepository dtOrderRepository;
    @Autowired
    private CROrderRepository crOrderRepository;
    @Autowired
    private RecycleFirmRepository recycleFirmRepository;
    @Autowired
    private BaseOrderRepository baseOrderRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private RequestComponent requestComponent;

    // ----------- 仅用于生产环境下 ------------
    private static final int FARMERUID = 1;
    private static final int CLEANERUID = 5;
    private static final int DRIVERUID = 6;
    private static final int RECYCLEFIRMUID = 8;
    private static final int TRANSFERSTATIONUID = 9;
    // ----------------------------------------

    /**
     * 插入订单统一处理方法
     *
     * @param baseOrder      the base order
     * @param garbageChooses the garbage chooses
     * @param opt            switch 的条件
     */
    public void addOrder(BaseOrder baseOrder, List<GarbageChoose> garbageChooses, String opt) {
        switch (opt) {
            case SwitchUtil.CDORDER:
                addCDOrder(baseOrder, garbageChooses);
                break;
            case SwitchUtil.FCORDER:
                addFCOrder(baseOrder, garbageChooses);
                break;
            case SwitchUtil.CRORDER:
                addCROrder(baseOrder, garbageChooses);
                break;
            case SwitchUtil.DTORDER:
                addDTOrder(baseOrder, garbageChooses);
                break;
        }

        addBaseOrderGarbageList(baseOrder, garbageChooses);

    }

    public void checkOrder(BaseOrder baseOrder, List<GarbageChoose> garbageChooses, String opt) {
        switch (opt) {
            case SwitchUtil.FCORDER:
                checkFCOrder(baseOrder, garbageChooses);
                break;
            case SwitchUtil.CDORDER:
                checkCDOrder(baseOrder, garbageChooses);
                break;
            case SwitchUtil.CRORDER:
                checkCROrder(baseOrder, garbageChooses);
                break;
        }

        addBaseOrderGarbageList(baseOrder, garbageChooses);

    }

    private void checkFCOrder(BaseOrder baseOrder, List<GarbageChoose> garbageChooses) {
        int uid = requestComponent.getUid();
        FCOrder fcOrder = fcOrderRepository.getFCOrderById(baseOrder.getId());
        Cleaner cleaner = cleanerRepository.getCleanerById(uid);

        // 增加农户积分
        Farmer farmer = fcOrder.getFarmer();
        farmer.addScore(getScore(garbageChooses));
        farmerRepository.save(farmer);

        fcOrder.setCleaner(cleaner);
        baseOrder.setScore(getScore(garbageChooses));
        baseOrderRepository.save(baseOrder);
    }

    private void checkCDOrder(BaseOrder baseOrder, List<GarbageChoose> garbageChooses) {
        int uid = requestComponent.getUid();
        CDOrder cdOrder = cdOrderRepository.getCDOrderById(baseOrder.getId());
        Driver driver = driverRepository.getDriverById(uid);

        // 增加保洁员积分
        Cleaner cleaner = cdOrder.getCleaner();
        cleaner.addScore(getScore(garbageChooses));
        cleanerRepository.save(cleaner);

        cdOrder.setDriver(driver);
        baseOrder.setScore(getScore(garbageChooses));
        baseOrderRepository.save(baseOrder);
    }

    private void checkCROrder(BaseOrder baseOrder, List<GarbageChoose> garbageChooses) {
        int uid = requestComponent.getUid();
        CROrder crOrder = crOrderRepository.getCROrderById(baseOrder.getId());
        RecycleFirm recycleFirm = recycleFirmRepository.getRecycleFirmById(uid);


        Cleaner cleaner = crOrder.getCleaner();
        cleaner.addScore(getScore(garbageChooses));
        cleanerRepository.save(cleaner);

        crOrder.setRecycleFirm(recycleFirm);
        baseOrder.setScore(getScore(garbageChooses));
        baseOrderRepository.save(baseOrder);
    }

    private void addFCOrder(BaseOrder baseOrder, List<GarbageChoose> garbageChooses) {
        int uid = requestComponent.getUid();
        log.warn("{}", uid);
        FCOrder fcOrder = new FCOrder();
        Farmer farmer = farmerRepository.getFarmerById(uid);
        Cleaner cleaner = farmer.getCleaner();
        baseOrder.setScore(getScore(garbageChooses));
        fcOrder.setBaseOrder(baseOrder);
        fcOrder.setFarmer(farmer);
        fcOrder.setCleaner(cleaner);
        fcOrderRepository.save(fcOrder);
    }


    private void addCDOrder(BaseOrder baseOrder, List<GarbageChoose> garbageChooses) {
        int uid = requestComponent.getUid();
        CDOrder cdOrder = new CDOrder();
        Cleaner cleaner = cleanerRepository.getCleanerById(uid);
        Driver driver = cleaner.getDriver();
        baseOrder.setScore(getScore(garbageChooses));
        cdOrder.setBaseOrder(baseOrder);
        cdOrder.setCleaner(cleaner);
        cdOrder.setDriver(driver);

        cdOrderRepository.save(cdOrder);
    }

    private void addDTOrder(BaseOrder baseOrder, List<GarbageChoose> garbageChooses) {
        int uid = requestComponent.getUid();
        DTOrder dtOrder = new DTOrder();

        TransferStation transferStation = transferStationRepository.getTransferStationById(uid);
        baseOrder.setScore(getScore(garbageChooses));
        dtOrder.setBaseOrder(baseOrder);
        dtOrder.setTransferStation(transferStation);

        dtOrderRepository.save(dtOrder);
    }

    private void addCROrder(BaseOrder baseOrder, List<GarbageChoose> garbageChooses) {
        int uid = requestComponent.getUid();
        CROrder crOrder = new CROrder();

        RecycleFirm recycleFirm = recycleFirmRepository.getRecycleFirmById(uid);
        baseOrder.setScore(getScore(garbageChooses));
        crOrder.setBaseOrder(baseOrder);
        crOrder.setRecycleFirm(recycleFirm);


        crOrderRepository.save(crOrder);

    }

    private int getScore(List<GarbageChoose> garbageChooses) {
        try {
            Iterator<GarbageChoose> iterator = garbageChooses.iterator();
            int score = 0;
            while (iterator.hasNext()) {
                GarbageChoose choose = iterator.next();
                int id = choose.getGarbage().getId();
                Garbage garbage = garbageRepository.getGarbageById(id);
                score += choose.getAmount() * garbage.getScore();

            }
            return score;
        }
        catch (NullPointerException e) {
            throw new ApiException(ResultCode.RESOURCE_NOT_FOUND);
        }

    }


    /*
    private void addBaseOrderGarbageList(BaseOrder baseOrder, List<GarbageChoose> garbageChooses) {
        garbageChooses.forEach(each -> {
            int garbageId = each.getGarbage().getId();
            Optional<Garbage> garbage = garbageRepository.findById(garbageId);
            garbage.ifPresentOrElse(each::setGarbage
                    , () -> {
                        throw new ApiException(ResultCode.RESOURCE_NOT_FOUND);
                    });
            each.setBaseOrder(baseOrder);
            chooseRepository.save(each);
        });
    }

     */

    // TODO : 2020/10/13 : 重构插入基础垃圾集合方法
    private void addBaseOrderGarbageList(BaseOrder baseOrder, List<GarbageChoose> garbageChooses) {
        Optional.ofNullable(garbageChooses)
                .ifPresent(item -> item.forEach(each -> garbageRepository.findById(
                        Optional.ofNullable(each)
                                .map(GarbageChoose::getGarbage)
                                .map(Garbage::getId)
                                .orElseGet(() -> {
                                    throw new ApiException(ResultCode.RESOURCE_NOT_FOUND);
                                })
                        ).ifPresentOrElse(garbage -> {
                            each.setGarbage(garbage);
                            each.setBaseOrder(baseOrder);
                            chooseRepository.save(each);
                        }
                        , () -> {
                            throw new ApiException(ResultCode.RESOURCE_NOT_FOUND);
                        }))
                );
    }

}
