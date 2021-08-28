package com.revature.johnKimAPI.pojos;
import java.util.Objects;

public class EnrolledCourse {
    private String classID;
    private String name;
    boolean isOpen;
    private String teacher;
    private String username;

    public EnrolledCourse() {
    }

    public EnrolledCourse(String classID, String name, String teacher, String username) {
        this.classID = classID;
        this.name = name;
        this.teacher = teacher;
        this.username = username;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnrolledCourse that = (EnrolledCourse) o;
        return isOpen == that.isOpen && Objects.equals(classID, that.classID) && Objects.equals(name, that.name) && Objects.equals(teacher, that.teacher) && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classID, name, isOpen, teacher, username);
    }

    @Override
    public String toString() {
        return "EnrolledCourse{" +
                "classID='" + classID + '\'' +
                ", name='" + name + '\'' +
                ", isOpen=" + isOpen +
                ", teacher='" + teacher + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}