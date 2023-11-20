package com.library.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.library.dto.BooksDTO;
import com.library.dto.SearchRentRecordDto;
import com.library.dto.StockBookDTO;
import com.library.service.StockService;

@Controller
@RequestMapping("tylibrary")
public class AdminController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	int stockState = 1; // 스톡 상태 : 0 잘못된 접근 1 기본상태 2 조사버튼 클릭 3 카메라확인 (여기서 디비 초기화) 1 종료 후 기본상태로

	List<BooksDTO> a;

	@Autowired
	private StockService stockService;

	@RequestMapping("test")
	public String admin() {
		return "/admin/calendar";
	}

	// admin login ====================
	@RequestMapping("/admin")
	public String index2(HttpSession session, @RequestParam(required = false, defaultValue = "login") String adminId, Model model) {
		String sessionInfo = stockService.checkSession(session, adminId); //세션에 담긴 정보를 확인합니다.
		if (sessionInfo.equals("admin")) { //정보가 어드민이면 어드민 화면으로
			return "admin/admin-home";
		} else if (sessionInfo.equals("librarian")) { //정보가 사서면 사서 화면으로
			return "admin/librarian-home";
		}
		return "admin/admin-login"; //의미 없는 정보면 다시 로그인 화면으로
	}

	// 반납과 대여 컨트롤러
	@RequestMapping("admin/librarian")
	public String notyet(String state, Model model) {
		model.addAttribute("state", state);
		return "admin/librarian-scan";
	}

	@RequestMapping("isbn")
	public String isbn() {
		return "isbn";
	}

	@RequestMapping("qrgen")
	public String qrgen() {
		return "qrgen";
	}

	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

	@RequestMapping("admin/stock-count")
	public String stockCount(@RequestParam(required = false) String stock, Model model, String state) {
		a = stockService.selectBooksByNStateAndNStock();
		if (stock == null) {
			// 체크안됨
			model.addAttribute("book_one", stockService.selectBooksByNStockNBook());
			// 체크됨
			model.addAttribute("book_two", stockService.selectBooksByYStockNBook());
			// 대여 책 표시
			// model.addAttribute("book_three",stockService.selectBooksByYState());
			// 조사완료 버튼
			model.addAttribute("state", state == null ? 0 : stockState);
			return "admin/stock-count-list";
		} else if (stock.equals("start")) {
			if (stockState == 1) stockState = 2;
			model.addAttribute("state", stockState);
			return "admin/stock-count-scan";
		} else if (stock.equals("finish")) {
			model.addAttribute("book_one", stockService.selectBooksByNStockNBook());
			// 체크됨
			model.addAttribute("book_two", stockService.selectBooksByYStockNBook());
			// 대여 책 표시
			// model.addAttribute("book_three",stockService.selectBooksByYState());
			// 조사완료 버튼
			stockState = 1;
			model.addAttribute("state", stockState);
			return "admin/stock-count-list";
		}

		return "";
	}
	
	
	@RequestMapping("admin/rent-record")
	public String getRentRecord() {
		return "rent-record";
	}
	
	@PostMapping("select-rent-record-by-date-range")
	@ResponseBody
	public List<SearchRentRecordDto> getRentRecords(@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate){
		List<SearchRentRecordDto> result=stockService.selectRentRecordsByDateRange(startDate, endDate);
		
		return result;
	}
	
	
	//ajax  모음-------------------------------
	
	//카메라 확인 후 n초기화
	@RequestMapping("/stock-camera-ok")
	@ResponseBody
	public int isCamera(@RequestParam String cameraState) {
		stockState = Integer.parseInt(cameraState);
		stockService.updateInitialNStock();
		return 3;
	}

	// 스캔 완료시 y로 변경 후 리턴
	@RequestMapping("/stock-is-exist")
	@ResponseBody
	public StockBookDTO isExist(@RequestParam String id) {
		if(1==stockService.updateYStockByBId(id)) {
			System.out.println("확인2");
			return stockService.selectBooksByBId(id);
		}
		return null;
	}

	// 스캔하기 클릭시 스테이트 체크용
	@RequestMapping("/check-state")
	@ResponseBody
	public int determin() {
		return stockState;
	}
	// 반납 대여 관련 ajax----------------------------------
	@RequestMapping("/check-bookInfo")
	@ResponseBody
	public StockBookDTO doSometing(@RequestParam String id,@RequestParam String state) {
		if(state.equals("return")) {
			if(stockService.updateNStatusByBid(id)==1) { //추가 checkout 테이블 변경
				return stockService.selectBooksByBId(id);
			}
		}else if (state.equals("rent")) {
			return stockService.selectBooksByBId(id); //여기는 jsp에서 연결하는 로직 추가
		}
		return null;
	}
	
	
	// 테스트용
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
