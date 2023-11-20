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
	//update=================================================
	public void updateInitialNStock(); //재고 조사 시작시 모든 stock 상태 N으로 초기화.
	public int updateYStockByBId(String id); //입력 id에 맞는 stock 상태 Y으로 초기화
	public int updateNStatusByBid(String id); //입력 id에 맞는 Status 상태 N으로 초기화
	//select=================================================
	public StockBookDTO selectBooksByBId(String id);
	public List<BooksDTO> selectBooksByYState();
	public List<BooksDTO> selectBooksByNStateAndNStock();
	public List<BooksDTO> selectBooksByNStateAndYStock();
	public List<String> getIds();
	public List<StockBookDTO> selectBooksByNStockNBook();
	public List<StockBookDTO> selectBooksByYStockNBook();
	public List<SearchRentRecordDto> selectRentRecordsByDateRange(String startDate,String endDate);
}
