package com.acmebank.controllerDto;

public class BalanceResponseDto {

	String accountNo;
	String balance;
	
	
	public BalanceResponseDto(String accountNo, String balance) {
		super();
		this.accountNo = accountNo;
		this.balance = balance;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	
	
}
