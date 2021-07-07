package br.com.saveup.saveupbackend.configuration.oauth;


import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import br.com.saveup.saveupbackend.service.UsuarioService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
public class ApplicationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCrypt;
    private final UsuarioService usuarioService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("saveup")
                .secret(bCrypt.encode("saveup-backend"))
                .scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(2000 * 2)
                .refreshTokenValiditySeconds(3600 * 2);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore())
                .accessTokenConverter(accesTokenConverter())
                .reuseRefreshTokens(false)
                .userDetailsService(userDetailsService)
                .authenticationManager(authenticationManager);
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accesTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accesTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter(){
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                User user = (User) authentication.getPrincipal();
                Map<String, Object> aditionalInfo = new HashMap<>();

                usuarioService.repository().findByEmail(user.getUsername())
                        .ifPresent((usuario) -> {
                            aditionalInfo.put("nome", usuario.getNome());
                            aditionalInfo.put("email", usuario.getEmail());
                            aditionalInfo.put("id", usuario.getId());
                        });


                ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(aditionalInfo);
                accessToken = super.enhance(accessToken, authentication);
                return accessToken;
            }
        };
        accessTokenConverter.setSigningKey("saveup");

        return accessTokenConverter;
    }

}