package com.library.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.library.dto.BookInfoDTO;
import com.library.dto.BooksDTO;
import com.library.dto.CheckoutDTO;
import com.library.dto.EmployeeDTO;
import com.library.mapper.bookmanagementMapper;

@Controller
public class BookManagementController {
	
	// ********************************** 변수 ********************************** //	
	@Autowired
	private bookmanagementMapper libMapper;
	
	
	// ********************************** API method ********************************** //
	// ============ &&& calendar 테스트 &&& ============
	@RequestMapping("/camera")
	public String index() {
		return "camera";
	}
	
	
	// ************************************************** 관리자 로그인 ************************************************** //
	
	
	
	
	
	// ************************************************** 공통 ************************************************** //	
		
	// ============ &&& qr 이동 &&& ============
	@GetMapping("/tylibrary/books/{b_id}")
	public String moveToBookInfo(@PathVariable("b_id") String bookId, HttpServletRequest request, Model model) {
		int b_id = Integer.parseInt(bookId);
		// 도서 아이디 비교해서 데이터 가져오기
		BookInfoDTO bookInfo = libMapper.selectBookInfo(b_id);
		BooksDTO books = libMapper.selectBooks(b_id);
		List<BooksDTO> bookStatus = libMapper.selectBooksStatus(bookInfo.getIsbn());
		
		if(books.getB_status().equals("N"))
			model.addAttribute("status", "NN");
		else if(books.getB_status().equals("Y"))
			model.addAttribute("status", "YY");

		// 대여 페이지로 보낼 정보 저장
		model.addAttribute("b_id", b_id);
		model.addAttribute("bookInfo", bookInfo);
		model.addAttribute("bookStatus", bookStatus);

		// 세션에 정보 저장
		HttpSession session = request.getSession(true);
		session.setAttribute("isbn", bookInfo.getIsbn());
		session.setAttribute("b_id", b_id);
		session.setAttribute("bookTitle", bookInfo.getTitle());

		return "books";
	}

	// ============ &&& 로그인 페이지 이동 &&& ============
	@ResponseBody
	@RequestMapping(value = "/tylibrary/login/{b_id}")
	public String rent_login(@RequestParam(value = "arr[]", required = false) String[] sort, HttpSession session,
			HttpServletRequest request) {
		
		String returnType = null;
		session.setAttribute("sort", sort[0]);

		if (sort[0].equals("rent") || sort[0].equals("renew") || sort[0].equals("assign"))
			returnType = "/tylibrary/login";
		else if (sort[0].equals("return"))
			returnType = "/tylibrary/return/check";

		return returnType;
	}

	@RequestMapping("/tylibrary/login")
	public String login(HttpSession session,
			HttpServletRequest request, Model model) {
		return "login";
	}
	
	
	// ************************************************** 대여 ************************************************** //
	
	// ============ &&& 로그인 &&& ============
	@RequestMapping(value = "/tylibrary/rent/loginProcess/{b_id}", method = RequestMethod.POST)
	public String rent_loginProcess(@RequestParam("e_id") String e_id,
			HttpSession session, HttpServletRequest request, Model model) {
		
		// 사번 비교 및 사원 정보 가져오기
		EmployeeDTO emplInfo = libMapper.selectEmplInfo(e_id);

		// 세션에 사원 정보 저장
		session.setAttribute("e_id", emplInfo.getE_id());
		session.setAttribute("e_name", emplInfo.getE_name());

		return "selectDate";
	}


	// ============ &&& 대여 기간 선택 &&& ============
	@RequestMapping(value = "/tylibrary/rent/due")
	@ResponseBody
	public String rent_due(@RequestParam(value = "arr[]") String[] arr, HttpSession session, HttpServletRequest request,
			Model model) {

		// 대여 정보 확인 페이지에 표시될 내용
		String rent_date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		String due_date = arr[0] + "/" + arr[1] + "/" + arr[2];

		// 세션에 정보 저장 > 저장해야 jsp 화면에 정보 표시됨 **
		session.setAttribute("rent_date", rent_date);
		session.setAttribute("due_date", due_date);

		return "check";
	}

	@RequestMapping("/tylibrary/rent/check")
	public String rent_check() {
		return "check";
	}

	// ============ &&& 대여 진행 &&& ============
	@RequestMapping("/tylibrary/rent/{b_id}")
	public String rent(@PathVariable("b_id") String bookId, HttpSession session, HttpServletRequest request, Model model) {
		
		String returnType = null;
		int b_id = Integer.parseInt(bookId);
		if(libMapper.selectBooks(b_id).getB_status().equals("N")) {
			// checkout,books 에 저장될 DTO의 데이터
			int c_id = checkoutIdMax();
			String e_id = (String) session.getAttribute("e_id");
			
			// 대여,반납 일자 저장
			SimpleDateFormat format = new SimpleDateFormat("yyyyy/MM/dd");
	        Date rent_date = null; Date due_date = null;
			try {
				rent_date = format.parse((String) session.getAttribute("rent_date"));
		        due_date = format.parse((String) session.getAttribute("due_date")); 
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			} 
			//DTO 생성, 저장
			CheckoutDTO checkout = new CheckoutDTO(c_id, b_id, e_id, rent_date);
			BooksDTO books = new BooksDTO(b_id, "Y", due_date);

			libMapper.updateBooks(books); // books 테이블에 대여 정보 기록
			libMapper.insertCheckout(checkout); // checkout 테이블에 도서 대여 현황 기록
			
			returnType = "confirm";
		} else if(libMapper.selectBooks(b_id).getB_status().equals("Y")) {
			return "redirect:/tylibrary/books/{b_id}";
		}
		return returnType;
	}
	
	
	
	// ************************************************** 연장 ************************************************** //
	
	// ============ &&& 로그인 &&& ============
	@RequestMapping(value = "/tylibrary/renew/loginProcess/{b_id}", method = RequestMethod.POST)
	public String renew_loginProcess(@PathVariable("b_id") String bookId, @RequestParam("e_id") String e_id,
			HttpSession session, HttpServletRequest request, Model model) {
		
		int b_id = Integer.parseInt(bookId);
		// 이전에 선택한 대여 기간 불러오기
		BooksDTO books_select = libMapper.selectBooks(b_id);
		
		// date to string + 날짜 타입 지정
		Date latest_due_date = books_select.getDue_date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String str = format.format(latest_due_date);
		String ldd = (String) str.replace("-", "");
		model.addAttribute("latestDueDate", ldd);
		
		// 연장하려는 도서의 대여자 정보 가져오기
		EmployeeDTO emplInfoByBid = libMapper.selectEmplInfoByBid(b_id);
	
		if (emplInfoByBid.getE_id().equals(e_id)) {
			// 세션에 사원 정보 저장
			session.setAttribute("e_id", emplInfoByBid.getE_id());
			session.setAttribute("e_name", emplInfoByBid.getE_name());
			return "selectDate_renew";
		} else {
			System.out.println("cant renew");
			return "redirect:/tylibrary/books/{b_id}";
		}
	}

	// ============ &&& 연장 기간 선택 &&& ============
	@RequestMapping(value = "/tylibrary/renew/due")
	@ResponseBody
	public String renew_due(@RequestParam(value = "arr[]") String[] arr, HttpSession session,
			HttpServletRequest request, Model model) {

		// 연장 정보 확인 페이지에 표시될 내용
		String rent_date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		String due_date = arr[0] + "/" + arr[1] + "/" + arr[2];

		// 세션에 정보 저장 > 저장해야 jsp 화면에 정보 표시됨 **
		session.setAttribute("rent_date", rent_date);
		session.setAttribute("due_date", due_date);

		return "check";
	}

	@RequestMapping("/tylibrary/renew/check")
	public String renew_check() {
		return "check";
	}

	@RequestMapping("/tylibrary/renew/{b_id}")
	public String renew(@PathVariable("b_id") String bookId, HttpSession session, HttpServletRequest request, Model model) {
		
		String returnType = null;
		int b_id = Integer.parseInt(bookId);
		String e_id = (String) session.getAttribute("e_id");
		if(libMapper.selectBooks(b_id).getB_status().equals("Y") && libMapper.selectEmplInfoByBid(b_id).getE_id().equals(e_id)) {
			// books 에 저장될 DTO의 데이터
			// 대여,반납 일자 
			SimpleDateFormat format = new SimpleDateFormat("yyyyy/MM/dd");
	        Date due_date = null;
			try {
		        due_date = format.parse((String) session.getAttribute("due_date")); 
		        System.out.println(due_date);
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
			//DTO 생성, 저장
			BooksDTO books = new BooksDTO(b_id, "Y", due_date);
			libMapper.updateBooks(books); // books 테이블에 대여 정보 기록

			returnType = "confirm";
		} else if(libMapper.selectBooks(b_id).getB_status().equals("N") || !libMapper.selectEmplInfoByBid(b_id).getE_id().equals(e_id)) {
			returnType = "redirect:/tylibrary/books/{b_id}";
		}
		return returnType;
	}
	
	
	// ************************************************** 양도 ************************************************** //

	// ============ &&& 로그인 &&& ============
	@RequestMapping(value = "/tylibrary/assign/loginProcess/{b_id}", method = RequestMethod.POST)
	public String assign_loginProcess(@PathVariable("b_id") String bookId, @RequestParam("e_id") String e_id,
			HttpSession session, HttpServletRequest request, Model model) {
		int b_id = Integer.parseInt(bookId);
		// 사번 비교 및 사원 정보 가져오기
		EmployeeDTO emplInfoByBid = libMapper.selectEmplInfoByBid(b_id); // 기존 대여자 정보
		EmployeeDTO emplInfo = libMapper.selectEmplInfo(e_id); // 양도 받는 사원 정보
		
		if (!emplInfoByBid.getE_id().equals(e_id)) { // 두 사원 정보가 일치하는 지 확인
			// 세션에 사원 정보 저장
			session.setAttribute("e_id", e_id);
			session.setAttribute("e_name", emplInfo.getE_name());
			// 서비스 종류(rent, renew, assign) 저장
			model.addAttribute("sort", session.getAttribute("sort"));
			return "selectDate";
		} else {
			System.out.println("cant assign");
			return "redirect:/tylibrary/books/{b_id}";
		}
	}

	// ============ &&& 대여 기간 선택 &&& ============
	@RequestMapping(value = "/tylibrary/assign/due")
	@ResponseBody
	public String assign_due(@RequestParam(value = "arr[]") String[] arr, HttpSession session,
			HttpServletRequest request, Model model) {

		// 대여 정보 확인 페이지에 표시될 내용
		String rent_date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		String due_date = arr[0] + "/" + arr[1] + "/" + arr[2];

		// 세션에 정보 저장 > 저장해야 jsp 화면에 정보 표시됨 **
		session.setAttribute("rent_date", rent_date);
		session.setAttribute("due_date", due_date);

		return "check";
	}

	@RequestMapping("/tylibrary/assign/check")
	public String assign_check() {
		return "check";
	}

	// ============ &&& 양도 진행 &&& ============
	@RequestMapping("/tylibrary/assign/{b_id}")
	public String assign(@PathVariable("b_id") String bookId, HttpSession session, HttpServletRequest request, Model model) {

		String returnType = null;
		int b_id = Integer.parseInt(bookId);
		String e_id = (String) session.getAttribute("e_id");
		String c_id_byBid = libMapper.selectCidByBid(b_id);
		
		if(libMapper.selectBooks(b_id).getB_status().equals("Y") && !libMapper.selectEmplInfoByBid(b_id).getE_id().equals(e_id)) {	
			do {
				// checkout,books 에 저장될 DTO의 데이터
				int c_id = checkoutIdMax();
				
				// 대여,반납 일자 저장
				SimpleDateFormat format = new SimpleDateFormat("yyyyy/MM/dd");
		        Date rent_date = null; Date due_date = null;
				try {
					rent_date = format.parse((String) session.getAttribute("rent_date"));
			        due_date = format.parse((String) session.getAttribute("due_date")); 
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				} 
				//DTO 생성, 저장
				CheckoutDTO checkout = new CheckoutDTO(c_id, b_id, e_id, rent_date);
				BooksDTO books = new BooksDTO(b_id, "Y", due_date);

				libMapper.updateBooks(books); // books 테이블에 대여(양도) 정보 기록
				libMapper.updateCheckout(checkout); // checkout 테이블에 기존 대여 정보 기록
				libMapper.insertCheckout(checkout); // checkout 테이블에 도서 대여(양도) 현황 기록

				session.setAttribute("assignDB", c_id);
				returnType = "confirm";
			} while(false);
			
		} else if(libMapper.selectBooks(b_id).getB_status().equals("N") || !libMapper.selectEmplInfoByBid(b_id).getE_id().equals(e_id) 
				|| c_id_byBid.equals(session.getAttribute("assignDB"))) {
			returnType = "redirect:/tylibrary/books/{b_id}";
		}
		return returnType;
	}
	
	@RequestMapping("/tylibrary/assign")
	public String assign_confirm(HttpSession session, HttpServletRequest request) {
		session.getAttribute("b_id");
		return "confirm";
	}
	
	
	// ************************************************** 반납 ************************************************** //
	
	@RequestMapping("/tylibrary/return/check")
	public String return_check(HttpSession session,
			HttpServletRequest request, Model model) {
		
		String rent_date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		if(session.getAttribute("adminId").equals("admin")) {
			session.setAttribute("rent_date", rent_date);
			return "check";
		} else if(session.getAttribute("adminId")==null) {
			System.out.println("have to login");
			return "login";
		} else 
			return "redirect:/tylibrary/books/{b_id}";
	}
	
	@RequestMapping("/tylibrary/return/{b_id}")
	public String return_confirm(HttpSession session,
			HttpServletRequest request, Model model) {
		
		int b_id = (int) session.getAttribute("b_id");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyy/MM/dd");
        Date rent_date = null; 
		try {
			rent_date = format.parse((String) session.getAttribute("rent_date"));
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		} 
		BooksDTO books = new BooksDTO(b_id, "N", null);
		libMapper.updateBooks(books); // books 테이블에 대여 정보 기록
		
		CheckoutDTO checkout = new CheckoutDTO(b_id, rent_date);
		libMapper.updateCheckout(checkout);

		return "confirm";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// ************************************************** method ************************************************** //
	
	// checkoutId(+1) 추가하기
	public int checkoutIdMax() {

		int[] c_id = libMapper.selectCid();
		int max = -1;
		
		for (int i = 0; i < c_id.length; i++) {
			int r1 = (int)c_id[i];
			//int b = Integer.parseInt(r1);
			//System.out.println(b);
			if(max<r1)
				max = r1;
		}
		max++;
		return max;
	}
	
	// b_id(+1) 추가하기
	public int bIdMax() {

		List<Integer> b_id = libMapper.selectBid();
		int max = -1;
		
		for (int i = 0; i < b_id.size(); i++) {
			int r1 = (int)b_id.get(i);
			if(max<r1)
				max = r1;
		}
		max++;
		return max;
	}
	
	
	// ************************************************** non using mappers ************************************************** //
	
	// ============ &&& 연장 : 로그인 페이지 이동 &&& ============
	@GetMapping("/tylibrary/renew/login/{b_id}")
	public String renew_login(@RequestParam(value="arr[]", required=false) String[] sort,
								HttpSession session, HttpServletRequest request,
								Model model) {

		session.setAttribute("sort", sort[0]);
		int b_id = (int) session.getAttribute("b_id");
		
		BooksDTO books = libMapper.selectBooks(b_id);
		if(books.getB_status().equals("Y"))
			return "renew/login";
		else {
			System.out.println("cant renew");
			return "redirect:/tylibrary/books/{b_id}"; 
		}	
	}
	
	// ============ &&& 양도 : 로그인 페이지 이동 &&& ============
	@GetMapping("/tylibrary/assign/login/{b_id}")
	public String assign_login(@RequestParam(value="arr[]", required=false) String[] sort,
								HttpSession session, HttpServletRequest request,
								Model model) {
		session.setAttribute("sort", sort[0]);
		int b_id = (int) session.getAttribute("b_id");
		
		BooksDTO books = libMapper.selectBooks(b_id);
		if(books.getB_status().equals("Y"))
			return "assign/login";
		else {
			System.out.println("cant rent");
			return "redirect:/tylibrary/books/{b_id}"; 
		}	
	}
}


