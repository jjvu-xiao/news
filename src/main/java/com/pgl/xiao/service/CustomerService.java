package com.pgl.xiao.service;

import com.pgl.xiao.domain.Customer;

public interface CustomerService extends BasicService<Customer> {
	
	Customer login(String loginname, String passwd);
	
}
