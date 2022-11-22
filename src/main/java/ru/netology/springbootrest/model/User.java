package ru.netology.springbootrest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public record User(
        @NotEmpty @Size(min = 2, max = 15, message = "login should be between 2 and 15 characters") String login,
        @NotEmpty @Size(min = 5, max = 30, message = "Password should be between 5 and 30 characters") String password) {
}