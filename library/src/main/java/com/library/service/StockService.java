package com.library.service;

import java.util.List;

import com.library.dto.BooksDTO;
import com.library.dto.SearchRentRecordDto;
import com.library.dto.StockBookDTO;

public interface StockService {

	public void updateInitialNStock();
	public void updateyStockByBId(String id);
	public StockBookDTO selectBooksByBId(String id);
	public List<BooksDTO> selectBooksByYState();
	public List<BooksDTO> selectBooksByNStateAndNStock();
	public List<BooksDTO> selectBooksByNStateAndYStock();
	public List<String> getIds();
	public List<StockBookDTO> selectBooksByNStockNBook();
	public List<StockBookDTO> selectBooksByYStockNBook();
	
	public List<SearchRentRecordDto> selectRentRecordsByDateRange(String startDate,String endDate);
}
