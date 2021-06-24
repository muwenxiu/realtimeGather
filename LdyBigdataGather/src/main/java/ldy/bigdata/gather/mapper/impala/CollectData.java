package ldy.bigdata.gather.mapper.impala;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CollectData {
    boolean insertRecruitItem(@Param("recruitID") String recruitID, @Param("catalogIDs") List<Long> catalogIDs);
}
