package ldy.bigdata.gather;

import ldy.bigdata.gather.entities.DatabaseInfo;
import ldy.bigdata.gather.entities.TestInfo;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class test {
    @Test
    public void aa() throws Exception {

        Path path = Paths.get("E:/");
        for (Path p : Files.newDirectoryStream(path)) {
            if (!Files.isDirectory(p)) {
                continue;
            }
            System.out.println(p.getFileName());
        }
    }

    @Test
    public void methodTest() throws Exception {
        Function<Integer, String[]> fun = (args) -> new String[args];
        String[] strs = fun.apply(10);
        System.out.println(strs.length);
        System.out.println(strs);
    }
    @Test
    public void methodTest2() throws Exception {
      String  type="s.b";
      String[] s=type.split("\\.");
      System.out.println(s);

    }
}
