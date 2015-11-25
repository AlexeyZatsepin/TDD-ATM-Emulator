package ua.pti.myatm;

public class ATM {

    private double moneyInATM;
    private Card card;
    //Можно задавать количество денег в банкомате 
    ATM(double moneyInATM){
        if (moneyInATM<0) {
            throw new UnsupportedOperationException("money can not be under zero");
        }
            this.moneyInATM=moneyInATM;
            this.card=null;
    }
    //Вставляет картоку
    public boolean setCard(Card card){
        if (this.card==null){
            this.card=card;
            return true;
        }else{
            throw new UnsupportedOperationException("Карточка уже вставлена");
        }
    }
    //Вынимает карточку
    public boolean giveCard() throws NotCardInserted {
        if (this.card!=null){
            this.card=null;
            return true;
        }else{
            throw new NotCardInserted();
        }
    }
    // Возвращает каоличестов денег в банкомате
    public double getMoneyInATM() {
        //throw new UnsupportedOperationException("Not yet implemented");
        return moneyInATM;
    }


    //С вызова данного метода начинается работа с картой
    //Метод принимает карту и пин-код, проверяет пин-код карты и не заблокирована ли она
    //Если неправильный пин-код или карточка заблокирована, возвращаем false.
    // При этом, вызов всех последующих методов у ATM с данной картой должен генерировать исключение NoCardInserted
    public boolean validateCard(Card card, int pinCode) throws NotCardInserted {
        if (!card.isBlocked()&&card.checkPin(pinCode)){
            this.setCard(card);
            return true;
        }else {
            System.out.println("Card is blocked");
            return false;
        }
    }
    
    //Возвращает сколько денег есть на счету
    public double checkBalance() throws NotCardInserted {
        return this.card.getAccount().getBalance();
    }
    
    //Метод для снятия указанной суммы
    //Метод возвращает сумму, которая у клиента осталась на счету после снятия
    //Кроме проверки счета, метод так же должен проверять достаточно ли денег в самом банкомате
    //Если недостаточно денег на счете, то должно генерироваться исключение NotEnoughMoneyInAccount 
    //Если недостаточно денег в банкомате, то должно генерироваться исключение NotEnoughMoneyInATM 
    //При успешном снятии денег, указанная сумма должна списываться со счета, и в банкомате должно уменьшаться количество денег
    public double getCash(double amount) throws NotCardInserted, NotEnoughtMoneyInATM, NotEnoughtMoneyInAccount {
        //this.isCardPresence();
        Account account = card.getAccount();
        if (amount>0) {
            if (amount > this.getMoneyInATM()) {
                throw new NotEnoughtMoneyInATM();
            } else if (amount > this.checkBalance()) {
                throw new NotEnoughtMoneyInAccount();
            } else {
                moneyInATM -= account.withdrow(amount);
            }
            return this.checkBalance();
        }
        else{
            throw new UnsupportedOperationException("amount can't be negative");
        }
    }

}
