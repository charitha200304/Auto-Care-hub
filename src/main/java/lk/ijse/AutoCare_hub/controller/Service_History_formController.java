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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lk.ijse.AutoCare_hub.model.Service_History;
import lk.ijse.AutoCare_hub.model.Tm.Service_HistoryTm;
import lk.ijse.AutoCare_hub.model.Vehicle;
import lk.ijse.AutoCare_hub.repository.Service_HistoryRepo;
import lk.ijse.AutoCare_hub.repository.VehicleRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static lk.ijse.AutoCare_hub.util.ValidationUtil.addError;
import static lk.ijse.AutoCare_hub.util.ValidationUtil.removeError;

public class Service_History_formController {

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
    private Label VehicleIdlbl;
    @FXML
    private JFXComboBox<String> cmbVehicleId;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colVehicleId;

    @FXML
    private AnchorPane paneHolder;

    @FXML
    private TableView<Service_HistoryTm> tblServiceHistory;
    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtId;
    private List<Service_History> service_historyList = new ArrayList<>();
    public void initialize() throws SQLException {
        this.service_historyList = getAllServiceHistory();
        setCellDataFactory();
        loadAllServiceHistory();
        getVehicleId();
    }

    private void getVehicleId() throws SQLException {
        ObservableList<String> obList = FXCollections.observableArrayList();

        List<String> IdList = VehicleRepo.getIds();

        for (String id : IdList) {
            obList.add(id);
        }
        cmbVehicleId.setItems(obList);


    }

    private void loadAllServiceHistory() {
        ObservableList<Service_HistoryTm> tmList = FXCollections.observableArrayList();

        for (Service_History service_history :service_historyList) {
            Service_HistoryTm service_historyTm = new Service_HistoryTm(

                    service_history.getSH_id(),
                    service_history.getDescription(),
                    service_history.getV_id()


            );
            tmList.add(service_historyTm);

        }

        tblServiceHistory.setItems(tmList);
        Service_HistoryTm selectedItem = tblServiceHistory.getSelectionModel().getSelectedItem();
    }

    private List<Service_History> getAllServiceHistory() {
        List<Service_History> service_histories = null;
        try {
            service_histories = Service_HistoryRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return service_histories;
    }

    private void setCellDataFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("SH_id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("V_id"));
    }

    @FXML
    void BackBtnOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Service_form.fxml"));
        Pane registePane = (Pane) fxmlLoader.load();
        paneHolder.getChildren().clear();
        paneHolder.getChildren().add(registePane);
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }
    private void clearFields() {
        txtId.setText("");
        txtDescription.setText("");
        cmbVehicleId.getValue();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String SH_id = txtId.getText();

        try {
            boolean isDeleted = Service_HistoryRepo.delete(SH_id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String SH_id = txtId.getText();
        String Description = txtDescription.getText();
        String V_id = cmbVehicleId.getValue().toString();


        Service_History service_history = new Service_History(SH_id, Description, V_id);

        try {
            boolean isSaved = Service_HistoryRepo.save(service_history);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Service History saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String SH_id = txtId.getText();
        String Description = txtDescription.getText();
        String V_id = cmbVehicleId.getValue().toString();


        Service_History service_history = new Service_History(SH_id, Description, V_id);

        try {
            boolean isUpdated = Service_HistoryRepo.update(service_history);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "servce history updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void cmbVehicleIdOnAction(ActionEvent event) {
        String id = cmbVehicleId.getValue();
        try {
            Vehicle vehicle = VehicleRepo.searchById(id);
            if (vehicle != null) {
                VehicleIdlbl.setText(vehicle.getV_id());

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);}
    }



    public void txtDescriptionKeyReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[a-zA-Z 0-9]*$");
        if (!idPattern.matcher(txtDescription.getText()).matches()) {
            addError(txtDescription);
            Savebtn.setDisable(true);

        }else{
            removeError(txtDescription);
            Savebtn.setDisable(false);
        }
    }


    public void txtServiceHistoryIdKeyReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("(SH)[0-9].{1,9}$");
        if (!idPattern.matcher(txtId.getText()).matches()) {
            addError(txtId);
            Savebtn.setDisable(true);

        }else{
            removeError(txtId);
            Savebtn.setDisable(false);
        }
    }
}

