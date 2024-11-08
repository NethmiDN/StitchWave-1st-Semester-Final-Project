package lk.ijse.stitchwave1stsemesterfinalproject.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class OrdersDTO {
    private String order_id;
    private LocalDate date;
    private Integer qty;
    private String customer_id;
    public String payment_id;
}
