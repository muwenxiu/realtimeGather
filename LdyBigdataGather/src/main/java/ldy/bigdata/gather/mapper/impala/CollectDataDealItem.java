package ldy.bigdata.gather.mapper.impala;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CollectDataDealItem {
    /***
     * 成交_采购品聚集
     * @return
     */
    boolean insertDealItemPurchase(@Param("ids") List<Long> ids);

    boolean insertDealItemTender(@Param("ids") List<Long> ids);

    boolean insertDealItemAuction(@Param("ids") List<Long> ids);

    boolean insertDealItemOrder(@Param("ids") List<Long> ids);

    boolean insertDealItemContract(@Param("ids") List<Long> ids);
}
