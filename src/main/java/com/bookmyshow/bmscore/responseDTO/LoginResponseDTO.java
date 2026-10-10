package com.bookmyshow.bmscore.responseDTO;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    String message;
    String token;
}
