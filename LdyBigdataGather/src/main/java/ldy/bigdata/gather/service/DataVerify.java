package ldy.bigdata.gather.service;

import ldy.bigdata.gather.entities.DatabaseInfoExtend;
import ldy.bigdata.gather.entities.TableDataCount;
import ldy.bigdata.gather.mapper.sqlite.OpsDao;
import ldy.bigdata.gather.utils.DBClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DataVerify {
    @Autowired
    private OpsDao opsDao;
    @Autowired
    @Qualifier("impalaJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public List<TableDataCount> getOdsDataVerify() {
        List<TableDataCount> result = new ArrayList<>();
        List<DatabaseInfoExtend> lstTableInfo = opsDao.getGatherTableInfo();
        long start = System.nanoTime();
        result = lstTableInfo.parallelStream().map(
                (tableInfo) -> {
                    TableDataCount tdc = new TableDataCount();
                    tdc.setTableName(tableInfo.getTableName());
                    tdc.setGetCntError(false);
                    try {
                        DBClient dbClient = new DBClient(tableInfo.getIp(), tableInfo.getPort(), tableInfo.getUser(), tableInfo.getPwd(), tableInfo.getDataBase());
                        String sql = "select count(1) as cnt from " + tableInfo.getTableName();
                        Map<String, String> cntMap = dbClient.executeOne(sql);
                        tdc.setSrcTableCount(Integer.valueOf(cntMap.get("cnt")));
                        //////////////////////////////////////////////////////////
                        sql = "select count(1) as cnt from " + tableInfo.getKuduTableName();
                        Map<String, Object> kuduCnt = jdbcTemplate.queryForMap(sql);
                        Object cnt = kuduCnt.get("cnt");
                        tdc.setTarTableCount(Integer.valueOf(cnt.toString()));
                    } catch (Exception e) {
                        tdc.setGetCntError(true);
                    }
                    return tdc;
                }
        ).collect(Collectors.toList());
        System.out.println(System.nanoTime() - start);
        return result;
    }
}
