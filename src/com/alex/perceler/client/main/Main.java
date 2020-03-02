package com.alex.perceler.client.main;

import org.apache.log4j.Level;

import com.alex.perceler.client.server.ServerManager;
import com.alex.perceler.client.utils.InitLogging;
import com.alex.perceler.client.utils.UsefulMethod;
import com.alex.perceler.client.utils.Variables;

/**********************************
* Welcome to WASSGAR Client Main class
* 
* @author RATEL Alexandre
**********************************/
public class Main
	{
	
	/***************
	 * Constructor
	 ***************/
	public Main()
		{
		//Set the software name
		Variables.setSoftwareName("PERCELER Client");
		//Set the software version
		Variables.setSoftwareVersion("1.0");
		
		/****************
		 * Initialization of the logging
		 */
		Variables.setLogger(InitLogging.init(Variables.getSoftwareName()+"_LogFile.txt"));
		Variables.getLogger().info("\r\n");
		Variables.getLogger().info("#### Entering application");
		Variables.getLogger().info("## Welcome to : "+Variables.getSoftwareName()+" version "+Variables.getSoftwareVersion());
		Variables.getLogger().info("## Author : RATEL Alexandre : 2019");
		/*******/
		
		/******
		 * Initialization of the variables
		 */
		new Variables();
		/************/
		
		/**********
		 * We check if the java version is compatible
		 */
		//UsefulMethod.checkJavaVersion();
		/***************/
		
		/**********************
		 * Reading of the configuration file
		 */
		try
			{
			//Config files reading
			Variables.setMainConfig(UsefulMethod.readMainConfigFile(Variables.getConfigFileName()));
			}
		catch(Exception exc)
			{
			UsefulMethod.failedToInit(exc);
			}
		/********************/
		
		/*****************
		 * Setting of the inside variables from what we read in the configuration file
		 */
		try
			{
			UsefulMethod.initInternalVariables();
			}
		catch(Exception exc)
			{
			Variables.getLogger().error(exc.getMessage());
			Variables.getLogger().setLevel(Level.INFO);
			}
		/*********************/
		
		/*******************
		 * Start main interface
		 */
		try
			{
			Variables.getLogger().info("End init, Launching main process");
			new ServerManager();
			}
		catch (Exception exc)
			{
			UsefulMethod.failedToInit(exc);
			}
		/******************/
		
		//End of the main class
		}

	/****************************************/
	public static void main(String[] args)
		{
		new Main();
		}
	/*2019*//*RATEL Alexandre 8)*/
	}

