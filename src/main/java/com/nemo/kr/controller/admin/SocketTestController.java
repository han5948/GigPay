package com.nemo.kr.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class SocketTestController {
	@RequestMapping("/socketTest")
	public String sokcetTest() throws Exception {
		return "/admin/socket/socketTest.admin";
	}
	
	@RequestMapping("/alimSocket")
	public String alimSocket() throws Exception {
		return "/admin/socket/alimSocket.no_admin";
	}
}
