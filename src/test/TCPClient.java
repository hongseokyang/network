package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPClient {
	private static String SERVER_IP = "192.168.1.32";
	private static int SERVER_PORT = 6000;
	
	public static void main(String[] args) {
		Socket socket = null;
		
		try {
			// 1. 소켓생성
			socket = new Socket();
			
			// 2. 서버연결
			InetSocketAddress inetSocketAddress = new InetSocketAddress(SERVER_IP, SERVER_PORT);
			socket.connect(inetSocketAddress);

			System.out.println("[TCPClient] connected");
			
			// 3. IOStream 받아오기
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			
			// 4. 쓰기
			String data = "안녕하세요?\n";
			os.write(data.getBytes("UTF-8"));
			
			// 5. 데이터 읽기
			// 버퍼 설정
			byte[] buffer = new byte[256];
			int readByteCount = is.read(buffer);	// blocking
			if(readByteCount == -1) {
				System.out.println("[TCPClient] closed by Server");
				return;
			}
			
			data = new String(buffer, 0, readByteCount, "UTF-8");
			System.out.println("[TCPClient received: "+data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(socket != null && !socket.isClosed()) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
