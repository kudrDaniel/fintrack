package ru.duckcoder.fintrack.backend.controller.api.v1.def.persons.companies;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import ru.duckcoder.fintrack.backend.config.DependencyProvider;
import ru.duckcoder.fintrack.backend.controller.api.v1.CompanyController;
import ru.duckcoder.fintrack.backend.service.CompanyService;
import ru.duckcoder.fintrack.core.dto.person.company.CompanyCreateDTO;
import ru.duckcoder.fintrack.core.dto.person.company.CompanyDTO;
import ru.duckcoder.fintrack.core.dto.person.company.CompanyUpdateDTO;

import java.util.List;

public class CompanyControllerDef implements CompanyController {
    @Override
    public void create(Context ctx) {
        try {
            CompanyCreateDTO dto = ctx.bodyAsClass(CompanyCreateDTO.class);
            CompanyDTO response = this.getService().create(dto);
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
            CompanyDTO response = this.getService().read(id);
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
            List<CompanyDTO> response = this.getService().readAll();
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
            CompanyUpdateDTO dto = ctx.bodyAsClass(CompanyUpdateDTO.class);
            CompanyDTO response = this.getService().update(id, dto);
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

    private CompanyService getService() {
        return DependencyProvider.getInstance().getImplementation(CompanyService.class);
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
