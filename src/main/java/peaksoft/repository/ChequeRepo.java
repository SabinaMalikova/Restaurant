package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.chequeDto.AverageChequeResponse;
import peaksoft.dto.chequeDto.ChequeResponse;
import peaksoft.entity.Cheque;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChequeRepo extends JpaRepository<Cheque, Long> {

    @Query("select new peaksoft.dto.chequeDto.ChequeResponse(" +
            "u.id,"+
            "concat(u.firstName, ' ', u.lastName), " +
//            "c.menuItems, " +
            "c.priceAverage, " +
            "r.service, " +
            "(c.priceAverage + ((c.priceAverage/100) * r.service)))" +
            "from Cheque c " +
            "inner join c.user u " +
            "inner join u.restaurant r " +
            "where c.id=:id")
    Optional<ChequeResponse> getChequeById(Long id);

    @Query("select new peaksoft.dto.chequeDto.ChequeResponse(" +
            "u.id,"+
            "concat(u.firstName, ' ', u.lastName), " +
//            "c.menuItems, " +
            "c.priceAverage, " +
            "r.service, " +
            "(c.priceAverage + ((c.priceAverage/100) * r.service)))" +
            "from Cheque c " +
            "inner join c.user u " +
            "inner join u.restaurant r " +
            "where c.user.id=:id")
    List<ChequeResponse> getChequesByWaiterId(Long id);

    @Query("select new peaksoft.dto.chequeDto.ChequeResponse("+
            "u.id,"+
            "concat(u.firstName, ' ', u.lastName), " +
//            "c.menuItems, " +
            "c.priceAverage, " +
            "r.service, " +
            "(c.priceAverage + ((c.priceAverage/100) * r.service)))" +
            "from Cheque c " +
            "inner join c.user u " +
            "inner join u.restaurant r")
    List<ChequeResponse> getAllCheques();


    @Query("select new peaksoft.dto.chequeDto.AverageChequeResponse(" +
            "r.name," +
            "avg(c.priceAverage + ((c.priceAverage/100) * r.service))," +
            "c.createdAt) " +
            "from Cheque c " +
            "inner join c.user u " +
            "inner join u.restaurant r " +
            "where c.createdAt=:localDate " +
            "group by r.name, c.createdAt")
    List<AverageChequeResponse> getAverageChequeFor1Day(LocalDate localDate);


}
