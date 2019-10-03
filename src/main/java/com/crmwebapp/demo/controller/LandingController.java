package com.crmwebapp.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingController {

	@GetMapping("/")
	public String index() {
		return "landing/index";
	}

	@GetMapping("/info")
	public String info() {
		return "landing/info";
	}
	
	@GetMapping("/enter")
	public String enter() {
		return "redirect:/product/list";
	}
	
	@GetMapping("/showLoginPage")
	public String custlogin() {
		return "landing/login";
	}
	
	@GetMapping("/logout")
	public String showLogoutConfirm() {
		return "landing/logout-confirm";
	}
}
