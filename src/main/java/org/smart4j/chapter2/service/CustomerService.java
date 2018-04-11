package org.smart4j.chapter2.service;

/* 提供客户数据服务*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.helper.DatabaseHelper;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.util.PropsUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    /*获取客户列表*/
    public List<Customer> getCustomerList() {
        String sql = "select * from customer";
        return DatabaseHelper.queryEntityList(Customer.class  , sql);
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
