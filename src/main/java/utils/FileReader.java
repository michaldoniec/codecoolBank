package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
	private File file;

	public FileReader(File file){
		this.file = file;
	}

	public String retrieveStringFromFile() throws FileNotFoundException {
		StringBuilder stringBuilder = new StringBuilder();
		Scanner scanner = new Scanner(file);
		while(scanner.hasNextLine()){
			stringBuilder.append(scanner.nextLine());
		}
		return stringBuilder.toString();
	}
}
