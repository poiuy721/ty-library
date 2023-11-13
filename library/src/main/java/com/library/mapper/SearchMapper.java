package com.library.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.library.dto.*;

@Mapper //Mapper로 등록 시킨다.
public interface SearchMapper {
	
	public int maxNum() throws Exception;
	
	public int getDataCount(String searchKey, String searchValue) throws Exception;
	
	public List<SearchResultDto> getLists(SearchQuery query) throws Exception;
	

}