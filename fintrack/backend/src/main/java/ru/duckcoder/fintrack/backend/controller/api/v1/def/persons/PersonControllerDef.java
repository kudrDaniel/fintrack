package ru.duckcoder.fintrack.backend.controller.api.v1.def.persons;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import ru.duckcoder.fintrack.backend.config.DependencyProvider;
import ru.duckcoder.fintrack.backend.controller.api.v1.PersonController;
import ru.duckcoder.fintrack.backend.service.PersonService;
import ru.duckcoder.fintrack.core.dto.person.PersonDTO;

import java.util.List;

public final class PersonControllerDef implements PersonController {
    @Override
    public void create(Context ctx) {
        ctx.json("Unable to create abstract person")
                .status(HttpStatus.BAD_REQUEST);
    }

    @Override
    public void read(Context ctx) {
        try {
            long id = ctx.pathParamAsClass("id", Long.class).get();
            PersonDTO response = this.getService().read(id);
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
            List<PersonDTO> response = this.getService().readAll();
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
        ctx.json("Unable to update abstract person")
                .status(HttpStatus.BAD_REQUEST);
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

    private PersonService getService() {
        return DependencyProvider.getInstance().getImplementation(PersonService.class);
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
