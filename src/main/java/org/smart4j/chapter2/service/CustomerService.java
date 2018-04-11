package org.smart4j.chapter2.service;

/* 提供客户数据服务*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.util.PropsUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    private static final String DRIVER;

    private static final String URL;

    private static final String USERNAME;

    private static final String PASSWORD;

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



    /*获取客户列表*/
    public List<Customer> getCustomerList() {
        Connection conn = null;
        List<Customer> customerList = new ArrayList<Customer>();
        try {
            String sql = "select * from customer";
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getLong("id"));
                customer.setName(rs.getString("name"));
                customer.setContact(rs.getString("contact"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setEmail(rs.getString("email"));
                customer.setRemark(rs.getString("remark"));
                customerList.add(customer);
            }
        } catch (SQLException e) {
            LOGGER.error("execute sql failure", e);
        }finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error("close connection failure" ,e);
                }
            }
        }
        return customerList;
    }

    /*获取客户*/
    public Customer getCustomer(long id) {
        //TODO
        return null;
    }

    /*创建客户*/
    public boolean createCustomer(Map<String , Object> fieldMap){
        //TODO
        return false;
    }

    /*更新客户*/
    public boolean updateCustomer(long id ,Map<String , Object> fieldMap){
        //TODO
        return false;
    }

    /*删除客户*/
    public boolean deleteCustomer(long id){
        //TODO
        return false;
    }
}
