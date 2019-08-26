package chat;

import java.io.BufferedReader;
import java.io.IOException;

public class ChatClientThread extends Thread {
	private BufferedReader br;
	
	public ChatClientThread(BufferedReader br) {
		this.br = br;
	}
	
	@Override
	public void run() {
		
		try {
			while(true) {
				
				// 5. 데이터 읽기
				String data = br.readLine();	// blocking
				if(data == null || "quit".equals(data)) {
					ChatClient.log("closed by Server");
					break;
				}
				// 6. 콘솔 출력
				System.out.println(data);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
