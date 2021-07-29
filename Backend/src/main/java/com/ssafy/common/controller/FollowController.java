package com.ssafy.common.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.service.FollowService;

@RestController
@RequestMapping("/follow")
public class FollowController {

	@Autowired
	private FollowService followService;

	// 멤버 팔로우 ,팔로우 취소
	@PostMapping("/member")
	public Map<String, Object> memberFollow(@RequestBody Map<String, Long> req) {
		Map<String, Object> ret = new HashMap<String, Object>();

		Long memberNo = req.get("memberNo");

		try {
			ret = followService.memberFollow(memberNo);
		} catch (IllegalStateException e) {
			ret.put("success", "False");
			ret.put("msg", e.getMessage());
			return ret;
		}
		return ret;
	}

	// 알고리즘 팔로우 ,팔로우 취소
	@PostMapping("/algorithm")
	public Map<String, Object> algorithmFollow(@RequestBody Map<String, String> req) {
		Map<String, Object> ret = new HashMap<String, Object>();

		String algorithm = req.get("algorithm");

		try {
			ret = followService.algorithmFollow(algorithm);
		} catch (IllegalStateException e) {
			ret.put("success", "False");
			ret.put("msg", e.getMessage());
			return ret;
		}
		return ret;
	}

}
