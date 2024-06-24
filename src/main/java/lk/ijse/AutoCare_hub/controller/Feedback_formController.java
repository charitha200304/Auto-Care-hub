package lk.ijse.AutoCare_hub.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import lk.ijse.AutoCare_hub.model.Feedback;
import lk.ijse.AutoCare_hub.repository.CustomerRepo;
import lk.ijse.AutoCare_hub.repository.FeedbackRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static lk.ijse.AutoCare_hub.util.ValidationUtil.addError;
import static lk.ijse.AutoCare_hub.util.ValidationUtil.removeError;

public class Feedback_formController {

    @FXML
    private JFXButton Deletebtn;

    @FXML
    private JFXButton Savebtn;

    @FXML
    private JFXButton Updatebtn;

    @FXML
    private JFXButton clearbtn;

    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableView<FeedbackRepo> tblFeedback;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtFeedbackId;
    private List<Feedback> feedbackList = new ArrayList<>();
    public void initialize() {
        this.feedbackList = getAllFeedback();
        setCellDataFactory();
        loadAllFeedback();
        getCustomerId();
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
    private void loadAllFeedback() {
        ObservableList<FeedbackRepo> tmList = FXCollections.observableArrayList();

        for (Feedback feedback : feedbackList) {
            FeedbackRepo feedbackTm = new FeedbackRepo(

                    feedback.getF_id(),
                    feedback.getDescription(),
                    feedback.getCus_id()

            );
            tmList.add(feedbackTm);

        }
        tblFeedback.setItems(tmList);
        FeedbackRepo selectedItem = tblFeedback.getSelectionModel().getSelectedItem();
    }
    private void setCellDataFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("F_id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("Cus_id"));

    }
    private List<Feedback> getAllFeedback() {
        List<Feedback> feedbackList = null;
        try {
            feedbackList = FeedbackRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return feedbackList;
    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }
    private void clearFields() {
        txtFeedbackId.setText("");
        txtDescription.setText("");
        cmbCustomerId.setValue("");

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String f_id = txtFeedbackId.getText();

        try {
            boolean isDeleted = FeedbackRepo.delete(f_id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String f_id = txtFeedbackId.getText();
        String description = txtFeedbackId.getText();
        String cus_id = cmbCustomerId.getValue().toString();


        Feedback feedback = new Feedback(f_id, description, cus_id);

        try {
            boolean isSaved = FeedbackRepo.save(feedback);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Feedback saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String f_id = txtFeedbackId.getText();
        String description = txtDescription.getText();
        String cus_id = cmbCustomerId.getValue().toString();


        Feedback feedback = new Feedback(f_id, description, cus_id);

        try {
            boolean isUpdated = FeedbackRepo.update(feedback);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Feedback updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void txtFeedbackIdKeyReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("(F)[0-9].{1,9}$");
        if (!idPattern.matcher(txtFeedbackId.getText()).matches()) {
            addError(txtFeedbackId);
            Savebtn.setDisable(true);

        }else{
            removeError(txtFeedbackId);
            Savebtn.setDisable(false);
        }
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
}
