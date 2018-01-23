package chap2;

/**
 * Author: baojianfeng
 * Date: 2018-01-22
 * Description: an implementation of MergeSort, aiming to use the divide-and-conquer concept
 */
public class MergeSort<T extends Comparable<? super T>> {

    /**
     * constructor
     */
    public MergeSort() {

    }

    /**
     * recursively split and merge the array
     * @param start start
     * @param end end
     */
    public void recurSort(T[] origin, T[] temp, int start, int end) {
        if (start < end) {
            int mid = start + (end - start) / 2;
            recurSort(origin, temp, start, mid);
            recurSort(origin, temp, mid + 1, end);
//            merge(origin, temp, start, mid, end);
            mergeUsingOneLoop(origin, temp, start, mid, end);
        }
    }

    /**
     * The most common solution, use a temp array to store the sorted elements, use multiple while loop
     * @param start start index
     * @param mid mid index
     * @param end end index
     */
    private void merge(T[] origin, T[] temp, int start, int mid, int end) {
        int l1 = start, l2 = mid + 1;
        int i = start;
        while (l1 <= mid && l2 <= end && i <= end) {
            int compare = origin[l1].compareTo(origin[l2]);
            if (compare >= 0) {
                temp[i] = origin[l2];
                l2++;
            } else if (compare < 0) {
                temp[i] = origin[l1];
                l1++;
            }
            i++;
        }

        while (l1 <= mid) {
            temp[i] = origin[l1];
            l1++;
            i++;
        }

        while (l2 <= end) {
            temp[i] = origin[l2];
            l2++;
            i++;
        }

        // copy temp to original array
        // must copy temp to origin, otherwise the final array is wrong
        // must use end index instead of j, otherwise the final array is wrong
        int numbers = end - start + 1;
        for (int j = 0; j < numbers; j++, end--)
            origin[end] = temp[end];
    }

    /**
     * sort the array using only one loop
     * @param start start
     * @param mid mid
     * @param end end
     */
    private void mergeUsingOneLoop(T[] origin, T[] temp, int start, int mid, int end) {
        int l1 = start, l2 = mid + 1;
        for (int i = start; i <= end; i++) {
            // the below line of code will cause NullPointer exception
            // it should be moved behind l2 > end, since if l2 > end, the code will not be executed,
            // therefore no NullPointer exception will happen
//            int compare = origin[l1].compareTo(origin[l2]);

            // key codes are the below if statement
            // only two conditions for temp[i] = origin[l1]
            // 1. l1 <= mid && compare < 0
            // 2. l1 <= mid && l2 > end
            // therefore use l1 <= mid && (l2 > end || compare < 0) as the combination
            if (l1 <= mid && (l2 > end || origin[l1].compareTo(origin[l2]) < 0)) {
                temp[i] = origin[l1];
                l1++;
            } else {
                temp[i] = origin[l2];
                l2++;
            }
        }


        // copy temp to original array
        // must copy temp to origin, otherwise the final array is wrong, but why?
        // must use end index instead of j, otherwise the final array is wrong, but why?
        int numbers = end - start + 1;
        for (int j = 0; j < numbers; j++, end--)
            origin[end] = temp[end];
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
        Integer[] temp = new Integer[15];
        MergeSort<Integer> mergeSort = new MergeSort<>();

        mergeSort.recurSort(origin, temp, 0, origin.length - 1);
        mergeSort.printArray(origin);
    }
}
