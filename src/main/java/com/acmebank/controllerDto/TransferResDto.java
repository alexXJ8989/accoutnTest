package com.acmebank.controllerDto;

import java.math.BigDecimal;

public class TransferResDto {

	private String refNo;
	private BigDecimal openBalance;
	private BigDecimal closeBalance;
	TransferReqDto reqeust;
	
	
	
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public TransferReqDto getReqeust() {
		return reqeust;
	}
	public void setReqeust(TransferReqDto reqeust) {
		this.reqeust = reqeust;
	}

	public BigDecimal getOpenBalance() {
		return openBalance;
	}

	public void setOpenBalance(BigDecimal openBalance) {
		this.openBalance = openBalance;
	}

	public BigDecimal getCloseBalance() {
		return closeBalance;
	}

	public void setCloseBalance(BigDecimal closeBalance) {
		this.closeBalance = closeBalance;
	}
	
	
}
