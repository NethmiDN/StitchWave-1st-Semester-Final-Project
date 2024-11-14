package lk.ijse.stitchwave1stsemesterfinalproject.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClothesOrderDetailDTO {
    private String stock_Id;
    private String order_Id;
}