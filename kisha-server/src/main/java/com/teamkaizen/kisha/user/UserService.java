package com.teamkaizen.kisha.user;

/**
 * @author Michael Ryan A. Paclibar <michael@satellite.com.ph>
 */
public interface UserService {
    User register(User user);
    User update(User user);
    User findByUsername(String username);
}
