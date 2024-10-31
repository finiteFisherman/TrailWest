package game;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GenStore extends Application {

    private final int screenWidth = 1280;
    private final int screenHeight = 720;
    private ImageView imageView;
    private Group root;
    Scene s4;
    private int tempMeat,tempFruit,tempVeg,tempTool,tempClothes;

    public void start(Stage stage, Player player, Passenger pass1, Passenger pass2, Passenger pass3) throws FileNotFoundException {

        //sets up initial starting values of weight and cash amount
        Inventory playerStuff = new Inventory(0,0,0,0,0,1000.00);
        Rectangle rect = new Rectangle(1000,400,Color.WHEAT);
        FileInputStream input = new FileInputStream("raw/goods.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setX(0);
        imageView.setY(0);

        Text mText= new Text("Meat");
        mText.setFill(Color.BLACK);
        mText.setFont(Font.font ("Comic Sans MS", 25));
        Text mPriceLabel = new Text("$/lb");
        mPriceLabel.setFont(Font.font ("Comic Sans MS", 25));
        mPriceLabel.setFill(Color.BLACK);
        Text mPrice = new Text("2.50");
        mPrice.setFill(Color.BLACK);
        mPrice.setFont(Font.font ("Comic Sans MS", 25));
        TextField mTF = new TextField("0");
        Button mButton = new Button("Buy & add lbs of item(s) to wagon");
        Text mUpdate1 = new Text("Lbs. in your wagon:");
        mUpdate1.setFill(Color.BLACK);
        mUpdate1.setFont(Font.font ("Comic Sans MS", 25));
        String meaty = Integer.toString(playerStuff.getMeat());
        Text mUpdate2 = new Text(meaty);
        mUpdate2.setFill(Color.BLACK);
        mUpdate2.setFont(Font.font ("Comic Sans MS", 25));

        Text fText= new Text("Fruit");
        fText.setFill(Color.BLACK);
        fText.setFont(Font.font ("Comic Sans MS", 25));
        Text fPriceLabel = new Text("$/lb");
        fPriceLabel.setFill(Color.BLACK);
        fPriceLabel.setFont(Font.font ("Comic Sans MS", 25));
        Text fPrice = new Text("0.50");
        fPrice.setFill(Color.BLACK);
        fPrice.setFont(Font.font ("Comic Sans MS", 25));
        TextField fTF = new TextField("0");
        Button fButton = new Button("Buy & add lbs of item(s) to wagon");
        Text fUpdate1 = new Text("Lbs. in your wagon:");
        fUpdate1.setFill(Color.BLACK);
        fUpdate1.setFont(Font.font ("Comic Sans MS", 25));
        String fruity = Integer.toString(playerStuff.getFruit());
        Text fUpdate2 = new Text(fruity);
        fUpdate2.setFill(Color.BLACK);
        fUpdate2.setFont(Font.font ("Comic Sans MS", 25));

        Text vText= new Text("Vegetables");
        vText.setFill(Color.BLACK);
        vText.setFont(Font.font ("Comic Sans MS", 25));
        Text vPriceLabel = new Text("$/lb");
        vPriceLabel.setFill(Color.BLACK);
        vPriceLabel.setFont(Font.font ("Comic Sans MS", 25));
        Text vPrice = new Text("0.25");
        vPrice.setFill(Color.BLACK);
        vPrice.setFont(Font.font ("Comic Sans MS", 25));
        TextField vTF = new TextField("0");
        Button vButton = new Button("Buy & add lbs of item(s) to wagon");
        Text vUpdate1 = new Text("Lbs. in your wagon:");
        vUpdate1.setFill(Color.BLACK);
        vUpdate1.setFont(Font.font ("Comic Sans MS", 25));
        String vegy = Integer.toString(playerStuff.getVeggies());
        Text vUpdate2 = new Text(vegy);
        vUpdate2.setFill(Color.BLACK);
        vUpdate2.setFont(Font.font ("Comic Sans MS", 25));

        Text tText= new Text("Tools");
        tText.setFill(Color.BLACK);
        tText.setFont(Font.font ("Comic Sans MS", 25));
        Text tPriceLabel = new Text("$/SET");
        tPriceLabel.setFill(Color.BLACK);
        tPriceLabel.setFont(Font.font ("Comic Sans MS", 25));
        Text tPrice = new Text("5.00");
        tPrice.setFill(Color.BLACK);
        tPrice.setFont(Font.font ("Comic Sans MS", 25));
        TextField tTF = new TextField("0");
        Button tButton = new Button("Buy & add lbs of item(s) to wagon");
        Text tUpdate1 = new Text("Sets in your wagon:");
        tUpdate1.setFill(Color.BLACK);
        tUpdate1.setFont(Font.font ("Comic Sans MS", 25));
        String tooly = Integer.toString(playerStuff.getTools());
        Text tUpdate2 = new Text(tooly);
        tUpdate2.setFill(Color.BLACK);
        tUpdate2.setFont(Font.font ("Comic Sans MS", 25));

        Text cText= new Text("Clothes");
        cText.setFill(Color.BLACK);
        cText.setFont(Font.font ("Comic Sans MS", 25));
        Text cPriceLabel = new Text("$/SET");
        cPriceLabel.setFill(Color.BLACK);
        cPriceLabel.setFont(Font.font ("Comic Sans MS", 25));
        Text cPrice = new Text("3.75");
        cPrice.setFill(Color.BLACK);
        cPrice.setFont(Font.font ("Comic Sans MS", 25));
        TextField cTF = new TextField("0");
        Button cButton = new Button("Buy & add lbs of item(s) to wagon");
        Text cUpdate1 = new Text("Sets in your wagon:");
        cUpdate1.setFill(Color.BLACK);
        cUpdate1.setFont(Font.font ("Comic Sans MS", 25));
        String clothy = Integer.toString(playerStuff.getClothes());
        Text cUpdate2 = new Text(clothy);
        cUpdate2.setFill(Color.BLACK);
        cUpdate2.setFont(Font.font ("Comic Sans MS", 25));

        Text cashText= new Text("Current available funds:");
        cashText.setFill(Color.BLACK);
        cashText.setFont(Font.font ("Comic Sans MS", 25));
        Text cashDenom = new Text("$");
        cashDenom.setFill(Color.BLACK);
        cashDenom.setFont(Font.font ("Comic Sans MS", 25));
        String cashy = Double.toString(playerStuff.getCash());
        TextField cashTF = new TextField(cashy);
        Button leave = new Button("Leave the store");

        mButton.setOnAction(actionEvent -> {
            tempMeat = Integer.parseInt(mTF.getText());    //convert string to int
            playerStuff.setMeat(playerStuff.getMeat()+tempMeat);    //sets new values of items in player stuff
            double mCost = Double.parseDouble(mPrice.getText());    //gets cost from text as double
            double priceDeduct = mCost*tempMeat;                     //cost * quantity
            playerStuff.setCash(playerStuff.getCash()-priceDeduct);  //sets new cash available
            String cash = Double.toString(playerStuff.getCash());
            cashTF.setText(cash);                                   //sets cash text field again
            mUpdate2.setText(Integer.toString(playerStuff.getMeat()));
        });
        fButton.setOnAction(actionEvent -> {
            tempFruit = Integer.parseInt(fTF.getText());
            playerStuff.setFruit(playerStuff.getFruit()+tempFruit);
            double fCost = Double.parseDouble(fPrice.getText());    //gets cost from text as double
            double priceDeduct = fCost*tempFruit;                     //cost * quantity
            playerStuff.setCash(playerStuff.getCash()-priceDeduct);  //sets new cash available
            String cash = Double.toString(playerStuff.getCash());
            cashTF.setText(cash);
            fUpdate2.setText(Integer.toString(playerStuff.getFruit()));
        });
        vButton.setOnAction(actionEvent -> {
            tempVeg = Integer.parseInt(vTF.getText());
            playerStuff.setVeggies(playerStuff.getVeggies()+tempVeg);
            double vCost = Double.parseDouble(vPrice.getText());    //gets cost from text as double
            double priceDeduct = vCost*tempVeg;                     //cost * quantity
            playerStuff.setCash(playerStuff.getCash()-priceDeduct);  //sets new cash available
            String cash = Double.toString(playerStuff.getCash());
            cashTF.setText(cash);
            vUpdate2.setText(Integer.toString(playerStuff.getVeggies()));
        });
        tButton.setOnAction(actionEvent -> {
            tempTool = Integer.parseInt(tTF.getText());
            playerStuff.setTools(playerStuff.getTools()+tempTool);
            double tCost = Double.parseDouble(tPrice.getText());    //gets cost from text as double
            double priceDeduct = tCost*tempTool;                     //cost * quantity
            playerStuff.setCash(playerStuff.getCash()-priceDeduct);  //sets new cash available
            String cash = Double.toString(playerStuff.getCash());
            cashTF.setText(cash);
            tUpdate2.setText(Integer.toString(playerStuff.getTools()));
        });
        cButton.setOnAction(actionEvent -> {
            tempClothes = Integer.parseInt(cTF.getText());
            playerStuff.setClothes(playerStuff.getClothes()+tempClothes);
            double cCost = Double.parseDouble(cPrice.getText());    //gets cost from text as double
            double priceDeduct = cCost*tempClothes;                     //cost * quantity
            playerStuff.setCash(playerStuff.getCash()-priceDeduct);  //sets new cash available
            String cash = Double.toString(playerStuff.getCash());
            cashTF.setText(cash);
            cUpdate2.setText(Integer.toString(playerStuff.getClothes()));
        });

        HBox meats = new HBox();
        meats.setSpacing(10);
        meats.getChildren().addAll(mText,mPrice,mPriceLabel,mTF,mButton,mUpdate1,mUpdate2);

        HBox fruits = new HBox();
        fruits.setSpacing(10);
        fruits.getChildren().addAll(fText,fPrice,fPriceLabel,fTF,fButton,fUpdate1,fUpdate2);

        HBox veggie = new HBox();
        veggie.setSpacing(10);
        veggie.getChildren().addAll(vText,vPrice,vPriceLabel,vTF,vButton,vUpdate1,vUpdate2);

        HBox tools = new HBox();
        tools.setSpacing(10);
        tools.getChildren().addAll(tText,tPrice,tPriceLabel,tTF,tButton,tUpdate1,tUpdate2);

        HBox clothes = new HBox();
        clothes.setSpacing(10);
        clothes.getChildren().addAll(cText,cPrice,cPriceLabel,cTF,cButton,cUpdate1,cUpdate2);

        HBox cash = new HBox();
        cash.setSpacing(10);
        cash.getChildren().addAll(cashText,cashDenom,cashTF);

        VBox inv = new VBox();
        inv.setSpacing(10);

        inv.getChildren().addAll(meats,fruits,veggie,tools,clothes,cash,leave);
        inv.setLayoutY((screenHeight/2-300));
        inv.setLayoutX((30));

        //handler to scene 5, BeginTown2
        leave.setOnAction(e -> {
            BeginTownTwo gamer = new BeginTownTwo();
            try {
                gamer.start(stage,player,pass1,pass2,pass3,playerStuff);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        root=new Group(imageView,rect,inv);
        s4= new Scene(root, screenWidth, screenHeight, Color.WHEAT);
        stage.setScene(s4);
        stage.show();
    }

    @Override
    public void start(Stage stage) throws Exception {
    }
}


