package ru.duckcoder.fintrack.launcher;

import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.backend.Server;
import ru.duckcoder.fintrack.frontend.desktop.DesktopFrontend;

@Log4j2
public class Launcher {
    public static void main(String[] args) {
        ThreadGroup application = new ThreadGroup(Thread.currentThread().getThreadGroup(), "Desktop Application");


        Thread backend = new Thread(application, new Server(args),"Server");

        Thread frontend = new Thread(application, new DesktopFrontend(args), "Client");

        try {
            backend.start();
            frontend.start();
        } catch (Exception e) {
            log.error(e);
        }

        boolean isAlive = true;
        while (isAlive) {
            if (!frontend.isAlive()) {
                log.debug(new StringBuilder().append(frontend.getName()).append(" is dead"));
                backend.interrupt();
                isAlive = false;
            }
        }
    }
}
