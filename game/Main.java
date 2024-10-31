package game;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

public class Main extends Application {

    Scene s1;
    private Group root;
    private ImageView imageView;
    private final int screenWidth = 1280;
    private final int screenHeight = 720;

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        primaryStage.setTitle("Trail West");

        //sound on splash
        //String path = "raw/bensound-birthofahero.mp3";
        //String path = "raw/bensound-countryboy.mp3";
        //String path = "raw/Untitled Project.mp4";
        //String path = "raw/1676_Ted_Weems__His_Orchestra_-_Covered_Wagon_Days.mp3";
        //String path = "raw/0063_Monplaisir_-_10_-_The_Call_of_the_Coyote.mp3";
        String path ="raw/0247_TRG_Banks_-_03_-_Fresh_pastures.mp3";
        //String path = "raw/bensound-newdawn.mp3";

        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(.05);
        MediaView mediaView =  new MediaView(mediaPlayer);

        FileInputStream input = new FileInputStream("raw/splash.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);

        imageView.setX(0);
        imageView.setY(0);

        Button button1= new Button("New Game");
        Button button2= new Button("Load Game");
        Button button3= new Button("Exit Game");
        Button button4= new Button("Credits");
        Button button5= new Button("Music On/Off");
        button5.setAlignment(Pos.CENTER);

        button1.setMinWidth(200);
        button2.setMinWidth(200);
        button3.setMinWidth(200);
        button4.setMinWidth(200);
        button5.setMinWidth(200);

        VBox layout1 = new VBox();
        layout1.setSpacing(10);
        layout1.setLayoutY((screenHeight/2)-50);
        layout1.setLayoutX((screenWidth/2)-130);

        layout1.getChildren().addAll(button1,button2,button3,button4,button5);

        root = new Group(imageView,layout1,mediaView);
        s1= new Scene(root, screenWidth, screenHeight, Color.WHEAT);

        button1.setOnAction(e -> {
            Setup game = new Setup();
            try {
                game.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        button3.setOnAction(actionEvent -> {    //exit game
            mediaPlayer.stop();
            mediaPlayer.dispose();
            System.exit(0);
        });

        button5.setOnAction(actionEvent -> {    //toggle music on r off game
            MediaPlayer.Status status = mediaPlayer.getStatus();
            if(status == MediaPlayer.Status.PLAYING){
            mediaPlayer.stop();
            }
            else
                mediaPlayer.play();
        });

        button4.setOnAction(actionEvent -> {
            final int screenWidth = 1020;
            final int screenHeight = 712;
            Scene credScene;

            String credPath = "raw/fish1.mp4";
            Media credMedia = new Media(new File(path).toURI().toString());
            MediaPlayer credMediaPlayer = new MediaPlayer(credMedia);
            MediaView credMediaView = new MediaView(credMediaPlayer);
            credMediaPlayer.setAutoPlay(true);
            root = new Group(credMediaView);
            credScene = new Scene(root, screenWidth, screenHeight);
            // New Stage
            Stage newWindow = new Stage();
            newWindow.setTitle("Credits");
            newWindow.setScene(credScene);
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.initOwner(primaryStage);
            newWindow.setX(primaryStage.getX() + 200);
            newWindow.setY(primaryStage.getY() + 100);
            newWindow.show();
        });

        button2.setOnAction(actionEvent -> {
            /*
            String newFileName;
        Scanner scan = new Scanner(System.in);
        int userIn;
        System.out.println("");
        System.out.println("|======================================|");
        System.out.println("|-----Save To Do List to text file-----|\n"+
                "|======================================|\n");
        displayTaskList(list);
        System.out.println("");
        System.out.println("Enter task from list you wish to save (enter 0,1,2,3..");
        System.out.print(">>");
        userIn=scan.nextInt();
        newFileName = list.get(userIn).getTaskName() + ".txt";      //concat .txt to userIn to open text files
        try (Writer newFile = new FileWriter(newFileName)) {        //Writer writes an new .txt file
            newFile.write(list.get(userIn).getTaskName() + "\n");
            newFile.write(list.get(userIn).getPriorityLevel() + "\n");
            newFile.write(list.get(userIn).getDate() + "\n");
            newFile.write(list.get(userIn).getStatus() + "\n");
            newFile.flush();
            newFile.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Success, a task list named \""+list.get(userIn).getTaskName()+"\".txt has been created\n" +
                "inside of the project folder\n");
        System.out.println("To Do List:\n");
        displayTaskList(list);
        dispMenu(list);
    }

    //load
      String taskName, priority, date, status;
        Scanner scanner = new Scanner(System.in);
        System.out.println("");
        System.out.println("|======================================|");
        System.out.println("|-----Load To Do List to text file-----|\n"+
                "|======================================|\n");
        System.out.println("8. Enter a task name to load (do not include file extension)...");
        System.out.print(">>");
        String fileLoad = scanner.nextLine()+".txt";
        File file = new File(fileLoad);
        try(Scanner scan = new Scanner(file)){
            while(scan.hasNextLine()){
                taskName=scan.nextLine();
                priority=scan.nextLine();
                date = scan.nextLine();
                status=scan.nextLine();
                list.insertFront(new LinkedListItems(taskName,priority,date,status));     //instantiate new linkedListItems into list
                System.out.println("Success, task list named \""+fileLoad+"\" has been loaded into the To Do List:\n");
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("Current items in your To Do List:");
        displayTaskList(list);
        dispMenu(list);
  }
             */
        });


        primaryStage.setScene(s1);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
