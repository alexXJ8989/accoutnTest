package com.acmebank.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acmebank.constant.Currency;
import com.acmebank.controllerDto.BalanceResponseDto;
import com.acmebank.controllerDto.GeneralResponseDto;
import com.acmebank.controllerDto.TransferReqDto;
import com.acmebank.controllerDto.TransferResDto;
import com.acmebank.service.IAccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	IAccountService accountService;
	
	@GetMapping("/balance/{currency}/{accountNo}")
	public GeneralResponseDto<BalanceResponseDto> getBalance(@PathVariable("accountNo") String accountNo,@PathVariable("currency") Currency currency)
	{
		BigDecimal balance;
		GeneralResponseDto<BalanceResponseDto> response=null;
		try {
			balance = accountService.getBalance(accountNo,currency);
			BalanceResponseDto balanceDto = new BalanceResponseDto(accountNo,balance.toPlainString());
			response = GeneralResponseDto.success(balanceDto);
		} catch (Exception e) {
			response = GeneralResponseDto.fail(null,e.getMessage());
		}
	
		return response;
	}
	
	@PostMapping("/remittance")
	public GeneralResponseDto<TransferResDto> transferMoney(@RequestBody TransferReqDto request)
	{	
		TransferResDto result = null;
		GeneralResponseDto<TransferResDto> response= null;
		try {
			 result = accountService.transferMoney(request);
			 response = GeneralResponseDto.success(result);
		} catch (Exception e) {
			e.printStackTrace();
			 response = GeneralResponseDto.fail(null,e.getMessage());
		}
		return response;
	}
}
