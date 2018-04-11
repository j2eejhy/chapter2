package org.smart4j.chapter2.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.service.CustomerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RunWith(Parameterized.class)
public class CustomerServiceTest {

    private final CustomerService customerService;


    public CustomerServiceTest() {
        customerService = new CustomerService();
    }

    @Before
    public void init(){
        //todo 初始化数据库
    }

    @Test
    public void getCustomerListTest(){
        List<Customer> customerList = customerService.getCustomerList();
        customerList.stream().forEach((customer)->{
            System.out.println(customer.toString());
        });
    }

    @Test
    public void getCustomerTest(){
        long id = 1;
        Customer customer = customerService.getCustomer(id);
        System.out.println(customer.toString());
        Assert.assertNotNull(customer);
    }

    @Test
    public void createCustomerTest(){
        Map<String, Object> fielMap = new HashMap<String, Object>();
        fielMap.put("name", "customer3");
        fielMap.put("contact", "王五");
        fielMap.put("telephone", "13333333332");
        boolean result = customerService.createCustomer(fielMap);
        Assert.assertTrue(result);
    }

    @Test
    public void updateCustomerTest(){
        Map<String, Object> fielMap = new HashMap<String, Object>();
        long id = 1;
        fielMap.put("contact", "aaaaa");
        boolean result = customerService.updateCustomer(id , fielMap);
        Assert.assertTrue(result);
    }

    @Test
    public void deleteCustomerTest(){
        Map<String, Object> fielMap = new HashMap<String, Object>();
        long id = 1;
        boolean result = customerService.deleteCustomer(id);
        Assert.assertTrue(result);
    }
}
