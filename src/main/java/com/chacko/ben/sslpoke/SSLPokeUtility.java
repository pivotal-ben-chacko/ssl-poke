package com.chacko.ben.sslpoke;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;

public class SSLPokeUtility {
	@SuppressWarnings("finally")
	public static String connect(String host, int port) {
		String msg = "";
		if ("favicon.ico".equals(host))
			return msg;
		
		try {
			SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket(host, port);

			InputStream in = sslsocket.getInputStream();
			OutputStream out = sslsocket.getOutputStream();

			// Write a test byte to get a reaction :)
			out.write(1);

			while (in.available() > 0) {
				System.out.print(in.read());
			}
			System.out.println("Successfully connected");
			msg = "Successfully connected!\n";
		} catch (Exception exception) {
			msg = exception.getMessage();
		} finally {
			return msg;
		}
	}
}
