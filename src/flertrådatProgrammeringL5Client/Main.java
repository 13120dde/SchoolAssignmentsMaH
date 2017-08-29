package flertrådatProgrammeringL5Client;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Patrik Lind on 2016-12-21.
 */
public class Main {

    public static void main(String[] args) {

        Client c1 = new Client(new GUIChatClient(), "Patrik", "localhost", 45450);
        Client c2 = new Client(new GUIChatClient(), "Lanie", "localhost", 45450);
        Client c3 = new Client(new GUIChatClient(), "William", "localhost", 45450);

    }
}
