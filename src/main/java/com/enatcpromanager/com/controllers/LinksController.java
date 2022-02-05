package com.enatcpromanager.com.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LinksController {

	@GetMapping("/helpfullinks")
	public String display() {
		return "Links/helpfulLinks";
	}
}
