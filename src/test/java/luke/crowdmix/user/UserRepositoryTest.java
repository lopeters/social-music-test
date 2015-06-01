package luke.crowdmix.user;

import luke.crowdmix.user.User;
import luke.crowdmix.user.UserFactory;
import luke.crowdmix.user.UserRepository;
import org.junit.Test;

import static luke.crowdmix.user.User.Name.username;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

public class UserRepositoryTest {

    private final User.Name username = username("Alice");

    @Test
    public void createAndPersistUserIfTheyDoNotAlreadyExist() {
        UserFactory userFactory = mock(UserFactory.class);
        User expectedUser = mock(User.class);
        given(userFactory.create(username)).willReturn(expectedUser);
        UserRepository underTest = new UserRepository(userFactory);

        assertThat(underTest.get(username), is(expectedUser));
        assertThat(underTest.get(username), is(expectedUser));

        then(userFactory).should(times(1)).create(username);
    }
}