package org.smart4j.chapter2.helper;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.util.PropsUtil;
import org.smart4j.chapter2.util.StringUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class DatabaseHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    private static final String DRIVER;

    private static final String URL;

    private static final String USERNAME;

    private static final String PASSWORD;

    private static final QueryRunner QUERY_RUNNER;

    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL;

    private static final BasicDataSource DATA_SOURCE;

    static {
        CONNECTION_THREAD_LOCAL = new ThreadLocal<Connection>();
        QUERY_RUNNER = new QueryRunner();

        Properties config = PropsUtil.loadProps("src/test/resources/config.properties");
        DRIVER = config.getProperty("jdbc.driver");
        URL = config.getProperty("jdbc.url");
        USERNAME = config.getProperty("jdbc.username");
        PASSWORD = config.getProperty("jdbc.password");

        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(DRIVER);
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setUsername(USERNAME);
        DATA_SOURCE.setPassword(PASSWORD);
    }


    /*获取数据库链接*/
    public static Connection getConnection() {
        Connection conn = CONNECTION_THREAD_LOCAL.get();
        if (conn == null) {
            try {
                conn = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                LOGGER.error("get connection failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_THREAD_LOCAL.set(conn);
            }
        }
        return conn;
    }



    /*查询所有对象*/
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> entityList = null;
        Connection conn = getConnection();
        try {
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure", e);
        }
        return entityList;
    }

    /*查询单个实体*/
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        T entity;
        try {
            Connection conn = getConnection();
            entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("query entity failure", e);
            throw new RuntimeException(e);
        }
        return entity;
    }

    /*执行查询语句*/
    public static List<Map<String, Object>> executeQuery(String sql, Object... params) {
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            Connection conn = getConnection();
            result = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            LOGGER.error("execute query failure", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /*执行更新语句(包括 update instert delete)*/
    public static int executeUpdate(String sql, Object... params) {
        int rows = 0;
        try {
            Connection conn = getConnection();
            rows = QUERY_RUNNER.update(conn, sql, params);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure", e);
        }
        return rows;
    }

    /*插入实体*/
    public static <T> boolean insterEntity(Class<T> entityClass, Map<String, Object> fieldMap) {
        if (fieldMap.isEmpty()) {
            LOGGER.error("can not insert entity:fieldMap is empty");
            return false;
        }
        String sql = "insert into " + getTableName(entityClass);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append(",");
            values.append("?,");
        }

        columns.replace(columns.lastIndexOf(","), columns.length(), ")");
        values.replace(values.lastIndexOf(","), values.length(), ")");
        sql += columns + "values" + values;

        Object[] params = fieldMap.values().toArray();

        return executeUpdate(sql ,params) == 1;
    }

    /*更新实体*/
    public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap) {
        if (fieldMap.isEmpty()) {
            LOGGER.error("can not insert entity:fieldMap is empty");
            return false;
        }

        String sql = "update " + getTableName(entityClass) + " set ";
        StringBuilder colums = new StringBuilder();
        for (String fieldName : fieldMap.keySet()) {
            colums.append(fieldName).append("=?, ");
        }
        sql += colums.substring(0, colums.lastIndexOf(",")) + " where id = ?";
        List<Object> paramList = new ArrayList<>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);

        Object[] params = paramList.toArray();

        return executeUpdate(sql, params) == 1;
    }

    /*删除实体*/
    public static <T> boolean deleteEntity(Class<T> entityClass, long id) {
        String sql = "delete from " + getTableName(entityClass) + " where id = ?";
        return executeUpdate(sql ,id) == 1;
    }
    public static String getTableName(Class<?> entityClass) {
        return entityClass.getSimpleName();
    }
}
