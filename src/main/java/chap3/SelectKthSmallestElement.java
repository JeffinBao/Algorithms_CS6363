package chap3;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: baojianfeng
 * Date: 2018-01-29
 * Description: Selection problem, start from the special case,
 * which k = 1(the minimum element) or k = n(the maximum element),
 * to general case(k is picked randomly from 1 to n)
 */
public class SelectKthSmallestElement<T extends Comparable<? super T>> {

    /**
     * constructor
     */
    public SelectKthSmallestElement() {

    }

    /**
     * find the minimum element in the array
     * @param array input array
     * @return minimum element
     */
    public T findMin(T[] array) {
        int length = array.length;
        if (length == 0)
            return null;

        T min = array[0];
        for (int i = 1; i < length; i++) {
            if (array[i].compareTo(min) < 0)
                min = array[i];
        }

        return min;
    }

    /**
     * find the maximum element in the array
     * @param array input array
     * @return maximum element
     */
    public T findMax(T[] array) {
        int length = array.length;
        if (length == 0)
            return null;

        T max = array[0];
        for (int i = 1; i < length; i++) {
            if (array[i].compareTo(max) > 0)
                max = array[i];
        }

        return max;
    }

    /**
     * solution 1 of finding both min and max elements, running time is 2(n - 1)
     * @param array input array
     * @return an array of size 2: the first element is min, the second is max
     */
    public List<T> findMinMax1(T[] array) {
        int length = array.length;
        if (length == 0)
            return null;

        T min = array[0], max = array[0];
        for (int i = 1; i < length; i++) {
            if (array[i].compareTo(min) < 0)
                min = array[i];
            else if (array[i].compareTo(max) > 0)
                max = array[i];
        }

        return composeArr(min, max);
    }

    /**
     * solution 2 of find both min and max elements, running time is (n - 1) + (n - 2) = 2n - 3
     * @param array input array
     * @return an array of size 2: the first element is min, the second is max
     */
    public List<T> findMinMax2(T[] array) {
        int length = array.length;
        if (length == 0) return null;

        for (int i = 1; i < length; i++) {
            if (array[i].compareTo(array[0]) < 0) {
                T temp = array[0];
                array[0] = array[i];
                array[i] = temp;
            }
        }
        T min = array[0];

        for (int i = 2; i < length; i++) {
            if (array[i].compareTo(array[1]) > 0) {
                T temp = array[1];
                array[1] = array[i];
                array[i] =  temp;
            }
        }
        T max = array[1];

        return composeArr(min, max);
    }

    /**
     * private method to compose an array
     * @param min min element
     * @param max max element
     * @return an array of size 2
     */
    private List<T> composeArr(T min, T max) {
        List<T> result = new ArrayList<>();
        result.add(min);
        result.add(max);

        return result;
    }

    /**
     * solution 3 of find both min and max elements,
     * running time is O(3n/2) (when n is even, running time: 3n/2 - 2; when n is odd, running time: 3n/2)
     * @param array input array
     * @return an array of size 2: the first element is min, the second is max
     */
    public List<T> findMinMax3(T[] array) {
        int length = array.length;
        if (length == 0) return null;
        if (length == 1) return composeArr(array[0], array[0]);

        // compare elements in pairs, put smaller one before larger one. running time: n/2
        // must use i < length / 2, if use i < length, the last round of the iteration will cause ArrayOutOfBoundException if length is odd
        // since i += 2 will end at the last element and i + 1 will cause this exception
        for (int i = 0; i < length / 2; i++) {
            if (array[2 * i].compareTo(array[2 * i + 1]) > 0) {
                T temp = array[2 * i];
                array[2 * i] = array[2 * i + 1];
                array[2 * i + 1] = temp;
            }
        }
        // find min and max in pairs, running time: n/2 - 1(find min), n/2 - 1(find max)
        T min = array[0];
        T max = array[1];
        // must use i < length / 2, if use i < length, the last round of the iteration will cause ArrayOutOfBoundException if length is odd
        // since i += 2 will end at the last element and i + 1 will cause this exception
        for (int i = 1; i < length / 2; i++) {
            if (array[2 * i].compareTo(min) < 0)
                min = array[2 * i];
            if (array[2 * i + 1].compareTo(max) > 0)
                max = array[2 * i + 1];
        }

        // when array length is odd
        if (length % 2 != 0) {
            if (array[length - 1].compareTo(min) < 0)
                min = array[length - 1];
            if (array[length - 1].compareTo(max) > 0)
                max = array[length - 1];
        }

        return composeArr(min, max);
    }

    public static void main(String[] args) {
        SelectKthSmallestElement<Integer> kthSmallestElement = new SelectKthSmallestElement<>();
        Integer[] origin = new Integer[]{3, 1, 28, 13, 17, 12, -6, 4, 9, 13, 28, 5, 7, 17, -18, 32, 64, 39, -2, 73};
        System.out.println("min is: " + kthSmallestElement.findMin(origin));
        System.out.println("max is: " + kthSmallestElement.findMax(origin));
        List<Integer> result1 = kthSmallestElement.findMinMax1(origin);
        System.out.println("solution1 to find min and max: " + result1.get(0) + " " + result1.get(1));
        List<Integer> result2 = kthSmallestElement.findMinMax2(origin);
        System.out.println("solution2 to find min and max: " + result2.get(0) + " " + result2.get(1));
        List<Integer> result3 = kthSmallestElement.findMinMax3(origin);
        System.out.println("solution3 to find min and max: " + result3.get(0) + " " + result3.get(1));
    }
}
