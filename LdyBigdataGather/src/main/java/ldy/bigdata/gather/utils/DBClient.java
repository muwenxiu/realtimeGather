package ldy.bigdata.gather.utils;

import org.apache.jute.compiler.JString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBClient {
    static final Logger log = LoggerFactory.getLogger(DBClient.class);
    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    private String ip;
    private int port;
    private String user;
    private String pwd;
    private String database;

    public DBClient(String ip, int port, String user, String pwd, String database) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
        this.database = database;
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DBClient.JDBC_DRIVER);
            String url = "";
            if (this.database == null) {
                url = "jdbc:mariadb://" + this.ip + ":" + this.port + "/";
            } else {
                url = "jdbc:mariadb://" + this.ip + ":" + this.port + "/" + this.database;
            }
            connection = DriverManager.getConnection(url, this.user, this.pwd);
        } catch (ClassNotFoundException se) {
            log.error("获取数据库连接失败！", se);
        } catch (Exception e) {
            log.error("获取数据库连接失败！", e);
        }
        return connection;
    }

    public <R> List<R> execute(String sql, Class<R> clazz) {
        List<R> result = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                R r = clazz.newInstance();
                for (Field field : fields) {
                    String fieldName = field.getName();
                    Class filedClass = field.getType();
                    if (String.class.equals(filedClass)) {
                        String value = resultSet.getString(fieldName);
                        field.setAccessible(true);
                        field.set(r, value);
                    } else if (int.class.equals(filedClass)) {
                        int value = resultSet.getInt(fieldName);
                        field.setAccessible(true);
                        field.set(r, value);
                    } else if (short.class.equals(filedClass)) {
                        short value = resultSet.getShort(fieldName);
                        field.setAccessible(true);
                        field.set(r, value);
                    } else if (long.class.equals(filedClass)) {
                        Long value = resultSet.getLong(fieldName);
                        field.setAccessible(true);
                        field.set(r, value);
                    } else if (double.class.equals(filedClass)) {
                        Double value = resultSet.getDouble(fieldName);
                        field.setAccessible(true);
                        field.set(r, value);
                    } else if (float.class.equals(filedClass)) {
                        Float value = resultSet.getFloat(fieldName);
                        field.setAccessible(true);
                        field.set(r, value);
                    } else if (java.sql.Date.class.equals(filedClass)) {
                        java.sql.Date value = resultSet.getDate(fieldName);
                        field.setAccessible(true);
                        field.set(r, value);
                    } else if (java.sql.Time.class.equals(filedClass)) {
                        java.sql.Time value = resultSet.getTime(fieldName);
                        field.setAccessible(true);
                        field.set(r, value);
                    }
                }
                result.add(r);
            }
        } catch (Exception e) {
            log.error("数据封装失败！", e);
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
        return result;
    }

    public List<Map<String, String>> execute(String sql) {
        List<Map<String, String>> result = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            while (resultSet.next()) {
                Map<String, String> r = new HashMap<>();
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    String columnName = metaData.getColumnName(i + 1);
                    String value = resultSet.getString(columnName);
                    r.put(columnName, value);
                }
                result.add(r);
            }
        } catch (Exception e) {
            log.error("数据封装失败！", e);
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
        return result;
    }

    public Map<String, String> executeOne(String sql) {
        Connection connection = null;
        Statement statement = null;
        Map<String, String> r = new HashMap<>(10);
        try {
            connection = getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            if (resultSet.next()) {
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    String columnName = metaData.getColumnName(i + 1);
                    String value = resultSet.getString(columnName);
                    r.put(columnName, value);
                }
            }
        } catch (Exception e) {
            log.error("数据封装失败！", e);
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
        return r;
    }
}
