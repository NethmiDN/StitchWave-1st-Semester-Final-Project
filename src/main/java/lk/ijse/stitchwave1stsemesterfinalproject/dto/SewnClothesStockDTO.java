package lk.ijse.stitchwave1stsemesterfinalproject.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class SewnClothesStockDTO {
    private String stock_id;
    private Integer qty;
    private String fabric_id;
}
