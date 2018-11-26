package mx.edu.itt;

import org.junit.*;

import mx.edu.itt.model.User;
import mx.edu.itt.provider.repository.impl.SQLUserRepository;
import mx.edu.itt.provider.service.UserService;
import mx.edu.itt.provider.service.impl.UserServiceImpl;

/**
 * Unit test for simple App.
 */
public class AppTest {
	private static SQLUserRepository repo;
	private static UserService service;
    private User newuser;
    private User existinguser;

    @BeforeClass
    public static void prepare() {
        repo = SQLUserRepository.getInstance();
        repo.connect("jdbc:mysql://localhost/inventariodb?useSSL=false", "root", "");
        service = new UserServiceImpl(repo);
    }
    
    @Before
    public void init() {
    	newuser = new User();
    	newuser.setFirstName("Hasdai");
    	newuser.setLastName("Pacheco");
    	newuser.setAddress("Joaquin Camaño 11");
    	newuser.setEmail("me@hasdaipacheco.com");
    }
    
    @Test
    public void shouldConnectToDataBaseWithUserAndPassword() {
        Assert.assertNotNull(repo.getConnection());
    }
    
    //@Test
    public void shoudRemoveUserWithId() {
    	User result = service.removeById(35);
    }
    
    //@Test
    public void shouldFindUserWithId() {
		User result = service.findById(36);
		System.out.println(result.toString());
    }

    @Test
    public void shouldInsertRecordToDataBase() {
       User result = service.save(newuser);
       Assert.assertNotNull(result);
    }

    //@AfterClass
    public static void cleanUp() {
    	repo.clear();
        repo.disconnect();
    }
}