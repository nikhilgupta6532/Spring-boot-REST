package com.nikhilgupta.springboot.routes;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinkRouteController {

	@RequestMapping("/hello")
	public String sayHi() {
		return "Hi";
	}
}
