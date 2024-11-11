package lk.ijse.stitchwave1stsemesterfinalproject.dto.tm;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClothesOrderDetailTM {
    private String stock_id;
    private int stock_qty;
    private String order_id;
    private int order_qty;
}