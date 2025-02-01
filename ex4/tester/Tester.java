import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Tester {
	private static final String BASE_DIR = "C:\\Users\\danay\\IdeaProjects\\compilation\\ex4\\tester";
	private static final String ANALYZER_DIR = "C:\\Users\\danay\\IdeaProjects\\compilation\\ex4";
	private static final String ANALYZER_JAR = "ANALYZER";

	public static void main(String[] args) {
		File inputDir = new File(BASE_DIR + "\\inputs");
		File outputDir = new File(BASE_DIR + "\\outputs");
		File expectedDir = new File(BASE_DIR + "\\expected_outputs");

		if (!inputDir.exists() || !outputDir.exists() || !expectedDir.exists()) {
			System.err.println("Error: One or more required directories are missing.");
			return;
		}

		File[] inputFiles = inputDir.listFiles((dir, name) -> name.matches("input\\d+\\.txt"));
		if (inputFiles == null || inputFiles.length == 0) {
			System.err.println("Error: No input files found.");
			return;
		}

		int failedTests = 0;
		for (File inputFile : inputFiles) {
			String fileNumber = inputFile.getName().replaceAll("\\D+", "");
			File outputFile = new File(outputDir, "output" + fileNumber + ".txt");
			File expectedFile = new File(expectedDir, "expected_output" + fileNumber + ".txt");

			if (!expectedFile.exists()) {
				System.err.println("Warning: Expected output file missing for test " + fileNumber);
				continue;
			}

			try {
				ProcessBuilder processBuilder = new ProcessBuilder(
						"java", "-jar", ANALYZER_JAR,
						inputFile.getAbsolutePath(), outputFile.getAbsolutePath()
				);
				processBuilder.directory(new File(ANALYZER_DIR)); // Set working directory
				processBuilder.redirectErrorStream(true);
				Process process = processBuilder.start();
				process.waitFor();



				if (compareFiles(outputFile, expectedFile)) {
					System.out.println("Test " + fileNumber + " PASSED.");
				} else {
					System.out.println("Test " + fileNumber + " FAILED.");
					failedTests++;
				}
			} catch (IOException | InterruptedException e) {
				System.err.println("Error running test " + fileNumber + ": " + e.getMessage());
			}
		}
		System.out.println("Total failed tests: " + failedTests);
	}

	private static boolean compareFiles(File file1, File file2) throws IOException {
		List<String> lines1 = Files.readAllLines(file1.toPath());
		List<String> lines2 = Files.readAllLines(file2.toPath());
		return lines1.equals(lines2);
	}
}
