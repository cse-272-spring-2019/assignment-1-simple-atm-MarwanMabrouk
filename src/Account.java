import java.util.ArrayList;

public class Account {
    String Card = "";
    String Pin = "";
    int Balance = 0;
    ArrayList<Transaction> History = new ArrayList<Transaction>();
    public Account(){

        for(int i=0;i<1;i++){
            Card += Integer.toString(((int)(Math.random() * 9999 + 1000)));
        }
        System.out.println("Your Card Number is "+Card);

        Pin = Integer.toString(((int)(Math.random() * 9999 + 1000)));
        System.out.println("Your Pin Code is "+Pin);
    }
    public void Deposit(int Amount){
        Balance += Amount;

    }
    public boolean Withdraw(int Amount){
        if((Balance-Amount)<0){
            return false;
        }else {
            Balance -= Amount;
            return true;
        }
    }
}
