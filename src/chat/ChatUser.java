package chat;

import java.io.PrintWriter;

public class ChatUser {
	private String name;
	private PrintWriter pw;
	
	public ChatUser() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PrintWriter getPw() {
		return pw;
	}

	public void setPw(PrintWriter pw) {
		this.pw = pw;
	}
	
	
}
