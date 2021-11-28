package fire.overtime.security;

import fire.overtime.models.Firefighter;
import fire.overtime.repositories.FirefighterRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {
    final private FirefighterRepository firefighterRepository;

    public CustomAuthenticationProvider(FirefighterRepository firefighterRepository) {
        this.firefighterRepository = firefighterRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        Base64.Encoder encoder = Base64.getEncoder();
        String encodeLogin = encoder.encodeToString(authentication.getName().getBytes());
        String encodePassword = encoder.encodeToString(authentication.getCredentials().toString().getBytes());
        Firefighter byLoginAndPassword = firefighterRepository.findByLoginAndPassword(encodeLogin, encodePassword);
        if (byLoginAndPassword == null) {
            throw new AccessDeniedException("user not identified");
        }

        return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials().toString());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}