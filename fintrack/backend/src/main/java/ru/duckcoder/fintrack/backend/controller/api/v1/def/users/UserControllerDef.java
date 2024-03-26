package ru.duckcoder.fintrack.backend.controller.api.v1.def.users;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.backend.config.DependencyProvider;
import ru.duckcoder.fintrack.backend.controller.api.v1.UserController;
import ru.duckcoder.fintrack.core.dto.user.UserCreateDTO;
import ru.duckcoder.fintrack.core.dto.user.UserDTO;
import ru.duckcoder.fintrack.core.dto.user.UserUpdateDTO;
import ru.duckcoder.fintrack.backend.service.UserService;

import java.util.List;

@Log4j2
public final class UserControllerDef implements UserController {
    @Override
    public void create(Context ctx) {
        try {
            UserCreateDTO dto = ctx.bodyAsClass(UserCreateDTO.class);
            UserDTO response = this.getService().create(dto);
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
            UserDTO response = this.getService().read(id);
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
            List<UserDTO> response = this.getService().readAll();
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
            UserUpdateDTO dto = ctx.bodyAsClass(UserUpdateDTO.class);
            UserDTO response = this.getService().update(id, dto);
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
                .get(this.specPath(), this::read)
                .get(this.rootPath(), this::readAll)
                .put(this.specPath(), this::update)
                .delete(this.specPath(), this::delete);
    }

    private UserService getService() {
        return DependencyProvider.getInstance().getImplementation(UserService.class);
    }

    private String newPath() {
        return new StringBuilder()
                .append(this.rootPath())
                .append('/').append("new")
                .toString();
    }

    private String specPath() {
        return new StringBuilder()
                .append(this.rootPath())
                .append('/').append("{id}")
                .toString();
    }
}
