package com.teamkaizen.kisha.user;

import com.teamkaizen.kisha.KishaApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Michael Ryan A. Paclibar <michael@satellite.com.ph>
 */
class UserServiceTest extends KishaApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    public void registerUserTest() {

        final User user = new User();
        final String username = "username_" + UUID.randomUUID().toString();
        final String password = "password_" + UUID.randomUUID().toString();
        final String fullName = "fullname_" + UUID.randomUUID().toString();
        final String contactNumber = "contactNumber_" + UUID.randomUUID().toString();
        final String address = "address_" + UUID.randomUUID().toString();

        user.setUsername(username);
        user.setPassword(password);
        user.setFullName(fullName);
        user.setContactNumber(contactNumber);
        user.setAddress(address);

        user.setCoordinates(new double[]{122.561750, 10.719750});
        final User saved = userService.register(user);
        assertNotNull(saved);
        assertTrue(saved.getId() > 0);
        assertEquals(username, saved.getUsername());
        assertEquals(password, saved.getPassword());
        assertEquals(fullName, saved.getFullName());
        assertEquals(contactNumber, saved.getContactNumber());
        assertEquals(address, saved.getAddress());

    }

    @Test
    public void updateUserTest() {
        final User user = new User();
        final String username = "username_" + UUID.randomUUID().toString();
        final String password = "password_" + UUID.randomUUID().toString();
        final String fullName = "fullname_" + UUID.randomUUID().toString();
        final String contactNumber = "contactNumber_" + UUID.randomUUID().toString();
        final String address = "address_" + UUID.randomUUID().toString();

        user.setUsername(username);
        user.setPassword(password);
        user.setFullName(fullName);
        user.setContactNumber(contactNumber);
        user.setAddress(address);

        final User newUser = userService.register(user);
        newUser.setFullName("updated");
        final User updatedUser = userService.update(newUser);
        assertNotNull(updatedUser);
        assertEquals("updated", updatedUser.getFullName());

    }

    @Test
    public void findByUserNameTest() {

        final User user = new User();
        final String username = "username_" + UUID.randomUUID().toString();
        final String password = "password_" + UUID.randomUUID().toString();
        final String fullName = "fullname_" + UUID.randomUUID().toString();
        final String contactNumber = "contactNumber_" + UUID.randomUUID().toString();
        final String address = "address_" + UUID.randomUUID().toString();

        user.setUsername(username);
        user.setPassword(password);
        user.setFullName(fullName);
        user.setContactNumber(contactNumber);
        user.setAddress(address);

        final User newUser = userService.register(user);

        final User userByUserName = userService.findByUsername(newUser.getUsername());
        assertNotNull(userByUserName);

    }
}