import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Random;

public class MyUtil {

	private static MyUtil instance;

	public static MyUtil getInstance() {
		if (instance == null) {
			instance = new MyUtil();
		}

		return instance;
	}

	public void encryptFile(final String originalFilePath) {

		File originalFile = new File(originalFilePath);
		File encodedFile = new File(originalFilePath + ".cn");

		try {

			// 读文件
			FileInputStream fis = new FileInputStream(originalFile);
			FileOutputStream fos = new FileOutputStream(encodedFile);

			int length = 0;
			byte[] newbuffer = new byte[1024 * 4];
			while ((length = fis.read(newbuffer)) > 0) {
				for (int i = 0; i < length; i++) {
					newbuffer[i] = (byte) (newbuffer[i] ^ ((byte) 0x55));
				}
				fos.write(newbuffer, 0, length);
			}

			fos.close();
			fis.close();
			originalFile.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
