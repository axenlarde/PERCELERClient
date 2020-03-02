package com.alex.perceler.client.servicepilot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import com.alex.perceler.client.utils.UsefulMethod;
import com.alex.perceler.client.utils.Variables;

/**
 * Contains static method about service pilot task
 *
 * @author Alexandre RATEL
 */
public class spTools
	{
	
	/**
	 * Will replace the current ip by the new ip in the service pilot database text file
	 */
	public static boolean replaceIP(String currentIP, String newIP)
		{
		try
			{
			Variables.getLogger().debug("Replacing ip process : replacing "+currentIP+" by "+newIP);
			String charset = UsefulMethod.getTargetOption("charset");
			
			//We manage one or more file
			String[] filePathes = UsefulMethod.getTargetOption("databasefilepath").split(",");
			
			for(String fp : filePathes)
				{
				try
					{
					Variables.getLogger().debug("Replacing ip process : dealing with file : "+fp);
					File databaseFile = new File(fp);
					String content = IOUtils.toString(new FileInputStream(databaseFile),charset);
					content = content.replaceAll(currentIP, newIP);
					IOUtils.write(content, new FileOutputStream(databaseFile),charset);
					}
				catch (Exception e)
					{
					Variables.getLogger().error("ERROR while replacing the ip in the database file for file "+fp+" : "+e.getMessage(),e);
					}
				}
			
			Variables.getLogger().debug("Replacing ip process : done !");
			return true;
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while replacing the ip in the database file : "+e.getMessage(),e);
			}
		
		return false;
		}
	
	/**
	 * Will delete the given IP in the service pilot database text file
	 */
	public static boolean deleteIP(String targetIP)
		{
		try
			{
			Variables.getLogger().debug("Deleting ip process : deleting "+targetIP);
			String charset = UsefulMethod.getTargetOption("charset");
			
			//We manage one or more file
			String[] filePathes = UsefulMethod.getTargetOption("databasefilepath").split(",");
			
			for(String fp : filePathes)
				{
				try
					{
					Variables.getLogger().debug("Deleting ip process : dealing with file : "+fp);
					File databaseFile = new File(fp);
					LineIterator it = FileUtils.lineIterator(databaseFile, charset);
					StringBuffer s = new StringBuffer("");
					while (it.hasNext())
						{
						String row = it.nextLine();
						if(!row.contains(targetIP))
							{
							s.append(row);
							if(it.hasNext())s.append("\r\n");
							}
						}
					it.close();
					IOUtils.write(s.toString(), new FileOutputStream(databaseFile),charset);
					}
				catch (Exception e)
					{
					Variables.getLogger().error("ERROR while deleting IP in the database file for file "+fp+" : "+e.getMessage(),e);
					}
				}
			
			Variables.getLogger().debug("Deleting ip process : done !");
			return true;
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while deleting IP in the database file : "+e.getMessage(),e);
			}
		
		return false;
		}
	
	/**
	 * Will restart the service pilot service
	 */
	public static boolean restartService(String serviceName)
		{
		try
			{
			Variables.getLogger().debug("Restarting process "+serviceName);
			String[] command = {"net stop \""+serviceName+"\"","net start \""+serviceName+"\""};
			
			Variables.getLogger().debug("CLI : Sending command : "+command[0]);
			Process result = Runtime.getRuntime().exec(command[0]);
			BufferedReader in = new BufferedReader(new InputStreamReader(result.getInputStream(),"ibm850"));
			
			String row;
			int counter = 0;
			while ((row = in.readLine()) != null)
	        	{
	        	Variables.getLogger().debug("#CLI# : "+row);
		    	if(row.contains("successfully"))break;
		    	if(counter > 5)break;//Maybe means that the stop failed but we try the start anyway
		    	counter++;
	         	}
			in.close();
			
			Variables.getLogger().debug("CLI : Sending command : "+command[1]);
			result = Runtime.getRuntime().exec(command[1]);
			in = new BufferedReader(new InputStreamReader(result.getInputStream(),"ibm850"));
			counter = 0;
			while ((row = in.readLine()) != null)
	        	{
	        	Variables.getLogger().debug("#CLI# : "+row);
		    	if(row.contains("successfully"))break;
		    	if(counter > 5)return false;//Means that the start failed so we return false
		    	counter++;
	        	}
			
			Variables.getLogger().debug("Restarting process done !");
			return true;
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while restarting service pilot service : "+e.getMessage(),e);
			}
		
		return false;
		}
	
	
	
	
	
	/*2019*//*RATEL Alexandre 8)*/
	}
