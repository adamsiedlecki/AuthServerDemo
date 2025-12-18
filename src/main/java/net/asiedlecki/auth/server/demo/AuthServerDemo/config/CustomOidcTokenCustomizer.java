package net.asiedlecki.auth.server.demo.AuthServerDemo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomOidcTokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {

    private final UsersConfig usersConfig;

    @Override
    public void customize(JwtEncodingContext context) {
        var principalName = context.getPrincipal().getName();
        var testUser = usersConfig.getUsers().stream()
                .filter(u -> u.getUsername().equals(principalName))
                .findFirst()
                .orElse(null);

        if (testUser != null) {
            context.getClaims().claim("email", testUser.getEmail());
            context.getClaims().claim("given_name", testUser.getFirstName());
            context.getClaims().claim("family_name", testUser.getLastName());
        }
    }
}
