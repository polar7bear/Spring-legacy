package kr.co.tjoeun.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.tjoeun.bean.UserBean;

public class CheckLoginInterceptor implements HandlerInterceptor {
	//java방식에서는 interceptor에서 bean이 자동 주입이 안 됨.
	//ServletAppContext에서 bean을 자동 주입 받아서
	//ServletAppContext에서 CheckLoginInterceptor의 객체를 생성할 때
	//CheckLoginInterceptor 생성자의 argument로 넣어줌
	private UserBean loginUserBean;
	
	public CheckLoginInterceptor(UserBean loginUserBean) {
		this.loginUserBean = loginUserBean;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		//로그아웃 한 상태라면 
		if(loginUserBean.isUserLogin() == false) {
			//ContextPath를 얻어옴
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/user/not_login");
			return false;
		}
		//로그인이 된 상태라면
		return true;
	}
	
	
}
