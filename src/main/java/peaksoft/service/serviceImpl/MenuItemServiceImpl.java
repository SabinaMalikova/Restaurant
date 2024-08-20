package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.menuItemDto.MenuItemRequest;
import peaksoft.dto.menuItemDto.MenuItemResponse;
import peaksoft.entity.Cheque;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Restaurant;
import peaksoft.entity.Subcategory;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.MenuItemRepo;
import peaksoft.repository.RestaurantRepo;
import peaksoft.repository.SubcategoryRepo;
import peaksoft.service.MenuItemService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepo menuItemRepo;
    private final RestaurantRepo restaurantRepo;
    private final SubcategoryRepo subcategoryRepo;

    @Override
    public SimpleResponse saveMenuItem(MenuItemRequest menuItemRequest, Long restId, Long subcategoryId) {

        Restaurant restaurant = restaurantRepo.findById(restId)
                .orElseThrow(() -> new NotFoundException("restaurant with id: " + restId + " not found"));

        Subcategory subcategory = subcategoryRepo.findById(subcategoryId)
                .orElseThrow(() -> new NotFoundException("subcategory with id: " + subcategoryId + " not found"));

        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemRequest.name());
        menuItem.setImage(menuItemRequest.image());
        menuItem.setPrice(menuItemRequest.price());
        menuItem.setDescription(menuItemRequest.description());
        menuItem.setVegetarian(menuItemRequest.isVegetarian());
        menuItem.setSubcategory(subcategory);
        menuItem.setRestaurant(restaurant);
        menuItemRepo.save(menuItem);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("menu item successfully saved")
                .build();
    }

    @Override
    public MenuItemResponse getMenuItemById(Long id) {
        return menuItemRepo.getMenuItemById(id)
                .orElseThrow(()->new NotFoundException("menu item with id: " + id + " not found"));
    }

    @Override
    public List<MenuItemResponse> getAllMenuItemsByRestaurantId(Long id) {
        return menuItemRepo.getAllMenuItemsByRestaurantId(id);
    }

    @Override
    public SimpleResponse updateMenuItem(Long id, MenuItemRequest newMenuItemRequest) {

        MenuItem oldMenuItem = menuItemRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("menu item with id: " + id + " not found"));

        oldMenuItem.setName(newMenuItemRequest.name());
        oldMenuItem.setImage(newMenuItemRequest.image());
        oldMenuItem.setPrice(newMenuItemRequest.price());
        oldMenuItem.setDescription(newMenuItemRequest.description());
        oldMenuItem.setVegetarian(newMenuItemRequest.isVegetarian());
        menuItemRepo.save(oldMenuItem);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("menu item successfully updated")
                .build();
    }

    @Override
    public SimpleResponse deleteMenuItem(Long id) {

        MenuItem menuItem = menuItemRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("menu item with id: " + id + " not found"));

        for (Cheque cheque : menuItem.getCheques()) {
            cheque.getMenuItems().remove(menuItem);
        }
        menuItem.getCheques().clear();

        menuItemRepo.delete(menuItem);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("menu item successfully deleted")
                .build();
    }

    @Override
    public List<MenuItemResponse> search(String word) {
        return menuItemRepo.search(word);
    }

    @Override
    public List<MenuItemResponse> getByVegetarian(boolean isVegetarian) {
        return menuItemRepo.getByVegetarian(isVegetarian);
    }
}
