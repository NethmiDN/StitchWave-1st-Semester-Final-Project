package lk.ijse.stitchwave1stsemesterfinalproject.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class FabricTM {
    private String fabric_id;
    private String color;
    private Double weight_kg;
    private Double width_inch;
}
