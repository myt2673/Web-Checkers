package com.webcheckers.ui;
import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;
import spark.*;
public class PostSignOutRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostSignOutRoute.class.getName());
    private final PlayerLobby playerLobby;
    private final GameCenter gameCenter;
    private final Gson gson;

    public PostSignOutRoute(final PlayerLobby playerLobby, final GameCenter gameCenter, final Gson gson) {
        this.playerLobby = playerLobby;
        this.gameCenter = gameCenter;
        this.gson = gson;
        LOG.config("PostSignOutRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response){
        Session currentSession  = request.session();
        Player currentPlayer = currentSession.attribute(GetHomeRoute.CURRENTUSER_ATTR);
        if (gameCenter.isInAnyGame(currentPlayer)){
            gameCenter.getGame(currentPlayer).playerResigned(currentPlayer);
            currentPlayer.leaveGame();
            currentSession.attribute(GetGameRoute.GAME_ID_ATTR, null);
        }
        playerLobby.kickPlayer(currentPlayer);
        currentSession.attribute(GetHomeRoute.CURRENTUSER_ATTR, null);
        response.redirect(WebServer.HOME_URL);


        Message resignation = Message.info("This Player Has Signed out");
        return gson.toJson(resignation);
    }

}
