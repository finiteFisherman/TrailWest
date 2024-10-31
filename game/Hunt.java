package game;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Hunt extends Application {

    public final static int JUMP = 5;
    private ImageView imageView;
    private Line currentLine;
    private Group root;
    private final int screenWidth = 1280;
    private final int screenHeight = 720;
    Scene sH;

    public void start(Stage stage) throws IOException {

        String path1 = "raw/gun.wav";
        Media media = new Media(new File(path1).toURI().toString());
        MediaPlayer gunFx = new MediaPlayer(media);
        gunFx.setAutoPlay(false);
        //gunFx.setStopTime(Duration.seconds(2.0));
        //gunFx.setCycleCount(MediaPlayer.INDEFINITE);
        MediaView mediaView =  new MediaView(gunFx);

        FileInputStream input1 = new FileInputStream("raw/hunt1.png");  //backdrop
        Image image1 = new Image(input1);
        ImageView imageView = new ImageView(image1);
        imageView.setX(0);
        imageView.setY(0);

        FileInputStream input2 = new FileInputStream("raw/bisonResizeFrame.png");  //animal
        Image image2 = new Image(input2);
        ImageView imageView2 = new ImageView(image2);

        FileInputStream input3 = new FileInputStream("raw/gunOut4.png");   //gun not fire
        Image image3 = new Image(input3);
        ImageView imageView3 = new ImageView(image3);
        imageView3.setX(640);
        imageView3.setY(515);

        root = new Group(imageView,imageView2,imageView3,mediaView);
        sH = new Scene(root, screenWidth,screenHeight);

        sH.setOnKeyPressed(e -> {                                  //gun move controls
            //if (e.getCode() == KeyCode.UP) {
               // imageView3.setY(imageView3.getY() - JUMP);
            //}
           // if (e.getCode() == KeyCode.DOWN) {
            //    imageView3.setY(imageView3.getY() + JUMP);
           // }
            if (e.getCode() == KeyCode.RIGHT) {
                imageView3.setX(imageView3.getX() + JUMP);
            }
            if (e.getCode() == KeyCode.LEFT) {
                imageView3.setX(imageView3.getX() - JUMP);
            }
            if(e.getCode() == KeyCode.ENTER){
                MediaPlayer.Status status = gunFx.getStatus();
                if(status == MediaPlayer.Status.PLAYING){
                    gunFx.stop();
                }
                else
                    gunFx.play();
            }
        });

        stage.setScene(sH);
        stage.show();

    }

}
