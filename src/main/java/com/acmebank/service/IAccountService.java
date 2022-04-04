package com.acmebank.service;

import java.math.BigDecimal;

import com.acmebank.constant.Currency;
import com.acmebank.controllerDto.TransferReqDto;
import com.acmebank.controllerDto.TransferResDto;

public interface IAccountService {

	public BigDecimal getBalance(String accountNo,Currency currency) throws Exception;

	public TransferResDto transferMoney(TransferReqDto request) throws Exception;
}
