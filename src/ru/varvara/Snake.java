package ru.varvara;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private SnakeDirection direction;
    private boolean isAlive;
    private List<SnakeSection> sections;

    public Snake(int x, int y) {
        sections = new ArrayList<>();
        sections.add(new SnakeSection(x, y));
        isAlive = true;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getX() {
        return sections.get(0).getX();
    }

    public int getY() {
        return sections.get(0).getY();
    }

    public SnakeDirection getDirection() {
        return direction;
    }

    public void setDirection(SnakeDirection direction) {
        this.direction = direction;
    }

    public List<SnakeSection> getSections() {
        return sections;
    }

    /**
     * The method moves the snake by one move.
     * The direction of movement is set by the direction variable.
     */
    public void move() {
        if (!isAlive) return;

        if (direction == SnakeDirection.UP)
            move(0, -1);
        else if (direction == SnakeDirection.RIGHT)
            move(1, 0);
        else if (direction == SnakeDirection.DOWN)
            move(0, 1);
        else if (direction == SnakeDirection.LEFT)
            move(-1, 0);
    }

    /**
     * The method moves the snake to the next cage.
     * Cell coordinates are set relative to the current head using variables (dx, dy).
     */
    void move(int dx, int dy) {
        //Creating a new head - a new "piece of snake".
        SnakeSection head = sections.get(0);
        head = new SnakeSection(head.getX() + dx, head.getY() + dy);

        // Check whether the head has not got out of the room
        checkBorders(head);
        if (!isAlive) return;

        // Check if the snake crosses itself
        checkBody(head);
        if (!isAlive) return;

        // Check if the snake has eaten the mouse.
        Mouse mouse = Room.game.getMouse();
        if (head.getX() == mouse.getX() && head.getY() == mouse.getY()) // If ate
        {
            sections.add(0, head); // Added a new head
            Room.game.eatMouse(); // We don't delete the tail, but we create a new mouse.
        } else // Else it will continue to move
        {
            sections.add(0, head); // Added a new head
            sections.remove(sections.size() - 1); // Removed the last element from the tail
        }
    }

    /**
     * The method checks whether the new head is located within the room.
     */
    private void checkBorders(SnakeSection head) {
        if ((head.getX() < 0 || head.getX() >= Room.game.getWidth()) || head.getY() < 0 || head.getY() >= Room.game.getHeight()) {
            isAlive = false;
        }
    }

    /**
     * The method checks whether the head does not coincide with any part of the snake's body.
     */
    private void checkBody(SnakeSection head) {
        if (sections.contains(head)) {
            isAlive = false;
        }
    }
}
