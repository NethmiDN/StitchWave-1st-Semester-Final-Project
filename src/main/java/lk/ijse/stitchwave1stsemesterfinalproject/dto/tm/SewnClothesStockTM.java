package lk.ijse.stitchwave1stsemesterfinalproject.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class SewnClothesStockTM {
    private String stock_id;
    private Integer qty;
    private String fabric_id;
}
