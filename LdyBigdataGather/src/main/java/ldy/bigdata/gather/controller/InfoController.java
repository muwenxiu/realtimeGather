package ldy.bigdata.gather.controller;

import ldy.bigdata.gather.entities.*;
import ldy.bigdata.gather.mapper.sqlite.OpsDao;
import ldy.bigdata.gather.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

    @RequestMapping("/getRestartService")
    public boolean getRestartService(
            @RequestParam(value = "serviceName", required = true) String serviceName,
            @RequestParam(value = "serviceStartScript", required = true) String serviceStartScript) {
        return BackstageService.RestartBackstageServiceStatus(serviceName, serviceStartScript);
    }

    @RequestMapping("/getOnTimeGatherWarning")
    public List<OnTimeGatherWarningInfo> getOnTimeGatherWarning() {
        List<OnTimeGatherWarningInfo> lst = opsDao.getOnTimeGatherWarning();
        return lst;
    }

    @Autowired
    private DataVerify dataVerify;

    @RequestMapping("/getDataQualityOdsWarning")
    public List<TableDataCount> getDataQualityOdsWarning() {
        List<TableDataCount> result = dataVerify.getOdsDataVerify();
        return result;
    }

    @RequestMapping("/getDataQualityDwdWarning")
    public List<TableDataCount> getDataQualityDwdWarning() {
        List<TableDataCount> result = new ArrayList<>();
        //TODO
        return result;
    }

    @RequestMapping("/getTableDataQualityMonth")
    public List<TableDataCount> getTableDataQualityMonth(
            @RequestParam String mysqlTableName
    ) {
        List<TableDataCount> result = dataVerify.getDataQualityWarning_Month(mysqlTableName);
        return result;
    }

    @RequestMapping("/getTableDataQualityDay")
    public List<TableDataCount> getTableDataQualityDay(
            @RequestParam String mysqlTableName,
            @RequestParam String dataDate
    ) {
        List<TableDataCount> result = dataVerify.getDataQualityWarning_Day(mysqlTableName, dataDate);
        return result;
    }

    /***
     * 重新采集：按照月重新采集
     * @param mysqlTableName
     * @param dataDate
     * @return
     */
    @RequestMapping("/reGatherData_month")
    public boolean reGatherData_month(
            @RequestParam(value = "mysqlTableName") String mysqlTableName,
            @RequestParam(value = "dataDate") String dataDate
    ) {
        List<TableDataCount> result = dataVerify.getDataQualityWarning_Day(mysqlTableName, dataDate);
        return true;
    }

    @RequestMapping("/reGatherData_month_deleteSql")
    public String reGatherData_month_deleteSql(
            @RequestParam(value = "mysqlTableName") String mysqlTableName,
            @RequestParam(value = "dataDate") String dataDate
    ) {
        return dataVerify.reGatherData_month_deleteSql(mysqlTableName, dataDate);

    }

    @RequestMapping("/reGatherData_month_insertSql")
    public String reGatherData_month_insertSql(
            @RequestParam(value = "mysqlTableName") String mysqlTableName,
            @RequestParam(value = "dataDate") String dataDate
    ) {
        return dataVerify.reGatherData_month_insertSql(mysqlTableName, dataDate);

    }

    @RequestMapping("/getGatherProgress")
    public String getGatherProgress(
            @RequestParam(value = "mysqlTableName") String mysqlTableName,
            @RequestParam(value = "dataDate") String dataDate
    ) {
        return  dataVerify.getGatherProgress(mysqlTableName, dataDate);
    }

    /***
     * 重新采集：按照月叠加采集
     * @param mysqlTableName
     * @param dataDate
     * @return
     */
    @RequestMapping("/recoverGather_month")
    public boolean recoverGather_month(
            @RequestParam(value = "mysqlTableName") String mysqlTableName,
            @RequestParam(value = "dataDate") String dataDate
    ) {
        List<TableDataCount> result = dataVerify.getDataQualityWarning_Day(mysqlTableName, dataDate);
        return true;
    }

    /***
     * 重新采集：按照日重新采集
     * @param mysqlTableName
     * @param dataDate
     * @return
     */
    @RequestMapping("/reGatherData_day")
    public boolean reGatherData_day(
            @RequestParam(value = "mysqlTableName") String mysqlTableName,
            @RequestParam(value = "dataDate") String dataDate
    ) {
        List<TableDataCount> result = dataVerify.getDataQualityWarning_Day(mysqlTableName, dataDate);
        return true;
    }

    /***
     * 重新采集：按照日叠加采集
     * @param mysqlTableName
     * @param dataDate
     * @return
     */
    @RequestMapping("/recoverGather_day")
    public boolean recoverGather_day(
            @RequestParam(value = "mysqlTableName") String mysqlTableName,
            @RequestParam(value = "dataDate") String dataDate
    ) {
        List<TableDataCount> result = dataVerify.getDataQualityWarning_Day(mysqlTableName, dataDate);
        return true;
    }

}
