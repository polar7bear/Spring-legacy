package kr.co.tjoeun.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.tjoeun.bean.UserBean;
import kr.co.tjoeun.dao.UserDAO;

@Service
public class UserService {
	
	@Autowired
	private UserDAO userDAO;
	
	//서버가 실행될 때 Session Scope에  생성한 UserBean 객체
	@Resource(name="loginUserBean")
	private UserBean loginUserBean;
	
	//아이디 중복확인
	public boolean checkUserIdExist(String user_id) {
		String user_name = userDAO.checkUserIdExist(user_id);

		if(user_name == null) {
			return true;
		}else {
			return false;
		}
	}
	
	//회원가입
	public void addUserInfo(UserBean joinUserBean) {
		userDAO.addUserInfo(joinUserBean);
	}
	
	//세션에 담기
	public void getLoginUserInfo(UserBean tmpLoginjoinUserBean) {
		UserBean tmpLoginjoinUserBean2 = userDAO.getLoginUserInfo(tmpLoginjoinUserBean);
		
		/* 로그인 성공시 입력한 아이디와 비밀번호에 해당하는 user_idx와 user_name을 저장하고 있는 
		 * tmpLoginjoinUserBean2(UserBean) 객체가 null이 아니고 user_idx와 user_name을 Session Scope에
		 * 생성된 UserBean 객체(loginUserBean)의 멤버변수 user_idx와 user_name에 할당함.
		 * ㄴ 이값들은 사용자가 로그인 한 상태에서 계속 유지됨.(Session) */
		if(tmpLoginjoinUserBean2 != null) {
			loginUserBean.setUser_idx(tmpLoginjoinUserBean2.getUser_idx());
			loginUserBean.setUser_name(tmpLoginjoinUserBean2.getUser_name());
			loginUserBean.setUserLogin(true);
		}
	}
	
	//회원 정보 불러오기
	public void getModifyUserInfo(UserBean modifyUserBean) {
		//tmpModifyUserBean <- DB로부터 가져온 user_id, user_name 값을 가지고 있는 UserBean 객체
		
		UserBean tmpModifyUserBean = userDAO.getModifyUserInfo(loginUserBean.getUser_idx());
		
		/* tmpModifyUserBean 객체가 가지고 있는 user_id, user_name 값을
		 * Spring이 자동 생성해서 parameter로 전달해준 modifyUserBean 객체의 멤버변수 User_id, user_name에 저장함. */
		
		modifyUserBean.setUser_id(tmpModifyUserBean.getUser_id());
		modifyUserBean.setUser_name(tmpModifyUserBean.getUser_name());
		modifyUserBean.setUser_idx(loginUserBean.getUser_idx());
	}
	
	//회원 정보수정
	public void modifyUserInfo(UserBean modifyUserBean) {
		modifyUserBean.setUser_idx(loginUserBean.getUser_idx());
		userDAO.modifyUserInfo(modifyUserBean);
	}
	
	
}
