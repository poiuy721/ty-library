package com.library.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.library.dto.*;
import com.library.service.SearchService;

/**
 * 도서 검색 화면을 위한 controller
 * 
 * search : 최초 접근 화면
 * search/filtered : 검색 결과 화면
 * @author 김태형
 * 
 */
@Controller
public class SearchController {

	@Resource
	private SearchService searchService;


	@RequestMapping("search")
	public String getSearch() {
		return "search";
	}

	@PostMapping("/search/filtered")
	public String getSearchFiltered(@ModelAttribute SearchQuery query, Model m) throws Exception {
		System.out.println(searchService.getLists(query).size());
		List<SearchRemoveDuplicateDto> informations = searchService.removeDuplicates(searchService.getLists(query));
		m.addAttribute("informations", informations);
		m.addAttribute("query", query);
		System.out.println(informations.size());
		return "search-result";
	}

}
