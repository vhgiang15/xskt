package com.ungdungso.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.ungdungso.model.PrizeCode;



public interface PrizeCodePepository extends JpaRepository<PrizeCode, String> {
	@Query(value="select * from prizecode where nameCode like :carea order by nameCode", nativeQuery = true)
	List<PrizeCode> queryGetPrizeCode(@Param("carea") String area);

}
