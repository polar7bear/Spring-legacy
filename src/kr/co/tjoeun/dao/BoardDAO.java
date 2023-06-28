package kr.co.tjoeun.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.tjoeun.bean.ContentBean;
import kr.co.tjoeun.mapper.BoardMapper;

@Repository
public class BoardDAO {

	@Autowired
	private BoardMapper boardMapper;

	// 게시글 등록
	public void addContentInfo(ContentBean writeContentBean) {
		boardMapper.addContentInfo(writeContentBean);
	}

	// 게시판 index(카테고리)로 게시판 이름 조회
	public String getBoardInfoName(int board_info_idx) {
		return boardMapper.getBoardInfoName(board_info_idx);
	}

	// 게시판 목록 불러오기
	public ArrayList<ContentBean> getContentList(int board_info_idx, RowBounds rowBounds) {
		return boardMapper.getContentList(board_info_idx, rowBounds);
	}

	// 게시글 상세보기
	public ContentBean getContentInfo(int content_idx) {
		return boardMapper.getContentInfo(content_idx);
	}

	// 게시글 수정처리
	public void modifyContentInfo(ContentBean modifyContentBean) {
		boardMapper.modifyContentInfo(modifyContentBean);
	}

	// 게시글 삭제
	public void deleteContentInfo(int content_idx) {
		boardMapper.deleteContentInfo(content_idx);
	}
	
	// 게시글 글 개수 조회
	public int getContentCnt(int content_board_idx) {
		return boardMapper.getContentCnt(content_board_idx);
	}

}
