package vttp.batch5.sdf.task02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) throws IOException {

		String dirPath;

		dirPath = "vttp_b5_assessment_template\\task02\\TTT\\figure1.txt";
		System.out.println("To delete hardcoded path");
		System.out.println("To uncomment args error msg");

		if (args.length > 0) {
			dirPath = args[0];
		} else {
			// System.err.println("No file provided");
			// System.exit(0);
		}

		char[][] board = loadFile(dirPath);

		// Move bestMove = findBestMove(board);

		// System.out.printf("The Optimal Move is:\n");
		// System.out.printf("ROW: %d COL: %d\n\n", bestMove.row, bestMove.col);

		Game game = new Game(board);

		game.drawTheField();

		System.out.println("--------------------------");

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
