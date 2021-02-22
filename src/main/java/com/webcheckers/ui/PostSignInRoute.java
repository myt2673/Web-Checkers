package com.webcheckers.ui;

import java.util.*;
import java.util.logging.Logger;

import com.webcheckers.model.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.util.Message;
import spark.*;

public class PostSignInRoute implements Route{
    private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());
    static final String TITLE = "Sign In";
    static final String VIEW_NAME = "signin.ftl";
    public static final String NAME_PARAM = "fname";
    private PlayerLobby playerLobby;

    private static final Message SIGN_IN_MSG = Message.info("You have been signed in! Congratulations!");
    private static final Message INVALID_MSG = Message.info("Error! Need at least 1 alphanumeric character");
    private static final Message ERR_MSG = Message.info("Error! User name already in use!");


    private final TemplateEngine templateEngine;

    public PostSignInRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
        LOG.config("PostSignInRoute is initialized.");
        playerLobby = new PlayerLobby();
    }

    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostSignInRoute is invoked.");
        //
        Map<String, Object> vm = new HashMap<>();



        String playerName = request.queryParams(NAME_PARAM);
        if (playerName.equals("") || !playerName.matches("[A-Za-z0-9]+")){
            vm.put(GetSignInRoute.TITLE_ATTR, GetSignInRoute.TITLE_MSG);
            vm.put(GetSignInRoute.MESSAGE_ATTR, INVALID_MSG);
            return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
        }

        Player currentPlayer = new Player(playerName);

        if (playerLobby.contains(currentPlayer)){
            vm.put(GetSignInRoute.TITLE_ATTR, GetSignInRoute.TITLE_MSG);
            vm.put(GetSignInRoute.MESSAGE_ATTR, ERR_MSG);
            return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
        }

        // display a user message in the Home page
        vm.put(GetSignInRoute.TITLE_ATTR, GetHomeRoute.TITLE);
        vm.put("message", SIGN_IN_MSG);

        playerLobby.addPlayer(currentPlayer);
        Session httpSession = request.session();
        httpSession.attribute("currentUser", currentPlayer);


        httpSession.attribute("players", playerLobby.getPlayerNames());
        vm.put("currentUser", httpSession.attribute("currentUser"));
        vm.put("currentUser.name", currentPlayer.getName());
        vm.put(GetHomeRoute.PLAYER_ATTR, playerLobby.getListOfPlayers(currentPlayer));
        // render the View
        return templateEngine.render(new ModelAndView(vm, "home.ftl"));
    }

    public PlayerLobby getPlayerLobby(){
        return playerLobby;
    }

}
