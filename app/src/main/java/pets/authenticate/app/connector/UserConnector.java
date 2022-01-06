package pets.authenticate.app.connector;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.eclipse.jetty.http.HttpMethod;
import pets.authenticate.app.model.user.User;
import pets.authenticate.app.model.user.UserResponse;

import java.util.Map;

import static pets.authenticate.app.util.ConnectorUtil.sendHttpRequest;
import static pets.authenticate.app.util.EndpointUtil.endpointMap;
import static pets.authenticate.app.util.Util.getPetsServiceAuthHeader;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserConnector {

    public static UserResponse getUserByUsername(String username) {
        Map<String, String> headers = getPetsServiceAuthHeader();
        String getUserByUsernameUrl = endpointMap().get("getUserByUsernameUrl");
        String endpoint = String.format(getUserByUsernameUrl, username);
        return (UserResponse) sendHttpRequest(endpoint, HttpMethod.GET, null, headers, UserResponse.class);
    }

    public static UserResponse saveNewUser(User user) {
        Map<String, String> headers = getPetsServiceAuthHeader();
        String endpoint = endpointMap().get("saveNewUserUrl");
        return (UserResponse) sendHttpRequest(endpoint, HttpMethod.POST, user, headers, UserResponse.class);
    }
}
