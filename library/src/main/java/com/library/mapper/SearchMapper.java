package com.library.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.library.dto.SearchDto;
import com.library.dto.SearchQuery;

@Mapper //Mapper로 등록 시킨다.
public interface SearchMapper {
	
	public int maxNum() throws Exception;
	
	public int getDataCount(String searchKey, String searchValue) throws Exception;
	
	public List<SearchDto> getLists(SearchQuery query) throws Exception;
	

}