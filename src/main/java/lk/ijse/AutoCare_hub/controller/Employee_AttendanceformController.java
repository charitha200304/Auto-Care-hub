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
import lk.ijse.AutoCare_hub.model.EmployeeAttendance;
import lk.ijse.AutoCare_hub.repository.EmployeeAttendanceRepo;
import lk.ijse.AutoCare_hub.repository.EmployeeRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static lk.ijse.AutoCare_hub.util.ValidationUtil.addError;
import static lk.ijse.AutoCare_hub.util.ValidationUtil.removeError;

public class Employee_AttendanceformController {

    @FXML
    private JFXButton Clearbtn;

    @FXML
    private JFXButton Deletebtn;

    @FXML
    private JFXButton Savebtn;

    @FXML
    private JFXButton Updatebtn;

    @FXML
    private JFXButton backBtnOnAction;

    @FXML
    private JFXComboBox<String> cmbEmployeeId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colInTime;

    @FXML
    private TableColumn<?, ?> colOutTime;

    @FXML
    private AnchorPane paneHolder;

    @FXML
    private TableView<EmployeeAttendanceRepo> tblEmployeeAttendance;

    @FXML
    private TextField txtAttendanceIId;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtInTime;

    @FXML
    private TextField txtOutTime;
    private List<EmployeeAttendance> employeeAttendanceList = new ArrayList<>();
    public void initialize() {
        this.employeeAttendanceList = getAllEmployeeAttendance();
        setCellDataFactory();
        loadAllEmployeeAttendance();
        getEmployeeId();
    }

    private void getEmployeeId() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> IdList = EmployeeRepo.getIds();

            for (String id : IdList) {
                obList.add(id);
            }
            cmbEmployeeId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void loadAllEmployeeAttendance() {
        ObservableList<EmployeeAttendanceRepo> tmList = FXCollections.observableArrayList();

        for (EmployeeAttendance employeeAttendance : employeeAttendanceList) {
            EmployeeAttendanceRepo employeeAttendanceTm = new EmployeeAttendanceRepo(
                    employeeAttendance.getAttendance_id(),
                    employeeAttendance.getEmployee_id(),
                    employeeAttendance.getDate(),
                    employeeAttendance.getIn_time(),
                    employeeAttendance.getOut_time()


            );
            tmList.add(employeeAttendanceTm);
        }
        tblEmployeeAttendance.setItems(tmList);
        EmployeeAttendanceRepo selectedItem = tblEmployeeAttendance.getSelectionModel().getSelectedItem();
    }
    private void setCellDataFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("Attendance_id"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("Employee_id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colInTime.setCellValueFactory(new PropertyValueFactory<>("In_time"));
        colOutTime.setCellValueFactory(new PropertyValueFactory<>("Out_time"));
    }
    private List<EmployeeAttendance> getAllEmployeeAttendance() {
        List<EmployeeAttendance> employeeAttendanceList = null;
        try {
            employeeAttendanceList = EmployeeAttendanceRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeAttendanceList;
    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }
    private void clearFields() {
        cmbEmployeeId.setValue("");
        txtDate.getValue();
        txtInTime.setText("");
        txtOutTime.setText("");

    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String Employee_id = cmbEmployeeId.getValue().toString();

        try {
            boolean isDeleted = EmployeeAttendanceRepo.delete(Employee_id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String Attendance_id = txtAttendanceIId.getText();
        String Employee_id = cmbEmployeeId.getValue().toString();
        String Date = txtDate.getValue().toString();
        String In_time = txtInTime.getText();
        String Out_time = txtOutTime.getText();


        EmployeeAttendance employeeAttendance = new EmployeeAttendance(Attendance_id,Employee_id, Date, In_time,Out_time);

        try {
            boolean isSaved = EmployeeAttendanceRepo.save(employeeAttendance);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Attendance saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String Attendance_id = txtAttendanceIId.getText();
        String Employee_id = cmbEmployeeId.getValue().toString();
        String Date = txtDate.getValue().toString();
        String In_time = txtInTime.getText();
        String Out_time = txtOutTime.getText();


        EmployeeAttendance employeeAttendance = new EmployeeAttendance(Attendance_id,Employee_id,  Date, In_time,Out_time);

        try {
            boolean isUpdated = EmployeeAttendanceRepo.update(employeeAttendance);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Attendance updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Employee_form.fxml"));
        Pane registePane = (Pane) fxmlLoader.load();
        paneHolder.getChildren().clear();
        paneHolder.getChildren().add(registePane);
    }

    public void txtOutTimeKeyReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[0-9 . A-Z]*$");
        if (!idPattern.matcher(txtOutTime.getText()).matches()) {
            addError(txtOutTime);
            Savebtn.setDisable(true);

        }else{
            removeError(txtOutTime);
            Savebtn.setDisable(false);
        }
    }

    public void txtInTimeKeyReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[0-9 . A-Z]*$");
        if (!idPattern.matcher(txtInTime.getText()).matches()) {
            addError(txtInTime);
            Savebtn.setDisable(true);

        }else{
            removeError(txtInTime);
            Savebtn.setDisable(false);
        }
    }

    public void txtAttendanceIdKeyReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("(A)[0-9].{1,9}$");
        if (!idPattern.matcher(txtAttendanceIId.getText()).matches()) {
            addError(txtAttendanceIId);
            Savebtn.setDisable(true);

        }else{
            removeError(txtAttendanceIId);
            Savebtn.setDisable(false);
        }
    }
}
