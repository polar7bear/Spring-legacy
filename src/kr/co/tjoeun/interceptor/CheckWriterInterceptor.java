package kr.co.tjoeun.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.tjoeun.bean.ContentBean;
import kr.co.tjoeun.bean.UserBean;
import kr.co.tjoeun.service.BoardService;

public class CheckWriterInterceptor implements HandlerInterceptor {

	private UserBean loginUserBean;
	private BoardService boardService;

	public CheckWriterInterceptor(UserBean loginUserBean, BoardService boardService) {
		this.loginUserBean = loginUserBean;
		this.boardService = boardService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		int content_idx = Integer.parseInt(request.getParameter("content_idx"));

		ContentBean currentContentBean = boardService.getContentInfo(content_idx);
		
		if(currentContentBean.getContent_writer_idx() != loginUserBean.getUser_idx()) {
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/board/not_writer");
			return false;
		}
		return true;
	}

}
