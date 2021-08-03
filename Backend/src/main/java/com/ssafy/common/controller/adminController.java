package com.ssafy.common.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.service.AdminService;
@RestController
@RequestMapping("/admin")

public class adminController {
	  @Autowired
	  AdminService adminSvc;
	  @PostMapping("/discussion")
	  public Map<String,Object>insertdiscuss(		  
			  @RequestBody Map<String,Object> req
			  ){
		  return adminSvc.insertArticle("discussion", req);
	  };
}
