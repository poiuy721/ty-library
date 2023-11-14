package com.library.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.library.dto.BookDTO;



@Mapper
public interface LibMapper {

	
	//isbn중복확인
	public int selectBookInfo(String isbn);
		
	
	
public int insertBookInfo(BookDTO bdto);

public int insertBookInfo2(BookDTO bdto);


public int insertBooks(BookDTO bdto);
}