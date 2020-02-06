package com.pgl.xiao.controller;

import com.github.pagehelper.PageInfo;
import com.pgl.xiao.domain.Customer;
import com.pgl.xiao.service.CustomerService;
import com.pgl.xiao.utils.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = Constants.CUSTOMER_URL)
public class CustomerController implements BasicController<Customer> {

    @Resource
    private CustomerService service;

    @Override
    @CrossOrigin
    @GetMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
    public Object findByPage(@RequestParam Map<String, String> params) {
        int page = StringUtils.isNumeric(params.get("page")) ? 10 : Integer.valueOf(params.get("page"));
        int rows = StringUtils.isNumeric(params.get("rows")) ? 10 : Integer.valueOf(params.get("page"));
        PageInfo<Customer> datas = service.findByParams(params, page, rows);
        Map<String, Object> messages = new HashMap<>();
        messages.put("total", datas.getTotal());
        messages.put("rows", datas.getList());
        return messages;
    }

    @CrossOrigin
    @PostMapping(value = "/update", produces = {"application/json;charset=UTF-8"})
    @Override
    public Object update(@RequestBody Customer customer) {
        Map<String, Object> messages = new HashMap<>();
        service.update(customer);
        messages.put("suc", true);
        messages.put("msg", "添加成功");
        return messages;
    }

    @CrossOrigin
    @Override
    @GetMapping(value = "/find", produces = {"application/json;charset=UTF-8"})
    public Object find(Integer id) {
        Customer data = service.findById(id);
        Map<String, Object> messages = new HashMap<>();
        messages.put("datas", data);
        messages.put("suc", true);
        messages.put("msg", "查询成功");
        return messages;
    }

    @CrossOrigin
    @Override
    @DeleteMapping(value = "/delete", produces = {"application/json;charset=UTF-8"})
    public Object delete(@RequestBody Integer id) {
        service.delete(id);
        Map<String, Object> messages = new HashMap<>();
        messages.put("suc", true);
        messages.put("msg", "已删除");
        return messages;
    }

    @CrossOrigin
    @Override
    @PostMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    public Object save(@RequestBody Map<String, String> params) {
        Customer customer = new Customer();
        Map<String, Object> messages = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd.html");
        DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd.html HH:mm:ss");
        customer.setUsername(params.get("username").toString());
        customer.setStatus(Integer.valueOf(params.get("status").toString()));
        customer.setLoginname(params.get("loginname").toString());
        customer.setAge(Integer.valueOf(params.get("age").toString()));
        customer.setBirthday(LocalDate.parse(params.get("birthday").toString(), formatter));
        customer.setEmail(params.get("email").toString());
        customer.setPasswd(params.get("passwd").toString());
        customer.setRealname(params.get("realname").toString());
        customer.setRegisterdate(LocalDateTime.parse(params.get("registerdate").toString(), formatterDateTime));
        customer.setSex(params.get("sex").toString());
        messages.put("suc", true);
        messages.put("msg", "添加成功");
        return messages;
    }

}
