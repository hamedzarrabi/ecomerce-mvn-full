package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;


import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateNewUserWithOneRole() {
        Role roleAdmin = entityManager.find(Role.class, 2);
        User userHamed = new User("hamed.zarrabi87@gmail.com", "lightwave", "hamed", "zarrabi");
        userHamed.addRole(roleAdmin);

        User savedUser = userRepository.save(userHamed);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateNewUserWithTwoRole() {
        User userTabasom = new User("tabasom@gmail.com", "taritabiali", "tabasom", "ghobadi");
        Role roleEditor = new Role(3);
        Role roleAssistance = new Role(5);

        userTabasom.addRole(roleEditor);
        userTabasom.addRole(roleAssistance);

        User savedUser = userRepository.save(userTabasom);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers() {
        Iterable<User> users = userRepository.findAll();
        users.forEach(user -> System.out.println(user));
    }

    @Test
    public void testGetUserById() {
        User byId = userRepository.findById(3).get();
        System.out.println(byId);
        assertThat(byId).isNotNull();
    }

    @Test
    public void testUpdateUserDetails() {
        User byId = userRepository.findById(3).get();
        byId.setEnabled(true);
        byId.setEmail("tabasomghobadi@gmail.com");

    }

    @Test
    public void testUpdateUserRoles() {
        User byId = userRepository.findById(3).get();
        Role roleEditor = new Role(3);
        Role roleSalesperson = new Role(2);

        byId.getRoles().remove(roleEditor);
        byId.addRole(roleSalesperson);

        userRepository.save(byId);
    }

    @Test
    public void testDeleteUserById() {
        Integer userId = 3;
        userRepository.deleteById(userId);
    }

    @Test
    public void testGetUserByEmail() {
        String email = "hamed.zarrabi2021@gmail.com";
        User userByEmail = userRepository.getUserByEmail(email);

        assertThat(userByEmail).isNotNull();
    }

    @Test
    public void testCountById() {
        Integer id = 1;
        Long countById = userRepository.countById(id);

        assertThat(countById).isNotNull().isGreaterThan(0);
    }

    @Test
    public void testDisabledUser() {
        Integer id = 7;
        userRepository.updateEnabledStatus(id, false);
    }

    @Test
    public void testEnabledUser() {
        Integer id = 7;
        userRepository.updateEnabledStatus(id, true);
    }

    @Test
    public void testListForFirstPage() {
        int pageNumber = 1;
        int pageSize = 4;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = userRepository.findAll(pageable);

        List<User> listUsers = page.getContent();
        listUsers.forEach(System.out::println);

        assertThat(listUsers.size()).isEqualTo(pageSize);
    }

    @Test
    public void testSearchUsers() {
        String keyword = "bruce";

        int pageNumber = 0;
        int pageSize = 4;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = userRepository.findAll(keyword, pageable);

        List<User> listUsers = page.getContent();
        listUsers.forEach(System.out::println);

        assertThat(listUsers.size()).isGreaterThan(0);
    }
}
