package uk.gov.metoffice.apprentice.tickets.server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import uk.gov.metoffice.apprentice.tickets.handlers.EventsListingHandler;

public class WebServer {

    private EventsListingHandler eventsListingHandler;

    public WebServer(final EventsListingHandler eventsListingHandler) {
        this.eventsListingHandler = eventsListingHandler;
    }

    public void startServer() throws Exception {
        Server server = new Server(8080);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[]{"html/index.html"});
        resourceHandler.setResourceBase("src/main/resources");

        ContextHandler indexHandler = new ContextHandler();
        indexHandler.setContextPath("/");
        indexHandler.setHandler(resourceHandler);

        ContextHandler listCustomersHandler = new ContextHandler();
        listCustomersHandler.setContextPath("/events");
        listCustomersHandler.setHandler(eventsListingHandler);

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{indexHandler, listCustomersHandler});

        server.setHandler(contexts);

        server.start();
        server.join();
    }
}