public class myThread extends Thread {
	private String path;

	public myThread(String path) {
		this.path = path;
	}

	@Override
	public void run() {
		final String originalFilePath = path;
		MyUtil.getInstance().encryptFile(originalFilePath);
		Test.num++;
		System.out.println("已完成的  = " + Test.num);
		System.out.println("一共" + Test.sum + "个，当前完成第" + Test.num + "个");
		if (Test.num == Test.sum) {
			System.out.println("finish ............");
		}
		super.run();
	}

}