package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.*;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

public class PostCheckTurnRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());

    private final PlayerLobby playerLobby;
    private final GameCenter gameCenter;
    private Gson gson;

    public PostCheckTurnRoute(GameCenter gameCenter, Gson gson, PlayerLobby playerLobby){
        this.gameCenter = Objects.requireNonNull(gameCenter, "GameCenter is required.");
        this.gson = Objects.requireNonNull(gson, "gson is required");
        this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby is required.");

        LOG.config("PostCheckTurnRoute is initialized");
    }

    @Override
    public Object handle(Request request, Response response) {
        LOG.config("PostCheckTurnRoute is invoked.");

        Session currentSession = request.session();
        Player currentUser = currentSession.attribute("currentUser");
        Game game = gameCenter.getGame(currentUser);

        try {
            if (gameCenter.isMyTurn(gameCenter.getWhitePlayer()) || gameCenter.isMyTurn(gameCenter.getRedPlayer()) || game.isResigned()) {
                return gson.toJson(Message.info("true"));
            }
            else {
                return gson.toJson(Message.info("false"));
            }
        }
        catch (Exception e){
            return gson.toJson(Message.info("false"));
        }
    }
}