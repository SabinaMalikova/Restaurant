package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.menuItemDto.MenuItemRequest;
import peaksoft.dto.menuItemDto.MenuItemResponse;

import java.util.List;

public interface MenuItemService {
    SimpleResponse saveMenuItem(MenuItemRequest menuItemRequest, Long restId, Long subcategoryId);
    MenuItemResponse getMenuItemById(Long id);
    List<MenuItemResponse> getAllMenuItemsByRestaurantId(Long id);
    SimpleResponse updateMenuItem(Long id, MenuItemRequest newMenuItemRequest);
    SimpleResponse deleteMenuItem(Long id);

    List<MenuItemResponse> search (String word);
    List<MenuItemResponse> getByVegetarian(boolean isVegetarian);
}
