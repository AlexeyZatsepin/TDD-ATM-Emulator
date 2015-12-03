package ua.pti.myatm;

/**
 * Created by Alex on 11/24/15.
 */
public class NotCardInserted extends Exception{
    public NotCardInserted() {
        super("Нету карты");
    }
}
