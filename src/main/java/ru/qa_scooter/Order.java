package ru.qa_scooter;

import com.github.javafaker.Faker;

import java.util.List;

public class Order {
    public final String firstName;
    public final String lastName;
    public final String address;
    public final String metroStation;
    public final String phone;

    @Override
    public String toString() {
        return "Order{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", metroStation='" + metroStation + '\'' +
                ", phone='" + phone + '\'' +
                ", rentTime=" + rentTime +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", comment='" + comment + '\'' +
                ", color=" + color +
                '}';
    }

    public final int rentTime;
    public final String deliveryDate;
    public final String comment;
    public List<String> color;

    public Order(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public static Order getRandom(List<String> getColor){
        Faker faker = new Faker();
        final String firstName = faker.name().firstName();
        final String lastName = faker.name().lastName();
        final String address = faker.address().fullAddress();
        final String metroStation = faker.pokemon().name();
        final String phone = faker.phoneNumber().phoneNumber();
        final int rentTime = faker.random().nextInt(1, 30);
        final String deliveryDate = faker.business().creditCardExpiry();
        final String comment = faker.howIMetYourMother().catchPhrase();
        final List<String> color = getColor;
        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }
}
