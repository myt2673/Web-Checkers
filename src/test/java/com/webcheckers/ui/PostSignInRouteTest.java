package com.webcheckers.ui;

import com.webcheckers.model.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.webcheckers.ui.PostSignInRoute.NAME_PARAM;
import static com.webcheckers.ui.WebServer.HOME_URL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import spark.*;

import javax.swing.*;
import java.util.Map;

@Tag("UI-tier")
public class PostSignInRouteTest {
    private Request request;
    private Session session;
    private Response response;
    private PlayerLobby playerLobby;
    private PostSignInRoute postSignInRoute;
    private TemplateEngine engine;


    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);


        postSignInRoute = new PostSignInRoute(  engine );
        playerLobby = postSignInRoute.getPlayerLobby();

    }
    @Test
    public void SignINTest() {
        final Response response = mock(Response.class);
        TemplateEngineTester tester = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(tester.makeAnswer());
        String playerName = "Player";
        Player playerTester = new Player(playerName);
        when(request.queryParams(NAME_PARAM)).thenReturn(playerTester.getName());

        postSignInRoute.handle(request, response);


        tester.assertViewModelExists();
        tester.assertViewModelIsaMap();
        tester.assertViewModelAttribute(GetSignInRoute.TITLE_ATTR,GetHomeRoute.TITLE);





        assertTrue(playerLobby.contains(playerTester));
        assertTrue(playerLobby.isValid(playerName));
        playerLobby.addPlayer(playerTester);
        assertEquals(playerTester,playerLobby.getPlayer(playerTester));

    }
}
