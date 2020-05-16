package com.pgl.xiao.core.security;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pgl.xiao.core.Constants;
import com.pgl.xiao.dao.PermissionMapper;
import com.pgl.xiao.domain.Permission;
import com.pgl.xiao.service.PermissionService;

public class URLPathMatchingFilter extends PathMatchingFilter {

	@Resource
	PermissionMapper permissionMapper;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		boolean flag = true;
		String requestURI = super.getPathWithinApplication(request);
		log.debug("requestURI:" + Constants.CHAR_SEPARATOR + requestURI);
		Subject subject = SecurityUtils.getSubject();
		// 如果没有登录，就跳转到登录页面
		if (!subject.isAuthenticated()) {
			WebUtils.issueRedirect(request, response, "/login");
			return false;
		}
		String loginname = (String) SecurityUtils.getSubject().getPrincipal();
		if (request instanceof HttpServletRequest) {
			HttpServletRequest req = (HttpServletRequest) request;
			String requestURL = req.getRequestURI();
			String context = req.getContextPath();
			String url = requestURL.replace(context, "");
			String[] urlPath = url.split("/");
			// 看看这个路径权限里有没有维护，如果没有维护，一律放行(也可以改为一律不放行)
			List<Permission> permissions = permissionMapper.listPermissionsByUserName(loginname);
			for (String tmp : urlPath) {
				for (Permission p : permissions) {
					if (!p.getName().equals(tmp))
						flag = false;
				}	
			}
		}
		return true;
	}
}
