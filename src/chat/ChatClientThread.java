package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatClientThread extends Thread {
	private Socket socket;
	
	public ChatClientThread(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			while(true) {
				
				// 5. 데이터 읽기
				String data = br.readLine();	// blocking
				
				if(data == null || "quit".equals(data)) {
					ChatClient.log("closed by Server");
					break;
				} else if ("join".equals(data)) {
					data = "채팅에 참여합니다.";
				}
				// 6. 콘솔 출력
				System.out.println(data);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(socket != null && !socket.isClosed()) {
					ChatClient.log("socket is closed");
					socket.close();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
