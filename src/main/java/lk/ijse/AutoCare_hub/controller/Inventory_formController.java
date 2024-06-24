package lk.ijse.AutoCare_hub.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.AutoCare_hub.model.Inventory_item;
import lk.ijse.AutoCare_hub.model.Tm.InventoryTm;
import lk.ijse.AutoCare_hub.repository.Inventory_itemRepo;
import lk.ijse.AutoCare_hub.repository.VehicleRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static lk.ijse.AutoCare_hub.util.ValidationUtil.addError;
import static lk.ijse.AutoCare_hub.util.ValidationUtil.removeError;

public class Inventory_formController {

    @FXML
    private JFXButton Clearbtn;

    @FXML
    private JFXButton Deletebtn;

    @FXML
    private JFXButton Savebtn;

    @FXML
    private JFXButton Updatebtn;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private AnchorPane paneHolder;

    @FXML
    private TableView<InventoryTm> tblInventory;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQty;



    private List<Inventory_item> inventoryList = new ArrayList<>();

    public void initialize() {
        this.inventoryList = getAllInventoryItem();
        setCellDataFactory();
        loadAllInventoryItem();
    }

    private void loadAllInventoryItem() {
        ObservableList<InventoryTm> tmList = FXCollections.observableArrayList();

        for (Inventory_item inventory_item : inventoryList) {
            InventoryTm inventoryTm = new InventoryTm(

                    inventory_item.getItem_id(),
                    inventory_item.getDescription(),
                    inventory_item.getPrice(),
                    inventory_item.getQty_On_Hand()


            );
            tmList.add(inventoryTm);

        }

        tblInventory.setItems(tmList);
        InventoryTm selectedItem = tblInventory.getSelectionModel().getSelectedItem();
    }

    private void setCellDataFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("Item_id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty_On_Hand"));

    }

    private List<Inventory_item> getAllInventoryItem() {
        List<Inventory_item> inventory_itemList = null;
        try {
            inventory_itemList = Inventory_itemRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return inventory_itemList;
    }
    private void clearFields() {
        txtId.setText("");
        txtQty.setText("");
        txtPrice.setText("");
        txtQty.setText("");

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String Item_id = txtId.getText();

        try {
            boolean isDeleted = Inventory_itemRepo.delete(Item_id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String Item_id = txtId.getText();
        String Description = txtDescription.getText();
        String Price = txtPrice.getText();
        String Qty_On_Hand = txtQty.getText();


        Inventory_item inventory_item = new Inventory_item(Item_id, Description, Price, Qty_On_Hand);

        try {
            boolean isSaved = Inventory_itemRepo.save(inventory_item);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String Item_id = txtId.getText();
        String Description = txtDescription.getText();
        String Price = txtPrice.getText();
        String Qty_On_Hand = txtQty.getText();


        Inventory_item inventory_item = new Inventory_item(Item_id, Description, Price, Qty_On_Hand);

        try {
            boolean isUpdated = Inventory_itemRepo.update(inventory_item);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item  updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void txtInventoryIdKeyReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("(I)[0-9].{1,9}$");
        if (!idPattern.matcher(txtId.getText()).matches()) {
            addError(txtId);
            Savebtn.setDisable(true);

        }else{
            removeError(txtId);
            Savebtn.setDisable(false);
        }
    }

    public void txtDescriptionKeyReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[a-zA-Z 0-9 ]*$");
        if (!idPattern.matcher(txtDescription.getText()).matches()) {
            addError(txtDescription);
            Savebtn.setDisable(true);

        }else{
            removeError(txtDescription);
            Savebtn.setDisable(false);
        }
    }


    public void txtPriceKeyReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[0-9]*$");
        if (!idPattern.matcher(txtPrice.getText()).matches()) {
            addError(txtPrice);
            Savebtn.setDisable(true);
        }else{
            removeError(txtPrice);
            Savebtn.setDisable(false);
        }
    }

    public void txtQtyKeyReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[0-9]*$");
        if (!idPattern.matcher(txtQty.getText()).matches()) {
            addError(txtQty);
            Savebtn.setDisable(true);

        }else{
            removeError(txtQty);
            Savebtn.setDisable(false);
        }
    }
}


