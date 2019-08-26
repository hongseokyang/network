package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
	private static String SERVER_IP = "192.168.1.32";
	private static int SERVER_PORT = 8000;
	
	public static void main(String[] args) {

		Scanner sc = null;
		Socket socket = null;
		BufferedReader br = null;
		try {
			//1. 키보드 연결
			sc = new Scanner(System.in);
			
			//2. socket 생성
			socket = new Socket();
			
			//3. 연결
			InetSocketAddress inetSocketAddress = new InetSocketAddress(SERVER_IP, SERVER_PORT);
			socket.connect(inetSocketAddress);
			
			//4. reader/writer 생성
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			
			//5. join 프로토콜
			System.out.print("닉네임>>" );
			String nickname = sc.nextLine();
			pw.println(nickname);
			pw.flush();

			//6. ChatClientThread 시작
			Thread chatClientThread = new ChatClientThread(br);
			chatClientThread.start();
			
			
			//7. 키보드 입력 처리
			while( true ) {
				String input = sc.nextLine();

				if( "quit".equals( input ) == true ) {
					// 8. quit 프로토콜 처리
					pw.println("quit");					
					break;
				} 
				pw.println(input);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(br== null && socket != null && !socket.isClosed()) {
					socket.close();
				}
				if(sc != null) {
					sc.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	public static void log(String log) {
		System.out.println("[Echo CLient] " + log);
	}
}