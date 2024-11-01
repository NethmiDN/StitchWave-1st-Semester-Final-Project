package lk.ijse.stitchwave1stsemesterfinalproject.dto.tm;

import lombok.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class PaymentTM {
    private String payment_id;
    private Double amount;
    private LocalDate date;
}
