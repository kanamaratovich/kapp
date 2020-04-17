package kz.frosys.kapp.dto;

import lombok.Data;

@Data
public class AuthenticationResponseDto {
    private String token;
    private String username;
}
