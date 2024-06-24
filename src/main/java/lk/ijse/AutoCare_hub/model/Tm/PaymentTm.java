package lk.ijse.AutoCare_hub.model.Tm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentTm {
    private String Payment_id;
    private String Amount;
    private String Date;
    private String Payment_methods;
    private String Cus_id;
}
