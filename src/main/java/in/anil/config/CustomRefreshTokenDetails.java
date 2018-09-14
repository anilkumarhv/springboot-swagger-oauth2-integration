package in.anil.config;

import org.springframework.security.oauth2.client.token.grant.redirect.AbstractRedirectResourceDetails;

/**
 * class CustomRefreshTokenDetails
 * Created by Anil on 9/6/2018.
 */
public class CustomRefreshTokenDetails extends AbstractRedirectResourceDetails {
    private String code;
    private String refreshToken;

    public CustomRefreshTokenDetails() {
        setGrantType("refresh_token");
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
