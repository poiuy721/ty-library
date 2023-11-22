package com.library.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.dto.BooksDTO;
import com.library.dto.SearchRentRecordDto;
import com.library.dto.StockBookDTO;
import com.library.mapper.adminStockMapper;
import com.library.service.StockService;

@Service
public class StockServiceImpl implements StockService {

	@Autowired
	private adminStockMapper stockMapper;

	//update =======================================================
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
	
	//select ========================================================
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
		return stockMapper.selectRentRecordsByDateRange(startDate,endDate);
	}
	
	//return 구현
	public StockBookDTO returnMethod(int id) {
		if(stockMapper.isReturnalbe(id)==id) {
			Date currentDate = new Date();	       
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	        
	        String formattedDate = sdf.format(currentDate);
			stockMapper.updateReturn(id, formattedDate);
		}
		return stockMapper.selectBooksByBId(id);
	}
	
	
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
