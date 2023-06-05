package com.ungdungso.repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ungdungso.model.LotteryResults;

public interface LotteryResultReponsitory extends JpaRepository<LotteryResults, BigInteger> {

	@Query(value="select * from lotteryresults where statusLottery='enable' and datePrize=:cngay and idProvince=:cprovince", nativeQuery = true)
	LotteryResults getKetQuaXoSoByProvinceAndDate(@Param("cngay") Date ngay, @Param("cprovince") String province);
	
	@Query(value=" SELECT DISTINCT datePrize from lotteryresults where statusLottery='enable' and idProvince=:cid", nativeQuery = true)
	List<String> querygetDateSaveKQSX(@Param("cid") String idProvince);
	
	@Query(value=" SELECT DISTINCT datePrize from lotteryresults where statusLottery='enable' and idProvince=:cid and (ngayXoSo between :cdateTo and :cdateFrom) order by datePrize desc", nativeQuery = true)
	List<Date> querygetListKQSX(@Param("cid") String idProvince,@Param("cdateTo") Date cdateTo,@Param("cdateFrom") Date cdateFrom);
	
	@Query(value=" SELECT COUNT(DISTINCT datePrize) from lotteryresults where statusLottery='enable'and idProvince=:cid and (datePrize between :cdateTo and :cdateFrom)", nativeQuery = true)
	Integer queryCountKQSX(@Param("cid") String idProvince,@Param("cdateTo") Date cdateTo,@Param("cdateFrom") Date cdateFrom);

	
	@Query(value=" SELECT DISTINCT datePrize from lotteryresults where statusLottery='enable' and idProvince=:cid and (datePrize between :cdateTo and :cdateFrom) order by datePrize desc limit :coffset,5", nativeQuery = true)
	List<Date> querygetListKQSX(@Param("cid") String idProvince, @Param("cdateTo") Date cdateTo,@Param("cdateFrom") Date cdateFrom, @Param("coffset") int offset);
	
	
	@Query(value=" select idLottery from lotteryresults where statusLottery='enable' and idProvince=:cidProvince and datePrize= :cdate", nativeQuery = true)
	List<BigInteger> queryGetIDKQSXByProvinByDate(@Param("cidProvince") String idProvince, @Param("cdate") Date cdate);
	
	
	@Query(value="select count( distinct idProvince) from lotteryresults where statusLottery='enable' and idProvince= :cid", nativeQuery = true)
	Integer queryCountProvince(@Param("cid") String idProvince);
	
	
	@Query(value="select distinct datePrize from lotteryresults where statusLottery='enable' and idProvince=:cid order by datePrize desc limit 0,4", nativeQuery = true)
	List<Date> querygetListKQSXTop4(@Param("cid") String idProvince);
	
	@Query(value="select count(distinct idProvince) from lotteryresults where statusLottery='enable' and datePrize= :cdate", nativeQuery = true)
	Integer queryProvinceByDate(@Param("cdate") Date date);

}
