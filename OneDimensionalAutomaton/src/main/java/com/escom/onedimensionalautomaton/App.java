package com.escom.onedimensionalautomaton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    Automaton automaton;
    int maxEvolutions;

    @Override
    public void start(Stage stage) {

        initAutomaton();

        stage.setTitle("1-D Cellular Automaton");
        var root = new Group();
        var canvas = new Canvas((double) automaton.getSpaceLenght() + 1, (double) maxEvolutions + 1);
        var gc = canvas.getGraphicsContext2D();
        ScrollPane sp = new ScrollPane(canvas);

        if (automaton.getSpaceLenght() > 800) {
            sp.setPrefWidth(800);
        }
        if (maxEvolutions > 800) {
            sp.setPrefHeight(800);
        }

        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        root.getChildren().add(sp);
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                //Draw on screen
                for (int j = 0; j < automaton.getSpaceLenght(); j++) {
                    if (automaton.getArreglo()[j]) {
                        gc.setFill(Color.BLACK);
                    } else {
                        gc.setFill(Color.WHITE);
                    }
                    gc.fillRect(j, automaton.getEvolutionNumber(), 1, 1);
                }
                //Evolve the space
                automaton.evolve();
                //Check if done
                if (automaton.getEvolutionNumber() == maxEvolutions) {
                    this.stop();
                }
            }
        }.start();

    }

    public void initAutomaton() {
        try (var br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("One dimensional Cellular Automaton");
            System.out.println("Rule to evaluate (0 - 255): ");
            var rule = Integer.parseInt(br.readLine());
            System.out.println("Fill the space manually (m) o randomly (r) ?");
            String opt = br.readLine();
            switch (opt) {
                case "r": //Random
                    System.out.println("Size of the evolutionary space (2-10,000): ");
                    var size = Integer.parseInt(br.readLine());
                    automaton = new Automaton(rule, size);
                    automaton.randomize();
                    break;
                case "m": // Manual
                    System.out.println("Read input using \na)Console\nb)Archive");
                    opt = br.readLine();
                    switch (opt) {
                        case "a":
                            readFromConsole(br, rule);
                            break;
                        case "b":
                            readFromFile(br);
                            break;
                        default:
                            System.exit(0);
                            break;
                    }
                    break;
                default:
                    System.exit(0);
                    break;
            }
            System.out.println("Number of evolutions to evaluate: ");
            maxEvolutions = Integer.parseInt(br.readLine()) + 1;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void readFromFile(final BufferedReader br) throws IOException, FileNotFoundException, NumberFormatException {
        int size;
        System.out.println("Escribe el nombre del archivo");
        String filename = br.readLine();
        File file = new File(filename);
        size = Integer.parseInt(filename);
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(file));
        char text;
        for (int i = 0; i < size; i++) {
            text = (char) reader.read();
            automaton.getArreglo()[i] = (text == '1');
            if (automaton.getArreglo()[i]) {
                System.out.print("1");
            } else {
                System.out.print("0");
            }
        }
        System.out.print("\n");
    }

    public void readFromConsole(final BufferedReader br, int rule) throws IOException {
        System.out.println("Input the initial state (0|1 string): ");
        String input = br.readLine();
        automaton = new Automaton(rule, input.length());
        for (int i = 0; i < input.length(); i++) {
            automaton.getArreglo()[i] = input.charAt(i) == '1';
        }
    }

    public static void main(String[] args) {
        launch();
    }

}
