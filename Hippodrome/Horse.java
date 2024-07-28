package com.javarush.task.task21.task2113;

public class Horse {
    public Horse(String name, double speed, double distance) {
        this.name = name;
        this.speed = speed;
        this.distance = distance;
    }

    public String name;
    public double speed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double distance;

    public void move() {
        distance+=speed*Math.random();
    }
    public void print() {
        for (int i = 0; i < Math.round(distance-0.5); i++) {
            System.out.print('.');
        }
        System.out.print(name + "\n");
    }
}
