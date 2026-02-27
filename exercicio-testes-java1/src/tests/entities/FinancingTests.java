package tests.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import entities.Financing;
import tests.factory.FinancingFactory;

public class FinancingTests {
	
	@Test
	public void creatingValidFinancingShouldHaveNoError() {
		
		double totalAmount = 100000.0;
		double income = 2000.0;
		Integer months = 80;		
		
		Assertions.assertDoesNotThrow(() -> {
	        Financing fin = FinancingFactory.createFinancing(totalAmount, income, months);
	    });
	}
	
	@Test
	public void creatingInvalidFinancingShouldReturnError() {
					
		double totalAmount = 100000.0;
		double income = 2000.0;
		Integer months = 20;
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Financing fin = FinancingFactory.createFinancing(totalAmount, income, months);			
		} );
	}
	
	
	@Test
	public void setTotalAmountShouldUpdateWhenValidValue() {
		
		double totalAmount = 100000.0;
		double newTotalAmount = 80000.0;
		double income = 2000.0;
		Integer months = 80;
		
		Financing fin = FinancingFactory.createFinancing(totalAmount, income, months);
		
		Assertions.assertDoesNotThrow(() -> {
			fin.setTotalAmount(newTotalAmount);
	    });
		
		Assertions.assertTrue(newTotalAmount == fin.getTotalAmount());
	}
	
	@Test
	public void setTotalAmountShouldReturnErrorWhenInvalidValue() {
		
		double totalAmount = 100000.0;
		double newTotalAmount = 120000.0;
		double income = 2000.0;
		Integer months = 80;
		
		Financing fin = FinancingFactory.createFinancing(totalAmount, income, months);		
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			fin.setTotalAmount(newTotalAmount);			
		} );
	}
	
	
	@Test
	public void setIncomeShouldUpdateWhenValidValue() {
		
		double totalAmount = 100000.0;		
		double income = 2000.0;
		double newIncome = 2500.0;
		Integer months = 80;
		
		Financing fin = FinancingFactory.createFinancing(totalAmount, income, months);
		
		Assertions.assertDoesNotThrow(() -> {
			fin.setIncome(newIncome);
	    });
		
		Assertions.assertTrue(newIncome == fin.getIncome());
	}
	
	@Test
	public void setIncomeShouldReturnErrorWhenInvalidValue() {
		
		double totalAmount = 100000.0;		
		double income = 2000.0;
		double newIncome = 1500.0;
		Integer months = 80;
		
		Financing fin = FinancingFactory.createFinancing(totalAmount, income, months);		
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			fin.setIncome(newIncome);			
		} );
	}
	
	
	
	@Test
	public void setMonthsShouldUpdateWhenValidValue() {
		
		double totalAmount = 100000.0;		
		double income = 2000.0;		
		Integer months = 80;
		Integer newMonths = 100;
		
		Financing fin = FinancingFactory.createFinancing(totalAmount, income, months);
		
		Assertions.assertDoesNotThrow(() -> {
			fin.setMonths(newMonths);
	    });
		
		Assertions.assertTrue(newMonths == fin.getMonths());
	}
	
	@Test
	public void setMonthsShouldReturnErrorWhenInvalidValue() {
		
		double totalAmount = 100000.0;		
		double income = 2000.0;		
		Integer months = 80;
		Integer newMonths = 10;
		
		Financing fin = FinancingFactory.createFinancing(totalAmount, income, months);		
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			fin.setMonths(newMonths);			
		} );
	}
	
	
	@Test
	public void entryShouldCalculateCorrectValue() {
		
		double totalAmount = 100000.0;		
		double income = 2000.0;		
		Integer months = 80;		
		
		Financing fin = FinancingFactory.createFinancing(totalAmount, income, months);		
		
		Assertions.assertTrue( 20000.0 == fin.entry() );
	}
	
	
	@Test
	public void quotaShouldCalculateCorrectValue() {
		
		double totalAmount = 100000.0;		
		double income = 2000.0;		
		Integer months = 80;		
		
		Financing fin = FinancingFactory.createFinancing(totalAmount, income, months);		
		
		Assertions.assertTrue( 1000.0 == fin.quota() );
	}

}
