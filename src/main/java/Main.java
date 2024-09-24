import com.tuspring.config.ApplicationConfig;
import com.tuspring.config.DataGenerator;
import com.tuspring.config.DataGeneratorWithProcedures;
import com.tuspring.config.DatabaseSetup;
import com.tuspring.dao.FriendshipDAO;
import com.tuspring.dao.UserDAOImpl;
import com.tuspring.dao.UserProcedureDAOImpl;
import com.tuspring.dto.Friendship;
import com.tuspring.dto.User;
import com.tuspring.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.sql.SQLException;


public class Main {

    public static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws SQLException, IOException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        DatabaseSetup dbSetUp = context.getBean(DatabaseSetup.class);
        dbSetUp.dropTables();
        dbSetUp.createTables();
        logger.info("Tables created");

        dbSetUp.createStoredProcedures();
        logger.info("Stored procedures created");

        DataGenerator generator = context.getBean(DataGenerator.class);
        DataGeneratorWithProcedures generator2 = context.getBean(DataGeneratorWithProcedures.class);
        /*



        generator.generateFriendships();
        generator.generatePosts();
        generator.generateLikes();
        logger.info("Data generated");

        UserService userService = context.getBean(UserService.class);

        long friendShipNumberAverage = userService.getFriendshipNumberAverage();

        logger.info(String.format("Users with more than %s friends and 100 likes in the last month: %n", friendShipNumberAverage));
        userService.getUserNamesWithMoreThanFriendsNumberAnd100LikesInTheLasthMonth(friendShipNumberAverage).forEach(System.out::println);


        UserDAOImpl userDAO = context.getBean(UserDAOImpl.class);
        UserProcedureDAOImpl userDAO2 = context.getBean(UserProcedureDAOImpl.class);

        User user = new User("Marino", "Yepes", "2000-10-10");

        userDAO.save(user);
        userDAO2.save(user);*/

        UserDAOImpl userDAO = context.getBean(UserDAOImpl.class);
        UserProcedureDAOImpl userDAO2 = context.getBean(UserProcedureDAOImpl.class);

        FriendshipDAO friendshipDAO = context.getBean(FriendshipDAO.class);

        long startTime = System.nanoTime();
        generator.generateUsers();
        generator.generateFriendships();
        generator.generatePosts();
        generator.generateLikes();
        logger.info("Data generated 2");
        long endTime = System.nanoTime();
        checkTime(startTime, endTime);

        /*
        long startTime3 = System.nanoTime();
        UserService userService = context.getBean(UserService.class);
        long friendShipNumberAverage = userService.getFriendshipNumberAverage();
        logger.info(String.format("Users with more than %s friends and 100 likes in the last month: %n", friendShipNumberAverage));
        userService.getUserNamesWithMoreThanFriendsNumberAnd100LikesInTheLasthMonth(friendShipNumberAverage);
        long endTime3 = System.nanoTime();
        checkTime(startTime3, endTime3);



        long startTime4 = System.nanoTime();
        UserService userService2 = new UserService(userDAO2, friendshipDAO);
        long friendShipNumberAverage2 = userService.getFriendshipNumberAverage();
        logger.info(String.format("Users with more than %s friends and 100 likes in the last month: %n", friendShipNumberAverage2));
        userService2.getUserNamesWithMoreThanFriendsNumberAnd100LikesInTheLasthMonth(friendShipNumberAverage2);
        long endTime4 = System.nanoTime();
        checkTime(startTime4, endTime4);

         */

        dbSetUp.dropTables();
        dbSetUp.dropStoredProcedures();
        logger.info("Tables dropped");
        logger.info("Stored procedures dropped");



        context.close();
    }

    public static void checkTime(long startTime, long endTime) {
        // Execution time in nanoseconds
        long duration = endTime - startTime;

        // Convert to milliseconds if needed
        double durationInMillis = duration / 1_000_000_000.0;

        System.out.println("Execution time: " + duration + " nanoseconds");
        System.out.println("Execution time: " + durationInMillis + " seconds");
    }
}
