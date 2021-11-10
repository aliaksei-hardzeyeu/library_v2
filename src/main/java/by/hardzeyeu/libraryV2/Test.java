package by.hardzeyeu.libraryV2;

import java.util.stream.IntStream;

public class Test {

    public static void main(String[] args) {

        User user = new User();
        System.out.println(user.getId());


    }

    static class User{
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}