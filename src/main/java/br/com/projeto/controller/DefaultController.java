package br.com.projeto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

	@GetMapping(path = { "/", "/home" })
	public String home() {
		return "/home";
	}

	@GetMapping("/login")
	public String login() {
		return "/login";
	}

	@GetMapping("/admin")
	public String admin() {
		return "/admin/index";
	}

	@GetMapping("/user")
	public String user() {
		return "/user/index";
	}
}
