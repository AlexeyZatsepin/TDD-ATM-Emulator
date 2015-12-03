/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pti.myatm;


import org.junit.Test;

import org.mockito.InOrder;
import org.mockito.Mockito;
import sun.tools.asm.CatchData;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ATMTest {

    @Test
    public void testGetMoneyInATM() {
        System.out.println("getMoneyInATM");
        ATM test = new ATM(0.0);
        double expResult = 0.0;
        double result = test.getMoneyInATM();
        assertEquals(expResult, result, 0.0);
    }

    @Test(timeout = 1)
    public void testValidateBlockedCard() throws NotCardInserted {
        System.out.println("validateBlockedCard");
        Card card = Mockito.mock(Card.class);
        int pinCode = 0;
        ATM test = new ATM(1000.0);
        when(card.isBlocked()).thenReturn(true);
        when(card.checkPin(pinCode)).thenReturn(true);
        boolean result = test.validateCard(card, pinCode);
        assertFalse(result);
        verify(card,times(1)).isBlocked();
    }
    @Test
    public void testValidateCardNoValidPin() throws NotCardInserted {
        System.out.println("ValidateCardNoValidPin");
        Card card = Mockito.mock(Card.class);
        int pin=1000;
        ATM atm=new ATM(200);
        when(card.checkPin(pin)).thenReturn(false);
        when(card.isBlocked()).thenReturn(false);
        assertFalse(atm.validateCard(card,pin));
    }
    @Test(expected = NullPointerException.class)
    public void testValidateCardNullCard() throws NotCardInserted {
        System.out.println("ValidateCardNullCard");
        int pin=1023;
        ATM atm=new ATM(100);
        atm.validateCard(null,pin);
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
        InOrder inOrder=inOrder(card);
        inOrder.verify(card,times(1)).isBlocked();
        inOrder.verify(card,times(1)).checkPin(pinCode);

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
        InOrder inOrder=inOrder(card,account);
        inOrder.verify(card,times(1)).isBlocked();
        inOrder.verify(card,times(1)).getAccount();
        inOrder.verify(account,times(1)).getBalance();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckBalanceNoValidCard() throws NotCardInserted {
        System.out.println("checkBalanceNoValidCard");
        ATM atm=new ATM(1000);
        Card card=Mockito.mock(Card.class);
        atm.setCard(card);
        when(card.isBlocked()).thenReturn(true);
        atm.checkBalance();
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
        System.out.println("GiveCardResidue");
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
        InOrder inOrder=inOrder(card,account);
        atm.validateCard(card,pin);
        atm.getCash(amount);
        inOrder.verify(card).getAccount();
        inOrder.verify(account).getBalance();
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

    @Test(timeout = 15)
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
        InOrder inOrder = inOrder(card,account);
        inOrder.verify(card,times(1)).isBlocked();
        inOrder.verify(card,times(1)).checkPin(pin);
        inOrder.verify(card,times(4)).getAccount();
        verify(account,times(1)).withdrow(amount);
        inOrder.verify(account,times(1)).getBalance();

    }
    @Test(expected = UnsupportedOperationException.class)
    public void testConstructor(){
        ATM atm = new ATM(-10.0);
    }
}