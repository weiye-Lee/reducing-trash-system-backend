package com.work.recycle.component;

import com.work.recycle.common.ResultCode;
import com.work.recycle.entity.*;
import com.work.recycle.exception.ApiException;
import com.work.recycle.exception.Asserts;
import com.work.recycle.repository.*;
import com.work.recycle.utils.SwitchUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;


/**
 * 订单插入 审核组件
 *
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

        FCOrder fcOrder = fcOrderRepository.getFCOrderById(baseOrder.getId());
        if (fcOrder == null || fcOrder.getBaseOrder() == null || fcOrder.getFarmer() == null)
            throw new ApiException(ResultCode.RESOURCE_NOT_FOUND);
        double score = getScore(garbageChooses);
        addBaseOrderGarbageList(baseOrder, garbageChooses);

        fcOrder.getFarmer().setScore(score);
        fcOrder.getBaseOrder().setScore(score);
        fcOrder.getBaseOrder().setCheckStatus(true);
        fcOrderRepository.save(fcOrder);

    }

    private void checkCDOrder(BaseOrder baseOrder, List<GarbageChoose> garbageChooses) {

        CDOrder cdOrder = cdOrderRepository.getCDOrderById(baseOrder.getId());
        if (cdOrder == null || cdOrder.getBaseOrder() == null || cdOrder.getCleaner() == null)
            throw new ApiException(ResultCode.RESOURCE_NOT_FOUND);
        double score = getScore(garbageChooses);

        addBaseOrderGarbageList(baseOrder, garbageChooses);

        cdOrder.getCleaner().setScore(score);
        cdOrder.getBaseOrder().setCheckStatus(true);
        cdOrder.getBaseOrder().setScore(score);
        cdOrderRepository.save(cdOrder);
    }

    /**
     * 功能暂时不需要
     *
     * @param baseOrder      the baseOrder
     * @param garbageChooses the garbageChoose list
     */
    private void checkCROrder(BaseOrder baseOrder, List<GarbageChoose> garbageChooses) {

    }

    private void addFCOrder(BaseOrder baseOrder, List<GarbageChoose> garbageChooses) {
        int uid = requestComponent.getUid();
        log.warn("{}", uid);
        Farmer farmer = farmerRepository.getFarmerById(uid);
        ofNullable(farmer)
                .map(Farmer::getCleaner)
                .ifPresentOrElse(cleaner -> {
                    FCOrder fcOrder = new FCOrder();
                    fcOrder.setCleaner(cleaner);
                    fcOrder.setFarmer(farmer);
                    baseOrder.setScore(getScore(garbageChooses));
                    fcOrder.setBaseOrder(baseOrder);
                    fcOrderRepository.save(fcOrder);
                }, () -> {
                    throw new ApiException(ResultCode.FAILED);
                });
    }


    private void addCDOrder(BaseOrder baseOrder, List<GarbageChoose> garbageChooses) {
        int uid = requestComponent.getUid();
        Cleaner cleaner = cleanerRepository.getCleanerById(uid);
        ofNullable(cleaner)
                .map(Cleaner::getDriver)
                .ifPresentOrElse(driver -> {
                    CDOrder cdOrder = new CDOrder();
                    cdOrder.setCleaner(cleaner);
                    cdOrder.setDriver(driver);
                    baseOrder.setScore(getScore(garbageChooses));
                    cdOrder.setBaseOrder(baseOrder);
                    cdOrderRepository.save(cdOrder);
                }, () -> {
                    throw new ApiException(ResultCode.FAILED);
                });
    }

    /**
     * 功能暂时不需要
     *
     * @param baseOrder      the baseOrder
     * @param garbageChooses the garbageChoose list
     */
    private void addDTOrder(BaseOrder baseOrder, List<GarbageChoose> garbageChooses) {

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

    public double getScore(List<GarbageChoose> garbageChooses) {
        DecimalFormat df = new DecimalFormat("0.0");
        try {
            Iterator<GarbageChoose> iterator = garbageChooses.iterator();
            double score = 0.0;
            while (iterator.hasNext()) {
                GarbageChoose choose = iterator.next();
                int id = choose.getGarbage().getId();
                Garbage garbage = garbageRepository.getGarbageById(id);
                score += Double.parseDouble(
                        df.format(choose.getAmount() * garbage.getScore())
                );
            }
            log.warn("score:{}", score);
            return score;
        } catch (NullPointerException e) {
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

    public void addBaseOrderGarbageList(BaseOrder baseOrder, List<GarbageChoose> garbageChooses) {
        ofNullable(garbageChooses)
                .ifPresent(item -> item.forEach(each -> garbageRepository.findById(
                        ofNullable(each)
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
