package lk.ijse.AutoCare_hub.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.AutoCare_hub.db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

import static lk.ijse.AutoCare_hub.util.ValidationUtil.addError;
import static lk.ijse.AutoCare_hub.util.ValidationUtil.removeError;

public class SignUp_formController {
    @FXML
    public JFXButton signUpBtn;
    @FXML
    private JFXButton LogInBtn;

    @FXML
    private TextField TxtEmail;

    @FXML
    private TextField TxtFullName;

    @FXML
    private TextField TxtUserName;

    @FXML
    private TextField TxtPassword;

    @FXML
    private TextField TxtPhoneNumber;

    @FXML
    private AnchorPane rootNode;

    @FXML
    void LogInBtnOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/Login_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Form");
    }

    @FXML
    void SignUpBtnOnAction(ActionEvent event) {

        String Full_name = TxtFullName.getText();
        String User_name = TxtUserName.getText();
        String Email = TxtEmail.getText();
        String Phone_number = TxtPhoneNumber.getText();
        String Password = TxtPassword.getText();

        saveUser(Full_name, User_name, Email,Phone_number,Password);

    }

    private void saveUser(String Full_name, String User_name, String Email, String Phone_number, String Password) {
        try {
            String sql = "INSERT INTO user VALUES(?, ?, ?,?,?)";

            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setObject(1, Full_name);
            pstm.setObject(2, User_name);
            pstm.setObject(3, Email);
            pstm.setObject(4, Phone_number);
            pstm.setObject(5, Password);
            if(pstm.executeUpdate() == 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "user saved!").show();
                navigateToTheDashboard();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happend!").show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void txtEmailKeyReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[0-9 @ . a-z A-Z]*$");
        if (!idPattern.matcher(TxtEmail.getText()).matches()) {
            addError(TxtEmail);
            signUpBtn.setDisable(true);

        }else{
            removeError(TxtEmail);
            signUpBtn.setDisable(false);
        }
    }

    public void txtPhoneNumberKeyReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[0-9]*$");
        if (!idPattern.matcher(TxtPhoneNumber.getText()).matches()) {
            addError(TxtPhoneNumber);
            signUpBtn.setDisable(true);

        }else{
            removeError(TxtPhoneNumber);
            signUpBtn.setDisable(false);
        }
    }

    public void txtPasswordKeyReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[0-9 a-z A-Z]*$");
        if (!idPattern.matcher(TxtPassword.getText()).matches()) {
            addError(TxtPassword);
            signUpBtn.setDisable(true);

        }else{
            removeError(TxtPassword);
            signUpBtn.setDisable(false);
        }
    }

    public void txtFullNameKeyReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[a-z A-Z]*$");
        if (!idPattern.matcher(TxtFullName.getText()).matches()) {
            addError(TxtFullName);
            signUpBtn.setDisable(true);

        }else{
            removeError(TxtFullName);
            signUpBtn.setDisable(false);
        }
    }

    public void txtUserNameKeyReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[0-9 a-z A-Z]*$");
        if (!idPattern.matcher(TxtUserName.getText()).matches()) {
            addError(TxtUserName);
            signUpBtn.setDisable(true);
        }else{
            removeError(TxtUserName);
            signUpBtn.setDisable(false);
        }
    }
    private void navigateToTheDashboard() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/DashboardMain_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("DashboardMain Form");

    }
}
