package com.webcheckers.ui;
import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.util.Message;
import spark.*;
public class PostResignRoute implements Route{
    private static final Logger LOG = Logger.getLogger(PostResignRoute.class.getName());
    private final PlayerLobby playerLobby;
    private final GameCenter gameCenter;
    private final Gson gson;

    public PostResignRoute(final PlayerLobby playerLobby, final GameCenter gameCenter, final Gson gson){
        this.gson = gson;
        this.gameCenter = gameCenter;
        this.playerLobby = playerLobby;
        LOG.config("PostResignRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response){
        LOG.finer("PostResignGameRoute is invoked.");
        Map<String, Object> vm = new HashMap<>();
        Session currentSession = request.session();
        Player currentUser = currentSession.attribute(GetHomeRoute.CURRENTUSER_ATTR);
        Game game = gameCenter.getGame(currentUser);
        game.playerResigned(currentUser);

        Message resignation = Message.info(currentUser.getName() + " has Resigned.");
        return gson.toJson(resignation);
    }

}
