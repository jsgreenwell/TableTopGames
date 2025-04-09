public class Player {
    public String name;
    public int score;

    public Player() {}
    public Player(String name, int score) {}

    @Override
    public String toString() {
        return "Player " + name + "with score of " + score;
    }
}
