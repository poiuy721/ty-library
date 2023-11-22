package com.library.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.library.dto.BooksDTO;
import com.library.dto.SearchRentRecordDto;
import com.library.dto.StockBookDTO;

public interface StockService {
	//update ================================
	public void updateInitialNStock();
	public int updateYStockByBId(int id);
	public int updateNStatusByBid(int id);
	public int updateReturn(int c_id, String date);
	//select ================================
	public StockBookDTO selectBooksByBId(int id);
	public int isReturnalbe(int id);
	public int selectBooksByRstaus(int id);
	public List<BooksDTO> selectBooksByYState();
	public List<BooksDTO> selectBooksByNStateAndNStock();
	public List<BooksDTO> selectBooksByNStateAndYStock();
	public List<String> getIds();
	public List<StockBookDTO> selectBooksByNStockNBook();
	public List<StockBookDTO> selectBooksByYStockNBook();
	
	
	public StockBookDTO returnMethod(int id);
	public List<SearchRentRecordDto> selectRentRecordsByDateRange(String startDate,String endDate);
	public String checkSession(HttpSession session, String adminId);
}
