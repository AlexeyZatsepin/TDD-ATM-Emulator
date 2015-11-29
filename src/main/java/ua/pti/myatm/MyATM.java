package ua.pti.myatm;

import ua.pti.myatm.Exeptions.NotCardInserted;
import ua.pti.myatm.Exeptions.NotEnoughtMoneyInATM;
import ua.pti.myatm.Exeptions.NotEnoughtMoneyInAccount;

public class MyATM {

    public static void main(String[] args) throws NotEnoughtMoneyInATM, NotEnoughtMoneyInAccount, NotCardInserted {
        double moneyInATM = 1000;
        ATM atm = new ATM(moneyInATM);
        Card card = null;
        atm.validateCard(card, 1234);
        atm.checkBalance();
        atm.getCash(999.99);        
    }
}
