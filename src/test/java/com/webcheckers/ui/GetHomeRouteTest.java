package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetHomeRouteTest {

  private GetHomeRoute CuT;

  private Request request;
  private Session session;
  private PlayerLobby playerLobby;
  private TemplateEngine engine;
  private Response response;
  private GameCenter gameCenter;
  private Gson gson;

  @BeforeEach
  void setUp() {
    request = mock(Request.class);
    response = mock(Response.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    playerLobby = new PlayerLobby();
    gameCenter = new GameCenter();
    engine = mock(TemplateEngine.class);
    this.CuT = new GetHomeRoute( engine,gameCenter);
  }

  @Test
  void testHomePage(){
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
    when(session.attribute(GetHomeRoute.CURRENTUSER_ATTR)).thenReturn( new Player("currentUser"));
    CuT.handle(request, response);
    testHelper.assertViewModelExists();
    testHelper.assertViewModelIsaMap();
    testHelper.assertViewModelAttribute("title", "Welcome!" );
    testHelper.assertViewModelAttribute("message", GetHomeRoute.WELCOME_MSG);
    testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
  }
}

//NO OLD SESSION TEST AS THERE IS NOT TIME OUT