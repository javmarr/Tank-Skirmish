package javmarr.mazeGame;

public class Node {

    public Node next, prev;
    public int score;
    public String name;

    Node(String str, int x) {
        name = str;
        score = x;
        next = null;
        prev = null;
    }

    public String toString() {
        return (name + ": " + score + "\n");
    }

}
