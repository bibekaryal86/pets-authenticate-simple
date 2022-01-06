package pets.authenticate.app.server;

import jakarta.servlet.DispatcherType;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import pets.authenticate.app.filter.ServletFilter;
import pets.authenticate.app.servlet.AppPing;
import pets.authenticate.app.servlet.LoginServlet;
import pets.authenticate.app.servlet.RefreshServlet;

import java.util.EnumSet;

import static pets.authenticate.app.util.Util.CONTEXT_PATH;
import static pets.authenticate.app.util.Util.SERVER_IDLE_TIMEOUT;
import static pets.authenticate.app.util.Util.SERVER_MAX_THREADS;
import static pets.authenticate.app.util.Util.SERVER_MIN_THREADS;
import static pets.authenticate.app.util.Util.SERVER_PORT;
import static pets.authenticate.app.util.Util.getSystemEnvProperty;

@Slf4j
public class ServerJetty {

    public void start() throws Exception {
        QueuedThreadPool threadPool = new QueuedThreadPool(SERVER_MAX_THREADS, SERVER_MIN_THREADS, SERVER_IDLE_TIMEOUT);
        Server server = new Server(threadPool);

        try (ServerConnector connector = new ServerConnector(server)) {
            String port = getSystemEnvProperty(SERVER_PORT);
            connector.setPort(port == null ? 8080 : Integer.parseInt(port));
            server.setConnectors(new Connector[]{connector});
        }

        server.setHandler(getServletHandler());
        server.start();
    }

    private ServletHandler getServletHandler() {
        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addFilterWithMapping(ServletFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));

        servletHandler.addServletWithMapping(AppPing.class, CONTEXT_PATH + "/tests/ping");

        servletHandler.addServletWithMapping(LoginServlet.class, CONTEXT_PATH + "/login");
        servletHandler.addServletWithMapping(RefreshServlet.class, CONTEXT_PATH + "/refresh");

        return servletHandler;
    }
}
