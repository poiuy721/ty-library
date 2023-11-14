package com.library.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.dto.*;
import com.library.mapper.SearchMapper;
import com.library.service.SearchService;

@Service //객체 생성
public class SearchServiceImpl implements SearchService{
	
	@Autowired
	private SearchMapper searchMapper; // BoardMapper의 의존성 주입
	
	
	@Override
	public int maxNum() throws Exception {
		return searchMapper.maxNum();
	}

	@Override
	public int getDataCount(String searchKey, String searchValue) throws Exception {
		return searchMapper.getDataCount(searchKey, searchValue);
	}

	@Override
	public List<SearchResultDto> getLists(SearchQuery query) throws Exception {
		return searchMapper.getLists(query);
	}

}