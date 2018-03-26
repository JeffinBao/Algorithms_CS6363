package chap6;

/**
 * Author: baojianfeng
 * Date: 2018-03-20
 * Description: Disjoint set class, using union by size and path compression.
 */
public class DisjSets {
    private int[] s;

    /**
     * construct the disjoint sets object
     * @param num the size of the disjoint set
     */
    public DisjSets(int num) {
        s = new int[num];
        for (int i = 0; i < num; i++)
            s[i] = -1;
    }

    /**
     * perform union operation
     * @param root1 root 1
     * @param root2 root 2
     */
    public void union(int root1, int root2) {
        if (s[root1] < s[root2]) { // root1 is deeper
            s[root1] += s[root2]; // update the size of root1
            s[root2] = root1;    // make root1 the new root
        } else {
            s[root2] += s[root1]; // update the size of root2
            s[root1] = root2;     // make root2 the new root
        }
    }

    /**
     * perform a find with path compression
     * @param x the element being searched for
     * @return the index of set containing x
     */
    public int find(int x) {
        if (s[x] < 0)
            return x;
        else
            return s[x] = find(s[x]);
    }
}
