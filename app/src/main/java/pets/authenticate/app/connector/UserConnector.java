package pets.authenticate.app.connector;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.eclipse.jetty.http.HttpMethod;
import pets.authenticate.app.model.user.User;
import pets.authenticate.app.model.user.UserResponse;
import pets.authenticate.app.util.ConnectorUtil;
import pets.authenticate.app.util.EndpointUtil;
import pets.authenticate.app.util.Util;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserConnector {

    public static UserResponse getUserByUsername(String username) {
        Map<String, String> headers = Util.getPetsServiceAuthHeader();
        String getUserByUsernameUrl = EndpointUtil.endpointMap().get("getUserByUsernameUrl");
        String endpoint = String.format(getUserByUsernameUrl, username);
        return (UserResponse) ConnectorUtil.sendHttpRequest(
                endpoint, HttpMethod.GET, null, headers, UserResponse.class);
    }

    public static UserResponse saveNewUser(User user) {
        Map<String, String> headers = Util.getPetsServiceAuthHeader();
        String endpoint = EndpointUtil.endpointMap().get("saveNewUserUrl");
        return (UserResponse) ConnectorUtil.sendHttpRequest(
                endpoint, HttpMethod.POST, user, headers, UserResponse.class);
    }
}
