package com.library.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.library.dto.BookInfoDTO;
import com.library.dto.BooksDTO;
import com.library.dto.CheckoutDTO;
import com.library.dto.EmployeeDTO;

@Mapper
public interface LibMapper {

	//void insertMail(HashMap<String, String> mail);
	//MailDTO selectContent(@Param("no") String no);
	//List<StorageDTO> selectStorageMail(@Param("send_id") String send_id);	
	//void deleteStorage(@Param("storage_no") String storage_no);
	//void deleteMail(Map<String, String> deleteM);
	//HashMap<String,String> selectContentReceive(@Param("no") String no);
	
	BookInfoDTO selectBookInfo(@Param("b_id") String b_id);
	EmployeeDTO selectEmplInfo(@Param("e_id") String e_id);
	BooksDTO selectBooks(@Param("b_id") String b_id);
	List<BooksDTO> selectBooksStatus(@Param("isbn") String isbn);

	// 대여 시 업로드
	void insertCheckout(CheckoutDTO checkout);
	void updateBooks(BooksDTO books);
	
	// 연장
	void renewBooks(BooksDTO books);
	
	// 양도
	void updateCheckout(CheckoutDTO checkout);
	EmployeeDTO selectEmplInfoByBid(@Param("b_id") String b_id);
	
	List<String> selectCid();
	
	// 반납
	//void returnCheckout(CheckoutDTO checkout);
	
}
