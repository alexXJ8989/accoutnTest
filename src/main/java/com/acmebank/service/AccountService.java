package com.acmebank.service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acmebank.constant.Currency;
import com.acmebank.controllerDto.TransferReqDto;
import com.acmebank.controllerDto.TransferResDto;
import com.acmebank.dto.Account;
import com.acmebank.repository.AccountRespository;

@Service
public class AccountService implements IAccountService{

	@Autowired
	AccountRespository accountRespository;
	@Override
	public BigDecimal getBalance(String accountNo,Currency currency) throws Exception {
		
		Account a = null;
		if(Currency.HKD.equals(currency))
		{
			 a=accountRespository.findAccountByAccountNo(accountNo).orElseThrow(()-> new Exception("account not found"));
		}
		else
		{
			throw new Exception("currency is not supported"); // may customize exception when business required;
		}
		
		return a.getBalanceHKD();
	}
	@Override
	@Transactional // make sure atomic,  better work with config:spring.jpa.open-in-view=false
	public TransferResDto transferMoney(TransferReqDto request) throws Exception {
		
		if(Objects.equals(request.getFromAccountNo(), request.getToAccountNo()))
		{
			throw new Exception("not support same account transfer");
		}
		
		Account fromAccount=accountRespository.findAccountByAccountNo(request.getFromAccountNo()).orElseThrow(()->new Exception("source account not found"));
		Account toAccount=accountRespository.findAccountByAccountNo(request.getToAccountNo()).orElseThrow(()->new Exception("target account not found"));
		if(fromAccount.getBalanceHKD().compareTo(request.getAmount())<0)
		{
			throw new Exception("balance not sufficient");
		}
		TransferResDto response = new TransferResDto();
		response.setOpenBalance(fromAccount.getBalanceHKD());
		fromAccount.setBalanceHKD(fromAccount.getBalanceHKD().subtract(request.getAmount()));
		toAccount.setBalanceHKD(toAccount.getBalanceHKD().add(request.getAmount()));
		response.setCloseBalance(fromAccount.getBalanceHKD());
		accountRespository.save(fromAccount); // work with @Version to take advantage of optimistic lock.
		accountRespository.save(toAccount);
		//TODO: may add transaction record if business required.
		
		String refNo = UUID.randomUUID().toString(); // may have better design on the reference no according to business need.
		
		 response.setRefNo(refNo);
		 response.setReqeust(request);
		return response;
	}
	
	
}
