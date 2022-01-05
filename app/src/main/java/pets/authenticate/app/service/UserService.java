package pets.authenticate.app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pets.authenticate.app.connector.UserConnector;
import pets.authenticate.app.model.TokenRequest;
import pets.authenticate.app.model.UserDetails;
import pets.authenticate.app.model.user.User;
import pets.authenticate.app.model.user.UserResponse;

@Slf4j
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public UserDetails validateUser(TokenRequest tokenRequest) {
        UserDetails userDetails = null;
        boolean isSaveNewUser = tokenRequest.getUser() != null;
        String password = isSaveNewUser ? tokenRequest.getUser().getPassword() : tokenRequest.getPassword();

        try {
            UserResponse userResponse;
            if (isSaveNewUser) {
                String newPassword = bCryptPasswordEncoder.encode(tokenRequest.getUser().getPassword());
                User user = User.builder()
                        .username(tokenRequest.getUser().getUsername())
                        .password(newPassword)
                        .firstName(tokenRequest.getUser().getFirstName())
                        .lastName(tokenRequest.getUser().getLastName())
                        .streetAddress(tokenRequest.getUser().getStreetAddress())
                        .city(tokenRequest.getUser().getCity())
                        .state(tokenRequest.getUser().getState())
                        .zipcode(tokenRequest.getUser().getZipcode())
                        .email(tokenRequest.getUser().getEmail())
                        .phone(tokenRequest.getUser().getPhone())
                        .status(tokenRequest.getUser().getStatus())
                        .build();
                userResponse = UserConnector.saveNewUser(user);
            } else {
                userResponse = UserConnector.getUserByUsername(tokenRequest.getUsername());
            }

            if (userResponse != null && userResponse.getUsers() != null) {
                User user = userResponse.getUsers().stream()
                        .filter(userDetail -> bCryptPasswordEncoder.matches(password, userDetail.getPassword()))
                        .findFirst()
                        .orElse(null);

                if (user != null) {
                    userDetails = UserDetails.builder()
                            .username(user.getUsername())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .streetAddress(user.getStreetAddress())
                            .city(user.getCity())
                            .state(user.getState())
                            .zipcode(user.getZipcode())
                            .email(user.getEmail())
                            .phone(user.getPhone())
                            .status(user.getStatus())
                            .build();
                }
            }
        } catch (Exception ex) {
            log.error("Error in Validate User: {}", tokenRequest.getUsername(), ex);
        }

        return userDetails;
    }
}
