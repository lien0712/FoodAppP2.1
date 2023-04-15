package com.example.koreanrestaurantapp.model;

public class User {

    private String name;
    private String password;
    private String phone;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String active;
    private String age;
    private String sex;
    private String email;
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String name, String password, String active) {
        this.name = name;
        this.password = password;
        this.active = active;
    }

    private User(String name, String password, String phone, String active) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.active = active;
    }
    public User() {

    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
