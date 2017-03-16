package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import presentationmodel.DataPot;
import presentationmodel.users;

import java.net.InetAddress;

/**
 * Created by jp on 11.03.2017.
 */
public class RootPane extends FlowPane {
    private final DataPot model;
    private TextArea historyArea;
    private TextField input;
    private Button sendButton;
    private TableView<users> thetable;

    public RootPane(DataPot model)  {
        this.model = model;
        initializeParts();
        initializeTable();
        layoutParts();
        setupBindings();
        addEventHandlers();
    }

    private void initializeTable() {
        thetable = new TableView<>(model.getOnlineClients());
        thetable.setEditable(false);
        TableColumn<users, String> nameCol = new TableColumn<>("Name");
        TableColumn<users, String> ipCol = new TableColumn("IP");
        TableColumn<users, String> statusCol = new TableColumn("Status");
        TableColumn<users, String> lastupdateCol = new TableColumn("Last Update");

        nameCol.setCellValueFactory(cell -> cell.getValue().nameProperty());
        ipCol.setCellValueFactory(cell -> cell.getValue().ipProperty());
        statusCol.setCellValueFactory(cell -> cell.getValue().statusProperty());
        lastupdateCol.setCellValueFactory(cell -> cell.getValue().lastupdateProperty());

        thetable.getColumns().addAll(nameCol, ipCol, statusCol, lastupdateCol);
            thetable.getSelectionModel().selectFirst();
    }

    public void initializeSelf() {
//        String fonts = getClass().getResource("fonts.css").toExternalForm();
//        getStylesheets().add(fonts);
//
//        String stylesheet = getClass().getResource("style.css").toExternalForm();
//        getStylesheets().add(stylesheet);
//
//        getStyleClass().add("root-pane");
    }

    public void initializeParts() {
        historyArea = new TextArea();
        input = new TextField();
        sendButton = new Button();
    }

    public void layoutParts() {
        historyArea.setWrapText(true);
        historyArea.setEditable(false);
        sendButton.textProperty().set("send");

        getChildren().add(thetable);
        getChildren().add(historyArea);
        getChildren().add(input);
        getChildren().add(sendButton);

        input.setPrefWidth(900);
        sendButton.setDisable(true);
    }

    public void setupBindings() {
        historyArea.textProperty().bindBidirectional(model.historyProperty());
    }
    private void addEventHandlers() {

        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                model.sendTextMessage(thetable.getSelectionModel().getSelectedItem().getIp(),input.getText());
                input.clear();
            }
        });

        thetable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue == null){
                sendButton.setDisable(true);
            }else {
                sendButton.setDisable(false);
            }
        }));

    }

}
