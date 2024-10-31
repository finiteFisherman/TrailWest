package game;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BeginTownTwo extends Application {
    private final int screenWidth = 1280;
    private final int screenHeight = 720;
    private Group root;
    Scene s5;

    private static final int COLUMNS  =   2;
    private static final int COUNT    =  2;
    private static final int OFFSET_X =  0;
    private static final int OFFSET_Y =  0;
    private static final int WIDTH    = 500;
    private static final int HEIGHT   = 320;

    public void start(Stage stage, Player player, Passenger pass1, Passenger pass2, Passenger pass3, Inventory playerStuff) throws FileNotFoundException {

        FileInputStream input = new FileInputStream("raw/leaveTown.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setX(0);
        imageView.setY(0);

        Button butt1 = new Button("Leave Independence, Mo.");
        butt1.setLayoutY(300);
        butt1.setLayoutX(screenWidth/2-100);
        butt1.setMinWidth(200);

        String path1 = "raw/Wagon.mp3";
        Media media = new Media(new File(path1).toURI().toString());
        MediaPlayer wagonFx = new MediaPlayer(media);
        wagonFx.setAutoPlay(true);
        wagonFx.setStopTime(Duration.seconds(10.0));
        MediaView mediaView =  new MediaView(wagonFx);

        butt1.setOnAction(e -> {
            GameLooper game = new GameLooper();
            try {
                game.start(stage,player,pass1,pass2,pass3,playerStuff);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        FileInputStream input2 = new FileInputStream("raw/Image18.png");             //cows sprite
        Image image2 = new Image(input2);
        ImageView imageView2 = new ImageView(image2);

        FileInputStream input3 = new FileInputStream("raw/Image19.png");           //wagon
        Image image3 = new Image(input3);
        ImageView imageView3 = new ImageView(image3);

        Rectangle rect = new Rectangle(570,90,Color.WHEAT);

        Text t = new Text();
        t.setText("    In the year eighteen hundred and fifty, these left their\n    home in the East and began down the trail to the West." +
                "\n    This is their story. ");
        t.setFont(Font.font ("Comic Sans MS", 20));
        t.setFill(Color.BLACK);

        //Text pStatus = new Text();
        //pStatus.setText("Health: "+player.getName()+" "+player.getHealth()+"/100   "+pass1.getName()+" "+pass1.getHealth()+"/100    "
        //+pass2.getName()+" "+pass2.getHealth()+"/100    "+pass3.getName()+" "+pass3.getHealth()+"/100    ");
        //pStatus.setFill(Color.BLACK);
        //pStatus.setFont(Font.font ("Comic Sans MS", 20));

        //Text wStatus = new Text();
       // wStatus.setText(playerStuff.toString());
        //wStatus.setFill(Color.BLACK);
        //wStatus.setFont(Font.font ("Comic Sans MS", 20));

        VBox layout1 = new VBox();
        layout1.getChildren().addAll(t);

        imageView2.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));
        Path road = new Path();
        //road.setStrokeWidth(30);
        MoveTo moveTo = new MoveTo();
        moveTo.setX(750);
        moveTo.setY(600);
        LineTo line1 = new LineTo();
        line1.setX(0);
        line1.setY(600);

        road.getElements().addAll(moveTo,line1);

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(10000));
        pathTransition.setNode(imageView2);
        pathTransition.setPath(road);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(1);
        pathTransition.play();

        final Animation ani1 = new SpriteAnimation(
                imageView2,
                Duration.millis(1000),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );
        ani1.setCycleCount(10);
        ani1.play();

        Path p = new Path();
        MoveTo moveTo2 = new MoveTo();
        moveTo2.setX(1280);
        moveTo2.setY(500);
        LineTo line2 = new LineTo();
        line2.setX(500);
        line2.setY(500);
        p.getElements().addAll(moveTo2,line2);
        PathTransition pathTransition2 = new PathTransition();
        pathTransition2.setDuration(Duration.millis(10000));
        pathTransition2.setNode(imageView3);
        pathTransition2.setPath(p);
        pathTransition2.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition2.setCycleCount(1);
        pathTransition2.play();

        root=new Group(imageView,rect,layout1,road,imageView2,p,imageView3,butt1,mediaView);
        s5= new Scene(root, screenWidth, screenHeight, Color.WHEAT);
        stage.setScene(s5);
        stage.show();

    }
    @Override
    public void start(Stage stage) throws Exception {

    }
}
