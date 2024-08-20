package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.chequeDto.AverageChequeResponse;
import peaksoft.dto.chequeDto.ChequeResponse;
import peaksoft.entity.Cheque;
import peaksoft.entity.MenuItem;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exception.AlreadyExistException;
import peaksoft.exception.ForbiddenException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.ChequeRepo;
import peaksoft.repository.MenuItemRepo;
import peaksoft.repository.StopListRepo;
import peaksoft.repository.UserRepo;
import peaksoft.service.ChequeService;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChequeServiceImpl implements ChequeService {

    private final ChequeRepo chequeRepo;
    private final UserRepo userRepo;
    private final MenuItemRepo menuItemRepo;
    private final StopListRepo stopListRepo;


    @Override
    public SimpleResponse createCheque(Long userId, List<Long> menuItemsIds) {
        Cheque cheque = new Cheque();

        List<MenuItem> menuItems = menuItemRepo.getMenuItemsByIds(menuItemsIds);
        double priceAverage = 0;

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("user with id: " + userId + " not found"));

        if (user.getRole().equals(Role.WAITER)){
            for (MenuItem menuItem : menuItems) {
                if (stopListRepo.existsByMenuItem(menuItem.getName())){
                    throw new AlreadyExistException(menuItem.getName() + " in a stop list");
                }
            }
            cheque.setUser(user);
            cheque.setCreatedAt(LocalDate.now());
            cheque.setMenuItems(menuItems);
            for (MenuItem menuItem : menuItems) {
                priceAverage += menuItem.getPrice();
            }
            cheque.setPriceAverage(priceAverage);
            chequeRepo.save(cheque);

        }else throw new ForbiddenException("the cheque can only be added to the waiter");

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("cheque successfully created")
                .build();
    }

    @Override
    public ChequeResponse getChequeById(Long id) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepo.getUserByEmail(email)
                .orElseThrow(() -> new NotFoundException("user with email: " + email + " not found"));

        ChequeResponse chequeResponse = chequeRepo.getChequeById(id)
                .orElseThrow(() -> new NotFoundException("cheque with id: " + id + " not found"));

        if (currentUser.getRole().equals(Role.ADMIN) || currentUser.getId() == chequeResponse.userID()){
            return chequeResponse;
        }
        else throw new ForbiddenException("forbidden request");
    }

    @Override
    public List<ChequeResponse> getChequesByWaiterId(Long id) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepo.getUserByEmail(email)
                .orElseThrow(() -> new NotFoundException("user with email: " + email + " not found"));


        if (currentUser.getRole().equals(Role.ADMIN) || currentUser.getId() == id){
            return chequeRepo.getChequesByWaiterId(id);
        }
        else throw new ForbiddenException("forbidden request");
    }

    @Override
    public List<ChequeResponse> getAllCheques() {
        return chequeRepo.getAllCheques();
    }

    @Override
    public double getGrandTotalFromAllChequeByUserId(Long id) {

        double grandTotal = 0;
        List<ChequeResponse> allCheques = chequeRepo.getChequesByWaiterId(id);

        for (ChequeResponse cheque : allCheques) {
            grandTotal += cheque.grandTotal();
        }

        return grandTotal;
    }

    @Override
    public SimpleResponse updateCheque(Long id, List<Long> menuItemsIds) {
        double priceAverage = 0;

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepo.getUserByEmail(email)
                .orElseThrow(() -> new NotFoundException("user with email: " + email + " not found"));

        Cheque oldCheque = chequeRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("cheque with id: " + id + " not found"));

        List<MenuItem> newMenuItems = menuItemRepo.getMenuItemsByIds(menuItemsIds);

        if (currentUser.getRole().equals(Role.ADMIN) || currentUser.getId() == oldCheque.getId()){
            oldCheque.getMenuItems().clear();
            for (MenuItem newMenuItem : newMenuItems) {
                if (stopListRepo.existsByMenuItem(newMenuItem.getName())){
                    throw new AlreadyExistException(newMenuItem.getName() + " in a stop list");
                }
            }
            oldCheque.setMenuItems(newMenuItems);
            for (MenuItem newMenuItem : newMenuItems) {
                priceAverage += newMenuItem.getPrice();
            }
            oldCheque.setPriceAverage(priceAverage);
            chequeRepo.save(oldCheque);
        }
        else throw new ForbiddenException("forbidden request");


        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("cheque successfully updated")
                .build();
    }

    @Override
    public SimpleResponse deleteCheque(Long id) {

        Cheque cheque = chequeRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("cheque with id: " + id + " not found"));

        chequeRepo.delete(cheque);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("cheque successfully deleted")
                .build();
    }

    @Override
    public List<AverageChequeResponse> getAverageChequeFor1Day(LocalDate localDate) {
        return chequeRepo.getAverageChequeFor1Day(localDate);
    }
}
