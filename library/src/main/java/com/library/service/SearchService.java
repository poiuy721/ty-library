package com.library.service;
import java.util.List;

import com.library.dto.*;

public interface SearchService {
	
	public int maxNum() throws Exception;
	
	
	public int getDataCount(String searchKey, String searchValue) throws Exception;
	
	public List<SearchResultDto> getLists(SearchQuery query) throws Exception;


}