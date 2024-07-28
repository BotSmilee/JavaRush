package Hippodrome;

import java.util.ArrayList;
import java.util.List;

public class Hippodrome {
    private List<Horse> horses;
    static Hippodrome game;

    public Hippodrome(List<Horse> horses) {
        this.horses = horses;
    }

    public static void main(String[] args) {
        ArrayList<Horse> horsesMain = new ArrayList<>();
        horsesMain.add(new Horse("Jan", 3, 0));
        horsesMain.add(new Horse("Amigo", 3, 0));
        horsesMain.add(new Horse("Dio", 3, 0));
        game = new Hippodrome(horsesMain);
        game.run();
        game.printWinner();
    }

    public Horse getWinner() {
        Horse fastest = horses.get(0);
        for (int i = 1; i < horses.size(); i++) {
            if (horses.get(i).distance > fastest.distance) {
                fastest = horses.get(i);
            }
        }
        return fastest;
    }

    public void printWinner() {
        System.out.println("Winner is " + getWinner().name + '!');
    }

    void run() {
        for (int i = 1; i < 101; i++) {
            move();
            print();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    void move() {
        for (int i = 0; i < getHorses().size(); i++) {
            getHorses().get(i).move();
        }
    }

    void print() {
        for (int i = 0; i < getHorses().size(); i++) {
            getHorses().get(i).print();
        }
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }

    public List<Horse> getHorses() {
        return horses;
    }
}
