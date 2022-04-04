package com.acmebank.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="account")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name="account_no")
	private String accountNO;
	@Column(name="balance_hkd")
	private BigDecimal balanceHKD;
	@Version
	private long version;

	
	//=======================getter and setter
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAccountNO() {
		return accountNO;
	}
	public void setAccountNO(String accountNO) {
		this.accountNO = accountNO;
	}

	public BigDecimal getBalanceHKD() {
		return balanceHKD;
	}
	public void setBalanceHKD(BigDecimal balanceHKD) {
		this.balanceHKD = balanceHKD;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	@Override
	public String toString() {
		return "Account [id=" + id + ", accountNO=" + accountNO + ", balance=" + balanceHKD + ", version=" + version + "]";
	}
	
	
}
