/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pti.myatm;


import org.junit.Test;

import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author andrii
*/
public class ATMTest {

    @Test
    public void testGetMoneyInATM() {
        System.out.println("getMoneyInATM");
        ATM test = Mockito.mock(ATM.class);
        double expResult = 0.0;
        double result = test.getMoneyInATM();
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testValidateCardFalse() throws NotCardInserted {
        System.out.println("validateCardFalse");
        Card card = Mockito.mock(Card.class);
        int pinCode = 0;
        ATM test = new ATM(1000.0);
        boolean expResult = false;
        boolean result = test.validateCard(card, pinCode);
        assertEquals(expResult, result);
        verify(card).checkPin(pinCode);
        verify(card).isBlocked();
    }
    @Test
    public void testValidateCardTrue() throws NotCardInserted {
        System.out.println("validateCardTrue");
        Card card = Mockito.mock(Card.class);
        int pinCode = 0;
        when(card.checkPin(pinCode)).thenReturn(true);
        when(card.isBlocked()).thenReturn(false);
        ATM test = new ATM(1000.0);
        boolean expResult = true;
        boolean result = test.validateCard(card, pinCode);
        assertEquals(expResult, result);
        verify(card).checkPin(pinCode);
        verify(card).isBlocked();
    }
    @Test
    public void testCheckBalance() throws NotCardInserted {
        System.out.println("checkBalance");
        ATM test = new ATM(0.0);
        Card card = Mockito.mock(Card.class);
        Account account = Mockito.mock(Account.class);
        test.setCard(card);
        when(card.getAccount()).thenReturn(account);
        when(account.getBalance()).thenReturn(0.0);
        double expResult = 0.0;
        double result = test.checkBalance();
        assertEquals(expResult, result, 0.0);
        verify(card).getAccount();
        verify(account).getBalance();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetCashAmountBellowZero() throws NotEnoughtMoneyInATM, NotEnoughtMoneyInAccount, NotCardInserted {
        System.out.println("getCashAmountBellowZero");
        double amount = -5.0;
        ATM atm = new ATM(0.0);
        Card card=Mockito.mock(Card.class);
        atm.setCard(card);
        Account account = Mockito.mock(Account.class);
        when(card.getAccount()).thenReturn(account);
        atm.getCash(amount);
    }



    @Test(expected = NotCardInserted.class)
    public void testGiveCardNotCardInsertedExeption() throws NotEnoughtMoneyInATM, NotEnoughtMoneyInAccount, NotCardInserted {
        System.out.println("NotCardInsertedExeption");
        ATM test = new ATM(100.0);
        test.giveCard();
    }
    @Test(expected = NotCardInserted.class)
    public void testGiveCardResidue() throws NotCardInserted {
        System.out.println("testGiveCardResidue");
        ATM test = new ATM(0.0);
        Card mockCard=Mockito.mock(Card.class);
        test.setCard(mockCard);
        test.giveCard();
        test.giveCard();
    }
    @Test(expected = UnsupportedOperationException.class)
    public void testSetCardMaxOneCardInReader(){
        System.out.println("SetCardMaxOneCardInReader");
        ATM test = new ATM(0.0);
        Card mockCard = Mockito.mock(Card.class);
        test.setCard(mockCard);
        test.setCard(mockCard);
    }
    @Test
    public void testSetCardCorrect(){
        System.out.println("SetCardCorrect");
        ATM test = new ATM(0.0);
        Card mockCard = Mockito.mock(Card.class);
        boolean result=test.setCard(mockCard);
        assertTrue(result);
    }

    @Test(expected = NotEnoughtMoneyInAccount.class)
    public void testGetCashNotEnoughtMoneyInAccount() throws NotCardInserted, NotEnoughtMoneyInAccount, NotEnoughtMoneyInATM {
        System.out.println("getCashNotEnoughtMoneyInAccount");
        ATM atm = new ATM(1000.0);
        Card card = Mockito.mock(Card.class);
        Account account = Mockito.mock(Account.class);
        int pin=0000;
        double balance=200.0;
        double amount=500.0;
        when(card.getAccount()).thenReturn(account);
        when(account.getBalance()).thenReturn(balance);
        when(card.checkPin(pin)).thenReturn(true);
        when(card.getAccount().withdrow(amount)).thenReturn(balance-amount);
        atm.validateCard(card,pin);
        atm.getCash(amount);
    }

    @Test(expected = NotEnoughtMoneyInATM.class)
    public void testGetCashNotEnoughtMoneyInATM() throws NotCardInserted, NotEnoughtMoneyInAccount, NotEnoughtMoneyInATM {
        System.out.println("getCashNotEnoughtMoneyInATM");
        ATM atm = new ATM(1000.0);
        Card card = Mockito.mock(Card.class);
        Account account = Mockito.mock(Account.class);
        int pin=0000;
        when(card.getAccount()).thenReturn(account);
        when(card.checkPin(pin)).thenReturn(true);
        atm.validateCard(card,pin);
        atm.getCash(1100.0);
    }

    @Test
    public void testGetCashNotZeroBalance() throws NotCardInserted, NotEnoughtMoneyInAccount, NotEnoughtMoneyInATM {
        System.out.println("getCashNotZeroBalance");
        double atmMoney = 1000.0;
        ATM atm = new ATM(atmMoney);
        Card card=Mockito.mock(Card.class);
        Account account=Mockito.mock(Account.class);
        int pin=0000;
        double amount = 100.0;
        double balance = 600.0;
        when(card.getAccount()).thenReturn(account);
        when(card.checkPin(pin)).thenReturn(true);
        when(card.isBlocked()).thenReturn(false);
        when(account.getBalance()).thenReturn(balance);
        when(account.withdrow(amount)).thenReturn(amount);
        atm.validateCard(card,pin);
        atm.getCash(amount);
        when(account.getBalance()).thenReturn(balance-amount);
        assertEquals(atm.getMoneyInATM(),atmMoney-amount, 0.0);
        assertEquals(atm.checkBalance(),balance-amount, 0.0);
    }
}