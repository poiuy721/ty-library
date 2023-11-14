package com.library.controller;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.library.dto.BooksDTO;
import com.library.dto.StockBookDTO;
import com.library.service.StockService;

@Controller
@RequestMapping("tylibrary")
public class AdminController {

	int stockState = 0;
	
	List<BooksDTO> a;
	
	@Autowired
	private StockService stockService;
	
	@RequestMapping("admin2")
	public String admin() {
		return "/admin/adminhome";
	}
	// admin login
	@RequestMapping("/admin")
	public String index2(HttpSession session, @RequestParam(required = false) String adminId) {
		//세션 조사로 관리자일 경우 if 분기문 관리자 페이지로
		System.out.println(adminId);
		session.setAttribute("adminId", adminId);
		//조건문으로 관리자 확인
		if(session.getAttribute("adminId")!=null) {
			//테스트하기 위해 세션 시간 4초만 유지
			session.setMaxInactiveInterval(4);
			return "admin/adminhome";
		}
		return "admin-login";
	}
	
	
	//!!!!!!!미구현 알림!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	@RequestMapping("notyet")
	public String notyet() {
		return "test";
	}
	
	@RequestMapping("isbn")
	public String isbn() {
		return "isbn";
	}
	
	@RequestMapping("qrgen")
	public String qrgen() {
		return "qrgen";
	}
	
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	@RequestMapping("admin/stock-count")
	public String stockCount(@RequestParam(required = false) String stock,Model model) {	
		a=stockService.selectBooksByNStateAndNStock();
		if (stock==null) {
			//체크안됨
			model.addAttribute("book_one",stockService.selectBooksByNStockNBook());
			//체크됨
			model.addAttribute("book_two",stockService.selectBooksByYStockNBook());
			//대여 책 표시 
			//model.addAttribute("book_three",stockService.selectBooksByYState());
			//조사완료 버튼
			model.addAttribute("stock_state",stockState);
			return "admin/stock-count-list";
		}else if (stock.equals("start")) {
			System.out.println("@@@@@@@@@@@@@@@@@@@@");
			if (stockState ==0) stockState = 1;
			model.addAttribute("stock_state",stockState);
			return "admin/stock-count-scan";
		}else if(stock.equals("finish")) {
			model.addAttribute("book_one",stockService.selectBooksByNStockNBook());
			//체크됨
			model.addAttribute("book_two",stockService.selectBooksByYStockNBook());
			//대여 책 표시 
			//model.addAttribute("book_three",stockService.selectBooksByYState());
			//조사완료 버튼
			stockState = 0;
			model.addAttribute("stock_state",stockState);
			return "admin/stock-count-list";
		}
		
		return "";
	}
	
	
	//ajax  모음-------------------------------
	
	//카메라 확인 후 n초기화
	@RequestMapping("/stock-camera-ok")
	@ResponseBody
	public String isCamera(@RequestParam String cameraState) {
		stockState = Integer.parseInt(cameraState);
		System.out.println(cameraState+"	camera state is ok?");
		if(cameraState.equals("2")) {
			stockService.updateInitialNStock();
			System.out.println(cameraState+"	camera state is ok?");
		}
		return "{ data: 2 }";
	}
	//스캔 완료시 y로 변경 후 리턴
	@RequestMapping("/stock-is-exist")
	@ResponseBody
	public StockBookDTO isExist(@RequestParam String id) {
		stockService.updateyStockByBId(id);
		return stockService.selectBooksByBId(id);
	}
		
	//테스트용	
	@RequestMapping("/admin/testx")
	public String asd() {
		return "/admin/testxcz";
	}
	
	@RequestMapping("/get-ids")
	@ResponseBody
	public List<String> getId() {
		return stockService.getIds();
	}
	
}
