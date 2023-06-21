package kr.co.tjoeun.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	//RootAppContext의 @Bean 어노테이션에 네임을 기재해주면 @Resource(name="") Byname을 기재해줘야함
	@Resource(name="loginUserBean")
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home() {
		System.out.println("home");
		return "redirect:/main";
		/* return "index"; 라고만 입력해도 인덱스 페이지로 이동할 수 있게
		 * servlet-context.xml에서 설정을 해줘야 함. */
	}
	
	@RequestMapping(value="/tjoeun", method=RequestMethod.GET)
	public String tjoeun() {
		System.out.println("tjoeun");
		return null;
	}
	
	@RequestMapping(value="/son", method=RequestMethod.GET)
	public String son() {
		System.out.println("son");
		return null;
	}
}
