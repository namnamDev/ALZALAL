package com.ssafy.common.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.service.FollowService;
import com.ssafy.common.service.NotificationService;

@RestController
@RequestMapping("/follow")
public class FollowController {

	@Autowired
	private FollowService followService;

	@Autowired
	private NotificationService notificationService;
	
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
		
		//팔로우 취소는 알림 보내면안됨
		if(ret.get("msg").equals("팔로우 완료")) {
			notificationService.followMember(memberNo);
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

	// 문제 팔로우 ,팔로우 취소
	@PostMapping("/problem")
	public Map<String, Object> problemFollow(@RequestBody Map<String, String> req) {
		Map<String, Object> ret = new HashMap<String, Object>();

		String problemSite = req.get("problemSite");
		int problemNo = Integer.parseInt(req.get("problemNo"));

		try {
			ret = followService.problemFollow(problemSite, problemNo);
		} catch (IllegalStateException e) {
			ret.put("success", "False");
			ret.put("msg", e.getMessage());
			return ret;
		}
		return ret;
	}

}
