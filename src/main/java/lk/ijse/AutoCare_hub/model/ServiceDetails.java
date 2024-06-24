package lk.ijse.AutoCare_hub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ServiceDetails {
    private String serviceId;
    private String itemId;
    private String price;
    private String qty;
}
