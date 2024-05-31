import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Witaj w grze w labirynt!");

        System.out.println("Jak masz na imię?");
        String imie = scanner.next();

        System.out.println("Dziękuję " + imie + ", teraz wprowadz wysokosc labiryntu w zakresie 1-10");
        int height = scanner.nextInt();
        scanner.nextLine();

        while (height < 1 || height > 10) {
            System.out.println("Niepoprawna wartość");
            System.out.println("Wprowadz wysokość jeszcze raz");
            height = scanner.nextInt();
            scanner.nextLine();
        }

        System.out.println("Teraz wprowadz szerokosc labiryntu w zakresie 1-10");
        int width = scanner.nextInt();
        scanner.nextLine();

        while (width < 1 || width > 10) {
            System.out.println("Niepoprawna wartość");
            System.out.println("Wprowadz szerokość jeszcze raz");
            width = scanner.nextInt();
            scanner.nextLine();
        }

        char[][] maze = new char[height][width];
        System.out.println("Wprowadz labirynt ($ - start, # - ściana, @ - koniec, . - ścieżka)");

        for (int i = 0; i < height; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < width; j++) {
                maze[i][j] = line.charAt(j);
            }
        }

        MazeSolver solver = new MazeSolver();
        String solution = solver.solveMaze(maze);

        if (solution.isEmpty()) {
            System.out.println("Brak rozwiązania.");
        } else {
            System.out.println("Rozwiązanie: S" + solution + "K");
        }
    }
}

class MazeSolver {
    public String solveMaze(char[][] maze) {
        int startX = -1, startY = -1;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == '$') {
                    startX = i;
                    startY = j;
                }
            }
        }
        if (startX == -1 || startY == -1) {
            return "";
        }

        List<Character> path = new ArrayList<>();
        if (dfs(maze, startX, startY, path)) {
            StringBuilder result = new StringBuilder();
            for (char direction : path) {
                result.append(direction);
            }
            return result.toString();
        } else {
            return "";
        }
    }

    private boolean dfs(char[][] maze, int x, int y, List<Character> path) {
        if (maze[x][y] == '@') {
            return true;
        }
        maze[x][y] = 'X';

        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};
        char[] directions = {'P', 'L', 'D', 'G'};

        for (int i = 0; i < 4; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (isValid(maze, newX, newY)) {
                path.add(directions[i]);
                if (dfs(maze, newX, newY, path)) {
                    return true;
                }
                path.remove(path.size() - 1);
            }
        }

        return false;
    }

    private boolean isValid(char[][] maze, int x, int y) {
        int rows = maze.length;
        int cols = maze[0].length;
        return x >= 0 && x < rows && y >= 0 && y < cols && maze[x][y] != '#' && maze[x][y] != 'X';
    }
}