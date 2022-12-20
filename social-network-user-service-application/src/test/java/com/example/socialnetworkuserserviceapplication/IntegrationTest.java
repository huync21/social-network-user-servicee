package com.example.socialnetworkuserserviceapplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = {"test"})
public abstract class IntegrationTest {

    protected final String BASE_TEST_FOLDER_PATH = "./src/test/resources/files/";

    static {
        System.setProperty("log.json.disable", "true");
    }

    private static final int WIREMOCK_PORT = 8089;
    public static final String USER_NAME = "social-network-usecase-service";
    public static final String BACKOFFICE_USERNAME = "back-office-usecase-service";
    public static final String PASSWORD = "password";
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(WIREMOCK_PORT);

    @LocalServerPort
    protected int serverPort;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired(required = false)
    protected CacheManager cacheManager;

    @Autowired
    protected DataSource dataSource;
    @Before
    public final void setup() throws IOException, SQLException {
        RestAssured.port = this.serverPort;
        RestAssured.config = RestAssured.config().objectMapperConfig(RestAssured.config().getObjectMapperConfig()
                .jackson2ObjectMapperFactory((cls, charset) -> objectMapper));
        RestAssured.requestSpecification = new RequestSpecBuilder() //
                .setContentType(MediaType.APPLICATION_JSON_VALUE) //
                .setAccept(MediaType.APPLICATION_JSON_VALUE) //
                .build();

        if (cacheManager != null) {
            cacheManager.getCacheNames().forEach(name -> cacheManager.getCache(name).clear());
        }
    }
    protected RequestSpecification requestWithBasicAuth(String username, String password) {
        final RequestSpecBuilder builder = new RequestSpecBuilder();
        String rawBasicAuth = username + ":" + password;
        String encodedBasicAuth = Base64.getEncoder().encodeToString(rawBasicAuth.getBytes());
        builder.addHeader(HttpHeaders.AUTHORIZATION, "Basic " + encodedBasicAuth);
        return builder.build();
    }

}

