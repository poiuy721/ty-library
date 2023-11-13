package com.library.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.library.dto.*;
import com.library.service.SearchService;

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
		List<SearchRemoveDuplicateDto> informations = removeDuplicates(searchService.getLists(query));
		m.addAttribute("informations", informations);
		m.addAttribute("query", query);
		System.out.println(informations.size());
		return "search-result";
	}

	public List<SearchRemoveDuplicateDto> removeDuplicates(List<SearchResultDto> list) {

		Map<String, SearchRemoveDuplicateDto> resultMap = new HashMap<>();

        for (SearchResultDto resultDto : list) {
            String title = resultDto.getTitle();

            SearchRemoveDuplicateDto dto = resultMap.computeIfAbsent(title, k -> {
                SearchRemoveDuplicateDto newDto = new SearchRemoveDuplicateDto();
                newDto.setTitle(resultDto.getTitle());
                newDto.setAuthor(resultDto.getAuthor());
                newDto.setRenterList(new ArrayList<>());
                return newDto;
            });

            // Add renter to the renterList
            dto.getRenterList().add(resultDto.getRenter());
        }

        // Convert the values of resultMap to a List
        List<SearchRemoveDuplicateDto> resultList = new ArrayList<>(resultMap.values());

        // Set the bookStatus based on the values in the input list
        for (SearchRemoveDuplicateDto dto : resultList) {
            String bookStatus = determineBookStatus(list, dto.getTitle());
            dto.setBookStatus(bookStatus);
        }

        return resultList;
    }

    private static String determineBookStatus(List<SearchResultDto> list, String title) {
        for (SearchResultDto resultDto : list) {
            if (resultDto.getTitle().equals(title)) {
                if ("A".equals(resultDto.getBookStatus())) {
                    return "대여 가능";
                }
            }
        }
        return "대여 불가";
	}
}
