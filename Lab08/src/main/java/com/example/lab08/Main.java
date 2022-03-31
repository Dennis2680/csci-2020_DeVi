package com.example.lab08;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import static com.example.lab08.DataSource.save;

public class Main extends Application {

    public static String fileName;

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setWidth(530);
        stage.setHeight(500);
        stage.setTitle("Lab 08 Solution");

        Button file = new Button("File");
        Button newTable = new Button("New");
        Button open = new Button("Open");
        Button saveAs = new Button("Save As");
        Button save = new Button("Save");
        Button exit = new Button("Exit");

        TableView tableView = new TableView();
        TableColumn studentIDColumn = new TableColumn("Student ID");
        TableColumn assignmentColumn = new TableColumn("Assignment");
        TableColumn midTermColumn = new TableColumn("Midterm");
        TableColumn finalExamColumn = new TableColumn("Final Exam");
        TableColumn finalMarkColumn = new TableColumn("Final Mark");
        TableColumn letterGradeColumn = new TableColumn("Letter Grade");

        tableView.getColumns().addAll(studentIDColumn, assignmentColumn, midTermColumn, finalExamColumn, finalMarkColumn, letterGradeColumn);

        studentIDColumn.setCellValueFactory(new PropertyValueFactory<com.example.lab08.StudentRecord, String>("studentID"));
        assignmentColumn.setCellValueFactory(new PropertyValueFactory<com.example.lab08.StudentRecord, Float>("midTerm"));
        midTermColumn.setCellValueFactory(new PropertyValueFactory<com.example.lab08.StudentRecord, Float>("assignments"));
        finalExamColumn.setCellValueFactory(new PropertyValueFactory<com.example.lab08.StudentRecord, Float>("finalExam"));
        finalMarkColumn.setCellValueFactory(new PropertyValueFactory<com.example.lab08.StudentRecord, Double>("finalMark"));
        letterGradeColumn.setCellValueFactory(new PropertyValueFactory<com.example.lab08.StudentRecord, String>("letterGrade"));

        file.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Scene fileScene = new Scene(new Group());
                VBox vbox1 = new VBox();
                vbox1.setSpacing(5);
                vbox1.setPadding(new Insets(10, 0, 0, 10));
                vbox1.getChildren().addAll(file, newTable, open, save, saveAs, exit);

                ((Group) fileScene.getRoot()).getChildren().addAll(vbox1);

                stage.setScene(fileScene);
                stage.show();
            }
        });

        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                save(fileName);
                Label saveLabel = new Label("Data saved!");

                Scene saveScene = new Scene(new Group());
                VBox vbox1 = new VBox();
                vbox1.setSpacing(5);
                vbox1.setPadding(new Insets(10, 0, 0, 10));
                vbox1.getChildren().addAll(file, tableView, saveLabel);

                ((Group) saveScene.getRoot()).getChildren().addAll(vbox1);

                stage.setScene(saveScene);
                stage.show();
            }
        });

        saveAs.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                Label prompt = new Label("Enter a file name");

                TextField newFileName = new TextField();

                Button enter = new Button("Enter");

                Scene promptScene = new Scene(new Group());
                VBox vbox1 = new VBox();
                vbox1.setSpacing(5);
                vbox1.setPadding(new Insets(10, 0, 0, 10));
                vbox1.getChildren().addAll(file, prompt, newFileName, enter, tableView);

                ((Group) promptScene.getRoot()).getChildren().addAll(vbox1);

                stage.setScene(promptScene);
                stage.show();

                enter.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {

                        fileName = newFileName.getText();

                        save(fileName);

                        Scene saveAsScene = new Scene(new Group());
                        VBox vbox1 = new VBox();
                        vbox1.setSpacing(5);
                        vbox1.setPadding(new Insets(10, 0, 0, 10));
                        vbox1.getChildren().addAll(file, tableView);

                        ((Group) saveAsScene.getRoot()).getChildren().addAll(vbox1);

                        stage.setScene(saveAsScene);
                        stage.show();
                    }
                });
            }
        });

        newTable.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                tableView.getItems().clear();

                Scene newScene = new Scene(new Group());
                VBox vbox1 = new VBox();
                vbox1.setSpacing(5);
                vbox1.setPadding(new Insets(10, 0, 0, 10));
                vbox1.getChildren().addAll(file, tableView);

                ((Group) newScene.getRoot()).getChildren().addAll(vbox1);

                stage.setScene(newScene);
                stage.show();
            }
        });

        open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Comma-Separated Value", "*.csv*"));
                File selectedFile = fileChooser.showOpenDialog(stage);

                if (selectedFile != null) {
                    fileName = selectedFile.getName();
                }

                tableView.setItems(com.example.lab08.DataSource.load(fileName));


                Scene openScene = new Scene(new Group());
                VBox vbox1 = new VBox();
                vbox1.setSpacing(5);
                vbox1.setPadding(new Insets(10, 0, 0, 10));
                vbox1.getChildren().addAll(file, tableView);

                ((Group) openScene.getRoot()).getChildren().addAll(vbox1);

                stage.setScene(openScene);
                stage.show();
            }
        });

        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Platform.exit();
            }
        });

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(file, tableView);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}