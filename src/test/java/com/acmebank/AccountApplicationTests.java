package com.acmebank;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.acmebank.constant.Currency;
import com.acmebank.controllerDto.TransferReqDto;
import com.acmebank.service.IAccountService;


@SpringBootTest
class AccountApplicationTests {


	@Autowired
	IAccountService accountService;
	
	@Test
	public void testGetBalance() throws Exception
	{
		BigDecimal b=accountService.getBalance("88888888", Currency.HKD);
		Assert.assertTrue(Objects.nonNull(b)&&b.compareTo(BigDecimal.ZERO)>=0);
		
	}
	
	@Test
	public void testTransferMoney() throws Exception
	{
		BigDecimal b=accountService.getBalance("88888888", Currency.HKD);
		TransferReqDto request  = new TransferReqDto();
		request.setFromAccountNo("88888888");
		request.setToAccountNo("12345678");
		request.setCurrency(Currency.HKD);
		request.setAmount(new BigDecimal(100));
		
		BigDecimal b8_open=accountService.getBalance("88888888", Currency.HKD);
		accountService.transferMoney(request);
		
		BigDecimal b8_close=accountService.getBalance("88888888", Currency.HKD);
		
		Assert.assertTrue(new BigDecimal(100).compareTo(b8_open.subtract(b8_close))==0);
		
		// transer back, then no need to reset db data too often
		
		TransferReqDto requestBack  = new TransferReqDto();
		requestBack.setToAccountNo("88888888");
		requestBack.setFromAccountNo("12345678");
		requestBack.setCurrency(Currency.HKD);
		requestBack.setAmount(new BigDecimal(100));
	}
	
	
	@Test
	public void testConcurrencyTransfer() throws Exception
	{
		
		BigDecimal b8_init=accountService.getBalance("88888888", Currency.HKD);
		BigDecimal b1_init=accountService.getBalance("12345678", Currency.HKD);

		 List<Thread> threads = new ArrayList<Thread>();
		 
		 // create multiple threads
		 for(int i=0;i<50;i++)
		 {
			 final String guestName = "concurrentGuestName"+i;
			Thread t = new Thread(()->{
				
				try{ 
					TransferReqDto request  = new TransferReqDto();
					request.setFromAccountNo("88888888");
					request.setToAccountNo("12345678");
					request.setCurrency(Currency.HKD);
					request.setAmount(new BigDecimal(100));
					
					accountService.transferMoney(request);
					System.out.print(guestName+" transfer from 88888888 to 12345678" + " successfully");
					
				}
				catch(Exception e)
				{
					System.out.print(guestName+" transfer from 88888888 to 12345678" + " failed");
				}
				
				//transfer money back, then less db data reset;
				try {
					TransferReqDto requestBack  = new TransferReqDto();
					requestBack.setToAccountNo("88888888");
					requestBack.setFromAccountNo("12345678");
					requestBack.setCurrency(Currency.HKD);
					requestBack.setAmount(new BigDecimal(100));
					accountService.transferMoney(requestBack);
					
					System.out.print(guestName+" transfer from 12345678  to 88888888" + " successfully");
				} catch (Exception e) {
					System.out.print(guestName+" transfer from 12345678  to 88888888" + " failed");
				}	
			});
			threads.add(t);
		 }
		 
		 // run multiple thread in one batch
		 for(Thread t:threads)
		 {
			 t.start();
		 }
		 
		 //wait all thread done
		 for(Thread t:threads)
		 {
			 t.join();
		 }
		 
			BigDecimal b8_close=accountService.getBalance("88888888", Currency.HKD);
			BigDecimal b1_close=accountService.getBalance("12345678", Currency.HKD);
			
			// the totall amount of both account should keep same before and after money transfer;
			Assert.assertTrue((b8_init.add(b1_init)).compareTo(b8_close.add(b1_close))==0);
	 
	}
	
}
