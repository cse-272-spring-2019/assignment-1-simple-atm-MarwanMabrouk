import com.sun.org.apache.bcel.internal.generic.NEW;
import javafx.animation.PathTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import sun.rmi.runtime.Log;

import javafx.scene.control.Alert;

public class ATM extends Application {
    Bank myBank = new Bank();
    Stage Screen = new Stage();
    @Override
    public void start(Stage stage) {

        Screen.setScene(Login());
        Screen.show();
    }
    public Scene Login(){
        Button Enter = new Button("Enter");
        Button Create = new Button("Create");

        TextField Card = new TextField();
        TextField Pin = new TextField();

        Text CardL = new Text("Card Number");
        Text PinL = new Text("Pin Number");

        GridPane LoginGrid = new GridPane();

        LoginGrid.add(CardL,0,0);
        LoginGrid.add(PinL,0,1);


        LoginGrid.add(Card,1,0);
        LoginGrid.add(Pin,1,1);

        LoginGrid.add(Enter,2,1);
        LoginGrid.add(Create,2,0);
        LoginGrid.setHgap(10);
        LoginGrid.setVgap(10);
        LoginGrid.setPadding(new Insets(10, 10, 10, 10));

        Scene LoginWindow = new Scene(LoginGrid,320,100);

        Enter.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if(myBank.Check(Card.getText(),Pin.getText())){
                    Screen.setScene(Menu());
                }else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Wrong Card Number or Pin Code" , ButtonType.OK);
                    alert.showAndWait();

                }

            }
        }));
        Create.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                myBank.CreateAccount();
            }
        }));

        return LoginWindow;
    }
    public Scene Menu(){
        Button Withdraw = new Button("Withdraw");

        Button Deposit = new Button("Deposit");
        Button Balance = new Button("Balance Inquiry");
        Button Next = new Button("Next");
        Button Previous = new Button("Previous");

        TextField Amount = new TextField();
        Amount.setVisible(false);


        Button Confirm = new Button("Confirm");
        Confirm.setVisible(false);
        Text History = new Text("Transaction History");
        Text Type = new Text("Type");
        Text TypeList = new Text("---");
        Text AmountL = new Text("Amount");
        Text AmountList = new Text("---");

        Text IDL = new Text("ID");
        Text ID = new Text("---");


        Text Status = new Text("---");
        Text Message = new Text("Welcome ");

        GridPane Menu = new GridPane();

        Menu.add(Withdraw,0,0);
        Menu.add(Deposit,0,1);
        Menu.add(Balance,0,2);

        Menu.add(Message,2,0);
        Menu.add(Amount,2,1);
        Menu.add(Confirm,2,2);

        Menu.add(History,1,5);

        Menu.add(IDL,0,6);
        Menu.add(ID,0,7);

        Menu.add(Type,1,6);
        Menu.add(AmountL,2,6);
        Menu.add(TypeList,1,7);
        Menu.add(AmountList,2,7);
        Menu.add(Previous,0,8);
        Menu.add(Next,2,8);


        Menu.setHgap(10);
        Menu.setVgap(10);
        Menu.setPadding(new Insets(10, 10, 10, 10));

        Scene MenuPage = new Scene(Menu,400,320);

        Amount.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.equals("")){
                return;
            }
            try {
                int n = Integer.parseInt(newText);
            }catch (Exception e){
                Amount.setText(oldText);
            }
        });
        Withdraw.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Message.setText("Withdraw Amount");
                Amount.setVisible(true);
                Confirm.setVisible(true);
                Status.setText("Withdraw");
            }
        }));
        Deposit.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Message.setText("Deposit Amount");
                Amount.setVisible(true);
                Confirm.setVisible(true);
                Status.setText("Deposit");
            }
        }));
        Balance.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Message.setText("Current Balance : "+  myBank.getCurrentAccount().Balance +"$");
                myBank.getCurrentAccount().History.add(new Transaction("Balance Inq.",0));

            }
        }));

        Next.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (myBank.getCurrentAccount().History.size()>0){

                    if (myBank.CurrentTransaction+1 < myBank.getCurrentAccount().History.size()){
                        myBank.CurrentTransaction++;
                    }
                    Transaction Temp  = myBank.getCurrentAccount().History.get(myBank.CurrentTransaction);
                    TypeList.setText(Temp.Type);
                    AmountList.setText(Integer.toString(Temp.Amount));
                    ID.setText(Integer.toString(myBank.CurrentTransaction));
                }

            }
        }));
        Previous.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (myBank.getCurrentAccount().History.size()>0){
                    if (myBank.CurrentTransaction > 0) {
                        myBank.CurrentTransaction--;
                    }
                    Transaction Temp  = myBank.getCurrentAccount().History.get(myBank.CurrentTransaction);

                    TypeList.setText(Temp.Type);
                    AmountList.setText(Integer.toString(Temp.Amount));
                    ID.setText(Integer.toString(myBank.CurrentTransaction));
                }
            }

        }));

        Confirm.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(Amount.getText().equals("")){
                    return;
                }
                Message.setText("");
                int AmountC = Integer.parseInt(Amount.getText().trim());
                Amount.setVisible(false);
                Amount.setText("");
                Confirm.setVisible(false);
                switch (Status.getText()){
                    case "Withdraw":

                        if(myBank.getCurrentAccount().Withdraw(AmountC)){
                            Message.setText("Please collect your funds");
                            myBank.getCurrentAccount().History.add(new Transaction("Withdrawal",AmountC));

                        }else {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Insufficient Funds" , ButtonType.OK);
                            alert.showAndWait();
                        }
                        break;
                    case "Deposit":
                        myBank.getCurrentAccount().Deposit(AmountC);
                        myBank.getCurrentAccount().History.add(new Transaction("Deposit",AmountC));
                        break;

                }


            }
        }));




        return MenuPage;
    }
    public static void main(String args[]){
        launch(args);
    }
}