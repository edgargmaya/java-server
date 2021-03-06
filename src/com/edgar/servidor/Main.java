package com.edgar.servidor;

public class Main {
	
	public static void main(String[] args) {
		new Servidor(8080).startServer();
		
		System.out.println("Server is running on 8080 port...");
	}
}
