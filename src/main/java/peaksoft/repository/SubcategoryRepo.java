package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.subcategoryDto.SubcategoryResponse;
import peaksoft.entity.Subcategory;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubcategoryRepo extends JpaRepository<Subcategory, Long> {

    @Query("select new peaksoft.dto.subcategoryDto.SubcategoryResponse(" +
            "s.category.name, " +
            "s.name) " +
            "from Subcategory s " +
            "where s.id=:id")
    Optional<SubcategoryResponse> getSubcategoryById(Long id);

    @Query("select new peaksoft.dto.subcategoryDto.SubcategoryResponse(" +
            "s.category.name, " +
            "s.name) " +
            "from Subcategory s")
    List<SubcategoryResponse> getAllSubcategories();

    @Query("select new peaksoft.dto.subcategoryDto.SubcategoryResponse(" +
            "s.category.name, " +
            "s.name) " +
            "from Subcategory s where s.category.id=:categoryId order by s.name")
    List<SubcategoryResponse> getAllSubcategoriesByCategoryId(Long categoryId);

}
