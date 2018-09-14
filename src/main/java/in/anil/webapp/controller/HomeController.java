package in.anil.webapp.controller;

import in.anil.config.CustomRefreshTokenDetails;
import in.anil.config.RefreshTokenProvider;
import in.anil.webapp.service.HomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;

import static java.util.Arrays.asList;

/**
 *
 * Created by ah00554631 on 6/4/2018.
 */
@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    HomeService homeService;
    @Autowired
    @Qualifier(value = "casRestTemplate")
    private OAuth2RestOperations casRestTemplate;
    @Autowired
    @Qualifier(value = "casRefreshRestTemplates")
    private OAuth2RestOperations casRefreshRestTemplates;
//    @Resource
//    @Qualifier("accessTokenRequest")
//    private AccessTokenRequest accessTokenRequest;
//    @Resource
//    @Qualifier("casAccessTokenRequest")
//    private AccessTokenRequest accessTokenRequest;

    @RequestMapping(value = "/index")
    public String getHome(HttpServletRequest request) {
        request.getSession().setAttribute("redirectUrl", "http://localhost:8085/login");
        request.getSession().setAttribute("a", "a");
        logger.debug("====home====");
        System.out.println(homeService.getMessage());
        System.out.println(casRefreshRestTemplates.getAccessToken().getValue());
        System.out.println(casRefreshRestTemplates.getAccessToken().getRefreshToken().getValue());
//        System.out.println(casRestTemplate.getAccessToken().getRefreshToken().getValue());
//        System.out.println(casRestTemplate.getAccessToken().getTokenType());
//        System.out.println(casRestTemplate.getAccessToken().getValue());
////        System.out.println(oaut2Template.getAccessToken().getScope());
//        System.out.println(casRestTemplate.getAccessToken().getExpiration());
        return "home";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String uploadFile() {
        logger.debug("====upload====");
        return "fileupload";
    }

    @Bean
    public OAuth2ProtectedResourceDetails cas() {
        AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
        details.setId("CleardocsCAS");
        details.setClientId("spring-security-oauth2-read-client");
        details.setClientSecret("spring-security-oauth2-read-client-password1234");
        details.setAccessTokenUri("http://localhost:8085/oauth/token?grant_type=password&username=reader2&password=reader1234");
        details.setUserAuthorizationUri("http://localhost:8085/oauth/authorize");
        details.setUseCurrentUri(false);
        details.setPreEstablishedRedirectUri("http://localhost:8083");
        return details;
    }

    @Bean
    public OAuth2ProtectedResourceDetails clientAccess() {
        ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
        resourceDetails.setUsername("admin");
        resourceDetails.setPassword("admin1234");
        resourceDetails.setAccessTokenUri("http://localhost:8085/oauth/token");
        resourceDetails.setClientId("spring-security-oauth2-read-client");
        resourceDetails.setClientSecret("spring-security-oauth2-read-client-password1234");
        resourceDetails.setGrantType("password");
        resourceDetails.setScope(asList("read"));

//        DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();
//
//        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails, clientContext);
//        restTemplate.setMessageConverters(asList(new MappingJackson2HttpMessageConverter()));
//
//        final Greeting greeting = restTemplate.getForObject(format("http://localhost:%d/greeting", port), Greeting.class);
//
//        System.out.println(greeting);
        return resourceDetails;
    }

    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
    public OAuth2RestOperations casRestTemplate() {
//        AccessTokenRequest atr = new DefaultAccessTokenRequest();
        DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();
        OAuth2RestTemplate template = new OAuth2RestTemplate(clientAccess(), clientContext);
//        AccessTokenProviderChain provider = new AccessTokenProviderChain(Collections.singletonList(new AuthorizationCodeAccessTokenProvider()));
//        AccessTokenProviderChain provider = new AccessTokenProviderChain(Collections.singletonList(new AuthorizationCodeAccessTokenProvider()));
        AccessTokenProvider provider = new AccessTokenProviderChain(
                Arrays.<AccessTokenProvider> asList(
                        new AuthorizationCodeAccessTokenProvider(),
                        new ImplicitAccessTokenProvider(),
                        new ResourceOwnerPasswordAccessTokenProvider(),
                        new ClientCredentialsAccessTokenProvider())
        );

        template.setAccessTokenProvider(provider);
        return template;
    }

    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
    public OAuth2RestOperations casRefreshRestTemplate() {
//        AccessTokenRequest atr = new DefaultAccessTokenRequest();
        DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();
        OAuth2RestTemplate template = new OAuth2RestTemplate(clientAccess(), clientContext);
//        AccessTokenProviderChain provider = new AccessTokenProviderChain(Collections.singletonList(new AuthorizationCodeAccessTokenProvider()));
//        AccessTokenProviderChain provider = new AccessTokenProviderChain(Collections.singletonList(new AuthorizationCodeAccessTokenProvider()));
        AccessTokenProvider provider = new AccessTokenProviderChain(
                Arrays.<AccessTokenProvider> asList(
                        new AuthorizationCodeAccessTokenProvider(),
                        new ImplicitAccessTokenProvider(),
                        new ResourceOwnerPasswordAccessTokenProvider(),
                        new ClientCredentialsAccessTokenProvider())
        );

        template.setAccessTokenProvider(provider);
        return template;
    }

    @Bean
    public OAuth2ProtectedResourceDetails casClientAccess() {
        CustomRefreshTokenDetails resourceDetails = new CustomRefreshTokenDetails();
        resourceDetails.setAccessTokenUri("https://api.staging.bgl360.com.au/oauth/token");
        resourceDetails.setCode("CleardocsCAS");
        resourceDetails.setClientId("43479996-03d7-45dd-8e69-84c9579db7c7");
        resourceDetails.setClientSecret("ec4b6afb-7e5f-4103-86ad-50babcdee8a1");
        resourceDetails.setScope(Collections.singletonList("cas_incorporator"));
        resourceDetails.setPreEstablishedRedirectUri("http://localhost:8080");
        resourceDetails.setRefreshToken("060bf4a0-279c-4eb3-ae53-747c380f3382");
        return resourceDetails;
    }



    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
    public OAuth2RestOperations casRefreshRestTemplates() {
        DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();
        OAuth2RestTemplate template = new OAuth2RestTemplate(casClientAccess(), clientContext);
        AccessTokenProvider provider = new AccessTokenProviderChain(
                Arrays.<AccessTokenProvider> asList(
                        new AuthorizationCodeAccessTokenProvider(),
//                        new ImplicitAccessTokenProvider(),
//                        new ResourceOwnerPasswordAccessTokenProvider(),
//                        new ClientCredentialsAccessTokenProvider(),
                        new RefreshTokenProvider())
        );

        template.setAccessTokenProvider(provider);
        return template;
    }

//    @Bean
//    public DefaultOAuth2RefreshToken casRefreshTokens(){
//        DefaultOAuth2RefreshToken refreshToken=new DefaultOAuth2RefreshToken("060bf4a0-279c-4eb3-ae53-747c380f3382");
//        return refreshToken;
//    }

}
