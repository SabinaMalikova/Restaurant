package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.stopListDto.StopListRequest;
import peaksoft.dto.stopListDto.StopListResponse;
import peaksoft.entity.MenuItem;
import peaksoft.entity.StopList;
import peaksoft.exception.AlreadyExistException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.MenuItemRepo;
import peaksoft.repository.StopListRepo;
import peaksoft.service.StopListService;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StopListServiceImpl implements StopListService {

    private final StopListRepo stopListRepo;
    private final MenuItemRepo menuItemRepo;

    @Override
    public SimpleResponse createStopList(StopListRequest stopListRequest, Long menuItemId) {

        MenuItem menuItem = menuItemRepo.findById(menuItemId)
                .orElseThrow(() -> new NotFoundException("menu item with id: " + menuItemId + " not found"));

        if (stopListRepo.existsByMenuItem(menuItem.getName())){
            throw new AlreadyExistException("menu item with name: " + menuItem.getName() + " already exists in stop list");
        }

        StopList stopList = new StopList();
        stopList.setReason(stopListRequest.reason());
        stopList.setDate(LocalDate.now());
        stopList.setMenuItem(menuItem);
        stopListRepo.save(stopList);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("stopList successfully saved")
                .build();
    }

    @Override
    public StopListResponse getStopListById(Long id) {
        return stopListRepo.getStopListById(id)
                .orElseThrow(()->new NotFoundException("stop list with id: " + id + " not found"));
    }

    @Override
    public List<StopListResponse> getAllStopLists() {
        return stopListRepo.getAllStopLists();
    }

    @Override
    public SimpleResponse updateStopList(StopListRequest newStopListRequest, Long oldStopListId, Long menuItemId) {

        StopList oldStopList = stopListRepo.findById(oldStopListId)
                .orElseThrow(() -> new NotFoundException("stop list with id: " + oldStopListId + " not found"));
        MenuItem menuItem = menuItemRepo.findById(menuItemId)
                .orElseThrow(() -> new NotFoundException("menu item with id: " + menuItemId + " not found"));

        if (stopListRepo.existsByMenuItem(menuItem.getName())){
            throw new AlreadyExistException("menu item with name: " + menuItem.getName() + " already exists in stop list");
        }
        oldStopList.setMenuItem(menuItem);
        oldStopList.setReason(newStopListRequest.reason());
        stopListRepo.save(oldStopList);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("stop list successfully updated")
                .build();
    }

    @Override
    public SimpleResponse deleteStopList(Long id) {

        StopList stopList = stopListRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("stop list with id: " + id + " not found"));

        stopListRepo.delete(stopList);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("stop list successfully deleted")
                .build();
    }
}
