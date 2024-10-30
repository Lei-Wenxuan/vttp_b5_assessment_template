package vttp.batch5.sdf.task02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) throws IOException {

		String dirPath = "";

		if (args.length > 0) {
			dirPath = args[0];
		} else {
			System.err.println("No file provided");
			System.exit(0);
		}

		System.out.println("Processing: " + dirPath);

		char[][] board = loadFile(dirPath);

		Game game = new Game(board);

		System.out.println();
		game.drawTheField();

		System.out.println("--------------------------");

		if (game.isLegal(board))
			game.listPossibleMoves(board);
		else
			System.out.println("No legal moves for X");

	}

	public static char[][] loadFile(String dirPath) throws IOException {

		char[][] board = new char[3][3];

		Path p = null;
		p = Paths.get(dirPath);

		File file = p.toFile();

		if (!file.exists()) {
			System.err.println("File does not exist");
			System.exit(0);
		}

		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		String line;
		int yCoords = 0;

		while ((line = br.readLine()) != null) {
			char[] xArray = line.toCharArray();
			for (int xCoords = 0; xCoords < xArray.length; xCoords++) {
				board[yCoords][xCoords] = xArray[xCoords];
			}
			yCoords++;
		}

		br.close();

		return board;
	}

}
