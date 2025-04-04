package bookstore.service;

import bookstore.dto.user.UserRegistrationRequestDto;
import bookstore.dto.user.UserResponseDto;
import bookstore.entity.Cart;
import bookstore.entity.Role;
import bookstore.entity.User;
import bookstore.exception.EntityNotFoundException;
import bookstore.exception.RegistrationException;
import bookstore.mapper.UserMapper;
import bookstore.repository.CartRepository;
import bookstore.repository.RoleRepository;
import bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final CartRepository cartRepository;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto) {
        if (userRepository.existsByEmail(userRegistrationRequestDto.getEmail())) {
            throw new RegistrationException(
                    "User already exists with email: " + userRegistrationRequestDto.getEmail()
            );
        }
        User user = userMapper.toModel(userRegistrationRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName(Role.RoleName.ROLE_USER)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Role is not found: " + Role.RoleName.ROLE_USER)
                );
        user.setRoles(Set.of(role));
        user = userRepository.save(user);
        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);
        return userMapper.toDto(userRepository.save(user));
    }
}
