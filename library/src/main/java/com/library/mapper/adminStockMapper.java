package com.library.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.library.dto.BooksDTO;
import com.library.dto.SearchRentRecordDto;
import com.library.dto.StockBookDTO;

@Mapper
public interface adminStockMapper {
// 대여 중 리턴
// 대여 아닌 것 n으로 변경 후 리턴
// 대여 아닌 것 중 y인거 리턴
// 대여 아닌 것 중 n인거 리턴
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
