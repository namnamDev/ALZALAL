package com.ssafy.common.controller;
import java.util.Map;

import com.ssafy.common.service.ArticleService;
import com.ssafy.common.service.MainService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/main")
public class MainController {
	 @Autowired
	 MainService ms;
	 @GetMapping()
	 public Map<String,Object>sltMultiArticle(@RequestParam(defaultValue = "0") int page){
		 return ms.sltMainMulti(page);
	}
 }
