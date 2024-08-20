package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.menuItemDto.MenuItemResponse;
import peaksoft.entity.MenuItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemRepo extends JpaRepository<MenuItem, Long> {

    @Query("select new peaksoft.dto.menuItemDto.MenuItemResponse(" +
            "m.name," +
            "m.image," +
            "m.price," +
            "m.description," +
            "m.isVegetarian) from MenuItem m where m.id=:id")
    Optional<MenuItemResponse> getMenuItemById(Long id);

    @Query("select new peaksoft.dto.menuItemDto.MenuItemResponse(" +
            "m.name," +
            "m.image," +
            "m.price," +
            "m.description," +
            "m.isVegetarian) from MenuItem m where m.restaurant.id=:id")
    List<MenuItemResponse> getAllMenuItemsByRestaurantId(Long id);



    @Query("select distinct new peaksoft.dto.menuItemDto.MenuItemResponse(" +
            "m.name, " +
            "m.image, " +
            "m.price, " +
            "m.description, " +
            "m.isVegetarian ) from MenuItem m " +
            "where lower(m.name) like lower(concat('%', :word, '%'))")
    List<MenuItemResponse> search(String word);

    @Query("select m from MenuItem m where m.id in :menuItemsIds")
    List<MenuItem> getMenuItemsByIds(List<Long> menuItemsIds);

    @Query("select distinct new peaksoft.dto.menuItemDto.MenuItemResponse(" +
            "m.name, " +
            "m.image, " +
            "m.price, " +
            "m.description, " +
            "m.isVegetarian ) from MenuItem m where m.isVegetarian=:isVegetarian")
    List<MenuItemResponse> getByVegetarian(boolean isVegetarian);
}
