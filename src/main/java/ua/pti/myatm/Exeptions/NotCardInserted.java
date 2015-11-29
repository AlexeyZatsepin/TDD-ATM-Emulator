package ua.pti.myatm.Exeptions;

/**
 * Created by Alex on 11/24/15.
 */
public class NotCardInserted extends Exception{
    public NotCardInserted() {
        super("Нету карты");
    }
}
