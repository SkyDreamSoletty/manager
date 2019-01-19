package com.manager.controller.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/sec")
public class LoginController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void login(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			WebRequest webRequest,
			@RequestParam("username") String username,
			@RequestParam("password") String password)
			throws IOException {

		session.setAttribute("TEST_KEY","test");
		String url = request.getContextPath();
		System.out.println("项目路径:" + url);
		response.sendRedirect(request.getContextPath() + "/index");
	}

	/**
	 * 用户退出登录
	 *
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public Map<?,?> logout(HttpSession session) {
		Map<String,Object> result = new HashMap<String,Object>();
        session.removeAttribute("TEST_KEY");
		return result;
	}
}
