package ldy.bigdata.gather;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class impala {
    static String JDBC_DRIVER = "com.cloudera.impala.jdbc41.Driver";
    static String CONNECTION_URL = "jdbc:impala://172.16.1.92:21050/";
//    static String JDBC_DRIVER = "org.apache.hive.jdbc.HiveDriver";
//    static String CONNECTION_URL = "jdbc:hive2://172.16.10.76:21050/;auth=noSasl";

    public static void main(String[] args) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(CONNECTION_URL);
            ps = con.prepareStatement("select * from default.my_partrition_test");
            rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(1)  );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
