package com.webcheckers.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

class GetSignInRouteTest {

    private GetSignInRoute signIn;

    private TemplateEngine templateEngine;
    private Request request;
    private Session session;

    @BeforeEach
    void setUp() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        templateEngine = mock(TemplateEngine.class);

        signIn = new GetSignInRoute(templateEngine);
    }

    @Test
    public void test_playerNotSignedIn(){
        final Response response = mock(Response.class);
        final TemplateEngineTester myModelView = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(myModelView.makeAnswer());

        signIn.handle(request, response);

        myModelView.assertViewModelExists();
        myModelView.assertViewModelIsaMap();

        myModelView.assertViewModelAttribute(GetSignInRoute.TITLE_ATTR, GetSignInRoute.TITLE_MSG);
        myModelView.assertViewModelAttribute(GetSignInRoute.MESSAGE_ATTR, GetSignInRoute.MESSAGE_MSG);
    }
}