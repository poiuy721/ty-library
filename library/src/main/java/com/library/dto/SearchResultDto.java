package com.library.dto;


import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchResultDto {
	private String title;
	private String author;
	private String bookStatus;
	private String renter;
	
}
