package me.alewand.server.types.responses;

import lombok.Getter;
import me.alewand.server.models.User;

@Getter
public class LoginResponse extends CommonResponse {

    private String accessToken;
    private User user;

    public LoginResponse(String message, String accessToken, User user) {
        super(message);
        this.accessToken = accessToken;
        this.user = user;
    }

}
