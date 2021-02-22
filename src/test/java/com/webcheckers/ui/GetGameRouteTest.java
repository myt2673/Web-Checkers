package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@Tag("UI-tier")
public class GetGameRouteTest {

    /**
     * The component-under-test (CuT).
     *
     */
    private GetGameRoute CuT;

    // mock objects
    private Request request;
    private Session session;
    private PlayerLobby playerLobby;
    private TemplateEngine engine;
    private Response response;
    private GameCenter gameCenter;
    private Gson gson;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        //playerLobby = new PlayerLobby();
        //gameCenter = new GameCenter();
        playerLobby = mock(PlayerLobby.class);
        gameCenter = mock(GameCenter.class);
        engine = mock(TemplateEngine.class);
//        gson = mock(Gson.class);

        this.CuT = new GetGameRoute(engine, gameCenter,playerLobby , gson);
    }

    /**
     * Testing aspects of the GetGameRoute class
     * Tested:
     *      ViewModel: Non-NULL (Object)
     *      ViewModel is a map: Map<String, Object> (boolean)
     *      View_Name: "game.ftl" (String)
     *
     */
    @Test
    public void testLoadingGamePage(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        when(session.attribute(GetHomeRoute.CURRENTUSER_ATTR)).thenReturn(new Player("currentUser"));
        when(session.attribute("opponent")).thenReturn(new Player("opponent"));

    }
}