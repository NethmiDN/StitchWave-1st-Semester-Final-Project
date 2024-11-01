package lk.ijse.stitchwave1stsemesterfinalproject.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class FabricDTO {
    private String fabric_id;
    private String color;
    private Double weight_kg;
    private Double width_inch;
}
