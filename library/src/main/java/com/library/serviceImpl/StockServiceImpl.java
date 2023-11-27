package com.library.serviceImpl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.library.dto.BooksDTO;
import com.library.dto.SearchRentRecordDto;
import com.library.dto.StockBookDTO;
import com.library.mapper.adminStockMapper;
import com.library.service.StockService;

@Service
public class StockServiceImpl implements StockService {

	@Autowired
	private adminStockMapper stockMapper;

	// update =======================================================
	public void updateInitialNStock() {
		stockMapper.updateInitialNStock();
	}

	public int updateYStockByBId(int id) {
		return stockMapper.updateYStockByBId(id);
	}

	public int updateNStatusByBid(int id) {
		return stockMapper.updateNStatusByBid(id);
	}

	public int updateReturn(int c_id, String date) {
		return stockMapper.updateReturn(c_id, date);
	}

	// select ========================================================
	public StockBookDTO selectBooksByBId(int id) {
		return stockMapper.selectBooksByBId(id);
	}

	public int isReturnalbe(int id) {
		return stockMapper.isReturnalbe(id);
	}

	public List<BooksDTO> selectBooksByYState() {
		return stockMapper.selectBooksByYState();
	}

	public List<BooksDTO> selectBooksByNStateAndNStock() {
		return stockMapper.selectBooksByNStateAndNStock();
	}

	public List<BooksDTO> selectBooksByNStateAndYStock() {
		return stockMapper.selectBooksByNStateAndYStock();
	}

	public List<String> getIds() {
		return stockMapper.getIds();
	}

	public List<StockBookDTO> selectBooksByNStockNBook() {
		return stockMapper.selectBooksByNStockNBook();
	}

	public List<StockBookDTO> selectBooksByYStockNBook() {
		return stockMapper.selectBooksByYStockNBook();
	}

	public int selectBooksByRstaus(int id) {
		return stockMapper.selectBooksByRstaus(id);
	}

	@Override
	public List<SearchRentRecordDto> selectRentRecordsByDateRange(String startDate, String endDate) {
		return stockMapper.selectRentRecordsByDateRange(startDate, endDate);
	}

	// 사원 등록 구현
	public void goSingup(String ENum, String EName, MultipartFile EFile) {
		if (ENum != null && EName != null) {
		}
		if (EFile != null) {
			try {
				// MultipartFile에서 바이트 배열로 데이터 읽기
				InputStream inputStream = EFile.getInputStream();

				// 파일 데이터를 읽기 위한 버퍼 선언
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

				// 파일 내용을 읽어 로그에 출력
				String line;
				while ((line = bufferedReader.readLine()) != null) {
				}
				// 읽어온 바이트 배열 처리 로직 수행
				bufferedReader.close();
				inputStreamReader.close();
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// return 구현
	public StockBookDTO returnMethod(int id) {
		int c_id = stockMapper.isReturnalbe(id);
		if (c_id > 0) {
			String rent_date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			stockMapper.updateReturn(c_id, rent_date);
			stockMapper.updateNStatusByBid(id);
		}
		return stockMapper.selectBooksByBId(id);
	}

	// 세션 체크 구현
	public String checkSession(HttpSession session, String ID) {
		// 세션에 admin이나 librarian이 없음 추가
		if (ID.equals("admin") && session.getAttribute("admin") == null) {
			session.setAttribute("admin", ID);
		} else if (ID.equals("librarian") && session.getAttribute("librarian") == null) {
			session.setAttribute("librarian", ID);
		}

		// 세션 체크해서 admin인지 librarian인지 확인
		if (session.getAttribute("admin") != null && ((String) session.getAttribute("admin")).equals("admin")) {
			session.setMaxInactiveInterval(4); // 세션 유지 테스트용으로 4초
			return "admin";
		} else if (session.getAttribute("librarian") != null
				&& ((String) session.getAttribute("librarian")).equals("librarian")) {
			session.setMaxInactiveInterval(4);
			return "librarian";
		}

		return " ";
	}

}
