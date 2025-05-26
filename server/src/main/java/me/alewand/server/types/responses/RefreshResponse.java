package me.alewand.server.types.responses;

import lombok.Getter;

@Getter
public class RefreshResponse extends CommonResponse {

    String accessToken;

    public RefreshResponse(String message, String accessToken) {
        super(message);
        this.accessToken = accessToken;
    }

}
