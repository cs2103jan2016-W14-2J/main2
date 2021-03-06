import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/*
 *@author Chiang Jia Feng
 *@Description: Configuration (detect first entry and )
 */
public class Configuration extends Application 
{
	private static final String CONFIG_FIRSTLINE_DIRECTORY_UNKNOWN = "DBDIR = EMPTY";
	private static final String CONFIG_FIRSTLINE_DIRECTORY_KNOWN = "DBDIR = %1$s";
	private static final String CONFIG_SECONDLINE_FILENAME_UNKNOWN = "FILENAME = EMPTY";
	private static final String CONFIG_SECONDLINE_FILENAME_KNOWN = "FILENAME = %1$s";
	private static final String CONFIG_SEPARATOR = System.getProperty("line.separator");
	private static final String DIALOG_TITLE = "SAVE DATABASE"; 
	
	private static final int PARAM_FOR_DIR = 8;	//include space
	private static final int PARAM_FOR_NAME = 11; //include space
	
	final static public String PARAM_CONFIG_DEFAULT_FILENAME = "Config.txt";
	final static public String PARAM_DB_DEFAULT_FILENAME = "DODODB";
	
	final static public File configFile = new File(PARAM_CONFIG_DEFAULT_FILENAME);
	final static public FileChooser fileChooser = new FileChooser();
	public static ArrayList<String> listOfConfigs = new ArrayList<String>();
	public File dbFile=null;
	public FileOutputStream outputStream;
	public Stage primaryStage;
	public String strDBdir = "";
	public String strDBname = "";
	@Override
	public void start(Stage primaryStage) 
	{
		this.primaryStage = primaryStage;
		try 
		{
			if(checkConfigFileExist()==true)
			{
				System.out.println("config file exist, read config file now");
				listOfConfigs.addAll(readConfigFile());
				System.out.println("check content of listOfConfigs");
				System.out.println(listOfConfigs);
				updateParaFromList();
				checkConfigsContent();

			}
			else if(checkConfigFileExist()==false)
			{
				System.out.println("config file and db file dont exist creating one NOW!");
				createConfigFile();
				createDbFile();
				updateConfig();
				
			}
		} 
		catch(Exception e) 
		{
			System.out.println(e);
		}
		GUI gui = new GUI();
		System.out.println("config : " + strDBdir + " " + strDBname);
		gui.setDBdir(strDBdir);
		gui.setDBname(strDBname);
		System.out.println("proceed to gui");
		gui.start(primaryStage);
	}

	private void checkConfigsContent() throws IOException 
	{
		File testFile = new File(strDBdir);
		if(!testFile.exists() || !strDBname.contains(".txt"))
		{
			System.out.println("dosent exist such file in that directory");
			createDbFile();
			updateConfig();
		}		
	}
	private void updateConfig() throws IOException 
	{
		updateParameters();
		outputStream = new FileOutputStream(PARAM_CONFIG_DEFAULT_FILENAME);
		outputStream.write((String.format(CONFIG_FIRSTLINE_DIRECTORY_KNOWN, strDBdir) + CONFIG_SEPARATOR).getBytes());
		outputStream.write((String.format(CONFIG_SECONDLINE_FILENAME_KNOWN, strDBname) + CONFIG_SEPARATOR).getBytes());
		System.out.println("successfully update parameters(arraylist, dbdir, dbname) and write to config file");
	}
	private void updateParameters() 
	{
		strDBdir = dbFile.getPath();
		strDBname = dbFile.getName();
		listOfConfigs.add(strDBdir);
		listOfConfigs.add(strDBname);		
	}
	private void clearText() throws IOException {
		FileWriter fileWrite = new FileWriter(PARAM_CONFIG_DEFAULT_FILENAME);
		fileWrite.write("");
		fileWrite.close();
	}
	private ArrayList<String> readConfigFile() throws IOException 
	{
		ArrayList<String> tempListOfConfigs = new ArrayList<String>();
		FileReader fileReader = new FileReader(configFile);
		BufferedReader bufferReader = new BufferedReader(fileReader);
		String text="";
		while ((text = bufferReader.readLine()) != null) 
		{
			tempListOfConfigs.add(text);
		}
		bufferReader.close();
		System.out.println("successfully read from config file");
		return tempListOfConfigs;	
	}
	private void createDbFile() 
	{
		openDbDialog();
		System.out.println("successfully created db file");
	}
	private void openDbDialog() 
	{
		initFileChooser();
		dbFile = fileChooser.showSaveDialog(primaryStage);
        if(dbFile != null)
        {
            SaveFile("", dbFile);
        }
        else if(!dbFile.getPath().contains(".txt"))
        {
        	openDbDialog();
        }
        else
        {
       	 	System.exit(0);
        }
	}
	private void initFileChooser() 
	{
		fileChooser.setTitle(DIALOG_TITLE);
		fileChooser.setInitialFileName(PARAM_DB_DEFAULT_FILENAME);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);		
	}
	private void SaveFile(String content, File localfile)
	{
		try 
        {
            FileWriter fileWriter;
            fileWriter = new FileWriter(localfile);
            fileWriter.write(content);
            fileWriter.close();
        } 
        catch (IOException ex)
        {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        }    
	}
	private void createConfigFile() throws IOException 
	{
		outputStream = new FileOutputStream(PARAM_CONFIG_DEFAULT_FILENAME, true);
		writeFormat();
		System.out.println("successfully created config file and formatted ");
	}
	private void writeFormat() throws IOException 
	{
		outputStream.write((CONFIG_FIRSTLINE_DIRECTORY_UNKNOWN + CONFIG_SEPARATOR).getBytes());
		outputStream.write((CONFIG_SECONDLINE_FILENAME_UNKNOWN + CONFIG_SEPARATOR).getBytes());		
	}
	private boolean checkConfigFileExist() 
	{
		return configFile.exists();
	}
	private void updateParaFromList() 
	{
		try
		{
			strDBdir = listOfConfigs.get(0).substring(PARAM_FOR_DIR);
			strDBname = listOfConfigs.get(1).substring(PARAM_FOR_NAME);
			System.out.println(strDBdir + "             " + strDBname);		
		}
		catch(Exception e) 
		{
			System.out.println(e);
		}
	}

	/**
	 * Accessor
	 */	
	public String getDbDir()
	{
		return strDBdir;
	}
	public String strDbName()
	{
		return strDBname;
	}
	/**
	 * Mutators
	 */	
	public String setDbDir()
	{
		return strDBdir;
	}
	public String setDbName()
	{
		return strDBname;
	}

}
