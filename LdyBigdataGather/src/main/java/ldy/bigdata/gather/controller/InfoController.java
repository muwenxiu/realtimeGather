package ldy.bigdata.gather.controller;

import ldy.bigdata.gather.entities.*;
import ldy.bigdata.gather.mapper.sqlite.OpsDao;
import ldy.bigdata.gather.service.AsyncService;
import ldy.bigdata.gather.service.BackstageService;
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
    private OpsDao opsDao;


    @RequestMapping("/gatherTables")
    public List<KeyValue> gatherTables() {
        List<KeyValue> lst = opsDao.gatherTables();
        return lst;
    }

    @RequestMapping("/gatherTableColumns")
    public List<MysqlColumnInfo> gatherTableColumns(
            @RequestParam(value = "mysqlTableName", required = true) String mysqlTableName
    ) {
        String tableName = mysqlTableName;
        List<MysqlColumnInfo> lst = opsDao.getMysqlColumnInfo(tableName);
        return lst;
    }

    @RequestMapping("/getGatherBatch")
    public List<KeyValue> getGatherBatch(
            @RequestParam(value = "status", required = true) String status
    ) {
        List<KeyValue> lst = opsDao.gatherBatch(status);
        return lst;
    }

    @RequestMapping("/delGatherBatch")
    public boolean delGatherBatch(
            @RequestParam(value = "batchId", required = true) long batchId
    ) {
        boolean lst = opsDao.delGatherBatch(batchId);
        lst = opsDao.delHistoryGatherTask(batchId);
        return lst;
    }

    @RequestMapping("/getDataGatherBatchInfo")
    public List<BatchInfo> getDataGatherBatchInfo(
            @RequestParam(value = "batch", required = true) long batch
    ) {
        long batchID = batch;
        List<BatchInfo> lst = opsDao.gatherBatchInfo(batchID);
        return lst;
    }

    @RequestMapping("/getDataGatherInfo")
    public List<GatherInfo> getDataGatherInfo(@RequestParam(value = "batch", required = true) long batch) {
        long batchID = batch;
        List<GatherInfo> lst = opsDao.gatherInfo(batchID);
        return lst;
    }


    @RequestMapping("/getAnalyseBatch")
    public List<KeyValue> getAnalyseBatch(
            @RequestParam(value = "status", required = true) String status
    ) {
        List<KeyValue> lst = opsDao.getAnalyseBatch(status);
        return lst;
    }

    @RequestMapping("/delAnalyseTask")
    public boolean delAnalyseTask(
            @RequestParam(value = "time", required = true) String time
    ) {
        boolean lst = opsDao.delAnalyseTask(time);
        return lst;
    }

    @RequestMapping("/getAnalyseBatchTime")
    public String getAnalyseBatchTime(
            @RequestParam(value = "batchID", required = true) String batchId
    ) {
        String lst = opsDao.getAnalyseBatchTime(batchId);
        return lst;
    }

    @RequestMapping("/getAnalyseTaskInfo")
    public List<AnalyseTaskInfo> getAnalyseTaskInfo(
            @RequestParam(value = "batchId", required = true) String batchId
    ) {
        List<AnalyseTaskInfo> lst = opsDao.getAnalyseTaskInfo(batchId);
        return lst;
    }

    @Autowired
    private AsyncService asyncService;

    @RequestMapping("/async")
    public void async() {
        asyncService.executeAsync();
    }


    @RequestMapping("/getBackstageService")
    public List<ServiceInfo> getBackstageService() {
        List<ServiceInfo> lst = opsDao.getBackstageService();
        BackstageService.getBackstageServiceStatus(lst);
        return lst;
    }

    // @RequestParam(value = "batchId", required = true) long batchId
    @RequestMapping("/getRestartService")
    public boolean getRestartService(
            @RequestParam(value = "serviceName", required = true) String serviceName,
            @RequestParam(value = "serviceStartScript", required = true) String serviceStartScript ) {
        return BackstageService.RestartBackstageServiceStatus(serviceName,serviceStartScript);
    }
}
