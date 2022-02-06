package com.test.authorizationserver;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizationController {
	@GetMapping("/home")
	public Principal renderHome(Principal principal) {
		return principal;
	}
}
