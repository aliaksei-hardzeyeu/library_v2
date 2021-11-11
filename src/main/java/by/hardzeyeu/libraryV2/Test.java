package by.hardzeyeu.libraryV2;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Test {

    public static void main(String[] args) {
        BasicConfigurator.configure();
        Logger logger = Logger.getLogger(Test.class);
        logger.info("hey");
        User user = new User();
        System.out.println(user.getId());


    }

    static class User {
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}