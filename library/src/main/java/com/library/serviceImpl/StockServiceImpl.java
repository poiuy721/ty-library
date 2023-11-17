package com.library.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.dto.BooksDTO;
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

	public int updateYStockByBId(String id) {
		return stockMapper.updateYStockByBId(id);
	}
	
	public int updateNStatusByBid(String id) {
		return stockMapper.updateNStatusByBid(id);
	}
	
	//select ========================================================
	public StockBookDTO selectBooksByBId(String id) {
		return stockMapper.selectBooksByBId(id);
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
