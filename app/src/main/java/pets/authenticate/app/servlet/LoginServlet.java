package pets.authenticate.app.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pets.authenticate.app.model.TokenRequest;
import pets.authenticate.app.model.TokenResponse;
import pets.authenticate.app.model.UserDetails;
import pets.authenticate.app.service.TokenKeysService;
import pets.authenticate.app.service.UserService;

import java.io.IOException;

import static pets.authenticate.app.util.Util.getGson;
import static pets.authenticate.app.util.Util.getRequestBody;
import static pets.authenticate.app.util.Util.hasText;
import static pets.authenticate.app.util.Util.validateRequest;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TokenResponse tokenResponse = TokenResponse.builder().build();
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");

        TokenRequest tokenRequest = (TokenRequest) getRequestBody(request, TokenRequest.class);

        if (!validateRequest(tokenRequest)) {
            response.setStatus(400);
        } else {
            UserService userService = new UserService();
            UserDetails userDetails = userService.validateUser(tokenRequest);

            if (userDetails == null) {
                response.setStatus(401);
            } else {
                if (!hasText(tokenRequest.getSourceIp())) {
                    tokenRequest.setSourceIp(request.getRemoteAddr());
                }

                TokenKeysService tokenKeysService = new TokenKeysService();
                String token = tokenKeysService.createToken(tokenRequest);

                if (!hasText(token)) {
                    response.setStatus(500);
                } else {
                    response.setStatus(200);
                    tokenResponse = TokenResponse.builder()
                            .token(token)
                            .userDetails(userDetails)
                            .build();
                }
            }
        }
        response.getWriter().print(getGson().toJson(tokenResponse));
    }
}
