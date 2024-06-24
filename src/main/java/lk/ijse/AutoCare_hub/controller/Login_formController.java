package lk.ijse.AutoCare_hub.controller;

import com.jfoenix.controls.JFXButton;
import com.sun.javafx.css.parser.Token;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import static lk.ijse.AutoCare_hub.util.ValidationUtil.addError;
import static lk.ijse.AutoCare_hub.util.ValidationUtil.removeError;

public class Login_formController {

    @FXML
    private TextField PasswordTxtField;

    @FXML
    private JFXButton SignupButton;

    @FXML
    private TextField UserNameTextField;

    @FXML
    private JFXButton loginButton;

    @FXML
    private AnchorPane rootNode;


    @FXML
    void LogInBtnOnAction(ActionEvent event) throws IOException, SQLException  {
        String userId = UserNameTextField.getText();
        String pw = PasswordTxtField.getText();

        try {
            checkCredential(userId, pw);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "OOPS! something went wrong").show();
        }
    }

    private void checkCredential(String userId, String pw) throws SQLException, IOException {
        String sql = "SELECT User_name, Password FROM User WHERE User_name = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, userId);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String dbPw = resultSet.getString(2);

            if(dbPw.equals(pw)) {
                navigateToTheDashboard();
            } else {
                new Alert(Alert.AlertType.ERROR, "Password is incorrect!").show();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "user id not found!").show();
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


    @FXML
    void SignUpBtnOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/SignUp_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("SingUp Form");
    }

    public void txtUsernameRealesed(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[a-zA-Z 0-9 ]*$");
        if (!idPattern.matcher(UserNameTextField.getText()).matches()) {
            addError(UserNameTextField);
            loginButton.setDisable(true);

        }else{
            removeError(UserNameTextField);
            loginButton.setDisable(false);
        }
    }

    public void txtxPasswoardRelesed(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[a-zA-Z 0-9]{1,}$");
        if (!idPattern.matcher(PasswordTxtField.getText()).matches()) {
            addError(PasswordTxtField);
            loginButton.setDisable(true);

        }else{
            removeError(PasswordTxtField);
            loginButton.setDisable(false);
        }
    }
}
