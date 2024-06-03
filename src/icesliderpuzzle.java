// w1990839 | 20222388 | Iynkaran Pavanantham

import java.util.LinkedList;
import java.util.Queue;

public class icesliderpuzzle {
    class Coordinate implements Comparable<Coordinate> {
        int row;
        int column;
        int distanceFromStart;
        String path;  //  cardinal directions

        Queue<int[]> queue = new LinkedList<>();

        Coordinate(int row, int column, int distanceFromStart, String path, int[] l) {
            this.row = row;
            this.column = column;
            this.distanceFromStart = distanceFromStart;
            this.path = path  + " (" + (column + 1) + ", " + (row + 1) +") \n" ;
            this.queue.add(l);
        }

        @Override
        public int compareTo(Coordinate coordinate) {
            return this.distanceFromStart == coordinate.distanceFromStart ? this.path.compareTo(coordinate.path) : this.distanceFromStart - coordinate.distanceFromStart;
        }

        @Override
        public String toString() {
            return "Total distance: " + distanceFromStart + "\n" + "START: " + path ;
        }
    }

    public String shortestDistance(int[][] maze, int[] ball, int[] hole) {
        int rows = maze.length, cols = maze[0].length;
        boolean[][] visited = new boolean[rows][cols];

        Queue<Coordinate> pq = new LinkedList<>();
        pq.offer(new Coordinate(ball[0], ball[1], 0, "", new int[]{}));

        // 4 directions from a point
        String[] movableDirections = {"UP", "DOWN", "LEFT", "RIGHT"};
        int[][] movableCoordinates = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!pq.isEmpty()) {
            Coordinate position = pq.poll();
            if (position.row == hole[0] && position.column == hole[1]) {
                return position.toString();
            }

            for (int i = 0; i < movableCoordinates.length; i++) {
                int row = position.row;
                int column = position.column;
                int distanceFromStart = position.distanceFromStart;
                String path = position.path;

                // Explore directions until a "0" is hit
                while (row >= 0 && row < rows && column >= 0
                        && column < cols && maze[row][column] == 0
                        && (row != hole[0] || column != hole[1])) {
                    row += movableCoordinates[i][0];
                    column += movableCoordinates[i][1];
                    distanceFromStart += 1;
                }

                // if the goal is not found, need to roll back one step to get the correct place the player can go
                if (row != hole[0] || column != hole[1]) {
                    row -= movableCoordinates[i][0];
                    column -= movableCoordinates[i][1];
                    distanceFromStart -= 1;
                }

                if (!visited[row][column]) {
                    visited[position.row][position.column] = true;
                    pq.offer(new Coordinate(row, column, distanceFromStart, path + movableDirections[i], new int[]{row, column}));
                }
            }
        }

        return "No path found!";
    }
}


