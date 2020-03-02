package com.alex.perceler.client.utils;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import org.apache.log4j.Logger;



/**********************************
 * Used to store static variables
 * 
 * @author RATEL Alexandre
 **********************************/
public class Variables
	{
	/**
	 * Variables
	 */
		
	/**	MISC	**/
	private static String softwareName;
	private static String softwareVersion;
	private static Logger logger;
	private static String mainConfigFileDirectory;
	private static ArrayList<String[][]> mainConfig;
	private static String configFileName;
	
	/**************
     * Constructor
     **************/
	public Variables()
		{
		configFileName = "configFile.xml";
		mainConfigFileDirectory = ".";
		}

	public static String getSoftwareName()
		{
		return softwareName;
		}

	public static void setSoftwareName(String softwareName)
		{
		Variables.softwareName = softwareName;
		}

	public static String getSoftwareVersion()
		{
		return softwareVersion;
		}

	public static void setSoftwareVersion(String softwareVersion)
		{
		Variables.softwareVersion = softwareVersion;
		}

	public static Logger getLogger()
		{
		return logger;
		}

	public static void setLogger(Logger logger)
		{
		Variables.logger = logger;
		}

	public static String getMainConfigFileDirectory()
		{
		return mainConfigFileDirectory;
		}

	public static void setMainConfigFileDirectory(String mainConfigFileDirectory)
		{
		Variables.mainConfigFileDirectory = mainConfigFileDirectory;
		}

	public static ArrayList<String[][]> getMainConfig()
		{
		return mainConfig;
		}

	public static void setMainConfig(ArrayList<String[][]> mainConfig)
		{
		Variables.mainConfig = mainConfig;
		}

	public static String getConfigFileName()
		{
		return configFileName;
		}

	public static void setConfigFileName(String configFileName)
		{
		Variables.configFileName = configFileName;
		}

	
	
	/*2019*//*RATEL Alexandre 8)*/
	}
