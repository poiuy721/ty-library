package com.library.service;
import java.util.List;

import com.library.dto.SearchDto;
import com.library.dto.SearchQuery;

public interface SearchService {
	
	public int maxNum() throws Exception;
	
	
	public int getDataCount(String searchKey, String searchValue) throws Exception;
	
	public List<SearchDto> getLists(SearchQuery query) throws Exception;


}