package kr.co.tjoeun.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kr.co.tjoeun.bean.UserBean;

public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserBean userBean = (UserBean)target;
		
		//현재 validation이 적용되는 bean의 이름을 얻어옴
		String beanName = errors.getObjectName();
		
		/* 아래의 validation은 beanNam이 joinUserBean인 객체에 대한 것이므로
		 * beanName.equals("joinUserBean") 일 때만 적용되어야 함
		 * 즉, beanName이 tmpLoginjoinUserBean인 경우에 적용되면 오류가 발생함
		 * 이러한 이유로 if(beanName.equals("joinUserBean"))로 설정해줌*/
		
		if(beanName.contentEquals("joinUserBean") || beanName.contentEquals("modifyUserBean")) {
			if(userBean.getUser_pw().equals(userBean.getUser_pw2()) == false) {
				errors.rejectValue("user_pw", "NotEquals");
				errors.rejectValue("user_pw2", "NotEquals");
			}
		}
			//아이디가 DB에 이미 존재하는 경우
		if(beanName.contentEquals("joinUserBean")) {
			if(userBean.isUserIdExist() == false) {
				errors.rejectValue("user_id", "DontCheckuserIdExist");
			}
		}
	}

}
