package kr.co.tjoeun.controller;



import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.tjoeun.bean.ContentBean;
import kr.co.tjoeun.bean.UserBean;
import kr.co.tjoeun.service.BoardService;


@Controller
public class HomeController {
  
	@Autowired
	private BoardService boardService;
	
    @Resource(name="loginUserBean")
    @Lazy
    private UserBean loginUserBean;
  
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(HttpServletRequest request, @RequestParam("content_board_idx") int content_board_idx, Model model) {
		// String url = request.getContextPath();
		// System.out.println("주소표시줄에 " + url + "/ 이 입력되었습니다.");
		// return "/WEB-INF/views/index.jsp";
	    System.out.println("loginUserBean :" + loginUserBean);
		System.out.println(request.getServletContext().getRealPath("/"));
		
		ArrayList<ContentBean> contentList = boardService.getContentPreview(content_board_idx);
		model.addAttribute("contentList", contentList);
		
	    return "redirect:/main";
		
		/*
		  /WEB-INF/views/index.jsp 에서
		  /WEB-INF/views/ 이 부분을  prefix 로 설정하고
		  .jsp 이 부분을 suffix 로 설정해서
		  return "index" 라고만 coding 해도 전체 경로를 인식하게 함
		    ㄴ servlet-context.xml 파일에서 설정함   
		     
		 */
	}
	
	@RequestMapping(value="/tjoeun", method=RequestMethod.GET)
	public String tjoeun() {
		System.out.println("주소표시줄에 http://localhost:8080/SpringMVCXml/tjoeun 이 입력되었습니다.");
		return null;
	}
	
	@RequestMapping(value="/spring", method=RequestMethod.GET)
	public String spring() {
		System.out.println("주소표시줄에 http://localhost:8080/SpringMVCXml/spring 이 입력되었습니다.");
		return null;
	}
	
	
}





