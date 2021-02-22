package com.webcheckers.ui;
import com.webcheckers.appl.*;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import spark.*;

import java.util.*;
import java.util.logging.Logger;


public class GetSpectatorGameRoute implements Route{
    private static final Logger LOG = Logger.getLogger(GetSpectatorGameRoute.class.getName());

    private static final String VIEW_NAME = "game.ftl";
    static Game SPEC_GAME;


    private final TemplateEngine templateEngine;

    private final GameCenter gameCenter;
    private final PlayerLobby playerLobby;
    public GetSpectatorGameRoute(final TemplateEngine templateEngine, final GameCenter gameCenter, final PlayerLobby playerLobby) {
        this.templateEngine = templateEngine;
        this.gameCenter = gameCenter;
        this.playerLobby = playerLobby;
        LOG.config("GetSpectatorGameRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetSpectatorGameRoute is invoked.");
        final Map<String, Object> vm = new HashMap<>();
        Session currentSession = request.session();
        Player currentUser = currentSession.attribute(GetHomeRoute.CURRENTUSER_ATTR);

        String gameID;
        if (currentSession.attribute("specID") == null) {
            gameID = request.queryParams("gameID");
            currentSession.attribute("specID", gameID);
        } else {
            gameID = currentSession.attribute("specID");
        }
        SPEC_GAME = GetGameRoute.GAME_TO_SPEC;
        gameCenter.updateSpectator(SPEC_GAME, currentUser);

        vm.put(GetHomeRoute.CURRENTUSER_ATTR, currentUser);
        vm.put("viewMode", GameCenter.ViewMode.SPECTATOR);
        vm.put("redPlayer", SPEC_GAME.getRedPlayer());
        vm.put("whitePlayer", SPEC_GAME.getWhitePlayer());
        vm.put("activeColor", SPEC_GAME.getActivePlayer().getPlayerColor());

        boolean isRed = true;
        if (currentSession.attribute("isRed") != null){
            isRed = currentSession.attribute("isRed");
        }
        vm.put("board", SPEC_GAME.getSpectatorBoard(currentUser).flipBoard(SPEC_GAME.getSpectatorBoard(currentUser)));

        if((SPEC_GAME.isResigned()) || (SPEC_GAME.completedGame() != null)){
            vm.put("message", Message.info(SPEC_GAME.getGameResult(currentUser)));
        }

        vm.put("title", "Enjoy watching your game!");
        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
