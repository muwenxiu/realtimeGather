package ldy.bigdata.gather.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ExecComment {
    public static List<String> exec(String[] cmds) {
        List<String> result = new ArrayList<>();
        try {
            Process process = Runtime.getRuntime().exec(cmds);
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                result.add(line);
            }
            return null;
        } catch (Exception e) {

        }
        return result;
    }
}
