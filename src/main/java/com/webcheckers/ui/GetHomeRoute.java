package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;
import static spark.Spark.halt;
import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
  public static final String TITLE_ATTR = "title";
  public static final String TITLE = "Welcome!";
  public static final String WELCOME_ATTR = "message";
  public static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");
  public static final String CURRENTUSER_ATTR = "currentUser";
  public static final String PLAYER_ATTR = "playerList";
  public static final String NUMBER_ATTR = "number";
  public static final String VIEW_NAME = "home.ftl";


  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  private final TemplateEngine templateEngine;
  private PlayerLobby playerLobby;
  private GameCenter gameCenter;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   */
  public GetHomeRoute(final TemplateEngine templateEngine, GameCenter gameCenter) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
     playerLobby = new PlayerLobby();
     this.gameCenter = gameCenter;
    //
    LOG.config("GetHomeRoute is initialized.");
  }

  public PlayerLobby getPlayerLobby(){
    return playerLobby;
  }

  /**
   * Render the WebCheckers Home page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("GetHomeRoute is invoked.");
    //
    final Session session = request.session();
    Map<String, Object> vm = new HashMap<>();
    vm.put(TITLE_ATTR, TITLE);
    // display a user message in the Home page
    vm.put(WELCOME_ATTR, WELCOME_MSG);
    vm.put(CURRENTUSER_ATTR, session.attribute("currentUser"));

    vm.put(NUMBER_ATTR, playerLobby.playerCount());

    Player currentPlayer = session.attribute("currentUser");
    vm.put(PLAYER_ATTR, playerLobby.getListOfPlayers(currentPlayer));
    if (gameCenter.isPlayerInGame(currentPlayer)){
      response.redirect(WebServer.GAME_URL + "?opponentName=" + gameCenter.getOpponent(currentPlayer).getName());
      halt();
      return null;
    }

    // render the View
    return templateEngine.render(new ModelAndView(vm , "home.ftl"));
  }
}
