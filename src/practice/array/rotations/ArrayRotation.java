/*
 * Copyright (c) 2020.
 * File : ArrayRotation.java
 * Author : Ankur
 * Last modified : 5/9/2020
 * Problem Statement at the end of the code
 *
 * All code is for practice purpose only and strictly non-commercial.
 * All rights reserved.
 * Please refer to apache license terms in the project.
 */

package practice.array.rotations;

import java.util.Arrays;

// Problem Statement : https://www.geeksforgeeks.org/array-rotation/
public class ArrayRotation {
    // Approach 1 : TC - O(n), SC - O(n)
    public static int[] rightRotate(int[] arr, int k){
        int[] out = new int[arr.length];
        int n = arr.length;
        k = k%n;

        for(int i = 0; i<n; ++i){
            // Find the indx where rotate element will go
            int d = (i+k < n) ? i+k : i+k-n;

            // Update corresponding index
            out[d] = arr[i];
        }

        return out;
    }

    // Approach 2 : Right Rotate in O(1) space and O(n) time complexity
    public static int[] rotate(int[] nums, int rotations) {
        if(nums.length==0)
            return nums;

        rotations = rotations%nums.length;
        int start_idx = 0, prev = nums[start_idx], next = 0;

        for(int steps = 1; steps<=rotations; ++steps){
            int next_idx = start_idx+rotations;

            while(next_idx<nums.length){
                // Update corresponding index
                next = nums[next_idx];
                nums[next_idx] = prev;
                prev = next;

                next_idx += rotations;
            }

            start_idx = next_idx-nums.length;

            prev = nums[start_idx];
            nums[start_idx] = next;
        }

        return nums;
    }

    // Approach 3 : Rotate one at a time. TC - O(n*d), SC - O(1)
    static void leftRotate(int[] arr, int d)
    {
        for (int i = 0; i < d; i++)
            leftRotateByOne(arr);
    }

    static void leftRotateByOne(int[] arr)
    {
        int i, temp;
        temp = arr[0];
        for (i = 0; i < arr.length - 1; i++)
            arr[i] = arr[i + 1];
        arr[i] = temp;
    }

    // Approach 4 : Find GCD and rotate by set. TC - O(n), SC - O(1)
    static int gcd(int a, int b){
        if(b==0)
            return a;
        else return gcd(a, a%b);
    }

    static void rotateBySet(int[] arr, int d){
        d = d % arr.length;
        int j, k, temp;
        int g_c_d = gcd(d, arr.length);
        for (int i = 0; i < g_c_d; i++) {
            /* move i-th values of blocks */
            temp = arr[i];

            j = i;
            while (true) {
                k = j + d; // Calculate the index by adding offset
                if (k >= arr.length)
                    k = k - arr.length;
                if (k == i)
                    break;

                // Update ith element of corresponding set with ith element of next set
                arr[j] = arr[k];

                // Move on to next set
                j = k;
            }

            // Update ith element of last set
            arr[j] = temp;
        }
    }

    // https://www.geeksforgeeks.org/search-an-element-in-a-sorted-and-pivoted-array/
    public static int search(int[] arr, int key, int start, int end){
        if(start>=end)
            return -1;

        int mid = (start+end)/2;
        if(key==arr[mid])
            return mid;

        if(arr[start]<=arr[mid]){
            if(key>=arr[start] && key<arr[mid])
                search(arr, key, start, mid-1);
            else
                search(arr, key, mid+1, end);
        }

        if(key>arr[mid] && key<=arr[end-1])
            return search(arr, key, mid+1, end);
        else
            return search(arr, key, start,mid-1);
    }

    // Pivot in a sorted and rotated array is maximum element of array. O(log n)
    // https://www.geeksforgeeks.org/find-minimum-element-in-a-sorted-and-rotated-array/
    public static int findPivot(int[] arr, int start, int end){
        if(start==end)
            return start;

        int mid = (start+end)/2;
        if(arr[mid]>arr[start])
            return findPivot(arr, mid, end);
        else
            return findPivot(arr, start, mid);
    }

    // https://www.geeksforgeeks.org/find-rotation-count-rotated-sorted-array/
    public static int findRotationCount(int[] arr){
        return findPivot(arr, 0, arr.length) + 1;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        System.out.println("Input : " + Arrays.toString(arr));

        System.out.println("Approach 1 : " + Arrays.toString(rightRotate(arr, 3)));

        System.out.println("Approach 2 : " + Arrays.toString(arr) + " -> " + Arrays.toString(rotate(arr,3)));

        leftRotate(arr, 3);
        System.out.println("Approach 3 : " + Arrays.toString(arr) + " -> " + Arrays.toString(arr));

        rotateBySet(arr, 3);
        System.out.println("Approach 4 : " + Arrays.toString(arr) + " -> " + Arrays.toString(arr));

        System.out.println("Key " + 9 + " at index : " + search(arr, 9, 0 , arr.length));
        System.out.println("Pivot is at index : " + findPivot(arr, 0, arr.length));
        System.out.println("Number of rotations required to return to sorted array : " + findRotationCount(arr));
    }
}
