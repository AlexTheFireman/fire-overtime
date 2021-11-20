package fire.overtime.security;

import fire.overtime.models.Firefighter;
import fire.overtime.repositories.FirefighterRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
class UserDetailsServiceImpl implements UserDetailsService {

    final
    private FirefighterRepository firefighterRepository;

    public UserDetailsServiceImpl(FirefighterRepository firefighterRepository) {
        this.firefighterRepository = firefighterRepository;
    }

    @Override
    public User loadUserByUsername(String login) throws UsernameNotFoundException {
        Firefighter user = firefighterRepository.findByLogin(login);
        if(user == null) throw new AccessDeniedException("access denied");

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        return new User(user.getLogin(), user.getLogin(), grantedAuthorities);
    }

}