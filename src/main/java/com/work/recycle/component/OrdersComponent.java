package com.work.recycle.component;

import com.sun.xml.bind.v2.TODO;
import com.work.recycle.entity.*;
import com.work.recycle.exception.Asserts;
import com.work.recycle.repository.*;
import com.work.recycle.utils.SwitchUtil;
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
    private CDOrderRepository  cdOrderRepository;
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
    public void addOrder(BaseOrder baseOrder, List<GarbageChoose> garbageChooses,String opt) {
        switch (opt) {
            case SwitchUtil.CDORDER :
                addCDOrder(baseOrder,garbageChooses);
                break;
            case SwitchUtil.FCORDER :
                addFCOrder(baseOrder,garbageChooses);
                break;
            case SwitchUtil.CRORDER :
                addCROrder(baseOrder,garbageChooses);
                break;
            case SwitchUtil.DTORDER :
                addDTOrder(baseOrder,garbageChooses);
                break;
        }

        addBaseOrderGarbageList(baseOrder,garbageChooses);

    }

    public void checkOrder(BaseOrder baseOrder,List<GarbageChoose> garbageChooses,String opt) {
        switch (opt) {
            case SwitchUtil.FCORDER:
                checkFCOrder(baseOrder,garbageChooses);
                break;
            case SwitchUtil.CDORDER:
                checkCDOrder(baseOrder,garbageChooses);
                break;
            case SwitchUtil.CRORDER:
                checkCROrder(baseOrder,garbageChooses);
                break;
        }

        addBaseOrderGarbageList(baseOrder, garbageChooses);

    }

    private void checkFCOrder(BaseOrder baseOrder,List<GarbageChoose> garbageChooses) {
        FCOrder fcOrder = fcOrderRepository.getFCOrderById(baseOrder.getId());
        Cleaner cleaner = cleanerRepository.getCleanerById(CLEANERUID);
        fcOrder.setCleaner(cleaner);
        baseOrder.setScore(getScore(garbageChooses));
        baseOrderRepository.save(baseOrder);
    }

    private void checkCDOrder(BaseOrder baseOrder,List<GarbageChoose> garbageChooses) {
        CDOrder cdOrder = cdOrderRepository.getCDOrderById(baseOrder.getId());
        Driver driver = driverRepository.getDriverById(DRIVERUID);
        cdOrder.setDriver(driver);
        baseOrder.setScore(getScore(garbageChooses));
        baseOrderRepository.save(baseOrder);
    }

    private void checkCROrder(BaseOrder baseOrder,List<GarbageChoose> garbageChooses) {
        CROrder crOrder = crOrderRepository.getCROrderById(baseOrder.getId());
        RecycleFirm recycleFirm = recycleFirmRepository.getRecycleFirmById(RECYCLEFIRMUID);
        crOrder.setRecycleFirm(recycleFirm);
        baseOrder.setScore(getScore(garbageChooses));
        baseOrderRepository.save(baseOrder);
    }

    private void addFCOrder(BaseOrder baseOrder,List<GarbageChoose> garbageChooses) {
        FCOrder fcOrder = new FCOrder();

        // int uid = requestComponent.getUid();
        Farmer farmer = farmerRepository.getFarmerById(FARMERUID);
        baseOrder.setScore(getScore(garbageChooses));
        fcOrder.setBaseOrder(baseOrder);
        fcOrder.setFarmer(farmer);

        fcOrderRepository.save(fcOrder);
    }


    private void addCDOrder(BaseOrder baseOrder,List<GarbageChoose> garbageChooses) {
        CDOrder cdOrder = new CDOrder();

        // int CLEANERUID = requestComponent.getUid();
        Cleaner cleaner = cleanerRepository.getCleanerById(CLEANERUID);
        baseOrder.setScore(getScore(garbageChooses));
        cdOrder.setBaseOrder(baseOrder);
        cdOrder.setCleaner(cleaner);

        cdOrderRepository.save(cdOrder);
    }

    private void addDTOrder(BaseOrder baseOrder,List<GarbageChoose> garbageChooses) {
        DTOrder dtOrder = new DTOrder();

        TransferStation transferStation = transferStationRepository.getTransferStationById(TRANSFERSTATIONUID);
        baseOrder.setScore(getScore(garbageChooses));
        dtOrder.setBaseOrder(baseOrder);
        dtOrder.setTransferStation(transferStation);

        dtOrderRepository.save(dtOrder);
    }

    private void addCROrder(BaseOrder baseOrder,List<GarbageChoose> garbageChooses) {
        CROrder crOrder = new CROrder();

        RecycleFirm recycleFirm = recycleFirmRepository.getRecycleFirmById(RECYCLEFIRMUID);
        baseOrder.setScore(getScore(garbageChooses));
        crOrder.setBaseOrder(baseOrder);
        crOrder.setRecycleFirm(recycleFirm);


        crOrderRepository.save(crOrder);

    }

    // 最后写到这里，将getscore方法剥离出来，但是想到会不会影响到check和receive方法
    private int getScore(List<GarbageChoose> garbageChooses) {
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


    private void addBaseOrderGarbageList(BaseOrder baseOrder,List<GarbageChoose> garbageChooses) {
        garbageChooses.forEach(each -> {
            int garbageId = each.getGarbage().getId();
            Optional<Garbage> garbage = garbageRepository.findById(garbageId);
            garbage.ifPresentOrElse(each::setGarbage
                    , () -> Asserts.fail("数据集错误"));
            each.setBaseOrder(baseOrder);
            chooseRepository.save(each);
        });
    }


}
