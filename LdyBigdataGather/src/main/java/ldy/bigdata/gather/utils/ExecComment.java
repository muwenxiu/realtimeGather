package ldy.bigdata.gather.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ExecComment {
    static final Logger log = LoggerFactory.getLogger(ExecComment.class);

    public static List<String> exec(String[] cmds) {
        List<String> result = new ArrayList<>();
        try {
            log.info("linux 命令：" + cmds[cmds.length - 1]);
            Process process = Runtime.getRuntime().exec(cmds);
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(cmds[cmds.length - 1])) {
                    continue;
                }
                result.add(line);
            }
            log.info("linux 命令返回的行数：" + result.size());
            log.info("linux 命令返回的内容：");
            for (String re : result) {
                log.info(re);
            }
            log.info("--------------------------------------------");
        } catch (Exception e) {
            log.error("执行linux命令错误", e);
        }
        return result;
    }

    public static List<String> exec(String command) {
        List<String> result = new ArrayList<>();
        try {
            log.info("linux 命令：" + command);
            Process process = Runtime.getRuntime().exec(command);
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(command)) {
                    continue;
                }
                result.add(line);
            }
            log.info("linux 命令返回的行数：" + result.size());
            log.info("linux 命令返回的内容：");
            for (String re : result) {
                log.info(re);
            }
            log.info("--------------------------------------------");
        } catch (Exception e) {
            log.error("执行linux命令错误", e);
        }
        return result;
    }

}
