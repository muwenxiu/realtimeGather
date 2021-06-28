package ldy.bigdata.gather.service.impl;

import ldy.bigdata.gather.entities.MysqlColumnInfo;
import ldy.bigdata.gather.service.MysqlTableColumn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MysqlTableColumnImpl implements MysqlTableColumn {
    @Autowired
    @Qualifier("sysConfigAuction")
    private ldy.bigdata.gather.mapper.auction.SysConfig auctionSysConfig;

    @Autowired
    @Qualifier("sysConfigBusinessOpportunityMatch")
    private ldy.bigdata.gather.mapper.businessopportunitymatch.SysConfig businessopportunitymatchSysConfig;

    @Autowired
    @Qualifier("sysConfigContract")
    private ldy.bigdata.gather.mapper.contract.SysConfig contractSysConfig;

    @Autowired
    @Qualifier("sysConfigOrder")
    private ldy.bigdata.gather.mapper.order.SysConfig orderSysConfig;

    @Autowired
    @Qualifier("sysConfigPurchase")
    private ldy.bigdata.gather.mapper.purchase.SysConfig purchaseSysConfig;

    @Autowired
    @Qualifier("sysConfigRecruit")
    private ldy.bigdata.gather.mapper.recruit.SysConfig recruitSysConfig;

    @Autowired
    @Qualifier("sysConfigSpace")
    private ldy.bigdata.gather.mapper.space.SysConfig spaceSysConfig;

    @Autowired
    @Qualifier("sysConfigTender")
    private ldy.bigdata.gather.mapper.tender.SysConfig tenderSysConfig;

    @Autowired
    @Qualifier("sysConfigUresacl")
    private ldy.bigdata.gather.mapper.uresacl.SysConfig uresaclSysConfig;

    @Autowired
    @Qualifier("sysConfigUserCenter")
    private ldy.bigdata.gather.mapper.userCenter.SysConfig userCenterSysConfig;


    @Override
    public List<MysqlColumnInfo> getMysqlColumnInfo(String databaseName, String tableNames) {
        List<MysqlColumnInfo> lst = new ArrayList<>(100);
        switch (databaseName.toLowerCase()) {
            case "auction":
                lst = auctionSysConfig.getMysqlColumnInfo(databaseName, tableNames);
                break;
            case "businessopportunitymatch":
                lst = businessopportunitymatchSysConfig.getMysqlColumnInfo(databaseName, tableNames);
                break;
            case "contract":
                lst = contractSysConfig.getMysqlColumnInfo(databaseName, tableNames);
                break;
            case "order":
                lst = orderSysConfig.getMysqlColumnInfo(databaseName, tableNames);
                break;
            case "purchase":
                lst = purchaseSysConfig.getMysqlColumnInfo(databaseName, tableNames);
                break;
            case "recruit":
                lst = recruitSysConfig.getMysqlColumnInfo(databaseName, tableNames);
                break;
            case "space":
                lst = spaceSysConfig.getMysqlColumnInfo(databaseName, tableNames);
                break;
            case "tender":
                lst = tenderSysConfig.getMysqlColumnInfo(databaseName, tableNames);
                break;
            case "uresacl":
                lst = uresaclSysConfig.getMysqlColumnInfo(databaseName, tableNames);
                break;
            case "usercenter":
                lst = userCenterSysConfig.getMysqlColumnInfo(databaseName, tableNames);
                break;
        }
        return lst;
    }

}
