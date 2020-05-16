package com.pgl.xiao.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pgl.xiao.core.Constants;
import com.pgl.xiao.domain.Customer;
import com.pgl.xiao.service.CustomerService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import static com.pgl.xiao.core.Constants.*;

@RestController
@RequestMapping(value = Constants.APP_URL)
public class UseController {

	@Autowired
	private CustomerService customerService;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource
	private JavaMailSender mailService;
	
	@Resource
	private JedisPool jedisPool;

	/**
	 * @Title 用户登录功能，根据登录账号及密码判断用户是否存在
	 * @param customer  登录账号跟密码   
	 * @return 是否登录成功及提示信息
	 */
	@CrossOrigin
	@PostMapping(value = "/login", produces = { "application/json;charset=UTF-8" })
	public Object login(@RequestBody Customer customer) {
		Customer result = customerService.login(customer.getLoginname(), customer.getPasswd());
		Map<String, Object> callback = new HashMap<String, Object>();
		if (result != null) {
			callback.put("retCode", 0);
			callback.put("retMsg", "登录成功");
			callback.put("datas", result);
			log.debug("用户" + Constants.CHAR_SEPARATOR + customer.getLoginname() + "\t登录成功");
			return callback;
		}
		callback.put("retCode", 1);
		callback.put("retMsg", "登录失败");
		log.debug("用户" + Constants.CHAR_SEPARATOR + customer.getLoginname() + "\t登录失败");
		return callback;
	}

	/**
	 * @Title 邮箱验证
	 * @param email 用户的邮箱号
	 * @Descr 随机生成6位数字的验证码发送到用户的邮箱中，验证码与用户邮箱缓存到Redis中
	 * @return 验证码
	 */
	@CrossOrigin
	@PostMapping(value = "/validateEmail", produces = { "application/json;charset=UTF-8" })
	public Object validateEmail(HttpServletRequest req) {
		String email = req.getParameter("email");
		String random = (int) ((Math.random() * 9 + 1) * 100000) + "";
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("m18779334084@163.com");
		message.setTo(email);
		message.setSubject("用户注册验证信息");
		message.setText("，欢迎注册我们的APP，您的验证码为" + CHAR_SEPARATOR + random);
		mailService.send(message);
		Map<String, Object> callback = new HashMap<String, Object>();
		callback.put("retCode", 0);
		callback.put("retMsg", "邮件已发出，请注意查收");
		log.debug("邮箱" + CHAR_SEPARATOR + email  + "验证码" + CHAR_SEPARATOR + random);
		Jedis jedis = jedisPool.getResource();
		jedis.setex(email, 30, random);
		jedis.close();
		return callback;
	}
	
	/**
	 * @Title 手机号验证
	 * @param tel	用户的手机号码
	 * @Descr 将6位随机数字的验证码发送到用户的手机短信中，验证码与用户邮箱缓存到Redis中
	 * @return 提示信息
	 */
	@CrossOrigin
	@PostMapping(value = "/validateTel", produces = { "application/json;charset=UTF-8" })
	public Object validateTel(@RequestBody String tel) {
		Map<String, Object> callback = new HashMap<String, Object>();
		return callback;
	}
	
	/**
	 * @Title 验证码验证
	 * @param code	验证码的邮箱
	 * @return	提示信息
	 */
	@CrossOrigin
	@PostMapping(value = "/validateSubmit", produces = { "application/json;charset=UTF-8" })
	public Object validateSubmit(@RequestBody Map<String, String> code) {
		Jedis jedis = jedisPool.getResource();
		String validateCode = jedis.get(code.get("email"));
		String submitCode = code.get("code");
		Map<String, Object> callback = new HashMap<String, Object>();
		if (validateCode == null) {
			callback.put("retCode", 1);
			callback.put("retMsg", "验证码不存在");
		}
		if (!validateCode.equals(code)) {
			callback.put("retCode", 1);
			callback.put("retMsg", "验证码错误");
		}
		callback.put("retCode", 0);
		callback.put("retMsg", "验证成功");
		return callback;
	}

	public Object validateEmail() {
		return null;
	}
	
	public Object validateTel() {
		return null;
	}
}
