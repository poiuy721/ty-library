package com.library.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.library.dto.SearchDto;
import com.library.dto.SearchQuery;
import com.library.service.SearchService;

@Controller
public class SearchController {
	
	@Resource
	private SearchService searchService;

	@RequestMapping("search")
    public String getSearch(){
        return "search";
    }
	

	@GetMapping("/search/filtered")
	public String getSearchFiltered(@ModelAttribute SearchQuery query,
			Model m
			) throws Exception {
		List<SearchDto> informations=searchService.getLists(query);
		m.addAttribute("informations", informations);
		m.addAttribute("query", query);
		System.out.println(informations.size());
		return "search-result";
	}
}
