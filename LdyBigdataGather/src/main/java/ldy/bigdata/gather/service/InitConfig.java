package ldy.bigdata.gather.service;

import ldy.bigdata.gather.entities.CanalChannelStatus;
import ldy.bigdata.gather.entities.CanalGatherProgress;
import ldy.bigdata.gather.entities.SingleData;

import java.util.List;

public interface InitConfig {
    List<SingleData> getMysqlDatabase();

    List<CanalChannelStatus> getCanalChannel();

    List<CanalGatherProgress> getCanalGatherProgress();

    boolean initMysqlColumn();
}
