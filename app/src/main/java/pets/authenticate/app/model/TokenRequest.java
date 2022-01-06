package pets.authenticate.app.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pets.authenticate.app.model.user.User;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenRequest {
    @Setter(AccessLevel.NONE)
    private String username;
    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    private String password;
    private String sourceIp;
    private String token;
    private boolean logOut;
    // for save new user request
    private User user;
}
