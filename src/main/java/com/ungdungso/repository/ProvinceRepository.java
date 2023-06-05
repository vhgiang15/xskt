package com.ungdungso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.ungdungso.model.Province;



public interface ProvinceRepository extends JpaRepository<Province, Integer> {
	
	@Query(value="select * from province where nameProvince=:cname", nativeQuery = true)
	Province getProvinceByName(@Param("cname") String name);
	
	@Query(value="select * from province where idProvince=:cid", nativeQuery = true)
	Province getProvinceByID(@Param("cid") String idProvince);
	
	@Query(value="select * from province where dayOfWeek like :cdayOfWeek", nativeQuery = true)
	List<Province> getAllByDate(@Param("cdayOfWeek") String dayOfWeek);
	
	@Query(value="select * from province where dayOfWeek like :cdayOfWeek and area=:carea", nativeQuery = true)
	List<Province> querygetAllByDateByArea(@Param("cdayOfWeek") String dayOfWeek, @Param("carea") String area);
	
	@Query(value="select dayOfWeek from province where idProvince=:cid", nativeQuery = true)
	String queryGetThuById(@Param("cid") String id);
	
	
	/////// đã fix
	@Query(value="select idProvince from province where nameProvince=:cname", nativeQuery = true)
	String queryGetIdByName(@Param("cname") String name);
	
	
	@Query(value="select area from province where nameProvince=:cname", nativeQuery = true)
	String queryGetAreaByName(@Param("cname") String name);
	
	@Query(value="select * from province where area=:carea order by nameProvince", nativeQuery = true)
	List<Province> queryGetProvinceByArea(@Param("carea") String area);
	
	
	@Query(value=" select * from province where nameProvince like :cname order by idProvince limit :coffset,5", nativeQuery = true)
	List<Province> getAllByName(@Param("cname") String name,@Param("coffset") int offset);
	
	
	@Query(value=" select count(nameProvince) from province where nameProvince like :cname", nativeQuery = true)
	Integer countProvinceByName(@Param("cname") String name);
	
	@Query(value="select count(idProvince) from province where status = :cstatus", nativeQuery = true)
	Integer queryCountProvincStatus(@Param("cstatus") String status);		

}
