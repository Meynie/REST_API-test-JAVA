package ru.qa_scooter;

public class CourierCredentials {
    @Override
    public String toString() {
        return "CourierCredentials{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public final String login;
    public final String password;

    public CourierCredentials(String login, String password){
        this.login = login;
        this.password = password;
    }

    public static CourierCredentials getCourierCredentials(Courier courier){
        return new CourierCredentials(courier.login, courier.password);
    }
}
