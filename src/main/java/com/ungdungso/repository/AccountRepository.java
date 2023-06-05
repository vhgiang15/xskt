package com.ungdungso.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ungdungso.model.Account;
public interface AccountRepository extends JpaRepository<Account, String> {
	@Query(value="select permission from account where userName=:cid", nativeQuery = true)
	String getPermission(@Param("cid") String userName);

	@Query(value="select userName from account where userName=:cuser", nativeQuery = true)
	String queryGetUserName(@Param("cuser") String userName);
	
	@Query(value="select mail from account where mail=:cmail", nativeQuery = true)
	String queryGetMail(@Param("cmail") String mail);
	
	@Query(value="select phone from account where phone=:cphone", nativeQuery = true)
	String queryGetPhone(@Param("cphone") String phone);
	
	@Query(value="select * from account where mail=:cmail", nativeQuery = true)
	Account queryGetByEmail(@Param("cmail") String userName);
	
	@Query(value="select * from account order by userName limit :coffset,5", nativeQuery = true)
	List<Account> querygetAll(@Param("coffset") int offset);
	
	
	
	@Query(value=" select * from account where userName like :cuserName or phone like :cphone order by userName limit :coffset,5", nativeQuery = true)
	List<Account> querygetAccountByUserOrPhone(@Param("cuserName") String userName,@Param("cphone") String phone,@Param("coffset") int offset);
	
	
	@Query(value="select count(userName) from account", nativeQuery = true)
	Integer queryTotlalUser();
	
	@Query(value="select count(userName) from account where available='enable' ", nativeQuery = true)
	Integer querycountUserEnable();
}
