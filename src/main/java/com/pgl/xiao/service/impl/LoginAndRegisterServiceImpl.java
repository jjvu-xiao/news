//package com.pgl.xiao.service.impl;
//
//import com.pgl.xiao.dao.CustomerMapper;
//import com.pgl.xiao.domain.Customer;
//import com.pgl.xiao.domain.CustomerExample;
//import com.pgl.xiao.service.LoginAndRegisterService;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.List;
//
//@Service
//public class LoginAndRegisterServiceImpl implements LoginAndRegisterService {
//
//    @Resource
//    private CustomerMapper customerMapper;
//
//    @Value("${settings.loginname}")
//    private String username;
//
//    @Value("${settings.password}")
//    private String passwd;
//
//    @Override
//    public boolean login(Customer customer) {
//        return validation(customer);
//    }
//
//    @Override
//    public boolean register(Customer customer) {
//        return registerCustomer(customer);
//    }
//
//    @Override
//    public boolean logout(Customer customer) {
//        return cancel(customer);
//    }
//
//    @Override
//    public boolean loginAdmin(String username, String passwd) {
//        if (username.trim().equals(this.username) && passwd.trim().equals(this.passwd))
//            return true;
//        return false;
//    }
//
//    /**
//     * @return
//     */
//    private boolean validation(Customer customer) {
//        CustomerExample example = new CustomerExample();
//        CustomerExample.Criteria criteria = example.createCriteria();
//        criteria.andLoginnameEqualTo(customer.getLoginname());
//        criteria.andPasswdEqualTo(customer.getPasswd());
//        criteria.andStatusEqualTo("正常");
//        List<Customer> result = customerMapper.selectByExample(example);
//        if (result != null && result.size() > 0)
//            return true;
//        return false;
//    }
//
//    private boolean cancel(Customer customer) {
//        CustomerExample example = new CustomerExample();
//        CustomerExample.Criteria criteria = example.createCriteria();
//        criteria.andLoginnameEqualTo(customer.getLoginname());
//        criteria.andPasswdEqualTo(customer.getPasswd());
//        List<Customer> result = customerMapper.selectByExample(example);
//        Customer updateResult = result.get(0);
//        updateResult.setStatus("已注销");
//        customerMapper.insertSelective(updateResult);
//        return true;
//    }
//
//    private boolean registerCustomer(Customer customer) {
//        CustomerExample example = new CustomerExample();
//        CustomerExample.Criteria criteria = example.createCriteria();
//        criteria.andLoginnameEqualTo(customer.getLoginname());
//        criteria.andPasswdEqualTo(customer.getPasswd());
//        List<Customer> result = customerMapper.selectByExample(example);
//        if (result == null && result.size() == 0) {
//            customerMapper.insert(customer);
//            return true;
//        }
//        return false;
//    }
//
//}
