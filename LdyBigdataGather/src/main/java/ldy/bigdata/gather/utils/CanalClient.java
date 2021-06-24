package ldy.bigdata.gather.utils;


import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.exception.CanalClientException;
import ldy.bigdata.gather.config.CanalConfig;
import com.alibaba.otter.canal.client.CanalConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;


public class CanalClient {
    static final Logger log = LoggerFactory.getLogger(CanalClient.class);
    String destination;
    CanalConfig canalConfig;
    String subscribe;
    private CanalConnector canalConnector;

    public String name() {
        return this.destination;
    }

    public CanalClient(String destination, CanalConfig canalConfig, String subscribe) {
        this.destination = destination;
        this.canalConfig = canalConfig;
        this.subscribe = subscribe;
        getConnect();
    }


    public CanalConnector getConnect() {
        if (this.canalConnector == null) {
            this.canalConnector = CanalConnectors.newSingleConnector(
                    new InetSocketAddress(this.canalConfig.getServerIp(), this.canalConfig.getPort()),
                    this.destination,
                    this.canalConfig.getUserName(),
                    this.canalConfig.getPasswd());
            this.canalConnector.connect();
            this.canalConnector.subscribe(this.subscribe);
        }
        return this.canalConnector;
    }

    public void disconnect() {
        try {
            this.canalConnector.disconnect();
        } catch (CanalClientException e) {
            log.error("释放canal连接失败！", e);
        }
        this.canalConnector = null;
    }
}
