package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.categoryDto.CategoryResponseAll;
import peaksoft.entity.Category;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

    @Query("select new peaksoft.dto.categoryDto.CategoryResponseAll(c.name) " +
            "from Category c " +
            "where c.id = :id")
    Optional<CategoryResponseAll> getCategory(Long id);

    @Query("select new peaksoft.dto.categoryDto.CategoryResponseAll(c.name) from Category c")
    List<CategoryResponseAll> getAllCategories();
}
