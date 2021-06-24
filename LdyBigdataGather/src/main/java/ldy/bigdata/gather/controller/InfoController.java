package ldy.bigdata.gather.controller;

import ldy.bigdata.gather.entities.CanalChannelStatus;
import ldy.bigdata.gather.entities.CanalGatherProgress;
import ldy.bigdata.gather.entities.MysqlColumnInfo;
import ldy.bigdata.gather.entities.SingleData;
import ldy.bigdata.gather.service.AsyncService;
import ldy.bigdata.gather.service.InitConfig;
import ldy.bigdata.gather.service.MysqlTableColumn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/get")
public class InfoController {
    @Autowired
    private InitConfig initConfig;

    //@ApiOperation(value = "etl任务管理查询接口--etl任务管理查询接口", notes = "获取任务状态（即 coordinator 的状态）,时间格式必须是 yyyy-MM-dd HH:mm:ss 的格式")
    @RequestMapping("/mysqlDatabase")
    public List<SingleData> getMysqlDatabase() {
        List<SingleData> lst = initConfig.getMysqlDatabase();
        return lst;
    }

    @RequestMapping("/getCanalChannel")
    public List<CanalChannelStatus> getCanalChannel() {
        List<CanalChannelStatus> lst = initConfig.getCanalChannel();
        return lst;
    }

    @RequestMapping("/getCanalGatherProgress")
    public List<CanalGatherProgress> getCanalGatherProgress() {
        List<CanalGatherProgress> lst = initConfig.getCanalGatherProgress();
        return lst;
    }

    @RequestMapping("/initMysqlColumn")
    public boolean initMysqlColumn() {
        return initConfig.initMysqlColumn();
    }

    @Autowired
    private MysqlTableColumn mysqlTableColumn;

    @RequestMapping("/mysqlColumn")
    public List<MysqlColumnInfo> getMysqlTableColumn(
            @RequestParam(value = "databaseName", required = true) String databaseName,
            @RequestParam(value = "tableNames", required = true) String tableNames
    ) {
        List<MysqlColumnInfo> lst = mysqlTableColumn.getMysqlColumnInfo(databaseName, tableNames);
        return lst;
    }

    @Autowired
    private AsyncService asyncService;

    @RequestMapping("/async")
    public void async() {
        asyncService.executeAsync();
    }
}
