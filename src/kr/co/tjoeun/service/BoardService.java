package kr.co.tjoeun.service;

import java.io.File;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.tjoeun.bean.ContentBean;
import kr.co.tjoeun.bean.PageBean;
import kr.co.tjoeun.bean.UserBean;
import kr.co.tjoeun.dao.BoardDAO;

@Service
@PropertySource("/WEB-INF/properties/option.properties")
public class BoardService {

	@Autowired
	private BoardDAO boardDAO;

	@Resource(name = "loginUserBean")
	private UserBean loginUserBean;

	// 파일 업로드 경로
	@Value("${path.upload}")
	private String pathUpload;

	@Value("${page.listcnt}")
	private int pageListcnt;

	@Value("${page.paginationcnt}")
	private int pagiNationcnt;

	// 파일을 저장하는 메소드
	private String saveUploadFile(MultipartFile uploadFile) {
		String fileName = System.currentTimeMillis() + "_" + uploadFile.getOriginalFilename();

		try {
			uploadFile.transferTo(new File(pathUpload + "/" + fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileName;
	}

	// 게시글 등록
	public void addContentInfo(ContentBean writeContentBean) {
		/*
		 * System.out.println(writeContentBean.getContent_subject());
		 * System.out.println(writeContentBean.getContent_text());
		 * System.out.println(writeContentBean.getUpload_file());
		 */

		MultipartFile uploadFile = writeContentBean.getUpload_file();

		if (uploadFile.getSize() > 0) {
			// 파일 이름
			String fileName = saveUploadFile(uploadFile);
			writeContentBean.setContent_file(fileName);
			System.out.println("fileName : " + fileName);
		}

		/*
		 * 현재 로그인 상태인 사람이 작성자가 됨. 작성자 인덱스번호(content_writer_idx)에 현재 로그인 상태인
		 * 사람(UserBean("loginUserBean))의 user_idx를 할당함.
		 */
		writeContentBean.setContent_writer_idx(loginUserBean.getUser_idx());
		boardDAO.addContentInfo(writeContentBean);
		// Service에서 DAO(Repository) 에있는 addContentInfo() 호출하기
	}

	// 게시판 index(카테고리)로 게시판 이름 조회
	public String getBoardInfoName(int board_info_idx) {
		return boardDAO.getBoardInfoName(board_info_idx);
	}

	// 카테고리별 게시판 목록 불러오기
	public ArrayList<ContentBean> getContentList(int board_info_idx, int page) {
		int start = (page - 1) * pageListcnt;
		RowBounds rowBounds = new RowBounds(start, pageListcnt);
		return boardDAO.getContentList(board_info_idx, rowBounds);
	}

	// 게시글 상세보기
	public ContentBean getContentInfo(int content_idx) {
		return boardDAO.getContentInfo(content_idx);
	}

	// 게시글 수정처리
	public void modifyContentInfo(ContentBean modifyContentBean) {
		MultipartFile upload_file = modifyContentBean.getUpload_file();

		if (upload_file.getSize() > 0) {
			String fileName = saveUploadFile(upload_file);
			modifyContentBean.setContent_file(fileName);
		}
		boardDAO.modifyContentInfo(modifyContentBean);
	}

	// 게시글 삭제처리
	public void deleteContentInfo(int content_idx) {
		boardDAO.deleteContentInfo(content_idx);
	}

	// 게시글 글 개수 조회
	// Controller로부터 int content_board_idx, int currentPage 값을 받아옴.
	public PageBean getContentCnt(int content_board_idx, int currentPage) {
		int contentCnt = boardDAO.getContentCnt(content_board_idx);
		
		PageBean pageBean = new PageBean(contentCnt, currentPage, pageListcnt, pagiNationcnt);
		return pageBean;

	}
}
