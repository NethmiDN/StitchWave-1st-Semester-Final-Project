package lk.ijse.stitchwave1stsemesterfinalproject.dto.tm;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrdersTM {
    private String order_id;
    private LocalDate date;
    private Integer qty;
    private String customer_id;
    public String payment_id;
}
