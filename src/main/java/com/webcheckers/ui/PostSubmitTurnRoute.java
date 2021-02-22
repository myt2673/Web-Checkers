package com.webcheckers.ui;
import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import java.util.Objects;
import java.util.logging.Logger;
import spark.*;

public class PostSubmitTurnRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostSubmitTurnRoute.class.getName());
    private final GameCenter gameCenter;
    private final Gson gson;

    public PostSubmitTurnRoute(final GameCenter gameCenter, final Gson gson) {
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required");
        this.gson = Objects.requireNonNull(gson, "gson is required");
        LOG.config("PostSubmitTurnRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response){
        LOG.finer("PostSubmitMoveRoute is invoked.");
        Session currentSession  = request.session();
        Player currentPlayer = currentSession.attribute("currentUser");
        boolean isValid = gameCenter.submitTurn(currentPlayer);
        Message moveInfo;
        if(isValid){
            moveInfo = Message.info("Move Submitted Successfully.");
        }
        else{
            moveInfo = Message.error("You could have made a jump, but chose not to!");
        }

        return gson.toJson(moveInfo);
    }
}