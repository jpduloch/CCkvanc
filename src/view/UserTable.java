package view;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import presentationmodel.DataPot;
import presentationmodel.users;

/**
 * Created by jp on 3/13/17.
 */
public class UserTable extends TableView<users> {
    private DataPot model;

    public UserTable() {
        this.model = model;

        layoutControls();
    }

    private void layoutControls() {
        this.setEditable(false);
        TableColumn<users, String> nameCol = new TableColumn<>("Name");
        TableColumn<users, String> ipCol = new TableColumn("IP");
        TableColumn<users, String> statusCol = new TableColumn("Status");
        TableColumn<users, String> lastupdateCol = new TableColumn("Last Update");

        nameCol.setCellValueFactory(cell -> cell.getValue().nameProperty());
        ipCol.setCellValueFactory(cell -> cell.getValue().ipProperty());
        statusCol.setCellValueFactory(cell -> cell.getValue().statusProperty());
        lastupdateCol.setCellValueFactory(cell -> cell.getValue().lastupdateProperty());

        this.getColumns().addAll(nameCol, ipCol, statusCol, lastupdateCol);
    }
}
