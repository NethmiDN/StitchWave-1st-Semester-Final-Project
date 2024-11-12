package lk.ijse.stitchwave1stsemesterfinalproject.dto.tm;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClothesOrderDetailTM {
    private String order_id;
    private String stock_id;
    private LocalDate date;
    private int qty;
    private String customer_id;
    private String payment_id;
}