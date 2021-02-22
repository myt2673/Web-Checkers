package com.webcheckers.ui;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.*;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.util.Message;
import spark.*;
import static spark.Spark.halt;
import static spark.Spark.redirect;

import static spark.Spark.halt;

public class GetGameRoute implements Route{
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    public static final String GAME_ID_ATTR = "gameId";
    static final String title = "Checkers";
    static final String viewName = "game.ftl";
    static final String CURRENT_OPPONENT_KEY = "CURRENT_OPPONENT_KEY";

    static Game GAME_TO_SPEC;

    private GameCenter gameCenter;
    private Gson gson;

    public GetGameRoute(final TemplateEngine templateEngine, GameCenter gameCenter, final PlayerLobby playerlobby, final Gson gson) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");

        this.templateEngine = templateEngine;
        this.playerLobby = new PlayerLobby();
        this.gameCenter = gameCenter;
        this.gson = gson;
        LOG.config("GetGameRoute is initialized.");
    }
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetGameRoute is invoked.");
        Map<String, Object> vm = new HashMap<>();
        Session session = request.session();

        if (request.session().attribute("message") != null) {
            final Message messageToRender = request.session().attribute("message");
            request.session().removeAttribute("message");
            vm.put("message", messageToRender);
        }

        Game game;
        BoardView boardView = new BoardView();
        Player currentPlayer = session.attribute("currentUser");
        Player currentUser = session.attribute(GetHomeRoute.CURRENTUSER_ATTR);
        Player opponent = playerLobby.getPlayer(request.queryParams("opponentName"));
        if (gameCenter.isPlayerInGame(opponent)){
            if (gameCenter.isCurrentPlayerNotAbleToJoinGame(currentPlayer)){
                GAME_TO_SPEC = gameCenter.getGame(opponent);
                response.redirect(WebServer.SPECTATOR_GAME_URL);
                halt();
                Message cantJoin = Message.info("Game already full!");
                return gson.toJson(cantJoin);
            }
        }
        if (gameCenter.getGame(currentPlayer) == null){
            gameCenter.newGame(currentPlayer, opponent);
            gameCenter.addInGamePlayers(currentPlayer, opponent);
            game = gameCenter.getGame(currentPlayer);
            session.attribute(CURRENT_OPPONENT_KEY, opponent);
        }

        else { //Get game state
            game = gameCenter.getGame(currentPlayer);

        }
        if (game.isResigned() || (game.completedGame() != null)){
            System.out.println("Doing this thing.");
            gameCenter.removeGame(currentPlayer);
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }

        HashMap<String, Object> modeOptions = new HashMap<>(2);
        if ((game.isResigned()) || game.isOver){
            modeOptions = this.gameCenter.endGame(currentPlayer);
            vm.put("modeOptionsAsJSON", this.gson.toJson(modeOptions));
            session.removeAttribute(GAME_ID_ATTR);
        }
        if (game != null) {
            vm.put("title", title);
            vm.put("currentUser", currentPlayer);
            vm.put("viewMode", "PLAY");
//          vm.put("modeOptionsAsJSON", modeOptions);
            vm.put("activeColor", game.getActiveColor());

            vm.put("redPlayer", game.getRedPlayer());
            vm.put("whitePlayer", game.getWhitePlayer());

            vm.put("board",game.getCheckerBoard());
//            if (currentPlayer.getPlayerColor() == Player.PlayerColor.RED){
//                vm.put("board",game.getInvertedCheckerBoard());
//            }
//            else {
//                vm.put("board", game.getCheckerBoard());
//            }
        }
        return templateEngine.render(new ModelAndView(vm, viewName));
    }

}
