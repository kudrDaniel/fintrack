package ru.duckcoder.fintrack.desktop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.javafx.collections.ImmutableObservableList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.FocusModel;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.core.dto.account.AccountDTO;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Log4j2
public class AccountController {
    @FXML
    private Button readAllButton;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private ListView<String> accountsList;

    private final ObjectMapper om = new ObjectMapper();

    @FXML
    private void onReadAllButtonClick(ActionEvent event) {
        HttpResponse<String> response = Unirest.get("http://localhost:8095/api/accounts")
                .accept("application/json")
                .asString();
        try {
            AccountDTO[] accounts = om.readValue(response.getBody(), AccountDTO[].class);
            accountsList.setItems(FXCollections.observableList(Arrays.stream(accounts).map(AccountDTO::toString).toList()));
        } catch (Exception e) {
            log.error(e);
        }
    }

    @FXML
    private void onAddButtonClick(ActionEvent event) {
        if (username.getCharacters().isEmpty() || password.getCharacters().isEmpty())
            return;
        Map<String, String> body = Map.of(
                "username", username.getCharacters().toString(),
                "password", password.getCharacters().toString()
        );

        try {
            HttpResponse<String> response = Unirest.post("http://localhost:8095/api/accounts/new")
                    .accept("application/json")
                    .body(om.writeValueAsString(body))
                    .asString();
            List<String> mutableList = new ArrayList<>(accountsList.getItems());
            mutableList.add(om.readValue(response.getBody(), AccountDTO.class).toString());
            accountsList.setItems(FXCollections.observableList(mutableList));
        } catch (Exception e) {
            log.error(e);
        }
    }

    @FXML
    private void onUpdateButtonClick(ActionEvent event) {
        Map<String, String> body = new HashMap<>();
        if (!username.getCharacters().isEmpty())
            body.put("username", username.getCharacters().toString());
        if (!password.getCharacters().isEmpty())
            body.put("password", password.getCharacters().toString());
        int id = accountsList.getFocusModel().getFocusedIndex();

        try {
            HttpResponse<String> response = Unirest.put("http://localhost:8095/api/accounts/" + (id + 1))
                    .accept("application/json")
                    .body(om.writeValueAsString(body))
                    .asString();
            List<String> mutableList = new ArrayList<>(accountsList.getItems());
            mutableList.remove(id);
            mutableList.add(id, om.readValue(response.getBody(), AccountDTO.class).toString());
            accountsList.getItems().remove(id);
            accountsList.setItems(FXCollections.observableList(mutableList));
        } catch (Exception e) {
            log.error(e);
        }
    }
}
