package com.webcheckers.ui;
import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import spark.*;

import java.util.logging.Logger;

public class PostSpectatorCheckTurnRoute implements Route{
    private static final Logger LOG = Logger.getLogger(PostSpectatorCheckTurnRoute.class.getName());

    private final Gson gson;

    private final GameCenter gameCenter;
    public PostSpectatorCheckTurnRoute(final GameCenter gameCenter, final Gson gson) {
        this.gson = gson;
        this.gameCenter = gameCenter;
        LOG.config("PostSpectatorCheckTurnRoute is initialized.");
    }
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostSpectatorCheckTurnRoute is invoked.");
        //String gameID = request.queryParams("gameID");

        Session currentSession = request.session();
        //String gameID = currentSession.attribute("specID");
        Player currentUser = currentSession.attribute(GetHomeRoute.CURRENTUSER_ATTR);

        boolean isUpdated = gameCenter.isSpectatorUpdated(GetSpectatorGameRoute.SPEC_GAME,currentUser);
        //Game gameToSpec = gameCenter.getGame(currentUser);

        Message updateInfo;
        if(!isUpdated || GetSpectatorGameRoute.SPEC_GAME.isResigned())
            updateInfo = Message.info("true");
        else
            updateInfo = Message.info("false");

        return gson.toJson(updateInfo);
    }
}
