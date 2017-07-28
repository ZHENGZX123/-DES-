import java.io.File;
import java.util.ArrayList;

public class Test {

	private static ArrayList<File> al;
	public static int sum;
	public static int num;

	public static void main(String[] args) {

		al = new ArrayList<File>();
		String path = "F:\\smbTest";
		getFile(path, ".jpg");
		getFile(path, ".flv");
		getFile(path, ".mp4");

		sum = al.size();

		System.out.println("sum = sum");
		if (sum == 0) {
			System.out.println("no file");
			return;
		}

		for (int i = 0; i < al.size(); i++) {
			String filePath = al.get(i).getAbsolutePath();
			new myThread(filePath).start();
		}
	}

	private static void getFile(String path, String key) {

		File f = new File(path);
		if (!f.exists()) {
			return;
		}

		for (File file : f.listFiles()) {
			if (file.getName().endsWith(key)) {
				System.out.println(file.getPath());
				al.add(file);
			}

			if (file.isDirectory()) {
				getFile(file.getPath(), key);
			}
		}
	}

}
