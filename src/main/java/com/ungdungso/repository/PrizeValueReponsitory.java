package com.ungdungso.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.ungdungso.model.PrizeValue;



public interface PrizeValueReponsitory extends JpaRepository<PrizeValue, String> {
	@Query(value=" SELECT idPrize from prizevalue", nativeQuery = true)
	List<String> querygetIdPrize();
	
	@Query(value="SELECT * FROM prizevalue WHERE idPrize LIKE '%MN' ", nativeQuery = true)
	List<PrizeValue> querygetIDGiaTriGiaiMN();
	
	@Query(value="SELECT * FROM prizevalue WHERE idPrize LIKE '%MB' ", nativeQuery = true)
	List<PrizeValue> querygetIDGiaTriGiaiMB();

}
