package ru.duckcoder.fintrack.backend.controller;

import io.javalin.http.Context;
import ru.duckcoder.fintrack.core.dto.account.AccountCreateDTO;
import ru.duckcoder.fintrack.core.dto.account.AccountDTO;
import ru.duckcoder.fintrack.core.dto.account.AccountUpdateDTO;
import ru.duckcoder.fintrack.core.dto.person.PersonDTO;

import java.util.List;

public interface AccountController extends Routable {
     void create(Context ctx);
     void read(Context ctx);
     void readAll(Context ctx);
     void update(Context ctx);
     void delete(Context ctx);
}
