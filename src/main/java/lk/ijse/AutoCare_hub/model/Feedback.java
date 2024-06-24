package lk.ijse.AutoCare_hub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Feedback {
    private String F_id;
    private String Description;
    private String Cus_id;
}
