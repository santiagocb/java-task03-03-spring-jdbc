import com.tuspring.config.ApplicationConfig;
import com.tuspring.config.DataGeneratorWithProcedures;
import com.tuspring.config.DatabaseSetup;
import com.tuspring.dao.UserDAOImpl;
import com.tuspring.dao.UserProcedureDAOImpl;
import com.tuspring.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;


public class Main {

    public static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws SQLException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        DatabaseSetup dbSetUp = context.getBean(DatabaseSetup.class);
        dbSetUp.dropTables();
        dbSetUp.createTables();
        logger.info("Tables created");

        DataGeneratorWithProcedures generatorWithProcedures = context.getBean(DataGeneratorWithProcedures.class);

        generatorWithProcedures.generateUsers();
        generatorWithProcedures.generateFriendships();
        generatorWithProcedures.generatePosts();
        generatorWithProcedures.generateLikes();
        logger.info("Data generated thru procedures");

        UserDAOImpl userDAO = context.getBean(UserDAOImpl.class);
        UserProcedureDAOImpl userProcedureDAO = context.getBean(UserProcedureDAOImpl.class);

        UserService userService = context.getBean(UserService.class);

        long friendShipAverageNumber = userService.getFriendshipNumberAverage();

        long startTime = System.nanoTime();
        int querySize = userProcedureDAO.findUserNamesWithMoreThanFriendsNumberAnd100LikesInTheLasthMonth(friendShipAverageNumber).size();
        logger.info(String.format("[With Stored function] Number of users with more than %s friends and 100 likes in the last month: %s", friendShipAverageNumber, querySize));
        long endTime = System.nanoTime();
        checkTime(startTime, endTime, "SQL Query findUserNames thru Stored Function");

        long startTime2 = System.nanoTime();
        int querySize2 = userDAO.findUserNamesWithMoreThanFriendsNumberAnd100LikesInTheLasthMonth(friendShipAverageNumber).size();
        logger.info(String.format("Number of users with more than %s friends and 100 likes in the last month: %s", friendShipAverageNumber, querySize2));
        long endTime2 = System.nanoTime();
        checkTime(startTime2, endTime2, "SQL Query findUserNames thru SQL");


        dbSetUp.dropTables();
        context.close();
    }

    public static void checkTime(long startTime, long endTime, String operation) {
        long duration = endTime - startTime;
        double durationInSec = duration / 1_000_000_000.0;

        logger.info(String.format("Execution time (operation: %s): %s seconds", durationInSec, operation));
    }
}
