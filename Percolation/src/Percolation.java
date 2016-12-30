import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private int count;
    private int totalSites;
    private int[] sites;
    private int openSites = 0;

    /**
     * create n-by-n grid, with all sites blocked
     * @param n
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        count = n;
        totalSites = n * n + 2; // additional 2 for storing top and bottom
        uf = new WeightedQuickUnionUF(totalSites);
        sites = new int[totalSites];
        for (int i = 1; i < totalSites - 1; i++) {
            // setting all sites to block except top and bottom
            sites[i] = 0;
        }
        // setting top and bottom sites to open
        sites[0] = 1;
        sites[totalSites - 1] = 1;
    }

    /**
     * open site (row, col) if it is not open already
     *
     * @param row
     * @param col
     */
    public void open(int row, int col) {
        if (row >= 0 && row <= count && col >= 0 && col <= count) {
            int ID = getID(row, col);
            if (sites[ID] == 1) {
                return;
            }
            sites[ID] = 1;
            openSites++;

            ArrayList<Integer> neighborList = getNeighbor(row, col);
            for (int neighbor : neighborList) {
                if (sites[neighbor] == 1) {
                    // union only when neighbor is open
                    uf.union(ID, neighbor);

                }
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    private int getID(int row, int col) {
        return (row - 1) * count + col;
        // count represent 'n' in `n X n` grid
    }

    private ArrayList<Integer> getNeighbor(int row, int col) {
        ArrayList<Integer> neighbor = new ArrayList<Integer>();
        if (col > 1) {
            neighbor.add(getID(row, col - 1));
        }
        if (col < count) {
            neighbor.add(getID(row, col + 1));
        }
        if (row > 1) {
            neighbor.add(getID(row - 1, col));
        }
        if (row < count) {
            neighbor.add(getID(row + 1, col));
        }
        if (row == 1) {
            // if it is first row then  join with virtual top site
            neighbor.add(0);
        }
        if (row == count) {
            // if it is last row then  join with virtual bottom site
            neighbor.add(totalSites - 1);
        }
        return neighbor;
    }

    /**
     * is site (row, col) open?
     *
     * @param row
     * @param col
     * @return
     */
    public boolean isOpen(int row, int col) {
        if (row >= 0 && row <= count && col >= 0 && col <= count) {
            int ID = getID(row, col);
            return sites[ID] == 1 ? true : false;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * is site (row, col) full?
     *
     * @param row
     * @param col
     * @return
     */
    public boolean isFull(int row, int col) {
        if (row >= 1 && row <= count && col >= 1 && col <= count) {
            int ID = getID(row, col);
            return uf.connected(ID, 0);
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * number of open sites
     *
     * @return
     */
    public int numberOfOpenSites() {
        return openSites;
    }

    /**
     * does the system percolate?
     *
     * @return
     */
    public boolean percolates() {
        return uf.connected(0, totalSites - 1);
    }

    /*
     public static void main(String[] args) {
         try {
            Scanner scanner = new Scanner(new File("./src/input4-no.txt"));
            int n = scanner.nextInt();
            Percolation perc = new Percolation(n);
            int i, j;
            while (scanner.hasNextInt()) {
                i = scanner.nextInt();
                j = scanner.nextInt();
                perc.open(i, j);
            }

            int[] foo = perc.sites;
            int side = 4;
            for (int a = 1; a <= side * side; ++a) {
                //for (int b = 1; b <= side; ++b)
                System.out.printf("%4d", foo[a]);
                if (a % side == 0)
                    System.out.println();
            }

            System.out.println(perc.isFull(3, 2));
            System.out.println(perc.percolates());

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }*/

}
