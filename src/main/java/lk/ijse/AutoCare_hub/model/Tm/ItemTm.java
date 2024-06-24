package lk.ijse.AutoCare_hub.model.Tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ItemTm {
    private String itemId;
    private String name;
    private String qty;
    private String total;
}
