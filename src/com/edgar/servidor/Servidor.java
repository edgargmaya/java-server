package com.edgar.servidor;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class Servidor {

	private static final String TASK_ENDPOINT = "/task";
	
	private HttpServer server;
	private int port = 8080;
	
	public Servidor(){ }
	
	public Servidor(int port){
		this.port = port;
	}
	
	public void startServer() {
		try {
			this.server = HttpServer.create(new InetSocketAddress(this.port), 0);
		} catch(IOException ex){
			ex.printStackTrace();
			return;
		}
		
		HttpContext taskContext = this.server.createContext(TASK_ENDPOINT);
		taskContext.setHandler( (HttpExchange exchange) -> {
			
			if( !exchange.getRequestMethod().equalsIgnoreCase("POST") ) {
				exchange.close();
				return;
			}
			
			byte[] bodyBytes = exchange.getRequestBody().readAllBytes();
			byte[] response = new String(bodyBytes).toUpperCase().getBytes();
			
			exchange.sendResponseHeaders(200, response.length);
			OutputStream outputStream = exchange.getResponseBody();
			outputStream.write(response);
			outputStream.flush();
			outputStream.close();
			exchange.close();
			
		});
		
		server.setExecutor(Executors.newFixedThreadPool(10));
        server.start();
	}
	
}
