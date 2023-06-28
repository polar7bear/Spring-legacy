package kr.co.tjoeun.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import kr.co.tjoeun.bean.ContentBean;

public interface BoardMapper {

	// 현재 sequence 값 가져오기
	@SelectKey(statement = "SELECT content_seq.nextval FROM DUAL", keyProperty = "content_idx", before = true, resultType = int.class)

	// 게시글 등록
	@Insert("INSERT INTO content_table(content_idx, content_subject, content_text, content_file, content_writer_idx, content_board_idx, content_date) VALUES(#{content_idx}, #{content_subject}, #{content_text}, #{content_file, jdbcType=VARCHAR}, #{content_writer_idx}, #{content_board_idx}, default)")
	void addContentInfo(ContentBean writeContentBean);

	// 게시판 index(카테고리)로 게시판 이름 조회
	@Select("SELECT board_info_name FROM board_info_table WHERE board_info_idx = #{board_info_idx}")
	String getBoardInfoName(int board_info_idx);

	// 카테고리별 게시글 목록 조회
	@Select("SELECT c.content_idx, c.content_subject, u.user_name content_writer_name, TO_CHAR(c.content_date, 'YYYY-MM-DD') content_date FROM content_table c, user_table u WHERE c.content_writer_idx = u.user_idx AND c.content_board_idx = #{c.content_board_idx} ORDER BY c.content_idx desc")
	ArrayList<ContentBean> getContentList(int board_info_idx, RowBounds rowBounds);

	// 게시글 상세보기
	@Select("SELECT u.user_name content_writer_name, TO_CHAR(content_date, 'YYYY-MM-DD') content_date, c.content_subject, c.content_text, c.content_file, c.content_writer_idx FROM content_table c, user_table u WHERE c.content_writer_idx = u.user_idx AND c.content_idx = #{content_idx}")
	ContentBean getContentInfo(int content_idx);

	// 게시글 수정
	@Update("UPDATE content_table SET content_subject = #{content_subject}, content_text = #{content_text}, content_file = #{content_file, jdbcType=VARCHAR} WHERE content_idx = #{content_idx}")
	void modifyContentInfo(ContentBean modifyContentBean);

	// 게시글 삭제
	@Delete("DELETE FROM content_table WHERE content_idx = #{content_idx}")
	void deleteContentInfo(int content_idx);
	
	// 게시글 전체 개수 가져오기
	@Select("select count(*) from content_table where content_board_idx = #{content_board_idx}")
	int getContentCnt(int content_board_idx);

}
