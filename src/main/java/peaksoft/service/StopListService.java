package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.stopListDto.StopListRequest;
import peaksoft.dto.stopListDto.StopListResponse;

import java.util.List;


public interface StopListService {
    SimpleResponse createStopList(StopListRequest stopListRequest, Long menuItemId);
    StopListResponse getStopListById(Long id);
    List<StopListResponse> getAllStopLists();
    SimpleResponse updateStopList(StopListRequest newStopListRequest, Long oldStopListId, Long menuItemId);
    SimpleResponse deleteStopList(Long id);


}
