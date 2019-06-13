package com.escom.bmltraffic;

import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
 
/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {        
        //User Data
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Insert space dimension");        
        System.out.println("Dimension size: ");
        short x=Short.parseShort(br.readLine());
        short y =x;
        
        //JavaFX GUI Elements
        BorderPane root = new BorderPane();
        Scene scene;
        scene = new Scene(root);
        
        //Spaces
        Space currentEvolution=new Space(x,y);
        
        System.out.println("Insert density: ");
        float f=Float.parseFloat(br.readLine());
        currentEvolution.setRandomBiased(f);      
        
        //Game Automaton
        BMLAutomaton bml = new BMLAutomaton();  
        
        //Animation        
        Animation animation=new Animation(bml, currentEvolution, new Space(x,y));
        animation.start();
        
        //Key events. start-pause
        scene.setOnKeyPressed((KeyEvent e) -> {
            String code = e.getCode().toString();
            //System.out.println(code);
            switch (code) {
                case "P":
                    animation.stop();
                    break;
                case "C":
                    animation.start();
                    break;
                case "R":
                    animation.increaseSpeed();
                    break;
                case "L":
                    animation.decreaseSpeed();
                    break;  
                default:
                    break;
            }
        });

        //Display GUI, Canvas     
        ScrollPane sp = new ScrollPane(animation.getCanvas());
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        root.setCenter(sp);  
        //Top Pane
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");
        //Hbox components
        //Title
        Text current_rule=new Text("BML Traffic. "+currentEvolution.getXSize()+"x"+currentEvolution.getYSize());
        current_rule.setFill(Color.WHITE);
        current_rule.setFont(Font.font(15));
        
        Text zoom_info=new Text("Zoom");
        zoom_info.setFill(Color.WHITE);
        zoom_info.setFont(Font.font(15));
        
        //Play/pause button        
        ToggleButton buttonPlay = new ToggleButton("Play/Pause");
        buttonPlay.setPrefSize(100, 20);
        buttonPlay.setOnAction(new EventHandler<ActionEvent>() {
            boolean toggle=false;
            @Override
            public void handle(ActionEvent e) {
                if(toggle){
                    animation.start();                   
                    toggle=false;
                }else{
                    animation.stop();
                    toggle=true;
                }
            }
        });
        //Fast-Forward
        Button buttonFast=new Button("Increase Speed");
        buttonFast.setPrefSize(100, 20);
        buttonFast.setOnAction((ActionEvent e) -> {
            animation.increaseSpeed();
        });
        //Slow-Forward
        Button buttonSlow=new Button("Decrease Speed");
        buttonSlow.setPrefSize(140, 20);
        buttonSlow.setOnAction((ActionEvent e) -> {
            animation.decreaseSpeed();
        });
        
        //Slider 
        Slider slider = new Slider(1,13,3);
        slider.setMin(1);
        slider.setMax(13);
        slider.setBlockIncrement(1);
        slider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            animation.setZoom(Math.ceil((double)new_val));
            primaryStage.sizeToScene();
        });
        
        hbox.getChildren().addAll(current_rule,buttonPlay,buttonFast,buttonSlow,zoom_info,slider);        
        root.setTop(hbox);
        
        primaryStage.setTitle("BML Traffic");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
        
        
    }

    public static void main(String[] args) {
        launch(args);
    }

}