/*
 * Copyright (c) 2021.
 * File : BinaryHeap.java
 * Author : Ankur
 * Last modified : 4/4/2021
 * Problem Statement at the end of the code
 *
 * All code is for practice purpose only and strictly non-commercial.
 * All rights reserved.
 * Please refer to apache license terms in the project.
 */

package dsa;

import java.lang.reflect.Array;

public class BinaryHeap<T extends Comparable> {
    public static int MAX_SIZE = 40;
    public int count = 0;
    private T[] arr;

    public BinaryHeap(Class<T> clazz) {
        this(clazz, MAX_SIZE);
    }

    public BinaryHeap(Class<T> clazz, int size) {
        this.arr = (T[]) Array.newInstance(clazz, size);
    }

    public int getLeftChildIndex(int index) {
        int leftChildIndex = 2 * index + 1;
        if (leftChildIndex >= count)
            return -1;

        return leftChildIndex;
    }

    public int getRightChildIndex(int index) {
        int rightChildIndex = 2 * index + 2;
        if (rightChildIndex >= count)
            return -1;

        return rightChildIndex;
    }

    public int getParentIndex(int index) {
        if (index < 0 || index > count)
            return -1;

        return (index - 1) / 2;
    }

    public int getCount() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public boolean isFull() {
        return count == arr.length;
    }

    public T getElementAtIndex(int index) {
        return arr[index];
    }

    protected void swap(int index1, int index2) {
        T temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    public static void main(String[] args) {

    }
}

class MinHeap<T extends Comparable> extends BinaryHeap<T> {
    public MinHeap(Class<T> clazz) {
        super(clazz);
    }

    public MinHeap(Class<T> clazz, int size) {
        super(clazz, size);
    }

    protected void siftDown(int index) {
        int leftChildIndex = getLeftChildIndex(index);
        int rightChildIndex = getRightChildIndex(index);

        int smallerIndex = -1;
        if (leftChildIndex != -1 && rightChildIndex != -1)
            smallerIndex = getElementAtIndex(leftChildIndex).compareTo(getElementAtIndex(rightChildIndex)) < 0
                    ? leftChildIndex : rightChildIndex;
        else if (leftChildIndex != -1)
            smallerIndex = leftChildIndex; // Right child does not exist
        else if (rightChildIndex != -1)
            smallerIndex = rightChildIndex; // Left child doesnot exist

        if(smallerIndex == -1)
            return;

        if (getElementAtIndex(index).compareTo(getElementAtIndex(smallerIndex)) > 0) {
            swap(index, smallerIndex);
            siftDown(smallerIndex);
        } else return;
    }

    protected void siftUp(int index) {
        int parentIndex = getParentIndex(index);

        if (parentIndex != -1 &&
                getElementAtIndex(index).compareTo(getElementAtIndex(parentIndex)) < 0) {
            swap(index, parentIndex);
            siftUp(parentIndex);
        } else return;
    }
}
