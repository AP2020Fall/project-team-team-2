package controller;

import model.Player;
import model.Country;
import model.Map;
import model.Countries;

public class NeighbourCheckController {
    Player currentPlayer;
    Country sourceContry;
    Country DestinationCountry;
    Countries mapCountries;



    // Method for finding and printing
    // whether the path exists or not
    public void isPath( int matrix[][], int n, int m)
    {
        // Defining visited array to keep
        // track of already visited indexes
        boolean visited[][]
                = new boolean[n][m];

        // Flag to indicate whether the
        // path exists or not
        boolean flag = false;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // if matrix[i][j] is source
                // and it is not visited
                if (matrix[i][j] == 1 && !visited[i][j])
                    // Starting from i, j and
                    // then finding the path
                    if (isPath(matrix, i, j, visited)) {
                        // if path exists
                        flag = true;
                        break;
                    }
            }
        }
        if (flag)
            System.out.println("YES");
        else
            System.out.println("NO");
    }

    // Method for checking boundries
    public boolean isSafe( int i, int j, int matrix[][])
    {

        if ( i >= 0 && i < matrix.length && j >= 0 && j < matrix[0].length)
            return true;
        return false;
    }

    // Returns true if there is a
    // path from a source (a
    // cell with value 1) to a
    // destination (a cell with
    // value 2)
    public boolean isPath( int matrix[][], int i, int j, boolean visited[][])
    {

        // Checking the boundries, walls and
        // whether the cell is unvisited
        if ( isSafe(i, j, matrix) && matrix[i][j] != 0 && !visited[i][j]) {
            // Make the cell visited
            visited[i][j] = true;

            // if the cell is the required
            // destination then return true
            if (matrix[i][j] == 2)
                return true;

            // traverse up
            boolean up = isPath(matrix, i - 1, j, visited);

            // if path is found in up
            // direction return true
            if (up)
                return true;

            // traverse up
            boolean upRight = isPath(matrix, i - 1, j + 1, visited);

            // if path is found in up
            // direction return true
            if (upRight)
                return true;

            // traverse up
            boolean upLeft = isPath(matrix, i - 1, j - 1, visited);

            // if path is found in up
            // direction return true
            if (upLeft)
                return true;

            // traverse left
            boolean left = isPath(matrix, i, j - 1, visited);

            // if path is found in left
            // direction return true
            if (left)
                return true;

            // traverse right
            boolean right = isPath(matrix, i, j + 1, visited);

            // if path is found in right
            // direction return true
            if (right)
                return true;

            // traverse down
            boolean down = isPath(matrix, i + 1, j, visited);

            // if path is found in down
            // direction return true
            if (down)
                return true;

            // traverse down
            boolean downRight = isPath(matrix, i + 1, j + 1, visited);

            // if path is found in down
            // direction return true
            if (downRight)
                return true;

            // traverse down
            boolean downLeft = isPath(matrix, i + 1, j - 1, visited);

            // if path is found in down
            // direction return true
            if (downLeft)
                return true;

        }
        // no path has been found
        return false;
    }

    // driver program to
    // check above function
    public void setCountryNumbers(Country sourceCountry, Country destinationCountry, Player CurrentPlayer)
    {
        int[][] matrix = new int[][];



        // calling isPath method
        isPath(matrix, 5, 4);
    }


}
