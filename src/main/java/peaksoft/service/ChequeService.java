package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.chequeDto.AverageChequeResponse;
import peaksoft.dto.chequeDto.ChequeResponse;

import java.time.LocalDate;
import java.util.List;


public interface ChequeService {
    SimpleResponse createCheque(Long userId, List<Long> menuItemsIds);
    ChequeResponse getChequeById(Long id);
    List<ChequeResponse> getChequesByWaiterId(Long id);
    List<ChequeResponse> getAllCheques();
    double getGrandTotalFromAllChequeByUserId(Long id);
    SimpleResponse updateCheque(Long id, List<Long> menuItemsIds);
    SimpleResponse deleteCheque(Long id);
    List<AverageChequeResponse> getAverageChequeFor1Day(LocalDate localDate);

}
