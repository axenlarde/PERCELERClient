package com.alex.perceler.client.server;

import java.net.ServerSocket;
import java.net.Socket;

import com.alex.perceler.client.utils.UsefulMethod;
import com.alex.perceler.client.utils.Variables;

/**
 * used to manage connection request
 *
 * @author Alexandre RATEL
 */
public class ServerManager extends Thread
	{
	/**
	 * variables
	 */
	private ServerSocket ss;
	private boolean run;
	
	public ServerManager()
		{
		super();
		run = true;
		start();
		}
	
	public void run()
		{
		Variables.getLogger().info("Listener manager started !");
		
		try
			{
			String port = UsefulMethod.getTargetOption("serverport");
			ss = new ServerSocket(Integer.parseInt(port));
			
			while(run)
				{
				Variables.getLogger().debug("Listener : Waiting for a new connection on port "+port);
				
				//Connection accepted
				Socket newRequest = ss.accept();
				String serverIP = newRequest.getInetAddress().getHostAddress();
				Variables.getLogger().info("Listener : New connection attempt from : "+serverIP);
				
				if(serverIP.equals(UsefulMethod.getTargetOption("serverip")))
					{
					new ConnectionManager(newRequest);
					}
				else
					{
					Variables.getLogger().debug("The request comes from an unknown server so we dismiss it");
					}
				}
			Variables.getLogger().info("Listener manager stopped !");
			}
		catch(Exception e)
			{
			Variables.getLogger().error("ERROR : While listening for new request : "+e.getMessage(),e);
			}
		}
	
	public void bye()
		{
		run = false;
		}
	
	
	
	
	
	/*2019*//*RATEL Alexandre 8)*/
	}
