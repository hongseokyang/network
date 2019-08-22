package test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class LocalHost {

	public static void main(String[] args) {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String hostName = inetAddress.getHostName();
			String hostaddress = inetAddress.getHostAddress();
			byte[] ipAddresses = inetAddress.getAddress();			
			
			System.out.println(hostName);
			System.out.println(hostaddress);
			System.out.println(Arrays.toString(ipAddresses));
			for (byte ipAddress : ipAddresses) {
				System.out.print(ipAddress & 0x000000ff);
				System.out.print(".");
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
