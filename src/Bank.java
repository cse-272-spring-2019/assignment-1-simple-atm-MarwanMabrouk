import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Bank {
        String BankName ="Bank Of SSP";
        ArrayList<Account> AccountList = new ArrayList<Account>();
        int CurrentCustomer = 0;
        int CurrentTransaction = 0;

        public Bank(){
            AccountList.add(new Account());
        }
        public boolean Check(String CardN,String PinN){
            int i=0;
            for (Account Acc:
                    AccountList) {
                if((Acc.Card.equals(CardN)) && (Acc.Pin.equals(PinN))){
                    CurrentCustomer = i;
                    return true;
                }
                i++;
            }
            return false;
        }

        public void CreateAccount(){
            AccountList.add(new Account());
        }
        public Account getCurrentAccount(){
            return AccountList.get(CurrentCustomer);
        }

}
