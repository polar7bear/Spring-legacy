package kr.co.tjoeun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import kr.co.tjoeun.service.UserService;

@RestController
public class RestfulAPIController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/user/checkUserIdExist/{user_id}")
	public String checkUserIdExist(@PathVariable String user_id) {
		//UserService의 checkUserIdExist() 메소드 호출하기
		boolean checkId = userService.checkUserIdExist(user_id);
		return checkId + "";
	}
}
