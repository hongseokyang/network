package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(System.in);
			String data = null;
			InetAddress[] inetAddresses = null;
			while(true) {
				System.out.print(">>");
				if("exit".equals(data = sc.next())) {
					break;
				}
				inetAddresses = InetAddress.getAllByName(data);
				for (InetAddress inetAddress : inetAddresses) {
					System.out.println(inetAddress.getHostAddress());
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
 
}
