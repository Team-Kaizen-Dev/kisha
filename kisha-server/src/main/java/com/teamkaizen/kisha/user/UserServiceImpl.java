package com.teamkaizen.kisha.user;

import org.springframework.stereotype.Service;

/**
 * @author Michael Ryan A. Paclibar <michael@satellite.com.ph>
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        final User currentUser = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("User does not exists"));
        currentUser.setAddress(user.getAddress());
        currentUser.setCoordinates(user.getCoordinates());
        currentUser.setContactNumber(user.getContactNumber());
        currentUser.setFullName(user.getFullName());
        return userRepository.save(currentUser);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User does not exist"));
    }
}
