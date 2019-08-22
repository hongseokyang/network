package echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer {
	private static final int PORT = 8000;
	
	public static void main(String[] args) {
		
ServerSocket serverSocket = null;
		
		try {
			// 1. 서버소켓 생성
			serverSocket = new ServerSocket();
			
			// 2. Binding : Socket 에 SocketAddress(IPAddress + Port) 바인딩
			InetAddress inetAddress = InetAddress.getLocalHost();
			InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, PORT);
			serverSocket.bind(inetSocketAddress);
						
			// 3. accept : 클라이언트로부터 연결요청 (Connect)을 기다린다. - blocking
			Socket socket = serverSocket.accept();
			
			try {
				// 4. IOStream 받아오기
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				
				while(true) {
					// 5. 데이터 읽기
					// 버퍼 설정
					byte[] buffer = new byte[256];
					int readByteCount = is.read(buffer);	// blocking
					if(readByteCount == -1) {
						// 정상종료 : remote socket이 close()
						// 메소드를 통해서 정상적으로 소켓을 닫음
						System.out.println("[TCPServer] closed by client");
						break;
					}
					String data = new String(buffer, 0, readByteCount, "UTF-8");
					System.out.println("[TCPServer] received: "+ data);
					
					// 6. 데이터 쓰기
					os.write(data.getBytes("UTF-8"));
				}
			} catch (SocketException e) {
				System.out.println("[TCPServer] abnormal closed by client");	// 비정상 종료
			}catch (IOException e) {
				e.printStackTrace();
			} finally {
				// 7. Socket 자원정리
				if(socket != null && !socket.isClosed()) {
					socket.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 8. Server Socket 자원정리
			try {
				if(serverSocket != null && !serverSocket.isClosed()) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
