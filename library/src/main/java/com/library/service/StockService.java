package com.library.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.library.dto.BooksDTO;
import com.library.dto.SearchRentRecordDto;
import com.library.dto.StockBookDTO;

public interface StockService {
	//update ================================
	public void updateInitialNStock();
	public int updateYStockByBId(String id);
	public int updateNStatusByBid(String id);
	public int updateReturn(String c_id, String date);
	//select ================================
	public StockBookDTO selectBooksByBId(String id);
	public String isReturnalbe(String id);
	public List<BooksDTO> selectBooksByYState();
	public List<BooksDTO> selectBooksByNStateAndNStock();
	public List<BooksDTO> selectBooksByNStateAndYStock();
	public List<String> getIds();
	public List<StockBookDTO> selectBooksByNStockNBook();
	public List<StockBookDTO> selectBooksByYStockNBook();
	
	
	public StockBookDTO returnMethod(String id);
	public List<SearchRentRecordDto> selectRentRecordsByDateRange(String startDate,String endDate);
	public String checkSession(HttpSession session, String adminId);
}
