package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.TemplateEngine;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

public class WebServerTest {

    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private GameCenter gameCenter;
    private Gson gson;
    /**
     * create mock object
     */
    @BeforeEach
    public void setup() {
        templateEngine = mock(TemplateEngine.class);
        playerLobby = new PlayerLobby();
        gameCenter = new GameCenter();
        gson = new Gson();
    }

    @Test
    public void testInitialize(){
        WebServer testWebServer = new WebServer(playerLobby, gameCenter, templateEngine, gson);
        try {
            testWebServer.initialize();
        }catch(Exception e){fail();}
    }
}