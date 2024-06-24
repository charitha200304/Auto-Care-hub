package lk.ijse.AutoCare_hub.controller;

import com.jfoenix.controls.JFXButton;
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
import lk.ijse.AutoCare_hub.model.Customer;
import lk.ijse.AutoCare_hub.model.Employee;
import lk.ijse.AutoCare_hub.model.Vehicle;
import lk.ijse.AutoCare_hub.repository.CustomerRepo;
import lk.ijse.AutoCare_hub.repository.EmployeeRepo;
import lk.ijse.AutoCare_hub.repository.VehicleRepo;

import java.io.IOException;
import java.security.cert.PolicyNode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static lk.ijse.AutoCare_hub.util.ValidationUtil.addError;
import static lk.ijse.AutoCare_hub.util.ValidationUtil.removeError;

public class Employee_formController{

    @FXML
    private JFXButton Clearbtn;

    @FXML
    private JFXButton Deletebtn;

    @FXML
    private JFXButton EmployeeAttendanceBtn;

    @FXML
    private JFXButton Savebtn;

    @FXML
    private JFXButton Updatebtn;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContactNumber;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<EmployeeRepo> tblEmployee;

    @FXML
    private TextField txtAdress;

    @FXML
    private TextField txtContactNumber;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtName;
    private List<Employee> employeeList = new ArrayList<>();


    @FXML
    private AnchorPane paneHolder;

    public void initialize() {
        this.employeeList = getAllEmployee();
        setCellDataFactory();
        loadAllEmployee();
    }


    private void loadAllEmployee() {
        ObservableList<EmployeeRepo> tmList = FXCollections.observableArrayList();

        for (Employee employee : employeeList) {
            EmployeeRepo employeeTm = new EmployeeRepo(

                    employee.getEmployee_id(),
                    employee.getName(),
                    employee.getAddress(),
                    employee.getContact_number()


            );
            tmList.add(employeeTm);

        }
        tblEmployee.setItems(tmList);
        EmployeeRepo selectedItem = tblEmployee.getSelectionModel().getSelectedItem();
    }
    private void setCellDataFactory() {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("Employee_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("Contact_number"));

    }
    private List<Employee> getAllEmployee() {
        List<Employee> employeeList = null;
        try {
            employeeList = EmployeeRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeList;
    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }
    private void clearFields() {
        txtEmployeeId.setText("");
        txtName.setText("");
        txtAdress.setText("");
        txtContactNumber.setText("");

    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String employee_id = txtEmployeeId.getText();

        try {
            boolean isDeleted = EmployeeRepo.delete(employee_id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnEmployeeAttendanceOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Employee_Attendance_form.fxml"));
        Pane registePane = (Pane) fxmlLoader.load();
        paneHolder.getChildren().clear();
        paneHolder.getChildren().add(registePane);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String Employee_id = txtEmployeeId.getText();
        String Name = txtName.getText();
        String Address = txtAdress.getText();
        String Contact_number = txtContactNumber.getText();


        Employee employee = new Employee(Employee_id, Name, Address, Contact_number);

        try {
            boolean isSaved = EmployeeRepo.save(employee);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String Employee_id = txtEmployeeId.getText();
        String Name = txtName.getText();
        String Address = txtAdress.getText();
        String Contact_number = txtContactNumber.getText();


        Employee employee = new Employee(Employee_id, Name, Address, Contact_number);

        try {
            boolean isUpdated = EmployeeRepo.update(employee);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void txtEmployeeIdKeyReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("(E)[0-9].{1,9}$");
        if (!idPattern.matcher(txtEmployeeId.getText()).matches()) {
            addError(txtEmployeeId);
            Savebtn.setDisable(true);

        }else{
            removeError(txtEmployeeId);
            Savebtn.setDisable(false);
        }
    }

    public void txtEmployeeNameKeyReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[a-zA-Z ]*$");
        if (!idPattern.matcher(txtName.getText()).matches()) {
            addError(txtName);
            Savebtn.setDisable(true);

        }else{
            removeError(txtName);
            Savebtn.setDisable(false);
        }
    }


    public void txtContactNumberKeyReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[0-9]{10}$");
        if (!idPattern.matcher(txtContactNumber.getText()).matches()) {
            addError(txtContactNumber);
            Savebtn.setDisable(true);

        }else{
            removeError(txtContactNumber);
            Savebtn.setDisable(false);
        }
    }


    public void txtAddressKeyReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[a-zA-Z 0-9 ]*$");
        if (!idPattern.matcher(txtAdress.getText()).matches()) {
            addError(txtAdress);
            Savebtn.setDisable(true);

        }else{
            removeError(txtAdress);
            Savebtn.setDisable(false);
        }
    }
}
