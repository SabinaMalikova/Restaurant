package peaksoft.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.chequeDto.AverageChequeResponse;
import peaksoft.dto.chequeDto.ChequeResponse;
import peaksoft.service.ChequeService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cheques")
@Tag(name = "cheque-api")
@RequiredArgsConstructor
public class ChequeApi {

    private final ChequeService chequeService;

    @PostMapping("/createCheque/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    SimpleResponse createCheque(@PathVariable Long userId, @RequestParam List<Long> menuItemsIds){
        return chequeService.createCheque(userId, menuItemsIds);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    ChequeResponse getChequeById(@PathVariable Long id){
        return chequeService.getChequeById(id);
    }

    @GetMapping("/getChequesByWaiterId/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    List<ChequeResponse> getChequesByWaiterId(@PathVariable Long id){
        return chequeService.getChequesByWaiterId(id);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ADMIN')")
    List<ChequeResponse> getAllCheques(){
        return chequeService.getAllCheques();
    }

    @GetMapping("/getUsersGrandTotal/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    double getGrandTotalFromAllChequeByUserId(@PathVariable Long id){
        return chequeService.getGrandTotalFromAllChequeByUserId(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    SimpleResponse updateCheque(@PathVariable Long id, @RequestParam List<Long> menuItemsIds){
        return chequeService.updateCheque(id, menuItemsIds);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    SimpleResponse deleteCheque(@PathVariable Long id){
        return chequeService.deleteCheque(id);
    }

    @GetMapping("/getAverageChequeFor1Day")
    @PreAuthorize("hasAuthority('ADMIN')")
    List<AverageChequeResponse> getAverageChequeFor1Day(@RequestParam LocalDate localDate){
        return chequeService.getAverageChequeFor1Day(localDate);
    }

}
