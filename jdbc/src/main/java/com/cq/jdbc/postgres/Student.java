package com.cq.jdbc.postgres;

/**
 * @Author chenquan
 * @Description
 * @Date 2022/6/21 9:03
 **/


public class Student {
    private String Id;
    private String Name;
    private String Sex;
    private String Age;

    Student(String Name, String Sex, String Age) {
        this.Name = Name;
        this.Sex = Sex;
        this.Age = Age;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String Sex) {
        this.Sex = Sex;
    }

    public String getAge() {
        return Age;
    }

    public void setage(String Age) {
        this.Age = Age;
    }
}
