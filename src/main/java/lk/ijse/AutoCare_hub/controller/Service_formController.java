package lk.ijse.AutoCare_hub.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lk.ijse.AutoCare_hub.model.Inventory_item;
import lk.ijse.AutoCare_hub.model.Point_System;
import lk.ijse.AutoCare_hub.model.Service;
import lk.ijse.AutoCare_hub.model.Tm.ItemTm;
import lk.ijse.AutoCare_hub.model.Vehicle;
import lk.ijse.AutoCare_hub.repository.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Service_formController {

    public JFXButton btnAddToCart;
    public JFXComboBox<String> cmbItemId;
    public Label lblItemName;
    public Label lblNetTotal;
    public Label lblItemQty;
    public Label lblItemPrice;
    public TextField txtQty;
    public Label lblDate;
    public Label lblModel;
    public Label lblType;
    public Label lblCustomerId;
    public TextField txtServiceId;
    public JFXComboBox cmbVehicleId;
    public Label lblDiscount;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colVehicleModel;

    @FXML
    private TableColumn<?, ?> colVehicleType;

    @FXML
    private TableColumn<?, ?> colVehicleid;

    @FXML
    private AnchorPane paneHolder;

    @FXML
    private TableView<ItemTm> tblVehicle;

    private ObservableList<ItemTm> observableList = FXCollections.observableArrayList();
    private double fullTotal=0;


    public void initialize() {
        setCellDataFactory();
        setComboBoxValues();
        setVehicleId();
        setServiceId();
        lblDate.setText(String.valueOf(LocalDate.now()));
    }

    private void setServiceId() {
        try {
            String id = ServiceRepo.fintLastServiceId();
            if (id!=null){
                int lastId = Integer.parseInt(id.substring(1));
                String nextId="S00"+(lastId+1);
                txtServiceId.setText(nextId);
            }else {
                txtServiceId.setText("S001");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setVehicleId() {
        try {
            List<Vehicle> all = VehicleRepo.getAll();
            ArrayList<String> allId = new ArrayList<>();
            for (Vehicle vehicle:all) {
                allId.add(vehicle.getV_id());
            }
            cmbVehicleId.setItems(FXCollections.observableList(allId));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setComboBoxValues() {
        try {
            List<Inventory_item> all = Inventory_itemRepo.getAll();
            ArrayList<String> allItemId = new ArrayList<>();
            for (Inventory_item item:all) {
                allItemId.add(item.getItem_id());
            }
            cmbItemId.setItems(FXCollections.observableList(allItemId));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellDataFactory() {
        colVehicleid.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colVehicleModel.setCellValueFactory(new PropertyValueFactory<>("name"));
        colVehicleType.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    private List<Vehicle> getAllVehicle() {
        List<Vehicle> vehicleList = null;
        try {
            vehicleList = VehicleRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vehicleList;
    }


    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String serviceId = txtServiceId.getText();
        String date = lblDate.getText();
        String vehicleId = String.valueOf(cmbVehicleId.getValue());

        Service service = new Service(serviceId, String.valueOf(fullTotal), date, vehicleId);
        boolean isSave = PlaceOrderRepo.saveService(service,observableList);
        if (isSave){
            new Alert(Alert.AlertType.CONFIRMATION,"Service Order..!").show();
            clearAll();
            setServiceId();
        }else {
            new Alert(Alert.AlertType.ERROR,"Something Wrong..!").show();
        }
    }

    private void clearAll() {
        lblDate.setText("");
        lblType.setText("");
        lblCustomerId.setText("");
        lblModel.setText("");
        lblItemPrice.setText("");
        lblNetTotal.setText("");
        lblItemPrice.setText("");
        lblItemName.setText("");
        cmbVehicleId.setValue("");
        cmbItemId.setValue("");
        observableList.clear();
        tblVehicle.setItems(observableList);
        txtQty.clear();
    }

    private void clearField(){
            lblItemPrice.setText("");
            lblItemPrice.setText("");
            lblItemName.setText("");
            cmbItemId.setValue("");
            txtQty.clear();
            lblDiscount.setText("");
    }

    @FXML
    void btnServiceHistoryOnAction(ActionEvent event) throws IOException, IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Service_History_form.fxml"));
        Pane registePane = (Pane) fxmlLoader.load();
        paneHolder.getChildren().clear();
        paneHolder.getChildren().add(registePane);
    }

    public void cmbItemIdOnAction(ActionEvent actionEvent) throws SQLException {
        String itemId = cmbItemId.getValue();
        ResultSet resultSet = Inventory_itemRepo.searchItem(itemId);
        if (resultSet.next()){
            lblItemName.setText(resultSet.getString(2));
            lblItemPrice.setText(resultSet.getString(3));
            lblItemQty.setText(resultSet.getString(4));
        }
    }

    public void btnAddToCartOnAction() {
        String itemId = cmbItemId.getValue();
        String name = lblItemName.getText();
        double price= Double.parseDouble(lblItemPrice.getText());
        int qty= Integer.parseInt(txtQty.getText());
        double total=price*qty;

        fullTotal+=total;
        lblNetTotal.setText(String.valueOf(fullTotal));

        ItemTm itemTm = new ItemTm(itemId, name, String.valueOf(qty), String.valueOf(total));
        observableList.add(itemTm);
        tblVehicle.setItems(observableList);
        clearField();
    }

    public void cmbVehicleId(ActionEvent actionEvent) throws SQLException {
        Vehicle vehicle = VehicleRepo.searchById(String.valueOf(cmbVehicleId.getValue()));
       if (vehicle!=null){
           lblModel.setText(vehicle.getModel());
           lblType.setText(vehicle.getType());
           lblCustomerId.setText(vehicle.getCus_id());

           Point_System search = Point_SystemRepo.search(vehicle.getCus_id());
           if (search!=null){
               if (Integer.parseInt(search.getTotal_point())>=1000){
                   lblDiscount.setText("Free service");
               }else {
                   lblDiscount.setText("");
               }
           }else {
               lblDiscount.setText("");
           }
       }
    }
}


