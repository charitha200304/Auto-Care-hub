package lk.ijse.AutoCare_hub.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class EmployeeAttendance {
    private String Attendance_id;
    private String Employee_id;
    private String Date;
    private String In_time;
    private String Out_time;
}
