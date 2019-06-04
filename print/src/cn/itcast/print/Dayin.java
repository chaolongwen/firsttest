package cn.itcast.print;

public class Dayin {

	public static void main(String[] args) {
		try {
			
			//vb程序打开少53个文件
			//管理一个进程属性集
			ProcessBuilder pb = new ProcessBuilder("H:\\temp\\RS\\parkson.exe");
			pb.redirectErrorStream(true); //标准错误将与标准输出合并
			
			//创建本机进程
			Process process = pb.start();
			process.waitFor(); //当前线程等待
			
			//输出子进程的出口集
			System.out.println(process.exitValue());
			byte[] bytes = new byte[process.getInputStream().available()];
			
			
			process.getInputStream().read(bytes);
			System.out.println(new String(bytes));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
