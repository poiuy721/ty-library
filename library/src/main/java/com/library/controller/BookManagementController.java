package com.library.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
import com.library.mapper.LibMapper;

@Controller
public class BookManagementController {
	
	// ********************************** 변수 ********************************** //
	BookInfoDTO Books = new BookInfoDTO(); // *** ArrayList 형태로 바꿔도 무방 ***
	
	@Autowired
	private LibMapper libMapper;

	
	// ********************************** 세션에 저장되는 정보들 ********************************** //
	// isbn
	// bookTitle
	// bookId
	// emplId
	// emplName
	// sort
	
	
	// ********************************** API method ********************************** //
	// ============ &&& calendar 테스트 &&& ============
	@RequestMapping("/camera")
	public String index() {
		return "camera";
	}
	

	
	// ************************************************** 관리자 로그인 ************************************************** //
	
	@RequestMapping("/tylibrary/admin")
	public String index2() {
		return "admin-login";
	}
	
	@RequestMapping("/tylibrary/admin/login")
	public String admin_login(@RequestParam(value = "adminId") String adminId, HttpSession session,
			HttpServletRequest request, Model model) {

		session.setAttribute("adminId", adminId);
		System.out.println(adminId);

		return "redirect:/tylibrary/books/A103";
	}
	
	
	// ************************************************** 공통 ************************************************** //	
		
	// ============ &&& qr 이동 &&& ============
	@GetMapping("/tylibrary/books/{b_id}")
	public String moveToBookInfo(@PathVariable("b_id") String b_id, HttpServletRequest request, Model model) {
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
		String rent_date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String due_date = arr[0] + "-" + arr[1] + "-" + arr[2];

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
	public String rent(HttpSession session, HttpServletRequest request, Model model) {

		// checkout,books 에 저장될 DTO의 데이터
		String c_id = checkoutIdMax();
		String b_id = (String) session.getAttribute("b_id");
		String e_id = (String) session.getAttribute("e_id");
		String rent_date = (String) session.getAttribute("rent_date");
		String due_date = (String) session.getAttribute("due_date");

		CheckoutDTO checkout = new CheckoutDTO(c_id, b_id, e_id, rent_date);
		BooksDTO books = new BooksDTO(b_id, "Y", due_date);

		libMapper.updateBooks(books); // books 테이블에 대여 정보 기록
		libMapper.insertCheckout(checkout); // checkout 테이블에 도서 대여 현황 기록

		return "confirm";
	}
	
	
	// ************************************************** 연장 ************************************************** //
	
	// ============ &&& 로그인 &&& ============
	@RequestMapping(value = "/tylibrary/renew/loginProcess/{b_id}", method = RequestMethod.POST)
	public String renew_loginProcess(@PathVariable("b_id") String b_id, @RequestParam("e_id") String e_id,
			HttpSession session, HttpServletRequest request, Model model) {
		// 이전에 선택한 대여 기간 불러오기
		BooksDTO books_select = libMapper.selectBooks(b_id);
		String latest_due_date = books_select.getDue_date();
		String ldd = latest_due_date.replace("-", "");
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
		String rent_date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String due_date = arr[0] + "-" + arr[1] + "-" + arr[2];

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
	public String renew(HttpSession session, HttpServletRequest request, Model model) {
		// books 에 저장될 DTO의 데이터
		String b_id = (String) session.getAttribute("b_id");
		String due_date = (String) session.getAttribute("due_date");

		BooksDTO books = new BooksDTO(b_id, "Y", due_date);
		libMapper.updateBooks(books); // books 테이블에 대여 정보 기록

		return "confirm";
	}
	
	
	
	
	// ************************************************** 양도 ************************************************** //

	// ============ &&& 로그인 &&& ============
	@RequestMapping(value = "/tylibrary/assign/loginProcess/{b_id}", method = RequestMethod.POST)
	public String assign_loginProcess(@PathVariable("b_id") String b_id, @RequestParam("e_id") String e_id,
			HttpSession session, HttpServletRequest request, Model model) {
		// 사번 비교 및 사원 정보 가져오기
		EmployeeDTO emplInfoByBid = libMapper.selectEmplInfoByBid(b_id);
		EmployeeDTO emplInfo = libMapper.selectEmplInfo(e_id);
		if (!emplInfoByBid.getE_id().equals(e_id)) {
			// 세션에 사원 정보 저장
			session.setAttribute("e_id", e_id);
			session.setAttribute("e_name", emplInfo.getE_name());

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
		String rent_date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String due_date = arr[0] + "-" + arr[1] + "-" + arr[2];

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
	public String assign(HttpSession session, HttpServletRequest request, Model model) {

		// checkout,books 에 저장될 DTO의 데이터
		String c_id = checkoutIdMax();
		String b_id = (String) session.getAttribute("b_id");
		String e_id = (String) session.getAttribute("e_id");
		String rent_date = (String) session.getAttribute("rent_date");
		String due_date = (String) session.getAttribute("due_date");

		CheckoutDTO checkout = new CheckoutDTO(c_id, b_id, e_id, rent_date);
		BooksDTO books = new BooksDTO(b_id, "Y", due_date);

		libMapper.updateBooks(books); // books 테이블에 대여 정보 기록
		libMapper.updateCheckout(checkout);
		libMapper.insertCheckout(checkout); // checkout 테이블에 도서 대여 현황 기록

		return "confirm";
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
		
		String b_id = (String) session.getAttribute("b_id");
		String rent_date = (String) session.getAttribute("rent_date");
		
		BooksDTO books = new BooksDTO(b_id, "N", "");
		libMapper.updateBooks(books); // books 테이블에 대여 정보 기록
		
		CheckoutDTO checkout = new CheckoutDTO(b_id, rent_date);
		libMapper.updateCheckout(checkout);

		return "confirm";

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	// ============ &&& isbn 등록 &&& ============
	@GetMapping("/test/isbn")
	public String isbn(@PathVariable("bookId") String bookId,
									HttpServletRequest request,
									Model model) {
		
		model.addAttribute("bookId", bookId);
		
		//bookId 값으로 데이터 베이스 조회 -> 해당 도서의 isbn 값 가져와서 searchIsbn() 메소드에 인자로 넣어
		//Books = searchIsbn_xml();
		try {
			Books = searchIsbn_json("0");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("bookIsbn", Books.getIsbn());
		model.addAttribute("bookName", Books.getTitle());
		model.addAttribute("category", Books.getCategory());
		model.addAttribute("writer", Books.getAuthor());
		model.addAttribute("publisher", Books.getPublisher());
		
		return "final-library/rent_book";
	}
	
	// ============ &&& 연장 : 로그인 페이지 이동 &&& ============
	@GetMapping("/tylibrary/renew/login/{b_id}")
	public String renew_login(@RequestParam(value="arr[]", required=false) String[] sort,
								HttpSession session, HttpServletRequest request,
								Model model) {

		session.setAttribute("sort", sort[0]);
		String b_id = (String) session.getAttribute("b_id");
		
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
		String b_id = (String) session.getAttribute("b_id");
		
		BooksDTO books = libMapper.selectBooks(b_id);
		if(books.getB_status().equals("Y"))
			return "assign/login";
		else {
			System.out.println("cant rent");
			return "redirect:/tylibrary/books/{b_id}"; 
		}	
	}
	
	
	
	
	
	
	// ************************************************** method ************************************************** //
	
	// checkoutId(+1) 추가하기
	public String checkoutIdMax() {

		List<String> c_id = libMapper.selectCid();
		int max = -1;
		
		for (int i = 0; i < c_id.size(); i++) {
			String r1 = c_id.get(i).substring(c_id.get(i).lastIndexOf("C") + 1);
			int b = Integer.parseInt(r1);
			//System.out.println(b);
			if(max<b)
				max = b;
		}
		max++;
		String checkoutId = "C" + Integer.toString(max);

		return checkoutId;
	}
	
	// ============ &&& isbn 받아오기 (JSON 버전) &&& ============ 
	 public BookInfoDTO searchIsbn_json(String bookIsbn) throws IOException{
	 
			bookIsbn = "9788963720654"; // 임의로 값 넣어둔 것 ***
			BookInfoDTO book = new BookInfoDTO();
			
			StringBuilder urlBuilder = new StringBuilder("https://www.nl.go.kr/seoji/SearchApi.do?cert_key=461ef3112bc97ed275fbed101ea88fbabffae5fa94df34ee5a54dff44beb7c99"
					+ "&result_style=json&page_no=1&page_size=1&isbn="+bookIsbn); /*URL*/
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			System.out.println("Response code: " + conn.getResponseCode());
			BufferedReader rd;

			// 서비스코드 정상이면 200~300사이의 숫자 나옴
			if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
					rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
					rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
					sb.append(line);
			}
			rd.close();
			conn.disconnect();

			try {
				JSONObject result = (JSONObject) new JSONParser().parse(sb.toString());
				JSONArray bookdata = (JSONArray) result.get("docs");
				System.out.println(bookdata);

				for(int i=0; i<bookdata.size(); i++){
					result = (JSONObject) bookdata.get(i);
				    String title = (String) result.get("TITLE");
				    String publisher = (String) result.get("PUBLISHER");
				    String author = (String) result.get("AUTHOR");
				    
				    book = new BookInfoDTO(title, author, publisher, bookIsbn,"cat");
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			return book;
	 }
	 
	/* 
	// ============ &&& isbn 받아오기 (XML 버전) &&& ============
		public BookDTO searchIsbn_xml() { //@RequestParam("isbn") String isbn
			
			// 인자로 받은 isbn 값을 아래 isbnCode 변수에 넣어준다 굿
			
			// 받은 api 키
			String key = "461ef3112bc97ed275fbed101ea88fbabffae5fa94df34ee5a54dff44beb7c99";
			// 검색할 도서 isbn code
			//String isbnCode = "8984993727";
			String isbnCode = "9791193235065";
			// 도서 정보 저장할 BookDTO
			BookDTO book = new BookDTO();	
			
			try{
				// parsing url 지정(API 키 포함해서)
				String url = "https://www.nl.go.kr/NL/search/openApi/search.do?key="+key+"&detailSearch=true&isbnOp=isbn&isbnCode="+isbnCode;
	    
				DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
				Document doc = dBuilder.parse(url);	
				// 제일 첫번째 태그
				doc.getDocumentElement().normalize();			
				// 파싱할 tag
				NodeList nList = doc.getElementsByTagName("result");
				
				for(int temp = 0; temp < nList.getLength(); temp++){
					Node nNode = nList.item(temp);
					Element eElement = (Element) nNode;

					// BookSTO 객체에 도서 정보 저장
					book.setBookName(getTagValue("title_info", eElement));
					book.setCategory(getTagValue("kdc_name_1s", eElement));
					book.setPublisher(getTagValue("pub_info", eElement));
					book.setWriter(getTagValue("author_info", eElement));
					book.setBookIsbn(getTagValue("isbn", eElement));
					
					System.out.println(getTagValue("title_info", eElement));
					System.out.println(getTagValue("author_info", eElement));
				}
			} catch (Exception e){	
				e.printStackTrace();
			}	
			return book;
		}
		
		// tag값의 정보를 가져오는 함수
		public String getTagValue(String tag, Element eElement) {
			
	        // 결과를 저장할 result 변수 선언
	        String result = "";
		    NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		    result = nlList.item(0).getTextContent();
		    
		    return result;
		}
		 public String getTagValue(String tag, String childTag, Element eElement) {
			 
			// 결과를 저장할 result 변수 선언
			String result = "";
			NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
			for(int i = 0; i < eElement.getElementsByTagName(childTag).getLength(); i++) {
				//result += nlList.item(i).getFirstChild().getTextContent() + " ";
				result += nlList.item(i).getChildNodes().item(0).getTextContent() + " ";
			}
			return result;
		}
	*/	 
	 

}

/*
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
*/

