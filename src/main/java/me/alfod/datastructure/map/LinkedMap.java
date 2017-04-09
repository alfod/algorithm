package me.alfod.datastructure.map;

import me.alfod.utils.Prime;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/30.
 */

public class LinkedMap<Key extends Serializable, Value> extends BaseHash<Key, Value> implements Map<Key, Value> {

    private int index;


    @SuppressWarnings(value = "unchecked")
    public LinkedMap() {
        table = new HashNode[defaultLength];
    }

    public void add(Key key, Value value, HashNode<Key, Value>[] nodes) {
        index = getIndex(key);
        if (nodes[index] == null) {
            nodes[index] = new HashNode<>(key, value);
        } else {
            int count = 0;
            HashNode<Key, Value> node = table[index];
            while (node.getNext() != null) {
                if (node.getKey().equals(key)) {
                    node.setValue(value);
                    return;
                }
                node = node.getNext();
                count++;
            }
            if (node.getKey().equals(key)) {
                node.setValue(value);
            } else {
                node.setNext(new HashNode<>(key, value));
            }
            if (count > Math.sqrt(table.length)) {
                extend();
            }
        }
    }

    @Override
    public void add(Key key, Value value) {


    }

    @SuppressWarnings("unchecked")
    private HashNode<Key, Value>[] extend(HashNode<Key, Value>[] hashNodes) {
        HashNode<Key, Value>[] newTable = new HashNode[Prime.getNext(2 * hashNodes.length)];
        for (HashNode<Key, Value> node : table) {
            while (node != null) {
                add(node.getKey(), node.getValue(), newTable);
                node = node.getNext();
            }
        }
        return newTable;
    }

    private void extend() {
        table = extend(table);
    }

    @Override
    public Value get(Key key) {
        index = getIndex(key);
        HashNode<Key, Value> node = table[index];
        while (node != null) {
            if (node.getKey().equals(key)) {
                return node.getValue();
            }
            node = node.getNext();
        }
        return null;
    }

    @Override
    public void del(Key key) {
        index = getIndex(key);
        HashNode<Key, Value> node = table[index];
        if (node == null) {
            return;
        }
        if (node.getKey().equals(key)) {
            table[index] = (null == node.getNext() ? null : node.getNext());
            return;
        }
        while (node.getNext() != null) {
            if (node.getNext().getKey().equals(key)) {
                if (node.getNext().getNext() == null) {
                    node.setNext(null);
                } else {
                    node.setNext(node.getNext().getNext());
                }
                return;
            }
            node = node.getNext();
        }

    }

    @Override
    public void set(Key key, Value value) {
        add(key, value);
    }


}


