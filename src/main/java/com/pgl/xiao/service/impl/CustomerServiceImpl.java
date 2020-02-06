package com.pgl.xiao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pgl.xiao.dao.CustomerMapper;
import com.pgl.xiao.domain.Customer;
import com.pgl.xiao.domain.CustomerExample;
import com.pgl.xiao.service.CustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private CustomerMapper mapper;

    @Override
    public Customer findById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean save(Customer type) {
        mapper.insertSelective(type);
        return true;
    }

    @Override
    public boolean update(Customer type) {
        mapper.updateByPrimaryKeySelective(type);
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        mapper.deleteByPrimaryKey(id);
        return true;
    }

    @Override
    public PageInfo<Customer> findByParams(Map<String, String> params, Integer indexPage, Integer maxRows) {
        PageHelper.startPage(indexPage, maxRows);
        CustomerExample example = new CustomerExample();
        CustomerExample.Criteria criteria = example.createCriteria();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String loginname, username, passwd, email, realname, sex;
        Integer status, startAge, endAge;
        LocalDate startBirthday, endBirthday, startRegister, endRegister;
        if (!StringUtils.isEmpty((CharSequence) params.get("loginname"))) {
            loginname = params.get("loginname").toString();
            criteria.andLoginnameLike("%" + loginname + "%");
        }
        if (!StringUtils.isEmpty((CharSequence) params.get("username"))) {
            username = params.get("username").toString();
            criteria.andUsernameLike("%" + username + "%");
        }
        if (!StringUtils.isEmpty((CharSequence) params.get("passwd"))) {
            passwd = params.get("passwd").toString();
            criteria.andPasswdLike("%" + passwd + "%");
        }
        if (!StringUtils.isEmpty((CharSequence) params.get("email"))) {
            email = params.get("email").toString();
            criteria.andEmailLike("%" + email + "%");
        }
        if (!StringUtils.isEmpty((CharSequence) params.get("realname"))) {
            realname = params.get("realname").toString();
            criteria.andRealnameLike("%" + realname + "%");
        }
        if (!StringUtils.isEmpty((CharSequence) params.get("sex"))) {
            sex = params.get("sex").toString();
            criteria.andSexEqualTo(sex);
        }
        if (!StringUtils.isEmpty((CharSequence) params.get("status"))) {
            status = Integer.valueOf(params.get("status").toString());
            criteria.andStatusEqualTo(status);
        }
        if (!StringUtils.isEmpty((CharSequence) params.get("startAge"))) {
            startAge = Integer.valueOf(params.get("startAge").toString());
            criteria.andAgeGreaterThanOrEqualTo(startAge);
        }
        if (!StringUtils.isEmpty((CharSequence) params.get("endAge"))) {
            endAge = Integer.valueOf(params.get("endAge").toString());
            criteria.andAgeLessThanOrEqualTo(endAge);
        }
        if (!StringUtils.isEmpty((CharSequence) params.get("startBirthday"))) {
            startBirthday = LocalDate.parse(params.get("startBirthday").toString(), formatterDate);
            criteria.andBirthdayGreaterThanOrEqualTo(startBirthday);
        }
        if (!StringUtils.isEmpty((CharSequence) params.get("endBirthday"))) {
            endBirthday = LocalDate.parse(params.get("endBirthday").toString(), formatterDate);
            criteria.andBirthdayLessThanOrEqualTo(endBirthday);
        }
        if (!StringUtils.isEmpty((CharSequence) params.get("startRegister"))) {
            startRegister = LocalDate.parse(params.get("startRegister").toString(), formatterDateTime);
            criteria.andBirthdayGreaterThanOrEqualTo(startRegister);
        }
        if (!StringUtils.isEmpty((CharSequence) params.get("endRegister"))) {
            endRegister = LocalDate.parse(params.get("endRegister").toString(), formatterDateTime);
            criteria.andBirthdayLessThanOrEqualTo(endRegister);
        }
        List<Customer> customers = mapper.selectByExample(example);
        return new PageInfo<>(customers);
    }

    @Override
    public List<Customer> findAll() {
        return mapper.selectByExample(null);
    }
}
