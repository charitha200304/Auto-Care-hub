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
import lk.ijse.AutoCare_hub.model.Payment;
import lk.ijse.AutoCare_hub.repository.CustomerRepo;
import lk.ijse.AutoCare_hub.repository.PaymentRepo;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import static java.lang.String.valueOf;
import static lk.ijse.AutoCare_hub.util.ValidationUtil.addError;
import static lk.ijse.AutoCare_hub.util.ValidationUtil.removeError;

public class Payment_formController {

    @FXML
    public JFXButton btnPrint;
    @FXML
    private JFXButton Clearbtn;

    @FXML
    private JFXButton Deletebtn;

    @FXML
    private JFXButton Savebtn;

    @FXML
    private JFXButton Updatebtn;

    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private JFXComboBox<String> cmbPaymentMethod;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colPaymentMethod;

    @FXML
    private TableView<PaymentRepo> tblPayment;

    @FXML
    private TextField txtAmount;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtPaymentId;
    private List<Payment> paymentList = new ArrayList<>();

    public void initialize() {
        this.paymentList = getAllPayment();
        setCellDataFactory();
        loadAllPayment();
        getCustomerId();
        setPaymentMethod();
    }

    private void setPaymentMethod() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        obList.add("Cash");
        obList.add("Card");
        cmbPaymentMethod.setItems(obList);
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
    private void loadAllPayment() {
        ObservableList<PaymentRepo> tmList = FXCollections.observableArrayList();

        for (Payment payment : paymentList) {
            PaymentRepo paymentTm = new PaymentRepo(

                    payment.getPayment_id(),
                    payment.getAmount(),
                    payment.getDate(),
                    payment.getPayment_methods(),
                    payment.getCus_id());
            tmList.add(paymentTm);

        }
        tblPayment.setItems(tmList);
        PaymentRepo selectedItem = tblPayment.getSelectionModel().getSelectedItem();
    }

    private void setCellDataFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("Payment_id"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("Payment_methods"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("Cus_id"));

    }

    private List<Payment> getAllPayment() {
        List<Payment> paymentList = null;
        try {
            paymentList = PaymentRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return paymentList;
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtPaymentId.setText("");
        txtAmount.setText("");
        txtDate.getValue();
        cmbPaymentMethod.setValue("");
        cmbCustomerId.setValue("");

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String payment_id = txtPaymentId.getText();

        try {
            boolean isDeleted = PaymentRepo.delete(payment_id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String Payment_id = txtPaymentId.getText();
        String Amount = txtAmount.getText();
        String Date = txtDate.getValue().toString();
        String Payment_methods = cmbPaymentMethod.getValue().toString();
        String Cus_id = cmbCustomerId.getValue().toString();


        Payment payment = new Payment(Payment_id, Amount, Date, Payment_methods, Cus_id);

        try {
            boolean isSaved = PaymentRepo.save(payment);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String Payment_id = txtPaymentId.getText();
        String Amount = txtAmount.getText();
        String Date = txtDate.getValue().toString();
        String Payment_methods = cmbPaymentMethod.getValue().toString();
        String Cus_id = cmbCustomerId.getValue().toString();


        Payment payment = new Payment(Payment_id, Amount, Date, Payment_methods, Cus_id);

        try {
            boolean isUpdated = PaymentRepo.update(payment);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void txtPaymentIdKeyReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("(P)[0-9].{0,9}$");
        if (!idPattern.matcher(txtPaymentId.getText()).matches()) {
            addError(txtPaymentId);
            Savebtn.setDisable(true);

        }else{
            removeError(txtPaymentId);
            Savebtn.setDisable(false);
        }
    }

    public void PaymentAmountKeyReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[0-9].{1,10}$");
        if (!idPattern.matcher(txtAmount.getText()).matches()) {
            addError(txtAmount);
            Savebtn.setDisable(true);

        }else{
            removeError(txtAmount);
            Savebtn.setDisable(false);
        }
    }

    public void PrintBtnOnAction(ActionEvent actionEvent) {
        String date = valueOf(txtDate.getValue());
        HashMap hashMap = new HashMap<>();
        hashMap.put("PaymentId",txtPaymentId.getText());
        hashMap.put("Amount",txtAmount.getText());
        hashMap.put("Date",date);
        hashMap.put("PaymentMethod",cmbPaymentMethod.getValue());
        hashMap.put("CustomerId",cmbCustomerId.getValue());

        try {
            JasperDesign load = JRXmlLoader.load(this.getClass().getResourceAsStream("/report/PaymentBlank_A4.jrxml"));
            JasperReport jasperReport = JasperCompileManager.compileReport(load);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hashMap, new JREmptyDataSource());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }
    }

