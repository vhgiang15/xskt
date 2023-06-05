package com.ungdungso.repository;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.ungdungso.model.HistorySearch;


public interface HistorySearchRepository extends JpaRepository<HistorySearch, BigInteger>{

	//@Query(value=" SELECT * from HistorySearch where userName=:cuser and (ngayDoVe between :cdateFrom and :cdateTo) order by ngayDoVe desc offset :coffset rows fetch next 5 rows only", nativeQuery = true)
	//List<HistorySearch> querygetListHistorySearch(@Param("cuser") String userName,@Param("cdateFrom") Date cdateFrom, @Param("cdateTo") Date cdateTo, @Param("coffset") int offset);
	
	@Query(value=" SELECT * from historysearch where userName=:cuser and (dateSearch between :cdateFrom and :cdateTo) order by dateSearch desc limit :coffset,5", nativeQuery = true)
	List<HistorySearch> querygetListHistorySearch(@Param("cuser") String userName,@Param("cdateFrom") Date cdateFrom, @Param("cdateTo") Date cdateTo, @Param("coffset") int offset);
	
	@Query(value=" SELECT count(*) from historysearch where userName=:cuser and (dateSearch between :cdateFrom and :cdateTo)", nativeQuery = true)
	int countHistorySearch(@Param("cuser") String userName,@Param("cdateFrom") Date cdateFrom,@Param("cdateTo") Date cdateTo);
	
}
