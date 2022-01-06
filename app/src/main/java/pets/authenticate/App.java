/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package pets.authenticate;

import lombok.extern.slf4j.Slf4j;
import pets.authenticate.app.server.ServerJetty;

@Slf4j
public class App {
    public static void main(String[] args) throws Exception {
        log.info("Begin pets-authenticate-simple initialization...");
        new ServerJetty().start();
        log.info("End pets-authenticate-simple initialization...");
    }
}
