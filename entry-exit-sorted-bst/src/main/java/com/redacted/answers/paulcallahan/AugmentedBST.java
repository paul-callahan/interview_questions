package com.redacted.answers.paulcallahan;

import com.redacted.model.Employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


/**
 * BST for time intervals.  Sorted by entered-time.
 *
 * Each node is augmented with a max exit time all subnodes.
 *
 * Created by paul on 11/8/16.
 */
public class AugmentedBST {

    private Node root;

    private static final class Node {
        final EnterExit interval;
        final List<Employee> value;
        Node left, right;
        int size;
        long max;

        Node(EnterExit interval, Employee value) {
            this.interval = interval;
            this.value = new LinkedList<>();
            this.value.add(value);
            this.size = 1;
            this.max = interval.exit;
        }
    }


    /**
     * find a node with this EnterExit interval or return null.
     *
     * @param node
     * @param interval
     * @return
     */
    private Node get(Node node, EnterExit interval) {
        if (node == null) return null;
        int cmp = interval.compareTo(node.interval);
        if (cmp < 0) return get(node.left, interval);
        else if (cmp > 0) return get(node.right, interval);
        else return node;
    }


    /**
     * Insert a new entry/exit record.
     */
    public final void add(EnterExit interval, Employee value) {
        Node exists = get(root, interval);
        if (exists != null) {
            System.out.println("interval collision!");
            exists.value.add(value);
        } else {
            root = insert(root, interval, value);
        }
    }

    private Node insert(Node node, EnterExit interval, Employee value) {
        if (node == null)
            return new Node(interval, value);
        if (Math.random() * size(node) < 1.0)
            return insertAtRoot(node, interval, value);
        if (interval.compareTo(node.interval) < 0)
            node.left = insert(node.left, interval, value);
        else
            node.right = insert(node.right, interval, value);
        fixMax(node);
        return node;
    }

    private Node insertAtRoot(Node node, EnterExit interval, Employee value) {
        if (node == null) return new Node(interval, value);
        // less goes left
        if (interval.compareTo(node.interval) < 0) {
            node.left = insertAtRoot(node.left, interval, value);
            node = rotateR(node);
        } else {
            node.right = insertAtRoot(node.right, interval, value);
            node = rotateL(node);
        }
        return node;
    }

    /**
     * Search tree for all intervals intersecting with checkTime
     *
     * @param checkTime  time
     * @param pageOffset page to start on
     * @param pageSize   page size
     * @return list of employees
     */
    public final List<Employee> search(Date checkTime, int pageOffset, int pageSize) {
        List<Employee> list = new ArrayList<>(pageSize);
        search(root, checkTime.getTime(), list, pageOffset, pageSize, 0);
        return list;
    }

    private boolean search(Node node, long checkTime, List<Employee> list, int pageOffset, int pageSize, int currOffset) {

        if (node == null)
            return false;
        boolean foundRoot = false;
        if (node.interval.contains(checkTime)) {
            if (currOffset >= (pageOffset * pageSize)) {
                list.addAll(node.value);
                foundRoot = true;
                if (list.size() >= pageSize)
                    return true;
            }
            currOffset++;
        }

        boolean foundLeft = false;
        if (node.left != null && node.left.max >= checkTime)
            foundLeft = search(node.left, checkTime, list, pageOffset, pageSize, currOffset);

        boolean foundRight = false;
        if (foundLeft || node.left == null || node.left.max < checkTime)
            foundRight = search(node.right, checkTime, list, pageOffset, pageSize, currOffset);
        return foundRoot || foundLeft || foundRight;
    }


    private int size(Node node) {
        if (node == null)
            return 0;
        else return node.size;
    }


    private void fixMax(Node node) {
        if (node == null)
            return;
        node.size = 1 + size(node.left) + size(node.right);
        node.max = max(node.interval.exit, max(node.left), max(node.right));
    }

    private long max(Node node) {
        if (node == null)
            return Integer.MIN_VALUE;
        return node.max;
    }

    private long max(long a, long b, long c) {
        return Math.max(a, Math.max(b, c));
    }

    private Node rotateR(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        fixMax(h);
        fixMax(x);
        return x;
    }

    private Node rotateL(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        fixMax(h);
        fixMax(x);
        return x;
    }


}