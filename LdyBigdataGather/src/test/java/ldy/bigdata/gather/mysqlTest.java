package ldy.bigdata.gather;

import ldy.bigdata.gather.utils.DBClient;

import java.sql.*;

public class mysqlTest {
    public static void main(String args[]) {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String url = "jdbc:mariadb://slave-bigdata-mariadb.internal.cg-xt.com:3306/purchase";
            connection = DriverManager.getConnection(url, "ldbigdata", "#EDC2wss");
            statement = connection.createStatement();
            String sql = " select\n" +
                    "        lower(concat(TABLE_SCHEMA,'.',table_name) ) as  tableName,\n" +
                    "        lower(COLUMN_NAME) as columnName,\n" +
                    "        lower(DATA_TYPE)  as columnType\n" +
                    "        from information_schema.columns b\n" +
                    "        where  lower(b.TABLE_SCHEMA)= 'purchase'\n" +
                    "         and lower(b.table_name) in ( 'purchase_project' )\n" +
                    "        order by b.ORDINAL_POSITION";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {

            }
        } catch (ClassNotFoundException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException se2) {
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
