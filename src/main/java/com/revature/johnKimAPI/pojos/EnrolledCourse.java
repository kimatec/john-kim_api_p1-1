package com.revature.johnKimAPI.pojos;
import java.util.Objects;

public class EnrolledCourse {
    private String classID;
    private String name;
    boolean isOpen;

    public EnrolledCourse() {
    }

    public EnrolledCourse(String classID, String name) {
        this.classID = classID;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnrolledCourse that = (EnrolledCourse) o;
        return isOpen == that.isOpen && Objects.equals(classID, that.classID) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classID, name, isOpen);
    }

    @Override
    public String toString() {
        return "EnrolledCourse{" +
                "classID='" + classID + '\'' +
                ", name='" + name + '\'' +
                ", isOpen=" + isOpen +
                '}';
    }
}