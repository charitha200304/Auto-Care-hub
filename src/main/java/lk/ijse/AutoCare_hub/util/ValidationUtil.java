package lk.ijse.AutoCare_hub.util;

import javafx.scene.control.TextField;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;


public class ValidationUtil {
    public static Object validation(LinkedHashMap<TextField, Pattern> map2) {
        for (TextField key : map2.keySet()) {
            Pattern pattern = map2.get(key);
            if (pattern.matcher(key.getText()).matches()) {
                removeError(key);
            } else {
                addError(key);
            }
        }
        return null;
    }

    public static void addError(TextField key) {
        key.setStyle("-fx-border-color: #ff0000; -fx-background-radius: 5; -fx-background-radius: 5");

    }

    public static void removeError(TextField key) {
        key.setStyle("-fx-border-color: #02ff02; -fx-background-radius: 5; -fx-background-radius: 5");

    }
}