package com.webcheckers.ui;
import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;

import java.lang.annotation.Repeatable;
import java.util.Objects;
import java.util.logging.Logger;
import spark.*;

public class PostBackupMoveRoute implements Route{
    private static final Logger LOG = Logger.getLogger(PostBackupMoveRoute.class.getName());
    private final GameCenter gameCenter;
    private final Gson gson;

    public PostBackupMoveRoute(final GameCenter gameCenter, final Gson gson){
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required");
        this.gson = Objects.requireNonNull(gson, "gson is required");
        LOG.config("PostBackupMoveRoute is initialized.");
    }
    @Override
    public Object handle(Request request, Response response){
        LOG.finer("PostBackupMoveRoute is invoked.");
        Session currentSession  = request.session();
        Player currentPlayer = currentSession.attribute("currentUser");
        boolean isValid = gameCenter.backup(currentPlayer);
        Message moveInfo;
        if(isValid){
            moveInfo = Message.info("Move backup successful.");
        }
        else{
            moveInfo = Message.error("Could not backup that move!");
        }

        return gson.toJson(moveInfo);
    }
}
