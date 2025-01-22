package Service;

import DAO.Thingy2;
import Model.Account;

public class Connector2 {
    private Thingy2 accountDAO;
    
    public Connector2(){
        accountDAO = new Thingy2();
    }

    public Connector2(Thingy2 t){
        this.accountDAO = t;
    }

    public Account insertUser(Account acc){
        return accountDAO.insertUser(acc);
    }

    public Account validateLogin(Account acc){
        return accountDAO.validateLogin(acc);
    }
}
