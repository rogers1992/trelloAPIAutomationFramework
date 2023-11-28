package com.trello.api.steps;

import io.cucumber.java.en.When;

public class BoardStepdefs {
    @When("I create a board with name {string}")
    public void iCreateABoardWithName(String boardName) {
        System.out.println("Create boards with name".concat(boardName).concat("Using Cucumber"));
    }
}
