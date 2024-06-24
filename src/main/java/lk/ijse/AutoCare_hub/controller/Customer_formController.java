package lk.ijse.AutoCare_hub.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lk.ijse.AutoCare_hub.model.Customer;
import lk.ijse.AutoCare_hub.model.Point_System;
import lk.ijse.AutoCare_hub.repository.CustomerRepo;
import lk.ijse.AutoCare_hub.repository.Point_SystemRepo;
import lk.ijse.AutoCare_hub.util.ValidationUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.security.cert.PolicyNode;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

import static lk.ijse.AutoCare_hub.util.ValidationUtil.addError;
import static lk.ijse.AutoCare_hub.util.ValidationUtil.removeError;

public class Customer_formController {

    public TextField txtDiscount;
    public Label lblDiscountPoint;
    @FXML
    private JFXButton Clearbtn;

    @FXML
    private JFXButton Deletebtn;

    @FXML
    private JFXButton Savebtn;

    @FXML
    private JFXButton Updatebtn;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<CustomerRepo> tblCustomer;

    @FXML
    private TextField txtCustomerId;
    @FXML
    private TextField txtCusName;


    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtContactNumber;

    private List<Customer> customerList = new ArrayList<>();
    @FXML
    private AnchorPane paneHolder;

    public void initialize() {
        this.customerList = getAllCustomer();
        setCellDataFactory();
        loadAllCustomer();

    }


    private void loadAllCustomer() {
        ObservableList<CustomerRepo> tmList = FXCollections.observableArrayList();
        for (Customer customer : customerList) {
            CustomerRepo customerTm = new CustomerRepo(

                    customer.getCus_id(),
                    customer.getName(),
                    customer.getDate(),
                    customer.getContact_number()


            );
            tmList.add(customerTm);
        }
        tblCustomer.setItems(tmList);
        CustomerRepo selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
    }

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();

    private void setCellDataFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("Cus_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("Contact_number"));
    }

    @FXML
    void txtCustomerIdReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("(C)[0-9].{1,9}$");
        if (!idPattern.matcher(txtCustomerId.getText()).matches()) {
            addError(txtCustomerId);
            Savebtn.setDisable(true);

        }else{
            removeError(txtCustomerId);
            Savebtn.setDisable(false);
        }
        }
        private List<Customer> getAllCustomer () {
            List<Customer> customerList = null;
            try {
                customerList = CustomerRepo.getAll();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return customerList;
        }
        @FXML
        void btnClearOnAction (ActionEvent event){
            clearFields();
        }
        private void clearFields () {
            txtCustomerId.setText("");
            txtCusName.setText("");
            txtDate.getValue();
            txtContactNumber.setText("");
            lblDiscountPoint.setText("");
            txtDiscount.clear();
        }

        @FXML
        void btnDeleteOnAction (ActionEvent event){
            String cus_id = txtCustomerId.getText();

            try {
                boolean isDeleted = CustomerRepo.delete(cus_id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

        @FXML
        void btnSaveOnAction (){
            String Cus_id = txtCustomerId.getText();
            String Name = txtCusName.getText();
            String Date = txtDate.getValue().toString();
            String Contact_number = txtContactNumber.getText();


            Customer customer = new Customer(Cus_id, Name, Date, Contact_number);

            try {
                boolean isSaved = CustomerRepo.save(customer);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer saved!").show();
                    loadAllCustomer();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

        @FXML
        void btnUpdateOnAction (ActionEvent event){
            String Cus_id = txtCustomerId.getText();
            String Name = txtCusName.getText();
            String Date = txtDate.getValue().toString();
            String Contact_number = txtContactNumber.getText();


            Customer customer = new Customer(Cus_id, Name, Date, Contact_number);

            try {
                boolean isUpdated = CustomerRepo.update(customer);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer updated!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

        }


        public void CustomerIdSearchOnAction (ActionEvent actionEvent){
            String customerId = txtCustomerId.getText();

            try {
                Customer customer = CustomerRepo.searchById(customerId);

                if (customer != null) {
                    txtCustomerId.setText(customer.getCus_id());
                    txtCusName.setText(customer.getName());
                    txtDate.getValue().toString();
                    txtContactNumber.setText(customer.getContact_number());
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

    public void txtCustomerNameReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[a-zA-Z ]*$");
        if (!idPattern.matcher(txtCusName.getText()).matches()) {
            addError(txtCusName);
            Savebtn.setDisable(true);

        }else{
            removeError(txtCusName);
            Savebtn.setDisable(false);
        }
    }

    public void txtCustomerConactReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[0-9]{10}$");
        if (!idPattern.matcher(txtContactNumber.getText()).matches()) {
            addError(txtContactNumber);
            Savebtn.setDisable(true);

        }else{
            removeError(txtContactNumber);
            Savebtn.setDisable(false);
        }
    }

    public void PrintBtnOnAction(ActionEvent actionEvent) {
        String date = String.valueOf(txtDate.getValue());
      HashMap hashMap = new HashMap<>();
      hashMap.put("id",txtCustomerId.getText());
      hashMap.put("Name",txtCusName.getText());
      hashMap.put("Date",date);
      hashMap.put("Contactnumber",txtContactNumber.getText());

        try {
            JasperDesign load = JRXmlLoader.load(this.getClass().getResourceAsStream("/report/customerBlank_A4.jrxml"));
            JasperReport jasperReport = JasperCompileManager.compileReport(load);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hashMap, new JREmptyDataSource());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnVehicleOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Vehicle_form.fxml"));
        Pane registePane = (Pane) fxmlLoader.load();
        paneHolder.getChildren().clear();
        paneHolder.getChildren().add(registePane);
    }

    public void btnSearchOnAction(ActionEvent actionEvent) throws SQLException {
        String contact = txtContactNumber.getText();
        Customer customer = CustomerRepo.searchByContact(contact);
        if (customer!=null){
            txtCustomerId.setText(customer.getCus_id());
            txtDate.setValue(LocalDate.parse(customer.getDate()));
            txtCusName.setText(customer.getName());

            Point_System search = Point_SystemRepo.search(customer.getCus_id());
            if (search!=null){
                txtDiscount.setStyle("-fx-background-color: red");
                lblDiscountPoint.setText("DisCount : point = "+search.getTotal_point());
            }
        }
    }
}

