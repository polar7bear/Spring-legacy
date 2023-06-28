package kr.co.tjoeun.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.tjoeun.bean.UserBean;
import kr.co.tjoeun.mapper.UserMapper;

@Repository
public class UserDAO {
	
	@Autowired
	private UserMapper userMapper;
	
	public String checkUserIdExist(String user_id) {
		/* String id = userMapper.checkUserIdExist(user_id);
		return id; */
		
		return userMapper.checkUserIdExist(user_id);
	}
	
	public void addUserInfo(UserBean joinUserBean) {
		userMapper.addUserInfo(joinUserBean);
	}
	
	public UserBean getLoginUserInfo(UserBean tmpLoginjoinUserBean) {
		UserBean userBean = userMapper.getLoginUserInfo(tmpLoginjoinUserBean);
		return userBean;
	}
	
	public UserBean getModifyUserInfo(int user_idx) {
		UserBean modifyUserBean = userMapper.getModifyUserInfo(user_idx);
		return modifyUserBean;
	}
	
	public void modifyUserInfo(UserBean modifyUserBean) {
		userMapper.modifyUserInfo(modifyUserBean);
	}
}
