package br.com.hlandim.supermarket;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import br.com.hlandim.supermarket.data.service.SessionService;
import br.com.hlandim.supermarket.data.service.response.CreateResponse;
import br.com.hlandim.supermarket.manager.SessionManager;
import br.com.hlandim.supermarket.page.main.signin.model.SignIn;
import br.com.hlandim.supermarket.page.main.signup.model.SignUp;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by hlandim on 18/01/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class SignInTest {
    private static final String EMAIL = "teste@gmail.com";
    private static final String PASSWORD = "teste123";
    private static final String TOKEN = "testeToken123soad123sdj";

    @Mock
    Context mMockContext;

    @Mock
    MainActivity mainActivity;

    @Mock
    SessionService mSessionService;

    @Mock
    SessionManager mSessionManager;

    @Test
    public void shouldCreateUser() {

        SignUp signUp = new SignUp("Test Name", EMAIL, PASSWORD, PASSWORD);

        /**
         * {
         "name": "Hugo Landim Santos",
         "createdAt": "1484776851275",
         "password": "$2a$10$xWI3a9OW5im6rxQnqFDnze2MEoXDhYVyA4n6/4Byoz0NkG8XSfgqK",
         "id": "201902997191498409",
         "email": "hlandim.s+s@gmail.com"
         }
         */

        CreateResponse createResponse = new CreateResponse("", "", "", "", null);
        when(mSessionService.createUser(signUp)).thenReturn(Observable.just(createResponse));

        Observable<CreateResponse> createResponseObservable = mSessionService.createUser(signUp);
        TestSubscriber<CreateResponse> testSubscriber = new TestSubscriber<>();
        createResponseObservable.subscribe(testSubscriber);

        List<CreateResponse> responses = new ArrayList<>();
        responses.add(createResponse);
        testSubscriber.assertNoErrors();
        testSubscriber.assertReceivedOnNext(responses);
    }

    @Test
    public void shouldSignInSuccess() {
        SignIn signIn = new SignIn(EMAIL, PASSWORD);
        String response = null;
        doAnswer(invocation -> {
            ((SessionManager.SignInCallback) invocation.getArguments()[0]).onSignInResponse(response);
            return null;
        })
                .when(mSessionManager).signIn(eq(signIn), any(SessionManager.SignInCallback.class));

        mSessionManager.signIn(eq(signIn), any(SessionManager.SignInCallback.class));

// Verify state and interaction
        verify(mSessionManager, times(1)).signIn(
                eq(signIn), any(SessionManager.SignInCallback.class));

        assertNull(mSessionManager.getToken());

        /*SignInResponse signInResponse = new SignInResponse(TOKEN);
        SignIn signIn = new SignIn(EMAIL, PASSWORD);
        when(mSessionService.signIn(signIn.getEmail(), signIn.getPassword(), signIn.getGrantType()))
                .thenReturn(Observable.just(signInResponse));

        Observable<SignInResponse> signInResponseObservable = mSessionService.signIn(signIn.getEmail(), signIn.getPassword(), signIn.getGrantType());
        TestSubscriber<SignInResponse> testSubscriber = new TestSubscriber<>();
        signInResponseObservable.subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        List<SignInResponse> responses = new ArrayList<>();
        responses.add(signInResponse);
        testSubscriber.assertReceivedOnNext(responses);*/

    }

    /*@Test
    public void shouldSignInWithSavedToken() {
        SignInResponse signInResponse = new SignInResponse(TOKEN);
        SignIn signIn = new SignIn(EMAIL, PASSWORD);
//        when(mSessionManager.signInWithSavedCredentials(any(SessionManager.SignInCallback.class))).thenAnswer(new Answer<String>() {
//        });

        doAnswer(invocation -> {
            ((SessionManager.SignInCallback) invocation.getArguments()[0]).onSignInResponse(null);
            return null;
        }).when(mSessionManager).signInWithSavedCredentials(any(SessionManager.SignInCallback.class));


    }*/
}
