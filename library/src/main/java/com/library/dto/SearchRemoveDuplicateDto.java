package com.library.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchRemoveDuplicateDto {
	private String title;
	private String author;
	private String bookStatus;
	private List<String> renterList;
}
