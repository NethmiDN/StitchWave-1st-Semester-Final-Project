package lk.ijse.stitchwave1stsemesterfinalproject.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@ToString

public class UserTM {
    private String userId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
}
