package br.com.hlandim.supermarket;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.hlandim.supermarket.data.service.SessionService;
import br.com.hlandim.supermarket.data.service.response.CreateResponse;
import br.com.hlandim.supermarket.manager.SessionManager;
import br.com.hlandim.supermarket.page.main.signup.model.SignUp;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Mockito.when;

/**
 * Created by hlandim on 18/01/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class SignInTest {

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

        long uniquePrefix = new Date().getTime();
        String password = "test123";
        SignUp signUp = new SignUp("Test Name", "teste" + uniquePrefix + "@gmail.com", password, password);

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

    }
}
