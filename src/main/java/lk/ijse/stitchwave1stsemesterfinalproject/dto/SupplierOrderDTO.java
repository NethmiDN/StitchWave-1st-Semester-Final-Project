package lk.ijse.stitchwave1stsemesterfinalproject.dto;

import lombok.*;

import java.time.LocalDate;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class SupplierOrderDTO {
    private String order_id;
    private double qty_kg;
    private LocalDate date;
    private String supplier_id;
}
