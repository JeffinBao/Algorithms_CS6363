package chap1;

/**
 * Author: baojianfeng
 * Date: 2018-01-22
 * Description: an implementation of InsertionSort, using two ways of scanning during sorting process
 */
public class InsertionSort<T extends Comparable<? super T>> {

    /**
     * constructor
     */
    public InsertionSort() {

    }

    /**
     * The most common insertion sort implementation, scan from right to left(from big index to small index).
     * This implementation is much easier compared to left to right scanning.
     * @param origin the original array
     * @return the sorted array
     */
    public T[] sortScanFromRightToLeft(T[] origin) {
        int length = origin.length;
        for (int i = 1; i < length; i++) {
            T moveElement = origin[i];
            int j = i - 1;

            while (j >= 0 && moveElement.compareTo(origin[j]) < 0 ) {
                // shift the previous element backward
                origin[j + 1] = origin[j];
                j--;
            }

            origin[j + 1] = moveElement; // don't forget to put the moveElement to the final position
        }

        return origin;
    }

    /**
     * Scan from left to right(from small index to big index) when sorting
     * @param origin the original array
     * @return the sorted array
     */
    public T[] sortScanFromLeftToRight(T[] origin) {
        int length = origin.length;
        for (int i = 1; i < length; i++) {
            T moveElement = origin[i];
            int j = 0;

            while (j < i) {
                if (moveElement.compareTo(origin[j]) < 0) {
                    // place the moveElement to j position
                    // update moveElement to the temp value, which is the old j position element
                    T temp = origin[j];
                    origin[j] = moveElement;
                    moveElement = temp;
                }

                j++;
            }

            origin[j] = moveElement; // don't forget to put the moveElement to the final position
        }

        return origin;
    }

    /**
     * print the array
     * @param array array
     */
    public void printArray(T[] array) {
        for (T element : array) {
            System.out.print(element + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Integer[] origin = new Integer[]{3, 1, 28, 13, 17, 12, -6, 4, 9, 13, 28, 5, 7, 17, -18};
        InsertionSort<Integer> insertionSort = new InsertionSort<>();
//        Integer[] sortedArr1 = insertionSort.sortScanFromRightToLeft(origin);
//        insertionSort.printArray(sortedArr1);
        Integer[] sortedArr2 = insertionSort.sortScanFromLeftToRight(origin);
        insertionSort.printArray(sortedArr2);
    }
}
