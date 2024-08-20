package peaksoft.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.stopListDto.StopListRequest;
import peaksoft.dto.stopListDto.StopListResponse;
import peaksoft.service.StopListService;

import java.util.List;

@RestController
@RequestMapping("/api/stopLists")
@Tag(name = "stopList-api")
@RequiredArgsConstructor
public class StopListApi {

    private final StopListService stopListService;

    @PostMapping("/createStopList/{menuItemId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    SimpleResponse createStopList(@RequestBody @Valid StopListRequest stopListRequest, @PathVariable Long menuItemId){
        return stopListService.createStopList(stopListRequest, menuItemId);
    }

    @GetMapping("/get/{id}")
    @PermitAll
    StopListResponse getStopListById(@PathVariable Long id){
        return stopListService.getStopListById(id);
    }

    @GetMapping("/getAll")
    @PermitAll
    List<StopListResponse> getAllStopLists(){
        return stopListService.getAllStopLists();
    }

    @PutMapping("/update/{oldStopListId}/{menuItemId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    SimpleResponse updateStopList(@RequestBody @Valid StopListRequest newStopListRequest, @PathVariable Long oldStopListId, @PathVariable Long menuItemId){
        return stopListService.updateStopList(newStopListRequest,oldStopListId, menuItemId);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    SimpleResponse deleteStopList(@PathVariable Long id){
        return stopListService.deleteStopList(id);
    }
}
