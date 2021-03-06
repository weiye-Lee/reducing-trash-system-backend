package com.work.recycle.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.common.ResultCode;
import com.work.recycle.component.ConstructVoComponent;
import com.work.recycle.component.OrdersComponent;
import com.work.recycle.component.RequestComponent;
import com.work.recycle.controller.vo.AddressVo;
import com.work.recycle.controller.vo.IndexOrderVo;
import com.work.recycle.controller.vo.SiftOrderVo;
import com.work.recycle.entity.*;
import com.work.recycle.exception.ApiException;
import com.work.recycle.exception.Asserts;
import com.work.recycle.repository.*;
import com.work.recycle.utils.SwitchUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

/**
 * The type Cleaner service.
 */
@Service
@Slf4j
public class CleanerService {
    @Autowired
    private RecycleFirmCompanyRepo companyRepo;
    @Autowired
    private FCOrderRepository fcOrderRepository;
    @Autowired
    private BaseOrderRepository baseOrderRepository;
    @Autowired
    private OrdersComponent ordersComponent;
    @Autowired
    private RequestComponent requestComponent;
    @Autowired
    private CleanerRepository cleanerRepository;
    @Autowired
    private CDOrderRepository cdOrderRepository;
    @Autowired
    private ConstructVoComponent constructVoComponent;
    @Autowired
    private FarmerRepository farmerRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private GarbageChooseRepository chooseRepository;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private WasteRecordRepository wasteRecordRepository;
    @Autowired
    private CROrderRepository crOrderRepository;
    @Autowired
    private RecycleDriverRepository recycleDriverRepository;
    @Autowired
    private RecycleFirmRepository firmRepository;

    public int getFCOrderTimes() {
        int uid = requestComponent.getUid();
        return fcOrderRepository.getCleanerFCOrderTimesById(uid);
    }

    // 审核农户订单
    public void checkFCOrder(BaseOrder order, List<GarbageChoose> garbageChooses) {
        ordersComponent.checkOrder(order, garbageChooses, SwitchUtil.FCORDER);
    }

    public BaseOrder receiveFCOrder(int id) {
        FCOrder fcOrder = fcOrderRepository.getFCOrderById(id);
        BaseOrder baseOrder = baseOrderRepository.getBaseOrderById(id);
        if (baseOrder == null) {
            Asserts.fail("数据集错误");
        }
        baseOrder.setReceiveStatus(true);
        baseOrderRepository.save(baseOrder);
        return baseOrder;

    }

    public void addCDOrder(BaseOrder order, List<GarbageChoose> garbageChooses) {

        ordersComponent.addOrder(order, garbageChooses, SwitchUtil.CDORDER);

    }


    /**
     * 获取保洁员审核完成的订单
     *
     * @return 订单vo list
     */
    public List<IndexOrderVo> getFCOrderChecked(SiftOrderVo siftOrderVo) {
        return constructVoComponent.getCommonOrders(true, SwitchUtil.FCORDER, siftOrderVo);

    }


    /**
     * 获取保洁员审核完成的订单
     *
     * @return 订单vo list
     */
    public List<IndexOrderVo> getFCOrderChecking(SiftOrderVo siftOrderVo) {
        return constructVoComponent.getCommonOrders(false, SwitchUtil.FCORDER, siftOrderVo);
    }

    public int getScore() {
        int uid = requestComponent.getUid();
        return cleanerRepository.getScoreById(uid);
    }

    // TODO 2020:11/15 代码重构
    public List<Cleaner> getRankList(AddressVo addressVo) {
        // 从 村 -> 街道 -> 区 -> 市 -> 省 依次判空，如果区域级别小，则按照小的查询
        if (addressVo == null) {
            return cleanerRepository.findTop10ByOrderByScoreDesc();
        } else if (addressVo.getVillage() != null) {
            return cleanerRepository.findTop10ByUser_VillageOrderByScoreDesc(addressVo.getVillage());
        } else if (addressVo.getStreet() != null) {
            return cleanerRepository.findTop10ByUser_StreetOrderByScoreDesc(addressVo.getStreet());
        } else if (addressVo.getArea() != null) {
            return cleanerRepository.findTop10ByUser_AreaOrderByScoreDesc(addressVo.getArea());
        } else if (addressVo.getCity() != null) {
            return cleanerRepository.findTop10ByUser_CityOrderByScoreDesc(addressVo.getCity());
        } else if (addressVo.getProvince() != null) {
            return cleanerRepository.findTop10ByUser_ProvinceOrderByScoreDesc(addressVo.getProvince());
        }

        return cleanerRepository.findTop10ByOrderByScoreDesc();

    }


    public List<IndexOrderVo> getCDOrderChecking(SiftOrderVo siftOrderVo) {
        return constructVoComponent.getCommonOrders(false, SwitchUtil.CDORDER, siftOrderVo);
    }

    public List<IndexOrderVo> getCDOrderChecked(SiftOrderVo siftOrderVo) {
        return constructVoComponent.getCommonOrders(true, SwitchUtil.CDORDER, siftOrderVo);
    }

    /**
     * 保洁员获取 CleanerRecycleFirm订单 ，没有预约审核关系
     *
     * @param siftOrderVo 过滤因子
     * @return the IndexOrderVo list
     */
    public List<IndexOrderVo> getCROrders(SiftOrderVo siftOrderVo) {
        return constructVoComponent.getCommonOrders(false, SwitchUtil.CRORDER, siftOrderVo);
    }

    public Farmer addFarmer(User user) {
        int uid = requestComponent.getUid();
        Cleaner cleaner = cleanerRepository.getCleanerById(uid);
        user.setRole(User.Role.FARMER);
        user.setPassword(encoder.encode("123456"));
        Farmer farmer = new Farmer();
        farmer.setUser(user);
        farmer.setCleaner(cleaner);
        farmerRepository.save(farmer);
        return farmer;
    }

    public List<Farmer> getFarmerRankList() {
        int uid = requestComponent.getUid();
        return farmerRepository.findTop10ByCleaner_IdOrderByScoreDesc(uid);
    }

    // TODO 2020/11/17 权限验证，只应该可以删除提交的 自己 的订单
    public CDOrder removeCDOrder(int id) {
        CDOrder cdOrder = cdOrderRepository.getCDOrderById(id);
        try {
            log.warn(mapper.writeValueAsString(cdOrder));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (cdOrder == null || cdOrder.getBaseOrder() == null) {
            throw new ApiException(ResultCode.VALIDATE_FAILED);
        } else if (cdOrder.getBaseOrder().getCheckStatus() == null) {
            throw new ApiException("审核完成订单不允许删除");
        }
        cdOrderRepository.delete(cdOrder);
        chooseRepository.getGarbageChooseByBaseOrder_Id(id)
                .forEach(garbageChoose -> {
                    log.warn("{}", garbageChoose.getId());
                    chooseRepository.delete(garbageChoose);
                });
        baseOrderRepository.deleteById(id);
        return cdOrder;

    }

    public List<Farmer> getFarmer() {
        int id = requestComponent.getUid();
        return farmerRepository.findByCleaner_Id(id);
    }

    public Farmer getFarmerIndex(int id) {
        return farmerRepository.getFarmerById(id);

    }

    // 保洁员添加危废垃圾
    public int addWaste(WasteRecord wasteRecord) {
        int uid = requestComponent.getUid();
        Cleaner cleaner = cleanerRepository.getCleanerById(uid);
        wasteRecord.setCleaner(cleaner);
        wasteRecordRepository.save(wasteRecord);
        return uid;
    }

    public int addCROrder(CROrder crOrder) {
        int uid = requestComponent.getUid();
        log.warn("{}", uid);
        Cleaner cleaner = cleanerRepository.getCleanerById(uid);
        if (crOrder == null) {
            throw new ApiException(ResultCode.RESOURCE_NOT_FOUND);
        }

        crOrder.setCleaner(cleaner);

        List<GarbageChoose> garbageChooses = crOrder.getBaseOrder().getGarbageChooses();
        BaseOrder baseOrder = crOrder.getBaseOrder();
        crOrder.getBaseOrder().setGarbageChooses(null);
        crOrder.getBaseOrder().setScore(ordersComponent.getScore(garbageChooses));
        // 插入订单没有即使没有审核，也要插入价格信息
        crOrder.setTradePrice(ordersComponent.getCRPrice(garbageChooses));
        crOrderRepository.save(crOrder);
        ordersComponent.addBaseOrderGarbageList(baseOrder, garbageChooses);
        return 0;
    }

    public List<RecycleDriver> listDriver() {
        return recycleDriverRepository.findAll();
    }

    public RecycleDriver showDriver(String phone) {
        return null;
    }

//    public List<RecycleFirm> listRecycleFirm() {
//        return firmRepository.findAll();
//    }

    public List<RecycleFirmCompany> listRecycleFirm() {
        return companyRepo.findAll();
    }
    public List<IndexOrderVo> getCROrderChecked(SiftOrderVo siftOrderVo) {
        return constructVoComponent.getCommonOrders(true,SwitchUtil.CRORDER,siftOrderVo);
    }

    public List<IndexOrderVo> getCROrderChecking(SiftOrderVo siftOrderVo) {
        return constructVoComponent.getCommonOrders(false,SwitchUtil.CRORDER,siftOrderVo);

    }

    public CROrder getCROrder(int id) {
        return crOrderRepository.getCROrderById(id);
    }

    public int addFCOrder(BaseOrder baseOrder,List<GarbageChoose> garbageChooses) {
        int uid = requestComponent.getUid();
        Cleaner cleaner = cleanerRepository.getCleanerById(uid);
        FCOrder fcOrder = new FCOrder();
        double score = ordersComponent.getScore(garbageChooses);
        baseOrder.setScore(score);
        baseOrder.setCheckStatus(true);
        fcOrder.setBaseOrder(baseOrder);
//        fcOrder.getCleaner().addScore(score);
        fcOrder.setTradePrice(ordersComponent.getFCPrice(garbageChooses));
        fcOrder.setCleaner(cleaner);
        fcOrderRepository.save(fcOrder);
        ordersComponent.addBaseOrderGarbageList(baseOrder,garbageChooses);
        return uid;
    }


}
