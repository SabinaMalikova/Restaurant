package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.stopListDto.StopListResponse;
import peaksoft.entity.StopList;

import java.util.List;
import java.util.Optional;

@Repository
public interface StopListRepo extends JpaRepository<StopList, Long> {
    @Query("select new peaksoft.dto.stopListDto.StopListResponse(" +
            "s.menuItem.name, " +
            "s.reason, " +
            "s.date) " +
            "from StopList s " +
            "where s.id=:id")
    Optional<StopListResponse>  getStopListById(Long id);

    @Query("select new peaksoft.dto.stopListDto.StopListResponse(" +
            "s.menuItem.name, " +
            "s.reason, " +
            "s.date) " +
            "from StopList s ")
    List<StopListResponse> getAllStopLists();

    @Query("select count(s) > 0 from StopList s where s.menuItem.name=:menuItem")
    boolean existsByMenuItem(String menuItem);


}
