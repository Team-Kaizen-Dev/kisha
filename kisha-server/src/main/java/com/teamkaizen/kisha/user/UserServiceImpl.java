package com.teamkaizen.kisha.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Michael Ryan A. Paclibar <michael@satellite.com.ph>
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        final User currentUser = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("User does not exists"));
        currentUser.setAddress(user.getAddress());
        currentUser.setLat(user.getLat());
        currentUser.setLng(user.getLng());
        currentUser.setContactNumber(user.getContactNumber());
        currentUser.setFullName(user.getFullName());
        return userRepository.save(currentUser);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User does not exist"));
    }

    @Override
    public User login(String username, String password) {
        final User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("Invalid username/password"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid username/password");
        }
        return user;
    }
}
