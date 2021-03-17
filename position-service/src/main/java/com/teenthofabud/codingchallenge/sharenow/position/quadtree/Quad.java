package com.teenthofabud.codingchallenge.sharenow.position.quadtree;

import lombok.Getter;
import lombok.Setter;

import java.awt.geom.Point2D;


@Getter
@Setter
public class Quad<T extends Node<?>, K> {

    private Point topLeft;
    private Point bottomRight;
    private Node<K> node;
    private Quad<T, K> topLeftTree;
    private Quad<T, K> bottomLeftTree;
    private Quad<T, K> topRightTree;
    private Quad<T, K> bottomRightTree;

    public Quad() {
        this.topLeft = new Point(0.0d, 0.0d);
        this.bottomRight = new Point(0.0d, 0.0d);
    }

    public Quad(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    private boolean withinBoundary(Point point)
    {
        return point.getX() >= this.topLeft.getX() && point.getX() <= this.bottomRight.getX()
                &&
                point.getY() >= this.topLeft.getY() && point.getY() <= this.bottomRight.getY();
    }

    public void insert(Node<K> node) {
        if (node == null) {
            return;
        }
        // Current quad cannot contain it
        if (!this.withinBoundary(node.getPoint())) {
            return;
        }

        // We are at a quad of unit area
        // We cannot subdivide this quad further
        if (Math.abs(this.topLeft.getX() - this.bottomRight.getX()) <= 1 && Math.abs(this.topLeft.getY() - this.bottomRight.getY()) <= 1) {
            if (this.node == null) {
                this.node = node;
            }
            return;
        }

        if ((this.topLeft.getX() + this.bottomRight.getX()) / 2 >= node.getPoint().getX()) {
            if ((this.topLeft.getY() + this.bottomRight.getY()) / 2 >= node.getPoint().getY()) {
                // Indicates topLeftTree
                if (this.topLeftTree == null) {
                    Point topLeft = new Point(this.topLeft.getX(), this.topLeft.getY());
                    Point bottomRight = new Point(
                            (this.topLeft.getX() + this.bottomRight.getX()) / 2,
                            (this.topLeft.getY() + this.bottomRight.getY()) / 2);
                    this.topLeftTree = new Quad<>(topLeft, bottomRight);
                }
                this.topLeftTree.insert(node);
            } else {
                // Indicates bottomLeftTree
                if (this.bottomLeftTree == null) {
                    Point topLeft = new Point(this.topLeft.getX(),
                            (this.topLeft.getY() + this.bottomRight.getY()) / 2);
                    Point bottomRight = new Point((this.topLeft.getX() + this.bottomRight.getY()) / 2,
                            this.bottomRight.getY());
                    this.bottomLeftTree = new Quad<>(topLeft, bottomRight);
                }
                this.bottomLeftTree.insert(node);
            }
        } else {
            if ((this.topLeft.getY() + this.bottomRight.getY()) / 2 >= node.getPoint().getY()) {
                // Indicates topRightTree
                if (this.topRightTree == null) {
                    Point topLeft = new Point((this.topLeft.getX() + this.bottomRight.getX()) / 2,
                            this.topLeft.getY());
                    Point bottomRight = new Point(this.bottomRight.getX(),
                            (this.topLeft.getY() + this.bottomRight.getY()) / 2);
                    this.topRightTree = new Quad<>(topLeft, bottomRight);
                }
                this.topRightTree.insert(node);
            } else {
                // Indicates bottomRightTree
                if (this.bottomRightTree == null) {
                    Point topLeft = new Point((this.topLeft.getX() + this.bottomRight.getX()) / 2,
                            (this.topLeft.getY() + this.bottomRight.getY()) / 2);
                    Point bottomRight = new Point(this.bottomRight.getX(), this.bottomRight.getY());
                    this.bottomRightTree = new Quad<>(topLeft, bottomRight);
                }
                this.bottomRightTree.insert(node);
            }
        }
    }

    public Node<K> search(Point point)
    {
        // Current quad cannot contain it
        if (!this.withinBoundary(point)) {
            return null;
        }

        // We are at a quad of unit length
        // We cannot subdivide this quad further
        if (this.node != null)
            return this.node;

        if ((this.topLeft.getX() + this.bottomRight.getX()) / 2 >= point.getX()) {
            if ((this.topLeft.getY() + this.bottomRight.getY()) / 2 >= point.getY()) {
                // Indicates topLeftTree
                if (this.topLeftTree == null) {
                    return null;
                }
                return this.topLeftTree.search(point);
            } else {
                // Indicates bottomLeftTree
                if (this.bottomLeftTree == null) {
                    return null;
                }
                return this.bottomLeftTree.search(point);
            }
        } else {
            // Indicates topRightTree
            if ((this.topLeft.getY() + this.bottomRight.getY()) / 2 >= point.getY())
            {
                if (this.topRightTree == null) {
                    return null;
                }
                return this.topRightTree.search(point);
            } else {
                // Indicates bottomRightTree
                if (this.bottomRightTree == null) {
                    return null;
                }
                return this.bottomRightTree.search(point);
            }
        }
    };

    public static void main(String[] args) {
        Point topLeft = new Point(0.0d, 0.0d);
        Point bottomRight = new Point(8.0d, 8.0d);
        Quad<Node<Integer>, Integer> q = new Quad<Node<Integer>, Integer>(topLeft, bottomRight);
        Point bCoordinates = new Point(2.0d, 5.0d);
        Point aCoordinates = new Point(1.0d, 1.0d);
        Point cCoordinates = new Point(7.0d, 6.0d);
        Point dCoordinates = new Point(17.0d, 66.0d);
        Node<Integer> a = new Node<Integer>(1, aCoordinates);
        Node<Integer> b = new Node<>(2, bCoordinates);
        Node<Integer> c = new Node<>(3, cCoordinates);
        q.insert(a);
        q.insert(b);
        q.insert(c);
        Node<Integer> result = q.search(aCoordinates);
        System.out.println("Node a: " + (result != null ? result.getData() : "NOT FOUND"));
        result = q.search(bCoordinates);
        System.out.println("Node b: " + (result != null ? result.getData() : "NOT FOUND"));
        result = q.search(cCoordinates);
        System.out.println("Node c: " + (result != null ? result.getData() : "NOT FOUND"));
        result = q.search(dCoordinates);
        System.out.println("Node d: " + (result != null ? result.getData() : "NOT FOUND"));
    }

}
