package org.smart4j.chapter2.helper;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.util.PropsUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class DatabaseHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    private static final String DRIVER;

    private static final String URL;

    private static final String USERNAME;

    private static final String PASSWORD;

    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL = new ThreadLocal<>();

    static {
        Properties config = PropsUtil.loadProps("config.properties");
        DRIVER = config.getProperty("jdbc.driver");
        URL = config.getProperty("jdbc.url");
        USERNAME = config.getProperty("jdbc.username");
        PASSWORD = config.getProperty("jdbc.password");

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("can not load jdbc driver" ,e);
        }
    }



    /*获取数据库链接*/
    public static Connection getConnection(){
        Connection conn = CONNECTION_THREAD_LOCAL.get();
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                LOGGER.error("get connection failure" , e);
            }finally {
                CONNECTION_THREAD_LOCAL.set(conn);
            }
        }
        return conn;
    }

    /*关闭数据库链接*/
    public static void closeConnection() {
        Connection conn = CONNECTION_THREAD_LOCAL.get();
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("close connection failure" , e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_THREAD_LOCAL.remove();
            }
        }
    }

    public static <T> List<T> queryEntityList(Class<T> entityClass , String sql, Object... params){
        List<T> entityList = null;
        Connection conn = getConnection();
        try {
            entityList = QUERY_RUNNER.query(conn,sql,new BeanListHandler<T>(entityClass),params);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure" , e);
        }finally {
            closeConnection();
        }
        return entityList;
    }
}
