package pets.authenticate.app.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pets.authenticate.app.exception.CustomRuntimeException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EndpointUtil {

    // base_urls
    private static final String PETS_SVC_ENDPOINT_BASE_DEV = "http://localhost:8003/pets-service";
    private static final String PETS_SVC_ENDPOINT_BASE_DOCKER = "http://pets-database:8003/pets-service";
    private static final String PETS_SVC_ENDPOINT_BASE_PROD = "https://pets-service.appspot.com/pets-service";
    // endpoint_urls
    private static final String PETS_SVC_GET_USER_BY_USERNAME = "/users/user/username/%s";
    private static final String PETS_SVC_SAVE_NEW_USER = "/users/user/";
    private static Map<String, String> theEndpointMap = null;

    private static Map<String, String> setEndpointMap() {
        Map<String, String> endpointMap = new HashMap<>();
        String profile = Util.getSystemEnvProperty(Util.PROFILE);
        String endpointBasePetsService;

        if (!Util.hasText(profile)) {
            throw new CustomRuntimeException("PROFILE NOT SET AT RUNTIME");
        }

        if ("development".equals(profile)) {
            endpointBasePetsService = PETS_SVC_ENDPOINT_BASE_DEV;
        } else if ("docker".equals(profile)) {
            endpointBasePetsService = PETS_SVC_ENDPOINT_BASE_DOCKER;
        } else {
            endpointBasePetsService = PETS_SVC_ENDPOINT_BASE_PROD;
        }

        endpointMap.put("getUserByUsernameUrl", endpointBasePetsService.concat(PETS_SVC_GET_USER_BY_USERNAME));
        endpointMap.put("saveNewUserUrl", endpointBasePetsService.concat(PETS_SVC_SAVE_NEW_USER));

        theEndpointMap = new HashMap<>();
        theEndpointMap.putAll(endpointMap);

        return endpointMap;
    }

    public static Map<String, String> endpointMap() {
        return Objects.requireNonNullElseGet(theEndpointMap, EndpointUtil::setEndpointMap);
    }
}
