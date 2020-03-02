package com.alex.perceler.client.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.alex.perceler.client.servicepilot.spTools;
import com.alex.perceler.client.utils.UsefulMethod;
import com.alex.perceler.client.utils.Variables;
import com.alex.perceler.remoteclient.Request;
import com.alex.perceler.remoteclient.Request.requestType;;

/**
 * Used to manage one connection from the server
 *
 * @author Alexandre RATEL
 */
public class ConnectionManager extends Thread
	{
	/**
	 * Variables
	 */
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public ConnectionManager(Socket socket)
		{
		super();
		this.socket = socket;
		start();
		}
	
	
	
	
	public void run()
		{
		Variables.getLogger().debug("Managing the new connection");
		
		try
			{
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			
			while(true)
				{
				//Managing the incoming request
				Object o = in.readObject();
				
				if(o instanceof Request)
					{
					Request r = (Request)o;
					
					if(r.getType().equals(requestType.replaceip))
						{
						String[] temp = r.getContent().split(":");
						boolean success = spTools.replaceIP(temp[0], temp[1]);
						
						Request reply;
						if(success)
							{
							reply = new Request(requestType.success, "");
							}
						else
							{
							reply = new Request(requestType.error, "");
							}
						out.writeObject(reply);
						}
					else if(r.getType().equals(requestType.deleteip))
						{
						boolean success = spTools.deleteIP(r.getContent());
						
						Request reply;
						if(success)
							{
							reply = new Request(requestType.success, "");
							}
						else
							{
							reply = new Request(requestType.error, "");
							}
						out.writeObject(reply);
						}
					else if(r.getType().equals(requestType.restartservice))
						{
						boolean success = spTools.restartService(UsefulMethod.getTargetOption("servicename"));
						
						Request reply;
						if(success)
							{
							reply = new Request(requestType.success, "");
							}
						else
							{
							reply = new Request(requestType.error, "");
							}
						out.writeObject(reply);
						//If we receive this request, we finish by ending the socket
						break;
						}
					else
						{
						Variables.getLogger().debug("Unknown request from the server : "+r.getType().name());
						}
					}
				else
					{
					Variables.getLogger().debug("Unknown object sent by the server");
					}
				}
			Variables.getLogger().debug("End of actions, closing socket");
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while managing server request : "+e.getMessage(),e);
			}
		
		//We end by closing the socket
		try
			{
			socket.close();
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while closing the socket : "+e.getMessage(),e);
			}
		}
	
	
	/*2019*//*RATEL Alexandre 8)*/
	}
