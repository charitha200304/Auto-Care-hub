package lk.ijse.AutoCare_hub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private String Employee_id;
    private String Name;
    private String Address;
    private String Contact_number;
}