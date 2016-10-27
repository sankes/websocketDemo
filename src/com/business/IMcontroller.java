package com.business;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Default;

@Controller
public class IMcontroller {
	@Autowired
	private IMService iMService;
	@RequestMapping(value="loginCheck.action")
	public void loginCkeck(HttpServletRequest request,
            HttpServletResponse response) throws Exception{
		String account = request.getParameter("account");
		String password=request.getParameter("password");
		boolean loginCheckFlag=iMService.loginCheck(account, password);
		if(loginCheckFlag){
			response.getWriter().write("SUCCESS");
		}else{
			response.getWriter().write("FAILE");
		}
	}
}
