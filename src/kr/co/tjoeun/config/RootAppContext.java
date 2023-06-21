package kr.co.tjoeun.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import kr.co.tjoeun.bean.UserBean;

//상속이 없음 : project 작업시 사용할 bean을 정의하는 클래스
@Configuration
public class RootAppContext {
	//로그인하면 UserBean 객체를 loginUserBean이라는 이름으로 Session Scope에 저장함.
	//UserBean 객체가 생성되었으나 멤버변수들에는 기본 생성자에 의해 각 type의 기본값들이 저장되어 있음
	//로그인을 하면 user_id와 user_pw에 사용자가 입력한 값이 저장됨.
	
	@Bean("loginUserBean")
	@SessionScope
	public UserBean loginUserBean() {
		return new UserBean();
	}
}
