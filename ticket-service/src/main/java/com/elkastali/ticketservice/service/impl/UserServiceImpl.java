package com.elkastali.ticketservice.service.impl;

import com.elkastali.ticketservice.entities.User;
import com.elkastali.ticketservice.model.HttpResponse;
import com.elkastali.ticketservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RestTemplate restTemplate;
    private final String URL = "http://localhost:8091/api/users";
    @Override
    public User findUserById(Long id) {

        System.err.println("id = " + id);
        HttpResponse response = restTemplate.getForEntity(URL + "/" + id, HttpResponse.class).getBody();
        Map<?, ?> responseData = response.getData();

        if (responseData != null) {
            Map<?, ?> userData = (Map<?, ?>) responseData.get("user");

            if (userData != null) {
                User user = mapUserDataToUser(userData);
                return user;
            }
        }

        return null; // Handle the case when user data is not available
    }

    private User mapUserDataToUser(Map<?, ?> userData) {
        return User.builder()
                .id(Long.parseLong(userData.get("id").toString()))
                .nom((String) userData.get("nom"))
                .prenom((String) userData.get("prenom"))
                .email((String) userData.get("email"))
                .password((String) userData.get("password"))
                .photo((String) userData.get("photo"))
                .tel((String) userData.get("tel"))
                .isAdmin((Boolean) userData.get("isAdmin"))
                .createdAt(LocalDateTime.parse((CharSequence) userData.get("createdAt")))
                .last_Login((LocalDateTime) userData.get("last_Login"))
                .isStaff((Boolean) userData.get("isStaff"))
                .isActive((Boolean) userData.get("isActive"))
                .build();
    }

    @Override
    public List<User> findAllUsers() {
        HttpResponse response = restTemplate.getForObject(URL, HttpResponse.class);
        Map<?, ?> responseData = response.getData();
        List<User> users = new ArrayList<>();

        if (responseData != null) {
            List<?> userDataList = (List<?>) responseData.get("users");
            if (userDataList != null) {
                for (Object userData : userDataList) {
                    if (userData instanceof Map) {
                        User user = mapUserDataToUser((Map<?, ?>) userData);
                        users.add(user);
                    }
                }
            }
        }

        return users;
    }
}
