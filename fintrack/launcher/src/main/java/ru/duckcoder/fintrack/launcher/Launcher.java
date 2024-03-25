package ru.duckcoder.fintrack.launcher;

import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.backend.Server;
import ru.duckcoder.fintrack.desktop.Client;

@Log4j2
public class Launcher {
    public static void main(String[] args) {
        ThreadGroup application = new ThreadGroup(Thread.currentThread().getThreadGroup(), "Desktop Application");

        Server server = new Server(args);
        Thread backend = new Thread(application, server, "Server Thread");

        Thread frontend = new Thread(application, new Client(args), "Client Thread");

        try {
            backend.start();
            frontend.start();
        } catch (Exception e) {
            log.error(e);
        }

        try {
            frontend.join();
            server.stop();
            backend.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
