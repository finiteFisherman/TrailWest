package game;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Setup extends Application {

    private Group root;
    private ImageView imageView;
    private final int screenWidth = 1280;
    private final int screenHeight = 720;
    private String player,p1,p2,p3;

    public void start(Stage stageOne) throws FileNotFoundException {

        stageOne.setTitle("Game Setup");

        TextField tf = new TextField("Biff");
        TextField tf1 = new TextField("Mary");
        TextField tf2 = new TextField("Cal");
        TextField tf3 = new TextField("James");
        Button button1= new Button("Add Player & Passengers");
        button1.setMinWidth(250);
        Button button2= new Button("Travel the Trail");
        button1.setMinWidth(250);
        button2.setMinWidth(250);
        VBox layout1 = new VBox();
        layout1.setSpacing(10);
        layout1.setLayoutY(50);
        layout1.setLayoutX((screenWidth/3)+180);
        layout1.getChildren().addAll(tf,tf1,tf2,tf3,button1,button2);

        Text l1=new Text("Player Name");
        l1.setFont(Font.font ("Comic Sans MS", 14));
        l1.setFill(Color.BLACK);
        Text l2=new Text("Passenger 1 Name");
        l2.setFont(Font.font ("Comic Sans MS", 14));
        l2.setFill(Color.BLACK);
        Text l3=new Text("Passenger 2 Name");
        l3.setFont(Font.font ("Comic Sans MS", 14));
        l3.setFill(Color.BLACK);
        Text l4=new Text("Passenger 3 Name");
        l4.setFont(Font.font ("Comic Sans MS", 14));
        l4.setFill(Color.BLACK);

        VBox layout2 = new VBox();
        layout2.setSpacing(20);
        layout2.setLayoutY(50);
        layout2.setLayoutX(screenWidth/3);
        layout2.getChildren().addAll(l1,l2,l3,l4);

        //FileInputStream input = new FileInputStream("raw/tMap.png");
        //Image image = new Image(input);
        //ImageView imageView = new ImageView(image);

        FileInputStream input1 = new FileInputStream("raw/trailMap3.png");
        Image image1 = new Image(input1);
        ImageView imageView1 = new ImageView(image1);
        imageView1.setY(0);
        imageView1.setX(0);

        //VBox layout3 = new VBox();
       // layout3.setSpacing(20);
        //layout3.setLayoutY(screenHeight/2-80);
       // layout3.setLayoutX(screenWidth/2-250);
       // layout3.getChildren().addAll(imageView1);

       //instantiate new players on button click
        button1.setOnAction(e-> {
            Player player1 = new Player(100, tf.getText());
            Passenger pass1 = new Passenger(100,tf1.getText());
            Passenger pass2 = new Passenger(100,tf2.getText());
            Passenger pass3 = new Passenger(100,tf3.getText());
            //next scene
            button2.setOnAction(f -> {
                    BeginTown town = new BeginTown();
                try {
                    town.start(stageOne, player1, pass1, pass2, pass3);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            });
        });

        root = new Group(imageView1,layout1,layout2);
        Scene s2 = new Scene(root, screenWidth, screenHeight, Color.WHEAT);
        stageOne.setScene(s2);
        stageOne.show();
    }
}
