package in.anil.config;

import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.OAuth2AccessTokenSupport;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Iterator;
import java.util.List;

/**
 * class RefreshTokenProvider
 * Created by Anil on 9/6/2018.
 */
public class RefreshTokenProvider extends OAuth2AccessTokenSupport implements AccessTokenProvider {

    public boolean supportsResource(OAuth2ProtectedResourceDetails resource) {
        return resource instanceof CustomRefreshTokenDetails && "refresh_token".equals(resource.getGrantType());
    }

    public boolean supportsRefresh(OAuth2ProtectedResourceDetails resource) {
        return supportsResource(resource);
    }

    public OAuth2AccessToken refreshAccessToken(OAuth2ProtectedResourceDetails resource,
                                                OAuth2RefreshToken refreshToken, AccessTokenRequest request) throws UserRedirectRequiredException,
            OAuth2AccessDeniedException {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "refresh_token");
        form.add("refresh_token", refreshToken.getValue());
        return retrieveToken(request, resource, form, new HttpHeaders());
    }

    public OAuth2AccessToken obtainAccessToken(OAuth2ProtectedResourceDetails details, AccessTokenRequest request)
            throws UserRedirectRequiredException, AccessDeniedException, OAuth2AccessDeniedException {

        CustomRefreshTokenDetails resource = (CustomRefreshTokenDetails) details;
        return retrieveToken(request, resource, getParametersForTokenRequest(resource, request), new HttpHeaders());

    }

    private MultiValueMap<String, String> getParametersForTokenRequest(CustomRefreshTokenDetails resource, AccessTokenRequest request) {

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.set("grant_type", "refresh_token");

        form.set("code", resource.getCode());
        form.set("refresh_token", resource.getRefreshToken());
        form.putAll(request);

        if (resource.isScoped()) {

            StringBuilder builder = new StringBuilder();
            List<String> scope = resource.getScope();

            if (scope != null) {
                Iterator<String> scopeIt = scope.iterator();
                while (scopeIt.hasNext()) {
                    builder.append(scopeIt.next());
                    if (scopeIt.hasNext()) {
                        builder.append(' ');
                    }
                }
            }

            form.set("scope", builder.toString());
        }

        return form;

    }

}
