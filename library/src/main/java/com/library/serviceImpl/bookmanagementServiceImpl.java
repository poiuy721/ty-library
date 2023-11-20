package com.library.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.dto.BookInfoDTO;
import com.library.dto.BooksDTO;
import com.library.dto.CheckoutDTO;
import com.library.dto.EmployeeDTO;
import com.library.mapper.bookmanagementMapper;
import com.library.service.bookmanagementService;

@Service //요 어노테이션 중요 ***
public class bookmanagementServiceImpl implements bookmanagementService{
	
	@Autowired
	private bookmanagementMapper bookmanagementMapper;
	private final Logger logger=LoggerFactory.getLogger(this.getClass());

	@Override
	public EmployeeDTO checkEmplInfo(String e_id) {
		
		EmployeeDTO returnValue = null;
		List<EmployeeDTO> allEmplInfo = bookmanagementMapper.selectAllEmplInfo();
		
		// 존재하는 사원인지 확인
		for(int i=0; i<allEmplInfo.size();i++) {
			if(allEmplInfo.get(i).getE_id().equals(e_id.toLowerCase())) {
				returnValue = allEmplInfo.get(i);
			} else
				System.out.println("존재하지 않는 사번입니다");
		}
		return returnValue;
	}

	
	@Override
	public EmployeeDTO checkEmplInfoByBid(int b_id, String e_id, String management_type) {
		
		EmployeeDTO employee = null; 
		EmployeeDTO returnValue = null;
		EmployeeDTO emplInfoByBid = bookmanagementMapper.selectEmplInfoByBid(b_id);
		List<EmployeeDTO> allEmplInfo = bookmanagementMapper.selectAllEmplInfo();
		
		// 존재하는 사원인지 확인
		for(int i=0; i<allEmplInfo.size();i++) {
			if(allEmplInfo.get(i).getE_id().equals(e_id.toLowerCase())) 
				employee = allEmplInfo.get(i);;
		}

		// 기존 대여자와 일치하는 지 확인
		if(employee.getE_id().equals(emplInfoByBid.getE_id())) {
			if(management_type.equals("renew"))
				returnValue = employee;
		} else if(!employee.getE_id().equals(emplInfoByBid.getE_id())) {
			if(management_type.equals("assign"))
				returnValue = employee;
		}

		return returnValue;
	}
	

	@Override
	public String getRecentReturnDate(int b_id) {
		
		BooksDTO book = bookmanagementMapper.selectBooks(b_id);
		
		// date to string + 날짜 타입 지정
		Date recent_return_date = book.getDue_date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String str = format.format(recent_return_date);
		String rrd = (String) str.replace("-", "");
		
		return rrd;
	}
	
	@Override
	public void updateBooks(int b_id, String due_date) {
		
		//int b_id = Integer.parseInt(bookId);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyy-MM-dd");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dd = null;
		Calendar cal = Calendar.getInstance();

        try {
        	cal.setTime(format.parse(due_date));
			cal.add(Calendar.DATE, 1);
			dd = format.parse(df.format(cal.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        BooksDTO book = new BooksDTO(b_id, "R", dd);
        bookmanagementMapper.updateBooks(book);
	}


	@Override
	public void insertCheckout(int b_id, String e_id) {
		
		//int b_id = Integer.parseInt(bookId);
		String rent_date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyy-MM-dd");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date rd = null;
		Calendar cal = Calendar.getInstance();

        try {
        	cal.setTime(format.parse(rent_date));
			cal.add(Calendar.DATE, 1);
			rd = format.parse(df.format(cal.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		CheckoutDTO checkout = new CheckoutDTO(b_id, e_id, rd);
		bookmanagementMapper.insertCheckout(checkout);
	}
	

	@Override
	public void updateCheckout(int b_id, String e_id) {
		
		//int b_id = Integer.parseInt(bookId);
		String rent_date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyy-MM-dd");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date rd = null;
		Calendar cal = Calendar.getInstance();

        try {
        	cal.setTime(format.parse(rent_date));
			cal.add(Calendar.DATE, 1);
			rd = format.parse(df.format(cal.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		CheckoutDTO checkout = new CheckoutDTO(b_id, e_id, rd);
		bookmanagementMapper.updateCheckout(checkout);	
	}
	

	

	
	
	// ===================================================================

	@Override
	public BookInfoDTO selectBookInfo(int b_id) {
		return bookmanagementMapper.selectBookInfo(b_id);
	}
	
	@Override
	public BooksDTO selectBooks(int b_id) {
		return bookmanagementMapper.selectBooks(b_id);
	}

	@Override
	public EmployeeDTO selectEmplInfoByBid(int b_id) {
		return bookmanagementMapper.selectEmplInfoByBid(b_id);
	}

	@Override
	public String selectCidByBid(int b_id) {
		return bookmanagementMapper.selectCidByBid(b_id);
	}

}
