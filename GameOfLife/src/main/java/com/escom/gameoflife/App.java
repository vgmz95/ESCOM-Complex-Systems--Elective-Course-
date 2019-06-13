package com.escom.gameoflife;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            //User Data
            System.out.println("Game of Life.\nInsert rule (x1,y1,x2,y2), leaving a space after a number: (For Conway's Game of Life use 2 3 3 3)");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String rule_not = br.readLine();
            String[] split = rule_not.split(" ");
            short x1 = Short.parseShort(split[0]), y1 = Short.parseShort(split[1]), x2 = Short.parseShort(split[2]), y2 = Short.parseShort(split[3]);
            if (x1 > 7 || x2 > 7 || y1 > 7 || y2 > 7 || x1 > y1 || x2 > y2) {
                System.out.println("Incorrect values");
                System.exit(0);
            }
            System.out.println("Insert space size");
            System.out.println("X size: ");
            short x = Short.parseShort(br.readLine());
            System.out.println("Y size: ");
            short y = Short.parseShort(br.readLine());
            System.out.println("Empty space (e), Random Space (r). Random with probability (b)");
            String option = br.readLine();
            System.out.println("Insert zoom factor. (Factor>0.0 . For a Better user interacion type 10.0. For better view type 1.5)");
            double factor = Double.parseDouble(br.readLine()); //Scale Factors       

            //JavaFX GUI Elements
            BorderPane root = new BorderPane();
            Canvas canvas = new Canvas(x, y);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            Scene scene;
            scene = new Scene(root);

            //Spaces
            Space currentEvolution = new Space(x, y);

            switch (option) {
                case "e":
                    break;
                case "r":
                    currentEvolution.setRandomSpace();
                    break;
                case "b":
                    System.out.println("Insert probability: ");
                    float f = Float.parseFloat(br.readLine());
                    currentEvolution.setRandomBiased(f);
                    break;
                default:
                    break;
            }

            //Re-size GUI
            modifyZoom(factor, canvas, gc, x, y);

            //Game Automaton
            GameAutomaton game = new GameAutomaton(x1, y1, x2, y2);

            //Animation        
            Animation animation = new Animation(game, currentEvolution, new Space(x, y), gc);
            currentEvolution = null;
            animation.start();

            //Key events. start-pause
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                double factor = 0.5;

                @Override
                public void handle(KeyEvent e) {
                    String code = e.getCode().toString();
                    //System.out.println(code);                
                    switch (code) {
                        case "P":
                            animation.stop();
                            break;
                        case "C":
                            animation.start();
                            break;
                        case "ADD":
                            animation.increaseSpeed();
                            break;
                        case "SUBTRACT":
                            animation.decreaseSpeed();
                            break;
                        default:
                            break;
                    }
                }
            });

            //Mouse Events
            canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent t) -> {

                //Recalculate positions
                short x_clic = (short) (t.getX() / factor);
                short y_clic = (short) (t.getY() / factor);
                //Get current state
                if (animation.getCurrentEvolution().getBooleanValueAt(x_clic, y_clic)) {
                    animation.getCurrentEvolution().setValueAt(x_clic, y_clic, false);
                } else {
                    animation.getCurrentEvolution().setValueAt(x_clic, y_clic, true);
                }
                //Draw Again
                animation.getCurrentEvolution().drawSpace(gc);
            });

            //Display GUI, Canvas     
            ScrollPane sp = new ScrollPane(canvas);
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
            Text current_rule = new Text("Rule: B" + x2 + "" + y2 + "/S" + x1 + y1);
            current_rule.setFill(Color.WHITE);
            current_rule.setFont(Font.font(15));

            //Play/pause button        
            Button buttonPlay = new Button("Play/Pause");
            buttonPlay.setPrefSize(100, 20);
            buttonPlay.setOnAction(new EventHandler<ActionEvent>() {
                boolean toggle = false;

                @Override
                public void handle(ActionEvent e) {
                    if (toggle) {
                        animation.start();
                        toggle = false;
                    } else {
                        animation.stop();
                        toggle = true;
                    }
                }
            });
            //Fast-Forward
            Button buttonFast = new Button("Increase Speed");
            buttonFast.setPrefSize(100, 20);
            buttonFast.setOnAction((ActionEvent e) -> {
                animation.increaseSpeed();
            });
            //Slow-Forward
            Button buttonSlow = new Button("Decrease Speed");
            buttonSlow.setPrefSize(140, 20);
            buttonSlow.setOnAction((ActionEvent e) -> {
                animation.decreaseSpeed();
            });

            hbox.getChildren().addAll(current_rule, buttonPlay, buttonFast, buttonSlow);
            root.setTop(hbox);

            primaryStage.setTitle("Game of life");
            scene.getRoot().autosize();
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch();
    }

    void modifyZoom(double factor, Canvas canvas, GraphicsContext gc, short x, short y) {
        canvas.setWidth(factor * (double) x);
        canvas.setHeight(factor * (double) y);
        gc.scale(factor, factor);
    }

}
