package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

public class PostValidateMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostValidateMoveRoute.class.getName());

    private final GameCenter gameCenter;
    private final Gson gson;
    private final PlayerLobby playerLobby;
    public PostValidateMoveRoute(final GameCenter gameCenter, final Gson gson, final PlayerLobby playerLobby ) {
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required");
        this.gson = Objects.requireNonNull(gson, "gson is required");
        this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby is required");
        LOG.config("PostValidateMoveRoute is initialized.");
    }
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostValidateMoveRoute is invoked.");
        Session currentSession = request.session();
        String moveAsJSONString = gson.toJson(request.queryParams("actionData"));
        String correctJSONMoveString = createCorrectJSONFormat(moveAsJSONString);
        Move move = gson.fromJson(correctJSONMoveString, Move.class);
        Player currentPlayer = currentSession.attribute("currentUser");
        boolean isValid = gameCenter.requestMove(currentPlayer,move);

        Message moveInfo;
        if(isValid){
            moveInfo = Message.info("Perfectly Valid move!");
        }else{
            moveInfo = Message.error("Not valid Move!");
        }
        return gson.toJson(moveInfo);
    }
    private static String createCorrectJSONFormat(String incorrectJSONString){
        String correctJSONString = incorrectJSONString.replace("\\", "'");
        correctJSONString = correctJSONString.replaceAll("'\"", "'");
        correctJSONString = correctJSONString.replace("\"", "");
        return correctJSONString;
    }
}