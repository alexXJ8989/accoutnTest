package com.acmebank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.acmebank.dto.Account;

@Repository
public interface AccountRespository extends CrudRepository<Account, Long>  {
	@Query("SELECT a FROM Account a WHERE a.accountNO = ?1")
	Optional<Account> findAccountByAccountNo(String accountNo);
	
}
