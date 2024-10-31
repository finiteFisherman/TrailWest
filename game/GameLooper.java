package game;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;


public class GameLooper extends Application {
    private final int screenWidth = 1280;
    private final int screenHeight = 720;
    private Group root;
    private int milesTraveled = 0;
    private int milesTraveledToday=0;
    Scene s6;
    Button b4;

    public void start(Stage stage, Player player, Passenger pass1, Passenger pass2, Passenger pass3, Inventory playerStuff) throws FileNotFoundException{

        Date date = new Date(5,1,1850);    //date class
        Weather temp = new Weather(75);                //weather class

        Rectangle rect = new Rectangle(1280,100,Color.BURLYWOOD);

        FileInputStream input = new FileInputStream("raw/trailMap2.png");           //main trail map
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setY(0);
        imageView.setX(0);

        Text pStatus = new Text();
        pStatus.setText("Health: "+player.getName()+" "+player.getHealth()+"/100   "+pass1.getName()+" "+pass1.getHealth()+"/100    "
                +pass2.getName()+" "+pass2.getHealth()+"/100    "+pass3.getName()+" "+pass3.getHealth()+"/100    ");
        pStatus.setFill(Color.BLACK);
        pStatus.setFont(Font.font ("Verdana", 14));

        Text wStatus = new Text();
        wStatus.setText(playerStuff.toString());
        wStatus.setFill(Color.BLACK);
        wStatus.setFont(Font.font ("Verdana", 14));

        Text dText = new Text();   //date
        dText.setText(date.toString());
        dText.setFill(Color.BLACK);
        dText.setFont(Font.font ("Verdana", 16));

        Text wText = new Text();   //weather
        wText.setFill(Color.BLACK);
        wText.setFont(Font.font ("Verdana", 16));

        Text location = new Text(); //location
        location.setFill(Color.BLACK);
        location.setFont(Font.font ("Verdana", 16));

        Text traveled = new Text(); //miles Traveled text
        traveled.setFill(Color.BLACK);
        traveled.setFont(Font.font ("Verdana", 16));

        Text commentsText = new Text();    //comments based on health
        commentsText.setFill(Color.BLACK);
        commentsText.setFont(Font.font ("Verdana", 16));

        HBox layout2 = new HBox();
        layout2.setSpacing(20);

        HBox layout3 = new HBox();
        layout3.setSpacing(20);

        Button guide = new Button("Trail Guide");                //guide book buttn
        guide.setMinWidth(100);
        guide.setOnAction(actionEvent -> {

            ImageView guideView;
            Group root;
            final int screenWidth = 1280;
            final int screenHeight = 720;
            Scene guideScene;
            FileInputStream input1 = null;          //basket+random plant
            try {
                input1 = new FileInputStream("raw/guideBook.png");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Image image1 = new Image(input1);
            guideView = new ImageView(image1);
            guideView.setX(0);
            guideView.setY(0);

            root = new Group(guideView);
            guideScene = new Scene(root, screenWidth, screenHeight);

            // New Stage
            Stage newWindow = new Stage();
            newWindow.setTitle("Trail Guide Book");
            newWindow.setScene(guideScene);
            // Specifies the modality for new window.
            newWindow.initModality(Modality.WINDOW_MODAL);
            // Specifies the owner Window (parent) for new window
            newWindow.initOwner(stage);
            // Set position of second window, related to primary window.
            newWindow.setX(stage.getX() + 200);
            newWindow.setY(stage.getY() + 100);
            newWindow.show();

        });

        Button restButt = new Button("Click to Rest A Day");
        restButt.setMinWidth(100);
        restButt.setOnAction(actionEvent -> {
            //date check and advance and reset
            if(date.getDay()<=30) {
                date.setDay(date.getDay() + 1);
                dText.setText(date.toString());
            }
            if(date.getDay()>30){
                date.setDay(1);    //reset first day on month
                date.setMonth(date.getMonth()+1);
                dText.setText(date.toString());
            }
            if(date.getMonth()>12){
                date.setMonth(1);    //reset month to new Year
                date.setDay(1);
                date.setYear(date.getYear()+1);
            }
            //resting a day boosts health, reduces food per person
            if(player.getHealth()<100){
                player.setHealth(player.getHealth()+10);
            }
            if(pass1.getHealth()<100){
                pass1.setHealth(pass1.getHealth()+10);
            }
            if(pass2.getHealth()<100){
                pass2.setHealth(pass2.getHealth()+10);
            }
            if(pass3.getHealth()<100){
                pass3.setHealth(pass3.getHealth()+10);
            }
            pStatus.setText("Health: "+player.getName()+" "+player.getHealth()+"/100   "+pass1.getName()+" "+pass1.getHealth()+"/100    "
                    +pass2.getName()+" "+pass2.getHealth()+"/100    "+pass3.getName()+" "+pass3.getHealth()+"/100    ");
            //food reduce
            if(playerStuff.getFruit()>=2){
                playerStuff.setFruit(playerStuff.getFruit()-2);
            }
            if(playerStuff.getMeat()>=1){
                playerStuff.setMeat(playerStuff.getMeat()-1);
            }
            if(playerStuff.getVeggies()>=2){
                playerStuff.setVeggies(playerStuff.getVeggies()-2);
            }
            wStatus.setText(playerStuff.toString());

            //dialog randomizes passanger name, function call to comments
            int num;
            String passName[] = {pass1.getName(),pass2.getName(),pass3.getName() };
            Random rand = new Random();
            num = rand.nextInt((passName.length));
            String randPass = passName[num];

            double avgHealth = (player.getHealth()+pass1.getHealth()+pass2.getHealth()+pass3.getHealth())/4;

            if(avgHealth>=70){
                commentsText.setText(randPass+" says: "+ RandomHappyComments());
            }
            if(avgHealth<70){
                commentsText.setText(randPass+ " says: "+ RandomNotHappyComments());
            }
        });

        Button b4 = new Button();
        b4.setText("Go Fishing");                 //fishing button
        b4.setDisable(true);    //default state
        b4.setMinWidth(100);
        b4.setOnAction(actionEvent -> {

            Group root;
            final int screenWidth = 1020;
            final int screenHeight = 712;
            Scene fishScene;
            ImageView imageViewFish;
            ImageView fishCatch;

            FileInputStream fishInput = null;  //backdrop
            try {
                fishInput = new FileInputStream("raw/fishAll.png");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Image imageF = new Image(fishInput);
            imageViewFish = new ImageView(imageF);
            imageViewFish.setViewport(new Rectangle2D(0,0,1020,712));

            FileInputStream inputF = null;  //fish catch
            try {
                inputF = new FileInputStream("raw/catchAll.png");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Image fish = new Image(inputF);
            fishCatch = new ImageView(fish);
            fishCatch.setX(10);
            fishCatch.setY(50);
            fishCatch.setViewport(new Rectangle2D(0,0,200,112));

            Button fishCast = new Button("Try a different spot");
            fishCast.setOnAction(actionEvent1 -> {
                int minX = RandomFishViewPortX();            //function calls to random min x and y on image
                int minY = RandomFishViewPortY();
                imageViewFish.setViewport(new Rectangle2D(minX,minY,1020,712));

                int fishX = RandomFishViewPortX();             //rand loc of fish horizontally
                int fishY = RandomFishViewPortY();             //rand loc of fish vert

                if(minX==fishX && minY==fishY){        //if catch
                    int fishLBS = FishLbs();                         //random funct call fish lbs
                    playerStuff.setMeat(playerStuff.getMeat()+fishLBS);      //update meat larder
                    wStatus.setText(playerStuff.toString());    //update player stuff inventory text
                    int pic = RandomFishCatchViewPortX();        //random x fish catch location
                    fishCatch.setViewport(new Rectangle2D(pic,0,200,112));
                    String p1 = "raw/Happy Ding.wav";
                    Media mediaF = new Media(new File(p1).toURI().toString());
                    MediaPlayer catchFx = new MediaPlayer(mediaF);
                    catchFx.setAutoPlay(true);
                }
            });

            HBox fLayout = new HBox();
            fLayout.getChildren().addAll(fishCast);
            fLayout.setSpacing(2);
            root = new Group(imageViewFish,fLayout,fishCatch);
            fishScene = new Scene(root, screenWidth, screenHeight);

            // New Stage
            Stage newWindow = new Stage();
            newWindow.setTitle("Gone Fishin");
            newWindow.setScene(fishScene);
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.initOwner(stage);
            newWindow.setX(stage.getX() + 200);
            newWindow.setY(stage.getY() + 100);
            newWindow.show();

        });

        Button butt = new Button("Click to Travel");
        butt.setMinWidth(100);
        FileInputStream input3 = new FileInputStream("raw/wagonSmall.png");           //wagon
        Image image3 = new Image(input3);
        ImageView wagonView = new ImageView(image3);
        wagonView.setX(1225);             //image is 50x41 px
        wagonView.setY(460);

        butt.setOnAction(actionEvent -> {
            //check health
            if(player.getHealth()<=0){
                System.exit(0);       //end game
            }
            if(pass1.getHealth()<=0){
                pass1.setName("Deceased");
                pass1.setHealth(-1000);       //pass dies
            }
            if(pass2.getHealth()<=0){
                pass2.setName("Deceased");
                pass2.setHealth(-1000);      //pass dies
            }
            if(pass3.getHealth()<=0){
                pass3.setName("Deceased");
                pass3.setHealth(-1000);         //pass dies
            }



            if (milesTraveled>=0 && milesTraveled <=300) {
                location.setText("Location: Between Independence, Mo. & Fort Kearny");

                //funct call to weather
                temp.setTempF(Weather());
                String dailyT = Integer.toString(temp.getTempF());
                wText.setText("High Temperature: "+dailyT+"*F");

                //date check and advance and reset
                if(date.getDay()<=30) {
                    date.setDay(date.getDay() + 1);
                    dText.setText(date.toString());
                }
                if(date.getDay()>30){
                    date.setDay(1);    //reset first day on month
                    date.setMonth(date.getMonth()+1);
                    dText.setText(date.toString());
                }
                if(date.getMonth()>12){
                    date.setMonth(1);    //reset month to new Year
                    date.setDay(1);
                    date.setYear(date.getYear()+1);
                }

            }

            if (milesTraveled >= 301 && milesTraveled <= 400){
                location.setText("Location: Between Fort Kearney & South Platte River");

                //funct call to weather
                temp.setTempF(Weather()+2);    //2 degrees warmer than ind
                String dailyT = Integer.toString(temp.getTempF());
                wText.setText("High Temperature: "+dailyT+"*F");

                //date check and advance and reset
                if(date.getDay()<=30) {
                    date.setDay(date.getDay() + 1);
                    dText.setText(date.toString());
                }
                if(date.getDay()>30){
                    date.setDay(1);    //reset first day on month
                    date.setMonth(date.getMonth()+1);
                    dText.setText(date.toString());
                }
                if(date.getMonth()>12){
                    date.setMonth(1);    //reset month to new Year
                    date.setDay(1);
                    date.setYear(date.getYear()+1);
                }
                b4.setDisable(false);
                wagonView.setX(1080);             //image is 50x41 px
                wagonView.setY(449);

            }

            if(milesTraveled >= 401 && milesTraveled <= 475){
                location.setText("Location: Between South Platte River & Courthouse Rock");
                //funct call to weather
                temp.setTempF(Weather()+2);    //2 degrees warmer than ind
                String dailyT = Integer.toString(temp.getTempF());
                wText.setText("High Temperature: "+dailyT+"*F");

                //date check and advance and reset
                if(date.getDay()<=30) {
                    date.setDay(date.getDay() + 1);
                    dText.setText(date.toString());
                }
                if(date.getDay()>30){
                    date.setDay(1);    //reset first day on month
                    date.setMonth(date.getMonth()+1);
                    dText.setText(date.toString());
                }
                if(date.getMonth()>12){
                    date.setMonth(1);    //reset month to new Year
                    date.setDay(1);
                    date.setYear(date.getYear()+1);
                }
                b4.setDisable(false);
                wagonView.setX(1025);             //image is 50x41 px
                wagonView.setY(453);

            }
            if(milesTraveled >= 476 && milesTraveled <= 526){
                location.setText("Location: Between Courthouse Rock & Chimney Rock");
                //funct call to weather
                temp.setTempF(Weather()+2);    //2 degrees warmer than ind
                String dailyT = Integer.toString(temp.getTempF());
                wText.setText("High Temperature: "+dailyT+"*F");

                //date check and advance and reset
                if(date.getDay()<=30) {
                    date.setDay(date.getDay() + 1);
                    dText.setText(date.toString());
                }
                if(date.getDay()>30){
                    date.setDay(1);    //reset first day on month
                    date.setMonth(date.getMonth()+1);
                    dText.setText(date.toString());
                }
                if(date.getMonth()>12){
                    date.setMonth(1);    //reset month to new Year
                    date.setDay(1);
                    date.setYear(date.getYear()+1);
                }
                b4.setDisable(true);    //default state
                wagonView.setX(953);             //image is 50x41 px
                wagonView.setY(451);

            }
            if(milesTraveled >= 527 && milesTraveled <= 575){
                location.setText("Location: Between Chimney Rock & Scott's Bluff");
                //funct call to weather
                temp.setTempF(Weather()+4);    //4 degrees warmer than ind
                String dailyT = Integer.toString(temp.getTempF());
                wText.setText("High Temperature: "+dailyT+"*F");

                //date check and advance and reset
                if(date.getDay()<=30) {
                    date.setDay(date.getDay() + 1);
                    dText.setText(date.toString());
                }
                if(date.getDay()>30){
                    date.setDay(1);    //reset first day on month
                    date.setMonth(date.getMonth()+1);
                    dText.setText(date.toString());
                }
                if(date.getMonth()>12){
                    date.setMonth(1);    //reset month to new Year
                    date.setDay(1);
                    date.setYear(date.getYear()+1);
                }
                b4.setDisable(true);    //default state
                wagonView.setX(925);             //image is 50x41 px
                wagonView.setY(450);

            }
            if(milesTraveled >= 576 && milesTraveled <= 675){
                location.setText("Location: Between Scott's Bluff & Fort Laramie");
                //funct call to weather
                temp.setTempF(Weather()+4);    //4 degrees warmer than ind
                String dailyT = Integer.toString(temp.getTempF());
                wText.setText("High Temperature: "+dailyT+"*F");

                //date check and advance and reset
                if(date.getDay()<=30) {
                    date.setDay(date.getDay() + 1);
                    dText.setText(date.toString());
                }
                if(date.getDay()>30){
                    date.setDay(1);    //reset first day on month
                    date.setMonth(date.getMonth()+1);
                    dText.setText(date.toString());
                }
                if(date.getMonth()>12){
                    date.setMonth(1);    //reset month to new Year
                    date.setDay(1);
                    date.setYear(date.getYear()+1);
                }
                b4.setDisable(true);    //default state
                wagonView.setX(825);             //image is 50x41 px
                wagonView.setY(425);


            }
            if(milesTraveled >= 676 && milesTraveled <= 875){
                location.setText("Location: Between Fort Laramie & North Platte River");
                //funct call to weather
                temp.setTempF(Weather()+4);    //4 degrees warmer than ind
                String dailyT = Integer.toString(temp.getTempF());
                wText.setText("High Temperature: "+dailyT+"*F");

                //date check and advance and reset
                if(date.getDay()<=30) {
                    date.setDay(date.getDay() + 1);
                    dText.setText(date.toString());
                }
                if(date.getDay()>30){
                    date.setDay(1);    //reset first day on month
                    date.setMonth(date.getMonth()+1);
                    dText.setText(date.toString());
                }
                if(date.getMonth()>12){
                    date.setMonth(1);    //reset month to new Year
                    date.setDay(1);
                    date.setYear(date.getYear()+1);
                }
                b4.setDisable(false);
                wagonView.setX(725);             //image is 50x41 px
                wagonView.setY(400);

            }
            if(milesTraveled >= 876 && milesTraveled<= 1075){
                location.setText("Location: Between North Platte River & Independence Rock");
                //funct call to weather
                temp.setTempF(Weather()+2);    //2 degrees warmer than ind
                String dailyT = Integer.toString(temp.getTempF());
                wText.setText("High Temperature: "+dailyT+"*F");

                //date check and advance and reset
                if(date.getDay()<=30) {
                    date.setDay(date.getDay() + 1);
                    dText.setText(date.toString());
                }
                if(date.getDay()>30){
                    date.setDay(1);    //reset first day on month
                    date.setMonth(date.getMonth()+1);
                    dText.setText(date.toString());
                }
                if(date.getMonth()>12){
                    date.setMonth(1);    //reset month to new Year
                    date.setDay(1);
                    date.setYear(date.getYear()+1);
                }
                b4.setDisable(false);
                wagonView.setX(650);             //image is 50x41 px
                wagonView.setY(410);

            }
            if(milesTraveled >= 1076 && milesTraveled<= 1150){
                location.setText("Location: Between Independence Rock & Sweetwater Branch 1");
                //funct call to weather
                temp.setTempF(Weather()+2);    //2 degrees warmer than ind
                String dailyT = Integer.toString(temp.getTempF());
                wText.setText("High Temperature: "+dailyT+"*F");

                //date check and advance and reset
                if(date.getDay()<=30) {
                    date.setDay(date.getDay() + 1);
                    dText.setText(date.toString());
                }
                if(date.getDay()>30){
                    date.setDay(1);    //reset first day on month
                    date.setMonth(date.getMonth()+1);
                    dText.setText(date.toString());
                }
                if(date.getMonth()>12){
                    date.setMonth(1);    //reset month to new Year
                    date.setDay(1);
                    date.setYear(date.getYear()+1);
                }
                b4.setDisable(false);
                wagonView.setX(625);             //image is 50x41 px
                wagonView.setY(410);

            }
            if(milesTraveled >= 1151 && milesTraveled<= 1200){
                location.setText("Location: Between Sweetwater Branch 1 & Sweetwater Branch 2");
                //funct call to weather
                temp.setTempF(Weather()+2);    //2 degrees warmer than ind
                String dailyT = Integer.toString(temp.getTempF());
                wText.setText("High Temperature: "+dailyT+"*F");

                //date check and advance and reset
                if(date.getDay()<=30) {
                    date.setDay(date.getDay() + 1);
                    dText.setText(date.toString());
                }
                if(date.getDay()>30){
                    date.setDay(1);    //reset first day on month
                    date.setMonth(date.getMonth()+1);
                    dText.setText(date.toString());
                }
                if(date.getMonth()>12){
                    date.setMonth(1);    //reset month to new Year
                    date.setDay(1);
                    date.setYear(date.getYear()+1);
                }
                b4.setDisable(false);
                wagonView.setX(600);             //image is 50x41 px
                wagonView.setY(425);

            }
            if(milesTraveled >= 1201 && milesTraveled<= 1250){
                location.setText("Location: Between Sweetwater Branch 2 & Green River");
                //funct call to weather
                temp.setTempF(Weather());
                String dailyT = Integer.toString(temp.getTempF());
                wText.setText("High Temperature: "+dailyT+"*F");

                //date check and advance and reset
                if(date.getDay()<=30) {
                    date.setDay(date.getDay() + 1);
                    dText.setText(date.toString());
                }
                if(date.getDay()>30){
                    date.setDay(1);    //reset first day on month
                    date.setMonth(date.getMonth()+1);
                    dText.setText(date.toString());
                }
                if(date.getMonth()>12){
                    date.setMonth(1);    //reset month to new Year
                    date.setDay(1);
                    date.setYear(date.getYear()+1);
                }
                b4.setDisable(false);
                wagonView.setX(575);             //image is 50x41 px
                wagonView.setY(450);

            }
            if(milesTraveled >= 1251 && milesTraveled<=1350){
                location.setText("Location: Between Green River & Rocky Mountains");
                //funct call to weather
                temp.setTempF(Weather()-2);
                String dailyT = Integer.toString(temp.getTempF());
                wText.setText("High Temperature: "+dailyT+"*F");

                //date check and advance and reset
                if(date.getDay()<=30) {
                    date.setDay(date.getDay() + 1);
                    dText.setText(date.toString());
                }
                if(date.getDay()>30){
                    date.setDay(1);    //reset first day on month
                    date.setMonth(date.getMonth()+1);
                    dText.setText(date.toString());
                }
                if(date.getMonth()>12){
                    date.setMonth(1);    //reset month to new Year
                    date.setDay(1);
                    date.setYear(date.getYear()+1);
                }
                b4.setDisable(false);
                wagonView.setX(560);             //image is 50x41 px
                wagonView.setY(455);

            }
            if(milesTraveled >= 1351 && milesTraveled<=1375){
                location.setText("Location: Between Rocky Mountains & Fort Bridger");
                //funct call to weather
                temp.setTempF(Weather()-4);
                String dailyT = Integer.toString(temp.getTempF());
                wText.setText("High Temperature: "+dailyT+"*F");

                //date check and advance and reset
                if(date.getDay()<=30) {
                    date.setDay(date.getDay() + 1);
                    dText.setText(date.toString());
                }
                if(date.getDay()>30){
                    date.setDay(1);    //reset first day on month
                    date.setMonth(date.getMonth()+1);
                    dText.setText(date.toString());
                }
                if(date.getMonth()>12){
                    date.setMonth(1);    //reset month to new Year
                    date.setDay(1);
                    date.setYear(date.getYear()+1);
                }
                b4.setDisable(true);    //default state
                wagonView.setX(560);             //image is 50x41 px
                wagonView.setY(455);
            }
            if(milesTraveled >= 1376 && milesTraveled <=1526){
                location.setText("Location: Between Fort Bridger & Soda Springs");
                //funct call to weather
                temp.setTempF(Weather()-6);
                String dailyT = Integer.toString(temp.getTempF());
                wText.setText("High Temperature: "+dailyT+"*F");

                //date check and advance and reset
                if(date.getDay()<=30) {
                    date.setDay(date.getDay() + 1);
                    dText.setText(date.toString());
                }
                if(date.getDay()>30){
                    date.setDay(1);    //reset first day on month
                    date.setMonth(date.getMonth()+1);
                    dText.setText(date.toString());
                }
                if(date.getMonth()>12){
                    date.setMonth(1);    //reset month to new Year
                    date.setDay(1);
                    date.setYear(date.getYear()+1);
                }
                b4.setDisable(true);    //default state
                wagonView.setX(550);             //image is 50x41 px
                wagonView.setY(425);
            }
            if(milesTraveled >= 1527 && milesTraveled<= 1552){
                location.setText("Location: Between Soda Springs & Fort Hall");
                //funct call to weather
                temp.setTempF(Weather()-8);
                String dailyT = Integer.toString(temp.getTempF());
                wText.setText("High Temperature: "+dailyT+"*F");

                //date check and advance and reset
                if(date.getDay()<=30) {
                    date.setDay(date.getDay() + 1);
                    dText.setText(date.toString());
                }
                if(date.getDay()>30){
                    date.setDay(1);    //reset first day on month
                    date.setMonth(date.getMonth()+1);
                    dText.setText(date.toString());
                }
                if(date.getMonth()>12){
                    date.setMonth(1);    //reset month to new Year
                    date.setDay(1);
                    date.setYear(date.getYear()+1);
                }
                b4.setDisable(true);    //default state
                wagonView.setX(500);             //image is 50x41 px
                wagonView.setY(400);
            }
            if(milesTraveled >= 1553 && milesTraveled<= 1653){
                location.setText("Location: Between Fort Hall & Snake River");
                //funct call to weather
                temp.setTempF(Weather()-10);
                String dailyT = Integer.toString(temp.getTempF());
                wText.setText("High Temperature: "+dailyT+"*F");

                //date check and advance and reset
                if(date.getDay()<=30) {
                    date.setDay(date.getDay() + 1);
                    dText.setText(date.toString());
                }
                if(date.getDay()>30){
                    date.setDay(1);    //reset first day on month
                    date.setMonth(date.getMonth()+1);
                    dText.setText(date.toString());
                }
                if(date.getMonth()>12){
                    date.setMonth(1);    //reset month to new Year
                    date.setDay(1);
                    date.setYear(date.getYear()+1);
                }
                b4.setDisable(false);
                wagonView.setX(450);             //image is 50x41 px
                wagonView.setY(400);
            }
            if(milesTraveled >= 1654 && milesTraveled<= 1754){
                location.setText("Location: Between Snake River & Fort Boise");
                //funct call to weather
                temp.setTempF(Weather()-12);
                String dailyT = Integer.toString(temp.getTempF());
                wText.setText("High Temperature: "+dailyT+"*F");

                //date check and advance and reset
                if(date.getDay()<=30) {
                    date.setDay(date.getDay() + 1);
                    dText.setText(date.toString());
                }
                if(date.getDay()>30){
                    date.setDay(1);    //reset first day on month
                    date.setMonth(date.getMonth()+1);
                    dText.setText(date.toString());
                }
                if(date.getMonth()>12){
                    date.setMonth(1);    //reset month to new Year
                    date.setDay(1);
                    date.setYear(date.getYear()+1);
                }
                b4.setDisable(false);
                wagonView.setX(350);             //image is 50x41 px
                wagonView.setY(375);
            }
            if(milesTraveled >= 1755 && milesTraveled<=1780){
                location.setText("Location: Between Fort Boise & Snake River 2");
                //funct call to weather
                temp.setTempF(Weather()-14);
                String dailyT = Integer.toString(temp.getTempF());
                wText.setText("High Temperature: "+dailyT+"*F");

                //date check and advance and reset
                if(date.getDay()<=30) {
                    date.setDay(date.getDay() + 1);
                    dText.setText(date.toString());
                }
                if(date.getDay()>30){
                    date.setDay(1);    //reset first day on month
                    date.setMonth(date.getMonth()+1);
                    dText.setText(date.toString());
                }
                if(date.getMonth()>12){
                    date.setMonth(1);    //reset month to new Year
                    date.setDay(1);
                    date.setYear(date.getYear()+1);
                }
                b4.setDisable(false);
                wagonView.setX(325);             //image is 50x41 px
                wagonView.setY(350);
            }
            if(milesTraveled >= 1781 && milesTraveled<=2080){
                location.setText("Location: Between Snake River 2 & Blue Mountains");
                //funct call to weather
                temp.setTempF(Weather()-16);
                String dailyT = Integer.toString(temp.getTempF());
                wText.setText("High Temperature: "+dailyT+"*F");

                //date check and advance and reset
                if(date.getDay()<=30) {
                    date.setDay(date.getDay() + 1);
                    dText.setText(date.toString());
                }
                if(date.getDay()>30){
                    date.setDay(1);    //reset first day on month
                    date.setMonth(date.getMonth()+1);
                    dText.setText(date.toString());
                }
                if(date.getMonth()>12){
                    date.setMonth(1);    //reset month to new Year
                    date.setDay(1);
                    date.setYear(date.getYear()+1);
                }
                b4.setDisable(false);
                wagonView.setX(300);             //image is 50x41 px
                wagonView.setY(300);
            }
            if(milesTraveled >= 2081 && milesTraveled<= 2181){
                location.setText("Location: Between Blue Mountains & The Dalles");
                //funct call to weather
                temp.setTempF(Weather()-18);
                String dailyT = Integer.toString(temp.getTempF());
                wText.setText("High Temperature: "+dailyT+"*F");

                //date check and advance and reset
                if(date.getDay()<=30) {
                    date.setDay(date.getDay() + 1);
                    dText.setText(date.toString());
                }
                if(date.getDay()>30){
                    date.setDay(1);    //reset first day on month
                    date.setMonth(date.getMonth()+1);
                    dText.setText(date.toString());
                }
                if(date.getMonth()>12){
                    date.setMonth(1);    //reset month to new Year
                    date.setDay(1);
                    date.setYear(date.getYear()+1);
                }
                b4.setDisable(true);    //default state
                wagonView.setX(225);             //image is 50x41 px
                wagonView.setY(200);
            }
            if(milesTraveled >= 2182 && milesTraveled<=2280){
                location.setText("Location: Between The Dalles & Oregon City");
                //funct call to weather
                temp.setTempF(Weather()-20);
                String dailyT = Integer.toString(temp.getTempF());
                wText.setText("High Temperature: "+dailyT+"*F");

                //date check and advance and reset
                if(date.getDay()<=30) {
                    date.setDay(date.getDay() + 1);
                    dText.setText(date.toString());
                }
                if(date.getDay()>30){
                    date.setDay(1);    //reset first day on month
                    date.setMonth(date.getMonth()+1);
                    dText.setText(date.toString());
                }
                if(date.getMonth()>12){
                    date.setMonth(1);    //reset month to new Year
                    date.setDay(1);
                    date.setYear(date.getYear()+1);
                }
                b4.setDisable(false);
                wagonView.setX(200);             //image is 50x41 px
                wagonView.setY(225);
            }
            if(milesTraveled >= 2280){
                location.setText("Location: Congratulations, you've arrived in Oregon City");
                //funct call to weather
                temp.setTempF(Weather()-20);
                String dailyT = Integer.toString(temp.getTempF());
                wText.setText("High Temperature: "+dailyT+"*F");

                //date check and advance and reset
                if(date.getDay()<=30) {
                    date.setDay(date.getDay() + 1);
                    dText.setText(date.toString());
                }
                if(date.getDay()>30){
                    date.setDay(1);    //reset first day on month
                    date.setMonth(date.getMonth()+1);
                    dText.setText(date.toString());
                }
                if(date.getMonth()>12){
                    date.setMonth(1);    //reset month to new Year
                    date.setDay(1);
                    date.setYear(date.getYear()+1);
                }
                b4.setDisable(true);    //default state
                wagonView.setX(100);             //image is 50x41 px
                wagonView.setY(200);
                System.exit(0);
            }

            //equation for miles traveled based on person, passenger health and food tools, weather etc

            double avgHealth = (player.getHealth()+pass1.getHealth()+pass2.getHealth()+pass3.getHealth())/4;

            if(avgHealth >= 90.00){
                milesTraveledToday = 20;
            }
            if(avgHealth < 90.00 && avgHealth  >= 80.00){
                milesTraveledToday = 18;
            }
            if(avgHealth < 80.00 && avgHealth  >= 70.00){
                milesTraveledToday = 16;
            }
            if(avgHealth < 70.00 && avgHealth  >= 60.00){
                milesTraveledToday = 10;
            }
            if(avgHealth < 60.00 && avgHealth >= 50.00){
                milesTraveledToday = 8;
            }
            if(avgHealth < 50.00 && avgHealth  >= 40.00){
                milesTraveledToday = 6;
            }
            if(avgHealth < 40.00){
                milesTraveledToday = 0;
            }

            //dialog randomizes passanger name, function call to comments
            int num;
            String passName[] = {pass1.getName(),pass2.getName(),pass3.getName() };
            Random rand = new Random();
            num = rand.nextInt((passName.length));
            String randPass = passName[num];

            if(avgHealth>=70){
                commentsText.setText(randPass+" says: "+ RandomHappyComments());
            }
            if(avgHealth<70){
                commentsText.setText(randPass+ " says: "+ RandomNotHappyComments());
            }

            //reduce player health, food per day
            if(player.getHealth()>=2){
                player.setHealth(player.getHealth()-2);
            }
            if(pass1.getHealth()>=2){
                pass1.setHealth(pass1.getHealth()-2);
            }
            if(pass2.getHealth()>=2){
                pass2.setHealth(pass2.getHealth()-2);
            }
            if(pass3.getHealth()>=2){
                pass3.setHealth(pass3.getHealth()-2);
            }
            pStatus.setText("Health: "+player.getName()+" "+player.getHealth()+"/100   "+pass1.getName()+" "+pass1.getHealth()+"/100    "
                    +pass2.getName()+" "+pass2.getHealth()+"/100    "+pass3.getName()+" "+pass3.getHealth()+"/100    ");
            //food reduce
            if(playerStuff.getFruit()>=2){
                playerStuff.setFruit(playerStuff.getFruit()-2);
            }
            if(playerStuff.getMeat()>=1){
                playerStuff.setMeat(playerStuff.getMeat()-1);
            }
            if(playerStuff.getVeggies()>=2){
                playerStuff.setVeggies(playerStuff.getVeggies()-2);
            }
            wStatus.setText(playerStuff.toString());


            String miles = Integer.toString(milesTraveled);
            traveled.setText("Total Miles Traveled: " + miles);
            System.out.println(miles);
            milesTraveled = milesTraveled + milesTraveledToday;

        });

        //  Buttons

        Button b1 = new Button("Exit Game");     //exit no save
        b1.setMinWidth(100);
        b1.setOnAction(actionEvent -> {
            System.exit(0);
        });

        Button b2 = new Button("Save Game");     //save game
        b2.setMinWidth(100);
        b2.setOnAction(actionEvent -> {

        });

        Button b3 = new Button("Go Hunt");     //hunting
        b3.setMinWidth(100);
        b3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Rectangle rectHunt = new Rectangle(1280,80,Color.BURLYWOOD);

                Text huntText = new Text("Use Lt. & Rt. ARROW keys. Press ENTER to fire.\nHint: If you miss, return to camp and aim for center mass next time");   //hunt text
                huntText.setFill(Color.BLACK);
                huntText.setFont(Font.font ("Verdana", 16));

                HBox huntBox = new HBox();

                int JUMP = 5;
                ImageView imageView;
                Line currentLine;
                Group root;
                final int screenWidth = 1280;
                final int screenHeight = 720;
                Scene secondScene;

                String path1 = "raw/gun.wav";
                Media media = new Media(new File(path1).toURI().toString());
                MediaPlayer gunFx = new MediaPlayer(media);
                gunFx.setAutoPlay(false);
                MediaView mediaView = new MediaView(gunFx);

                FileInputStream input1 = null;  //backdrop
                String randLoc = RandomHuntLocations();        //function call to rand locations
                try {
                    input1 = new FileInputStream("raw/"+randLoc);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Image image1 = new Image(input1);
                imageView = new ImageView(image1);
                imageView.setX(0);
                imageView.setY(0);

                FileInputStream input2 = null;  //animal
                try {
                    input2 = new FileInputStream("raw/buff.png");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Image image2 = new Image(input2);
                ImageView imageView2 = new ImageView(image2);
                imageView2.setX(RandomAnimalLocation());             //random x location function call
                imageView2.setY(515);     //constant, matching gun's Y

                FileInputStream input3 = null;   //gun
                try {
                    input3 = new FileInputStream("raw/gunOut4.png");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Image image3 = new Image(input3);
                ImageView imageView3 = new ImageView(image3);
                imageView3.setX(640);
                imageView3.setY(515);

                huntBox.getChildren().addAll(huntText);
                root = new Group(imageView, imageView2, imageView3, mediaView,rectHunt,huntBox);
                secondScene = new Scene(root, screenWidth, screenHeight);

                secondScene.setOnKeyPressed(e -> {                                  //gun move controls
                    if (e.getCode() == KeyCode.RIGHT) {
                        imageView3.setX(imageView3.getX() + JUMP);
                    }
                    if (e.getCode() == KeyCode.LEFT) {
                        imageView3.setX(imageView3.getX() - JUMP);
                    }
                    if (e.getCode() == KeyCode.ENTER) {
                        if(imageView3.getX() == imageView2.getX()+10||
                                imageView3.getX() == imageView2.getX()+11 ||imageView3.getX() == imageView2.getX()+12 ||
                                imageView3.getX() == imageView2.getX()+13 || imageView3.getX() == imageView2.getX()+14 ||
                                imageView3.getX() == imageView2.getX()+15 || imageView3.getX() == imageView2.getX()+16 ||
                                imageView3.getX() == imageView2.getX()+17 || imageView3.getX() == imageView2.getX()+18 ||
                                imageView3.getX() == imageView2.getX()+19 || imageView3.getX() == imageView2.getX()+20||
                                imageView3.getX() == imageView2.getX()+21 || imageView3.getX() == imageView2.getX()+22||
                                imageView3.getX() == imageView2.getX()+23||imageView3.getX() == imageView2.getX()+24||
                                imageView3.getX() == imageView2.getX()+25) {                                             //if hit target range values
                            playerStuff.setMeat(playerStuff.getMeat() + RandomAnimalLbs());    //adding meat to wagon via random lbs function call
                            wStatus.setText(playerStuff.toString());         //update text to field for meat
                            imageView2.setX(5000.00);                         //remove animal from scene
                            huntText.setText("Good Shot! Return to camp");
                        }
                        else
                            imageView2.setX(5000.00);                         //remove animal from scene

                        MediaPlayer.Status status = gunFx.getStatus();
                        if (status == MediaPlayer.Status.PLAYING) {
                            gunFx.stop();
                        }
                        else
                            gunFx.play();
                    }
                });


                // New Stage
                Stage newWindow = new Stage();
                newWindow.setTitle("Hunting");
                newWindow.setScene(secondScene);
                // Specifies the modality for new window.
                newWindow.initModality(Modality.WINDOW_MODAL);
                // Specifies the owner Window (parent) for new window
                newWindow.initOwner(stage);
                // Set position of second window, related to primary window.
                newWindow.setX(stage.getX() + 200);
                newWindow.setY(stage.getY() + 100);
                newWindow.show();
            }
        });

        Button b5 = new Button("Gather Fruits/Vegetables");     //gather
        b5.setMinWidth(100);
        b5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Rectangle rectGather = new Rectangle(1280,80,Color.BURLYWOOD);
                //String path1 = "raw/sick2.mp3";
                String path1 = "raw/sickFX.mp3";
                Media media = new Media(new File(path1).toURI().toString());
                MediaPlayer sickFx = new MediaPlayer(media);
                sickFx.setAutoPlay(false);
                MediaView sickView = new MediaView(sickFx);         //sound effect for bad plant sick
                sickFx.setStartTime(Duration.seconds(260.00));
                sickFx.setStopTime(Duration.seconds(270.00));          //end at part of clip


                Text gatherText = new Text("Hint: Consult your guide book if you are not sure if a plant is edible ");   //hunt text
                gatherText.setFill(Color.BLACK);
                gatherText.setFont(Font.font ("Verdana", 16));

                VBox layout = new VBox();

                ImageView imageView;
                Group root;
                final int screenWidth = 1280;
                final int screenHeight = 720;
                Scene secondScene;

                String randPlant = RandomPlants();    //function call to random plant method
                FileInputStream input1 = null;          //basket+random plant
                try {
                    input1 = new FileInputStream("raw/"+randPlant);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Image image1 = new Image(input1);
                imageView = new ImageView(image1);
                imageView.setX(0);
                imageView.setY(0);

                Button keep = new Button();
                keep.setText("Keep");
                Button dispose = new Button();
                dispose.setText("Throw Away");

                keep.setOnAction(actionEvent -> {
                    if (randPlant == "basketAlfalfa.png" || randPlant== "basketChicory.png" || randPlant=="basketDandelion.png"         //veggies
                            || randPlant=="basketLeeks.png" || randPlant=="basketLambsQuarters.png" || randPlant=="basketSunflower.png"){
                        int lbs = PlantLbs();   //random pounds gathered function call
                        playerStuff.setVeggies(playerStuff.getVeggies()+lbs);    //update veggie larder
                        wStatus.setText(playerStuff.toString());    //update player stuff inventory
                        keep.setDisable(true);
                        dispose.setDisable(true);
                        gatherText.setText("You've gathered "+lbs+" pounds of vegetables! Return to camp.");
                    }
                    if (randPlant=="basketElderberry.png" || randPlant == "basketPartridgeBerry.png"                                 //fruits
                            || randPlant == "basketPricklyPear.png" || randPlant == "basketWildCherry.jpg"){
                        int lbs = PlantLbs();   //random pounds gathered function call
                        playerStuff.setFruit(playerStuff.getFruit()+lbs);    //update fruit larder
                        wStatus.setText(playerStuff.toString());    //update player stuff inventory
                        keep.setDisable(true);
                        dispose.setDisable(true);
                        gatherText.setText("You've gathered "+lbs+" pounds of fruit! Return to camp.");
                    }
                    if (randPlant=="basketHollyBerriesBad.jpg" || randPlant == "basketHoneyvineMilkweedBad.png"                      //bad
                            || randPlant == "basketHorsenettleBerriesBad.png" || randPlant == "basketMexPricklyPoppyBad.png"
                    ||randPlant =="basketMoonseedBad.png" || randPlant == "basketMountailLaurelBad.png"|| randPlant =="basketPokeweedBad.png"
                    || randPlant =="basketRhododendronBad.png") {             //case for keeping something bad
                        keep.setDisable(true);
                        dispose.setDisable(true);
                        int temp;
                        String people[] = {player.getName(), pass1.getName(), pass2.getName(), pass3.getName()};    //randomizes who gets sick
                        Random rand = new Random();
                        temp = rand.nextInt((people.length));
                        String randSickie = people[temp];               //peroson who gets sick
                        if (randSickie == player.getName()) {
                            player.setHealth(player.getHealth() - 10);
                        }
                        if (randSickie == pass1.getName()) {
                            pass1.setHealth(pass1.getHealth() - 10);
                        }
                        if (randSickie == pass2.getName()) {
                            pass2.setHealth(pass2.getHealth() - 10);
                        }
                        if (randSickie == pass3.getName()) {
                            pass3.setHealth(pass3.getHealth() - 10);
                        }
                        pStatus.setText("Health: " + player.getName() + " " + player.getHealth() + "/100   " + pass1.getName() + " " + pass1.getHealth() + "/100    "  //updates health text
                                + pass2.getName() + " " + pass2.getHealth() + "/100    " + pass3.getName() + " " + pass3.getHealth() + "/100    ");
                        gatherText.setText("After retuning to camp, " + randSickie + " got sick from eating a bad plant.");           //updates text saying who got sick
                        sickFx.play();          //plays sick sound effect
                    }

                });

                dispose.setOnAction(actionEvent -> {
                    imageView.setX(5000.00);        //throws image out of view
                    rectGather.setHeight(720);
                    dispose.setDisable(true);
                    keep.setDisable(true);
                    gatherText.setText("Plants have been thrown out. Return to camp.");
                });


                HBox gatherLayout = new HBox();
                gatherLayout.setSpacing(5);
                gatherLayout.getChildren().addAll(keep,dispose);
                layout.getChildren().addAll(gatherText,gatherLayout);
                layout.setSpacing(2);
                root = new Group(imageView, rectGather,layout);
                secondScene = new Scene(root, screenWidth, screenHeight);


                // New Stage
                Stage newWindow = new Stage();
                newWindow.setTitle("Gather");
                newWindow.setScene(secondScene);
                // Specifies the modality for new window.
                newWindow.initModality(Modality.WINDOW_MODAL);
                // Specifies the owner Window (parent) for new window
                newWindow.initOwner(stage);
                // Set position of second window, related to primary window.
                newWindow.setX(stage.getX() + 200);
                newWindow.setY(stage.getY() + 100);
                newWindow.show();
            }
        });

        layout2.getChildren().addAll(b2,b1,b3,b4,b5, dText,wText);
        layout3.getChildren().addAll(location,traveled);

        VBox layout1 = new VBox();
        layout1.getChildren().addAll(layout2,wStatus,pStatus,layout3,butt, restButt,commentsText,guide);

        root=new Group(imageView,rect,layout1,wagonView);
        s6= new Scene(root, screenWidth, screenHeight, Color.WHEAT);

        stage.setScene(s6);
        stage.show();

    }


    public static int Weather(){           //function that randomizes temps
        int temp;
        Random rand = new Random();
        temp = rand.nextInt((79-53)+1)+53; //temp range of independence mo in may, other months are based on
        return temp;
    }

    public static int RandomAnimalLocation(){
        int temp;
        Random rand = new Random();
        temp = rand.nextInt((1500-0)+1)+0; //temp range of independence mo in may, other months are based on
        return temp;
    }

    public static int RandomAnimalLbs(){
        int temp;
        Random rand = new Random();
        temp = rand.nextInt((50-5)+1)+5; //temp range of independence mo in may, other months are based on
        return temp;
    }

    public static String RandomHappyComments(){              //pass comments when avg health > 70

        int temp;
        String comment[] = {"What a nice day it is!","What beautiful country!","The trail is well-marked.", "I think we are making great time","I can't wait to get to Oregon.","Did you see the coyote?" };
        Random rand = new Random();
        temp = rand.nextInt((comment.length));
        String randComment = comment[temp];
        return randComment;
    }

    public static String RandomNotHappyComments(){        //pass comments when avg health < 70
        int temp;
        String comment[] = {"I'm not feeling so well", "Are we there yet?", "Are we lost?", "Can we rest a day?","My feet hurt","Which way is West?","Oh shut-up already!"};
        Random rand = new Random();
        temp = rand.nextInt((comment.length));
        String randComment = comment[temp];
        return randComment;
    }

    public static String RandomPlants(){        //array of images randomized
        int temp;
        String plants[] = {"basketAlfalfa.png", "basketChicory.png", "basketDandelion.png", "basketElderberry.png",
                "basketHollyBerriesBad.jpg","basketHoneyvineMilkweedBad.png","basketHorsenettleBerriesBad.png",
                "basketLambsQuarters.png","basketLeeks.png","basketMexPricklyPoppyBad.png","basketMoonseedBad.png",
                "basketMountailLaurelBad.png","basketPartridgeBerry.png","basketPokeweedBad.png","basketPricklyPear.png",
                "basketRhododendronBad.png","basketSunflower.png","basketWildCherry.jpg"
        };

        Random rand = new Random();
        temp = rand.nextInt((plants.length));
        String randPlant = plants[temp];
        return randPlant;
    }

    public static int PlantLbs(){
        int temp;
        Random rand = new Random();
        temp = rand.nextInt((5-1)+1)+1; //range of plant pounds 1-5
        return temp;
    }

    public static String RandomHuntLocations(){        //array of images randomized
        int temp;
        String hunt[] = {"hunt1.png","hunt2.png","hunt3.png"};
        Random rand = new Random();
        temp = rand.nextInt((hunt.length));
        String randLocation = hunt[temp];
        return randLocation;
    }

    public static int FishLbs(){
        int temp;
        Random rand = new Random();
        temp = rand.nextInt((15-1)+1)+1; //range of fish pounds 1-15
        return temp;
    }

    public static int RandomFishViewPortX(){
        int temp;
        int minX[] = {0,1020,2040,3060};
        Random rand = new Random();
        temp = rand.nextInt((minX.length));
        int xVPort = minX[temp];
        return xVPort;
    }

    public static int RandomFishViewPortY(){
        int temp;
        int minY[] = {0,712,1424};
        Random rand = new Random();
        temp = rand.nextInt((minY.length));
        int yVPort = minY[temp];
        return yVPort;
    }

    public static int RandomFishCatchViewPortX(){
        int temp;
        int minX[] = {200,400,600};
        Random rand = new Random();
        temp = rand.nextInt((minX.length));
        int xVPort = minX[temp];
        return xVPort;
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
