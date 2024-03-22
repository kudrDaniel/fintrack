package ru.duckcoder.fintrack.backend;
import io.javalin.Javalin;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.backend.controller.AccountController;
import ru.duckcoder.fintrack.backend.controller.custom.AccountControllerImpl;
import ru.duckcoder.fintrack.core.dto.account.AccountCreateDTO;
import ru.duckcoder.fintrack.core.dto.account.AccountDTO;
import ru.duckcoder.fintrack.core.dto.account.AccountUpdateDTO;
import ru.duckcoder.fintrack.core.util.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.duckcoder.fintrack.core.config.Properties.FINTRACK_SERVER_PORT;

@Log4j2
public class Server implements Runnable {
    private final String[] args;

    public Server(String[] args) {
        this.args = Arrays.copyOf(args, args.length);
    }

    public static void main(String[] args) throws Exception {
        log.debug("Entry point reached.");

        Thread javalinThread = new Thread(
                Thread.currentThread().getThreadGroup(),
                new Server(args),
                "Javalin Thread");
        javalinThread.start();

        AccountCreateDTO accountCreateDTO0 = new AccountCreateDTO("user", "password");
        AccountCreateDTO accountCreateDTO1 = new AccountCreateDTO("lector", "12345");

        List<AccountDTO> accounts = new ArrayList<>();

        AccountController aController = new AccountControllerImpl();
        try {
            accounts.add(aController.create(accountCreateDTO0));
            accounts.add(aController.create(accountCreateDTO1));
            aController.update(1L, new AccountUpdateDTO(Nullable.of("kek"), Nullable.of("qwerty")));
            accounts = aController.readAll();
        } catch (Exception e) {
            log.error(e);
        }
        accounts.forEach(accountDTO -> log.debug(new StringBuilder().append('{').append(accountDTO).append('}')));

        javalinThread.interrupt();
        log.debug("End point reach");
    }

    private static Javalin initJavalin() {
        Javalin javalin = Javalin.create();

        javalin.before(context -> context.contentType("application/json; charset=UTF-8"));

/*        String webAppAbsolutePath = new File(new StringBuilder()
                .append(webAppDirLocation)
                .toString()
        ).getAbsolutePath();
        StandardContext context = (StandardContext) tomcat.addWebapp("/", webAppAbsolutePath);*/
        return javalin;
    }

    @Override
    public void run() {
        try {
            Javalin javalin = initJavalin();
            String webPort = System.getProperty(FINTRACK_SERVER_PORT.getKey(), FINTRACK_SERVER_PORT.getDef());
            javalin.start(Integer.parseInt(webPort));
            boolean isAlive = true;
            while (isAlive) {
                if (Thread.currentThread().isInterrupted()) {
                    new Thread(javalin::stop, "Javalin Stop Thread").start();
                    isAlive = false;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
