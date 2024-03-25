package ru.duckcoder.fintrack.backend.controller.custom;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import ru.duckcoder.fintrack.backend.config.DependencyProvider;
import ru.duckcoder.fintrack.backend.controller.AccountController;
import ru.duckcoder.fintrack.core.dto.account.AccountCreateDTO;
import ru.duckcoder.fintrack.core.dto.account.AccountDTO;
import ru.duckcoder.fintrack.core.dto.account.AccountUpdateDTO;
import ru.duckcoder.fintrack.backend.service.AccountService;

import java.util.List;

public final class AccountControllerImpl implements AccountController {
    private AccountService getService() {
        return DependencyProvider.getInstance().getImplementation(AccountService.class);
    }

    @Override
    public void create(Context ctx) {
        try {
            AccountCreateDTO dto = ctx.bodyAsClass(AccountCreateDTO.class);
            AccountDTO response = this.getService().create(dto);
            ctx.json(response)
                    .status(HttpStatus.CREATED);
        } catch (Exception e) {
            ctx.json(e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void read(Context ctx) {
        try {
            long id = ctx.pathParamAsClass("id", Long.class).get();
            AccountDTO response = this.getService().read(id);
            ctx.json(response)
                    .status(HttpStatus.OK);
        } catch (Exception e) {
            ctx.json(e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void readAll(Context ctx) {
        try {
            List<AccountDTO> response = this.getService().readAll();
            ctx.json(response)
                    .header("X-Total-Count", String.valueOf(response.size()))
                    .status(HttpStatus.OK);
        } catch (Exception e) {
            ctx.json(e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void update(Context ctx) {
        try {
            long id = ctx.pathParamAsClass("id", Long.class).get();
            AccountUpdateDTO dto = ctx.bodyAsClass(AccountUpdateDTO.class);
            AccountDTO response = this.getService().update(id, dto);
            ctx.json(response)
                    .status(HttpStatus.OK);
        } catch (Exception e) {
            ctx.json(e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(Context ctx) {
        try {
            long id = ctx.pathParamAsClass("id", Long.class).get();
            this.getService().delete(id);
            ctx.status(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            ctx.json(e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void route(Javalin javalin) {
        javalin.post(this.newPath(), this::create)
                .get(this.allPath(), this::readAll)
                .get(this.specPath(), this::read)
                .put(this.specPath(), this::update)
                .delete(this.specPath(), this::delete);
    }

    private String newPath() {
        return new StringBuilder()
                .append(this.allPath())
                .append('/').append("new")
                .toString();
    }

    private String allPath() {
        return new StringBuilder()
                .append(this.apiPath())
                .append('/').append("accounts")
                .toString();
    }

    private String specPath() {
        return new StringBuilder()
                .append(this.allPath())
                .append('/').append("{id}")
                .toString();
    }
}
