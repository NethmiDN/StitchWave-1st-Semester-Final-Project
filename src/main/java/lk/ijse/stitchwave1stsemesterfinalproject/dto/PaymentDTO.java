package lk.ijse.stitchwave1stsemesterfinalproject.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class PaymentDTO {
    private String payment_id;
    private Double amount;
    private LocalDate date;
}
