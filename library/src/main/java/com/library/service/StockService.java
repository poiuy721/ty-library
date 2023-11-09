package com.library.service;

import java.util.List;

import com.library.dto.BooksDTO;

public interface StockService {

	public void updateInitialNStock();
	public void updateyStockByBId(String id);
	public BooksDTO selectBooksByBId(String id);
	public List<BooksDTO> selectBooksByYState();
	public List<BooksDTO> selectBooksByNStateAndNStock();
	public List<BooksDTO> selectBooksByNStateAndYStock();
}
