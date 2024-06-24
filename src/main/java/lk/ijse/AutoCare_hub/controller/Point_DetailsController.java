package lk.ijse.AutoCare_hub.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.AutoCare_hub.model.Customer;
import lk.ijse.AutoCare_hub.model.Point_System;
import lk.ijse.AutoCare_hub.model.Service_History;
import lk.ijse.AutoCare_hub.repository.CustomerRepo;
import lk.ijse.AutoCare_hub.repository.Point_SystemRepo;
import lk.ijse.AutoCare_hub.repository.Service_HistoryRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static lk.ijse.AutoCare_hub.util.ValidationUtil.addError;
import static lk.ijse.AutoCare_hub.util.ValidationUtil.removeError;

public class Point_DetailsController {

    public TextField txtContact;
    public TextField txtDiscount;
    public Label lblDiscountPoint;
    @FXML
    private JFXButton Clearbtn;

    @FXML
    private JFXButton Deletebtn;

    @FXML
    private JFXButton Savebtn;

    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private JFXComboBox<String> cmbServiceId;

    @FXML
    private TextField TxtTotalPoint;

    @FXML
    private JFXButton Updatebtn;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colId;


    @FXML
    private TableColumn<?, ?> colServiceHistoryId;

    @FXML
    private TableView<Point_SystemRepo> colTablePontDetail;

    @FXML
    private TableColumn<?, ?> colTotalPoint;

    @FXML
    private TextField txtId;

    @FXML
    private Label lblCustomerId;

    @FXML
    private Label lblServiceHistoryId;

    private List<Point_System> point_systemList = new ArrayList<>();
    public void initialize() {
        this.point_systemList = getAllPontSystem();
        setCellDataFactory();
        loadAllPontSystem();
        getCustomerId();
        getserviceHistoryId();
    }

    private void getserviceHistoryId() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> IdList = Service_History.getIds();

            for (String id : IdList) {
                obList.add(id);
            }
            cmbServiceId.setItems(obList);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCustomerId() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> IdList = CustomerRepo.getIds();

            for (String id : IdList) {
                obList.add(id);
            }
            cmbCustomerId.setItems(obList);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadAllPontSystem() {
        ObservableList<Point_SystemRepo> tmList = FXCollections.observableArrayList();

        for (Point_System point_system : point_systemList) {
            Point_SystemRepo point_systemRepo = new Point_SystemRepo(

                    point_system.getPoint_id(),
                    point_system.getTotal_point(),
                    point_system.getCus_id(),
                    point_system.getSh_id()


            );
            tmList.add(point_systemRepo);

        }
        colTablePontDetail.setItems(tmList);
        Point_SystemRepo selectedItem = colTablePontDetail.getSelectionModel().getSelectedItem();
    }
    private void setCellDataFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("Point_id"));
        colTotalPoint.setCellValueFactory(new PropertyValueFactory<>("Total_point"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("Cus_id"));
        colServiceHistoryId.setCellValueFactory(new PropertyValueFactory<>("SH_id"));

    }
    private List<Point_System> getAllPontSystem() {
        List<Point_System> pointSystemList = null;
        try {
            pointSystemList = Point_SystemRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pointSystemList;
    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }
    private void clearFields() {
        txtId.setText("");
        TxtTotalPoint.setText("");
        cmbCustomerId.setValue("");
        cmbCustomerId.setValue("");
        lblDiscountPoint.setText("");
        txtDiscount.clear();
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String point_system = txtId.getText();

        try {
            boolean isDeleted = Point_SystemRepo.delete(point_system);
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
        String Total_point = TxtTotalPoint.getText();
        String Cus_id = cmbCustomerId.getValue().toString();
        String Service_id = cmbServiceId.getValue().toString();


        Point_System point_system = new Point_System(Item_id, Total_point, Cus_id, Service_id);

        try {
            boolean isSaved = Point_SystemRepo.save(point_system);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Point System saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String Item_id = txtId.getText();
        String Total_point = TxtTotalPoint.getText();
        String Cus_id = cmbCustomerId.getValue().toString();
        String Service_id = cmbServiceId.getValue().toString();


        Point_System point_system = new Point_System(Item_id, Total_point, Cus_id, Service_id);

        try {
            boolean isUpdated = Point_SystemRepo.update(point_system);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Point updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void txtPointIdKeyReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("(P)[0-9].{1,9}$");
        if (!idPattern.matcher(txtId.getText()).matches()) {
            addError(txtId);
            Savebtn.setDisable(true);
        }else{
            removeError(txtId);
            Savebtn.setDisable(false);
        }
    }

    public void txtTotalPointKeyReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[0-9]*$");
        if (!idPattern.matcher(TxtTotalPoint.getText()).matches()) {
            addError(TxtTotalPoint);
            Savebtn.setDisable(true);

        }else{
            removeError(TxtTotalPoint);
            Savebtn.setDisable(false);
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) throws SQLException {
        Customer customer = CustomerRepo.searchByContact(txtContact.getText());
        if (customer!=null){
            Point_System value = Point_SystemRepo.search(customer.getCus_id());
            if (value!=null){
                txtId.setText(value.getPoint_id());
                cmbCustomerId.setValue(value.getCus_id());
                cmbServiceId.setValue(value.getSh_id());
                TxtTotalPoint.setText(value.getTotal_point());

                Point_System search = Point_SystemRepo.search(customer.getCus_id());
                if (search!=null){
                    txtDiscount.setStyle("-fx-background-color: red");
                    lblDiscountPoint.setText("DisCount : point = "+search.getTotal_point());
                }
            }
        }
    }

    public void txtCustomerConactReleased(KeyEvent keyEvent) {
    }
}



