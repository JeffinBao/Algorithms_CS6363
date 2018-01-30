package chap2;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: baojianfeng
 * Date: 2018-01-24
 * Description: Find the closest pair points using brute force and CPP solutions
 * Incomplete of CPP solution_20180129
 */
public class ClosestPairPoints {

    /**
     * constructor
     */
    public ClosestPairPoints() {

    }

    /**
     * Implementing brute force solution to find the closest pair points
     * @param pointsArr points array(a 2d array which has two rows)
     * @return list where stores closest pair points
     */
    public List<Point> bruteForceCPP(double[][] pointsArr) {
        int row = pointsArr.length;
        if (row != 2)
            return null;

        int column = pointsArr[0].length;
        double min = Double.MAX_VALUE;
        int p1Index = -1, p2Index = -1;
        for (int i = 0; i < column - 1; i++) {
            for (int j = i + 1; j < column; j++) {
                double xDis = pointsArr[0][i] - pointsArr[0][j];
                double yDis = pointsArr[1][i] - pointsArr[1][j];
                // use square instead of square root, since Math.sqrt() method takes time, not as efficient as only use the square
                double distanceSquare = xDis * xDis + yDis * yDis;
                if (distanceSquare < min) {
                    min = distanceSquare;
                    p1Index = i;
                    p2Index = j;
                }
            }
        }

        if (min != Integer.MAX_VALUE) {
            Point point1 = new Point(pointsArr[0][p1Index], pointsArr[1][p1Index]);
            Point point2 = new Point(pointsArr[0][p2Index], pointsArr[1][p2Index]);
            List<Point> list = new ArrayList<>();
            list.add(point1);
            list.add(point2);

            return list;
        } else
            return null;

    }

//    /**
//     * Implementing cpp algorithm, improve the time complexity to n(logn)^2
//     * @param pointsArr
//     * @return
//     */
//    public List<Point> fancyCPP(double[][] pointsArr) {
//
//    }







    private class Point {
        double x, y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        double[][] points = new double[][]{
                {0, 1, 1.5, 2, 0.5, 0.75},
                {1, 0, 0,   2, 0.5, 0.75}
        };
        ClosestPairPoints cpp = new ClosestPairPoints();
        List<Point> list = cpp.bruteForceCPP(points);
    }
}
