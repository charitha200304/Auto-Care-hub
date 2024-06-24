package lk.ijse.AutoCare_hub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Service {
    private String serviceId;
    private String cost;
    private String date;
    private String vehicleId;
}
