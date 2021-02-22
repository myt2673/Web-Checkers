package com.webcheckers.ui;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Player;
import com.webcheckers.ui.GetHomeRoute;
import com.webcheckers.ui.WebServer;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;
public class GetSpectatorStopWatchingRoute implements Route{
    private static final Logger LOG = Logger.getLogger(GetSpectatorStopWatchingRoute.class.getName());

    private final GameCenter gameCenter;
    public GetSpectatorStopWatchingRoute(final GameCenter gameCenter) {
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required");
        LOG.config("GetSpectatorStopWatchingRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetSpectatorStopWatchingRoute is invoked.");
        Session currentSession = request.session();
        //String gameID = currentSession.attribute("specID");
        Player currentUser = currentSession.attribute(GetHomeRoute.CURRENTUSER_ATTR);

        gameCenter.removeSpectator(GetSpectatorGameRoute.SPEC_GAME, currentUser);

        response.redirect(WebServer.HOME_URL);
        halt();
        return null;
    }
}
