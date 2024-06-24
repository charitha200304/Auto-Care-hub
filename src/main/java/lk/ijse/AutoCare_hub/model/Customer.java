package lk.ijse.AutoCare_hub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Customer {
    private String Cus_id;
    private String Name;
    private String Date;
    private String Contact_number;
}
