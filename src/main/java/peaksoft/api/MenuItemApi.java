package peaksoft.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.menuItemDto.MenuItemRequest;
import peaksoft.dto.menuItemDto.MenuItemResponse;
import peaksoft.service.MenuItemService;

import java.util.List;

@RestController
@RequestMapping("/api/menuItems")
@Tag(name = "menuItem-api")
@RequiredArgsConstructor
public class MenuItemApi {

    private final MenuItemService menuItemService;

    @PostMapping("/save/{restId}/{subcategoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    SimpleResponse saveMenuItem(@RequestBody @Valid MenuItemRequest menuItemRequest, @PathVariable Long restId, @PathVariable Long subcategoryId){
        return menuItemService.saveMenuItem(menuItemRequest, restId, subcategoryId);
    }

    @GetMapping("/get/{id}")
    @PermitAll
    MenuItemResponse getMenuItemById(@PathVariable Long id){
        return menuItemService.getMenuItemById(id);
    }

    @GetMapping("/getAllMenuItemsByRestaurantId/{id}")
    @PermitAll
    List<MenuItemResponse> getAllMenuItemsByRestaurantId(@PathVariable Long id){
        return menuItemService.getAllMenuItemsByRestaurantId(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    SimpleResponse updateMenuItem(@PathVariable Long id, @RequestBody @Valid MenuItemRequest newMenuItemRequest){
        return menuItemService.updateMenuItem(id, newMenuItemRequest);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    SimpleResponse deleteMenuItem(@PathVariable Long id){
        return menuItemService.deleteMenuItem(id);
    }

    @GetMapping("/search")
    @PermitAll
    List<MenuItemResponse> search(@RequestParam String word){
        return menuItemService.search(word);
    }

    @GetMapping("/isVegetarian")
    @PermitAll
    List<MenuItemResponse> getByVegetarian(@RequestParam boolean isVegetarian){
        return menuItemService.getByVegetarian(isVegetarian);
    }

}
