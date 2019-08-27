package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.Vector;


public class ChatServerThread extends Thread {
	private Socket socket;
	private ChatUser user;
	private static List<ChatUser> users = new Vector<ChatUser>();
	
	@Override
	public void run() {
		InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();			
		
		ChatServer.log("connected from client[" + inetRemoteSocketAddress.getAddress().getHostAddress() + ":" + inetRemoteSocketAddress.getPort()+"]");
					
		try {
			// 4. IOStream 받아오기
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			
			// 버퍼 보조스트림
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(os, "UTF-8"), true);

			while(true) {
				// 5. 데이터 읽기(수신)
				String request = br.readLine();
				
				if( request == null || "quit".equals( request ) ) {
					doQuit(pw);
					break;
				}
				
				String[] tokens = request.split( ":" );
				
				if( "join".equals( tokens[0] ) ) {

				   doJoin( tokens[1], pw );

				} else if( "message".equals( tokens[0] ) ) {
				   
				   doMessage( tokens[1] );

				} else {

				   ChatServer.log( "에러:알수 없는 요청(" + tokens[0] + ")" );
				}
				
			}
			
		} catch (SocketException e) {
			ChatServer.log("abnormal closed by client");	// 비정상 종료
		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 7. Socket 자원정리
			if(socket != null && !socket.isClosed()) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void doJoin(String name, PrintWriter pw) {
		user = new ChatUser();
		users.add(user);
		user.setName(name);
		user.setPw(pw);
		pw.println("채팅에 참여합니다.");
		broadcast(" 님이 입장하였습니다.");
	}
	
	private void broadcast(String data) {
		for (ChatUser chatUser : users) {
			if(!chatUser.equals(user)) {
				chatUser.getPw().println(user.getName()+data);
			}
		}
	}
	
	private void doMessage(String data) {
		for (ChatUser chatUser : users) {
			chatUser.getPw().println(user.getName()+": " + data);
			
		}
	}
	
	private void doQuit(PrintWriter pw) {
		pw.println("quit");
		broadcast(" 님이 퇴장하였습니다.");
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((socket == null) ? 0 : socket.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	} 

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChatServerThread other = (ChatServerThread) obj;
		if (socket == null) {
			if (other.socket != null)
				return false;
		} else if (!socket.equals(other.socket))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	public ChatServerThread(Socket socket) {
		this.socket = socket;
	}
	
}
