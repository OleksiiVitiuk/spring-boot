package bookstore.security;

import bookstore.dto.user.UserLoginDto;
import bookstore.dto.user.UserResponseLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserResponseLoginDto authenticate(UserLoginDto userLoginDto) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDto.getEmail(),
                        userLoginDto.getPassword())
        );

        String token = jwtUtil.generateToken(authentication.getName());
        return new UserResponseLoginDto(token);
    }
}
