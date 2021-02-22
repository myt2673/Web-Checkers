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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class GetSpectatorGameRouteTest {
    private Request request;
    private Session session;
    private PlayerLobby playerLobby;
    private GetSpectatorGameRoute spectatorGameRoute;
    private TemplateEngine engine;
    private GameCenter gc;

    @BeforeEach
    public void setup(){
            request = mock(Request.class);
            session = mock(Session.class);
            when(request.session()).thenReturn(session);
            engine = mock(TemplateEngine.class);
            playerLobby = mock(PlayerLobby.class);
            gc = mock(GameCenter.class);
            spectatorGameRoute = new GetSpectatorGameRoute(engine,gc,playerLobby);
    }

    @Test
    public void test_spectator(){
        final Response response = mock(Response.class);
        final TemplateEngineTester myModelView = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(myModelView.makeAnswer());
    }
}
