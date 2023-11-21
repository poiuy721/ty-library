package com.library.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.library.dto.BookDTO;
import com.library.dto.BookDeleteDto;

@Mapper
public interface LibMapper {

	// isbn중복확인
	public int selectBookInfo(String isbn);

	// 신규책일때
	public int insertBookInfo(BookDTO bdto);

	public int insertBookInfo2(BookDTO bdto);

	// 이미 있는 책일때
	public int insertBooks(BookDTO bdto);

	// 삭제할 isbn검색하면 책 정보 가져오기
	public List<BookDeleteDto> bringBooksInfo(String isbn);

	// 도서삭제(books 업데이트)
	public int deleteBook(String b_id);

	public int getLastInsertId();

}
