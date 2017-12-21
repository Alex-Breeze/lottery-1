package org.steinbauer.lottery;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Net {

	public static String getIPInfo() {
		try {
			InetAddress localhost = InetAddress.getLocalHost();
			return String.format("%s (%s)", localhost.getHostName(), localhost.getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return "<no-hostname-available>";
	}
	
}
