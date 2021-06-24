package ldy.bigdata.gather.mapper.impala;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CollectDataQuoteItem {
    /***
     * 报价_采购品聚集
     * @return
     */
    boolean insertQuoteItemPurchase(@Param("ids") List<Long> ids);

    boolean insertQuoteItemTender(@Param("ids") List<Long> ids);

    boolean insertQuoteItemAuction1(@Param("ids") List<Long> ids);

    boolean insertQuoteItemAuction2(@Param("ids") List<Long> ids);


}
