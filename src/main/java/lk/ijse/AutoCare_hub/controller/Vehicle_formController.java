package lk.ijse.AutoCare_hub.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lk.ijse.AutoCare_hub.model.Payment;
import lk.ijse.AutoCare_hub.model.Vehicle;
import lk.ijse.AutoCare_hub.repository.CustomerRepo;
import lk.ijse.AutoCare_hub.repository.PaymentRepo;
import lk.ijse.AutoCare_hub.repository.VehicleRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static lk.ijse.AutoCare_hub.util.ValidationUtil.addError;
import static lk.ijse.AutoCare_hub.util.ValidationUtil.removeError;

public class Vehicle_formController {

    @FXML
    private JFXButton Clearbtn;

    @FXML
    private JFXButton Deletebtn;

    @FXML
    private JFXButton Savebtn;

    @FXML
    private JFXButton Updatebtn;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private JFXComboBox<String> cmbType;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colModel;

    @FXML
    private AnchorPane paneHolder;

    @FXML
    private TableView<VehicleRepo> tblVehicle;

    @FXML
    private TextField txtModel;

    @FXML
    private TextField txtVehicleId;
    private List<Vehicle> vehicleList = new ArrayList<>();

    public void initialize() {
        this.vehicleList = getAllvehicle();
        loadAllVehicle();
        setVehicleType();
        getCustomerId();
        setCellDataFactory();

    }
    private void loadAllVehicle() {
        ObservableList<VehicleRepo> tmList = FXCollections.observableArrayList();

        for (Vehicle vehicle : vehicleList) {
            VehicleRepo vehicleRepo = new VehicleRepo(

                    vehicle.getV_id(),
                    vehicle.getModel(),
                    vehicle.getType(),
                    vehicle.getCus_id());
            tmList.add(vehicleRepo);

        }
        tblVehicle.setItems(tmList);
        VehicleRepo selectedItem = tblVehicle.getSelectionModel().getSelectedItem();
    }

    private void setCellDataFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("V_id"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("Model"));
        colType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("Cus_id"));

    }

    private List<Vehicle> getAllvehicle() {
        List<Vehicle> vehicleList = null;
        try {
            vehicleList = VehicleRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vehicleList;
    }
    private void setVehicleType() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        obList.add("Car");
        obList.add("Bus");
        obList.add("Lorry");
        obList.add("Van");
        cmbType.setItems(obList);
    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }
    private void clearFields() {
        txtVehicleId.setText("");
        txtModel.setText("");
        cmbType.setValue("");
        cmbCustomerId.setValue("");
    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String V_id = txtVehicleId.getText();

        try {
            boolean isDeleted = VehicleRepo.delete(V_id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        String V_id = txtVehicleId.getText();
        String Model = txtModel.getText();
        String Type = cmbType.getValue().toString();
        String Cus_id = cmbCustomerId.getValue().toString();


        Vehicle vehicle = new Vehicle(V_id, Model, Type,  Cus_id);

        try {
            boolean isSaved = VehicleRepo.save(vehicle);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Vehicle saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String V_id = txtVehicleId.getText();
        String Model = txtModel.getText();
        String Type = cmbType.getValue().toString();
        String Cus_id = cmbCustomerId.getValue().toString();


        Vehicle vehicle = new Vehicle(V_id, Model, Type , Cus_id);

        try {
            boolean isUpdated = VehicleRepo.update(vehicle);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Vehicle updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
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

    public void txtVehicleIdReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("(V)[0-9].{0,9}$");
        if (!idPattern.matcher(txtVehicleId.getText()).matches()) {
            addError(txtVehicleId);
            Savebtn.setDisable(true);

        }else{
            removeError(txtVehicleId);
            Savebtn.setDisable(false);
        }
    }

    public void txtVehicleModelReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[a-zA-Z 0-9 ]*$");
        if (!idPattern.matcher(txtModel.getText()).matches()) {
            addError(txtModel);
            Savebtn.setDisable(true);

        }else{
            removeError(txtModel);
            Savebtn.setDisable(false);
        }
    }

    public void BackBtnOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Customer_form.fxml"));
        Pane registePane = (Pane) fxmlLoader.load();
        paneHolder.getChildren().clear();
        paneHolder.getChildren().add(registePane);
    }
}
