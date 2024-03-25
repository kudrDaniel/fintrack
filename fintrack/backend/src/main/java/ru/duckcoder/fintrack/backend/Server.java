package ru.duckcoder.fintrack.backend;
import io.javalin.Javalin;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.backend.config.EntityManagerProvider;
import ru.duckcoder.fintrack.backend.controller.custom.AccountControllerImpl;

import java.util.Arrays;

import static ru.duckcoder.fintrack.core.config.Properties.FINTRACK_SERVER_PORT;

@Log4j2
public class Server implements Runnable {
    private final String[] args;
    private boolean isStopping = false;

    public Server(String[] args) {
        this.args = Arrays.copyOf(args, args.length);
    }

    public static void main(String[] args) throws Exception {
        Thread javalinThread = new Thread(
                Thread.currentThread().getThreadGroup(),
                new Server(args),
                "Javalin Thread");
        javalinThread.start();
        try {
            javalinThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Javalin initJavalin() {
        Javalin javalin = Javalin.create();

        javalin.before(context -> context.contentType("application/json; charset=UTF-8"))
                .get("/shutdown", ctx -> {
                    ctx.json("Shutting down server");
                    this.stop();
                });

        AccountControllerImpl accountController = new AccountControllerImpl();
        accountController.route(javalin);

        return javalin;
    }

    private synchronized boolean isStopping() {
        return this.isStopping;
    }

    public synchronized void stop() {
        this.isStopping = true;
    }

    @Override
    public void run() {
        try {
            EntityManagerProvider.getInstance();

            Javalin javalin = initJavalin();
            String webPort = System.getProperty(FINTRACK_SERVER_PORT.getKey(), FINTRACK_SERVER_PORT.getDef());

            javalin.start(Integer.parseInt(webPort));
            while (true) {
                if (this.isStopping()) {
                    Thread stopThread = new Thread(javalin::stop, "Javalin Stop Thread");
                    stopThread.start();
                    stopThread.join();
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
