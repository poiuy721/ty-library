package com.library.service;

import com.library.dto.BookInfoDTO;
import com.library.dto.BooksDTO;
import com.library.dto.EmployeeDTO;

public interface bookmanagementService {
	
	EmployeeDTO checkEmplInfo(String e_id);
	EmployeeDTO checkEmplInfoByBid(String b_id, String e_id, String management_type);
	String getRecentReturnDate(String b_id);
	void updateBooks(String b_id, String due_date);
	void insertCheckout(String b_id, String e_id);
	void updateCheckout(String b_id, String e_id);

	BookInfoDTO selectBookInfo(String b_id);
	BooksDTO selectBooks(String b_id);
	EmployeeDTO selectEmplInfoByBid(String b_id);
	String selectCidByBid(String b_id);

	
}
