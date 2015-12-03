package ua.pti.myatm;

/**
 * Created by Alex on 11/24/15.
 */
public class NotEnoughtMoneyInAccount extends Exception{
    public NotEnoughtMoneyInAccount() {
        super("No Money, no honey");
    }
}
