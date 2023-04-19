package com.example.koreanrestaurantapp.model;

public class User {

    private String name;
    private String password;
    private String phone;
    private String active;
    private String age;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String gender;
    private String email;
    private String image;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


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

    public User(String name, String password, String active,String email, String gender,String age) {
        this.name = name;
        this.password = password;
        this.active = active;
        this.email=email;
        this.gender=gender;
        this.age=age;
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
