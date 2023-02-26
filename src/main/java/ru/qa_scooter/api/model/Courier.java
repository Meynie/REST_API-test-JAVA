package ru.qa_scooter.api.model;

import com.github.javafaker.Faker;

public class Courier {
    public final String login;
    public final String password;
    public final String firstName;
    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public static Courier getRandom() {
        Faker faker = new Faker();
        final String login = faker.name().firstName();
        final String password = faker.name().firstName();
        final String firstName = faker.name().firstName();
        return new Courier(login, password, firstName);
    }

    @Override
    public String toString() {
        return "Courier{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}