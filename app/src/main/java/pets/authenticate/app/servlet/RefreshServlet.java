package pets.authenticate.app.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pets.authenticate.app.model.TokenRequest;
import pets.authenticate.app.model.TokenResponse;
import pets.authenticate.app.service.TokenKeysService;

import java.io.IOException;

import static pets.authenticate.app.util.Util.getGson;
import static pets.authenticate.app.util.Util.getRequestBody;
import static pets.authenticate.app.util.Util.hasText;

public class RefreshServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TokenResponse tokenResponse = TokenResponse.builder().build();
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");

        TokenRequest tokenRequest = (TokenRequest) getRequestBody(request, TokenRequest.class);

        if (tokenRequest == null || !hasText(tokenRequest.getToken())) {
            response.setStatus(401);
        } else {
            TokenKeysService tokenKeysService = new TokenKeysService();
            String newToken = tokenKeysService.refreshToken(tokenRequest.getToken(), tokenRequest.isLogOut());

            if (!hasText(newToken)) {
                response.setStatus(403);
            } else {
                response.setStatus(200);
                tokenResponse = TokenResponse.builder()
                        .token(newToken)
                        .build();
            }
        }

        response.getWriter().print(getGson().toJson(tokenResponse));
    }
}
