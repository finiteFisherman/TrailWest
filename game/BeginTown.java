package game;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BeginTown extends Application {

    private final int screenWidth = 1280;
    private final int screenHeight = 720;
    private ImageView imageView;
    private Group root;
    Scene s3;

    public void start(Stage stage, Player player, Passenger pass1, Passenger pass2, Passenger pass3) throws FileNotFoundException {

        FileInputStream input = new FileInputStream("raw/genStore.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setX(0);
        imageView.setY(0);

        Text t = new Text();
        t.setText("Before "+player.getName()+", "+pass1.getName()+", "+pass2.getName()+", and "+pass3.getName()+
                " begin their journey,\nthey need to visit the general store to buy some supplies. ");
        t.setFont(Font.font ("Comic Sans MS", 20));
        t.setFill(Color.WHITE);
        Button button1= new Button("Enter Store");
        button1.setAlignment(Pos.CENTER);

        FileInputStream input2 = new FileInputStream("raw/w1.png");
        Image image2 = new Image(input2);
        ImageView imageView2 = new ImageView(image2);
        Path p = new Path();
        MoveTo moveTo = new MoveTo();
        moveTo.setX(30);
        moveTo.setY(600);
        LineTo line1 = new LineTo();
        line1.setX(1400);
        line1.setY(600);


        p.getElements().addAll(moveTo,line1);
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(10000));
        pathTransition.setNode(imageView2);
        pathTransition.setPath(p);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(1);
        pathTransition.play();

        FileInputStream input3 = new FileInputStream("raw/playerR.png");
        Image image3 = new Image(input3);
        ImageView imageView3 = new ImageView(image3);

        Rectangle rect = new Rectangle(1000,400,Color.WHEAT);

        imageView3.setX(20);
        imageView3.setY(300);

        VBox layout1 = new VBox();
        layout1.setSpacing(10);
        layout1.setLayoutY((screenHeight/2-300));
        layout1.setLayoutX((screenWidth/2));
        layout1.getChildren().addAll(t,button1);

        //handler to enter store
        button1.setOnAction(e -> {
            GenStore game = new GenStore();
            try {
                game.start(stage,player,pass1,pass2,pass3);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        root = new Group(imageView,layout1,p,imageView2,imageView3);
        s3= new Scene(root, screenWidth, screenHeight, Color.WHEAT);

        stage.setScene(s3);
        stage.show();
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
