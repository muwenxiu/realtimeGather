package ldy.bigdata.gather.controller;

import ldy.bigdata.gather.entities.CanalChannelStatus;
import ldy.bigdata.gather.service.InitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ModuleController {
    static final Logger log = LoggerFactory.getLogger(ModuleController.class);
    @Autowired
    private InitConfig initConfig;

    @RequestMapping("/realtimeGather_instance")
    public String realtimeGather_instance(Model model) {
        return "modules/realtimeGather_instance :: table";
    }

    @RequestMapping("/realtimeGather_Table")
    public String realtimeGather_Table() {
        return "modules/realtimeGather_Table :: table";
    }

    @RequestMapping("/realtimeGather_canalChannel")
    public String realtimeGather_canalChannel() {
        return "modules/realtimeGather_canalChannelStatus :: table";
    }

    @RequestMapping("/realtimeGather_canalChannelProgress")
    public String realtimeGather_canalChannelProgress() {
        return "modules/realtimeGather_canalChannelProgress :: table";
    }

    @RequestMapping("/onTimeGatherBatch")
    public String onTimeGatherBatch() {
        return "modules/onTimeGather :: table";
    }

    @RequestMapping("/onTimeGatherBatchDel")
    public String onTimeGatherBatchDel() {
        return "modules/onTimeGatherBatchDel :: table";
    }
    @RequestMapping("/analyseTemplate")
    public String analyseTemplate() {
        return "modules/analyse :: table";
    }

    @RequestMapping("/analyseHistoryDel")
    public String analyseHistoryDel() {
        return "modules/analyseHistoryDel :: table";
    }
}
