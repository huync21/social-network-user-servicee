package com.example.socialnetworkuserserviceapplication.controller;

import com.example.socialnetworkuserserviceapplication.IntegrationTest;
import com.example.socialnetworkuserserviceapplication.controller.request.UpdateUserRequest;
import com.example.socialnetworkuserserviceapplication.repo.IUsernameChangeLogRepository;
import com.example.socialnetworkuserserviceapplication.repo.IUsersRepository;
import com.example.socialnetworkuserserviceapplication.repo.entities.UserJPA;
import lombok.SneakyThrows;
import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

import static com.example.socialnetworkuserserviceapplication.common.BaseUri.USER_SERVICE.USERS;
import static com.example.socialnetworkuserserviceapplication.common.BaseUri.USER_SERVICE.V1;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class UserControllerTests extends IntegrationTest {
    public static String CONTEXT_PATH = "social-network-user-service/";
    @Autowired
    private IUsersRepository usersRepository;
    @Autowired
    private IUsernameChangeLogRepository usernameChangeLogRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @After
    public void clearData() throws IOException, SQLException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        RunScript.execute(connection,
                new FileReader(BASE_TEST_FOLDER_PATH + "clean.sql", StandardCharsets.UTF_8));
        DataSourceUtils.releaseConnection(connection, dataSource);
    }
    @Test
    @SneakyThrows
    public void create_user_should_success() {
        given(requestWithBasicAuth(USER_NAME, PASSWORD))
                .auth().preemptive().basic(USER_NAME, PASSWORD)
                .body(new String(Files.readAllBytes(Paths.get("src/test/resources/files/create_user_request.json"))))
                .when()
                .post(CONTEXT_PATH + V1 + USERS)
                .then()
                .statusCode(HttpStatus.OK.value());
        var savedUser = usersRepository.findByUsername("huy123").get();
        assertEquals("huy123", savedUser.getUsername());
        assertTrue(passwordEncoder.matches("Huy_211002",savedUser.getPassword()));
    }

    @Test
    @SneakyThrows
    public void update_user_should_success() {
        var userJPA = new UserJPA();
        userJPA.setUsername("huy123");
        userJPA.setPassword(passwordEncoder.encode("Huy2110_2000"));
        userJPA.setEnabled(true);
        var savedUser = usersRepository.save(userJPA);

        var updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setId(savedUser.getId());
        updateUserRequest.setUsername("huy2110");

        given(requestWithBasicAuth(USER_NAME, PASSWORD))
                .auth().preemptive().basic(USER_NAME, PASSWORD)
                .body(updateUserRequest)
                .when()
                .put(CONTEXT_PATH + V1 + USERS)
                .then()
                .statusCode(HttpStatus.OK.value());

        var changeLog = usernameChangeLogRepository.findAll().get(0);
        assertEquals("huy123", changeLog.getOldUserName());
        assertEquals("huy2110", changeLog.getNewUserName());
        assertTrue(usersRepository.findByUsername("huy2110").isPresent());
    }

}
