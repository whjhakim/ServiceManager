package common;

import java.io.File;
import java.io.IOException;

public class FilePath {
	public FilePath() throws IOException {
		File directory =  new File("");
		String course = directory.getCanonicalPath();
		System.out.println(course);
	}

/*	public static void main(String[] args) throws IOException {
		FilePath file = new FilePath();
	}*/
}
