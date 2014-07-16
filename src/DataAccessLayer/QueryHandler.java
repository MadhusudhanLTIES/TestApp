package DataAccessLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DTO.API;
import DTO.ChoiceInformation;
import DTO.ElementInformation;
import DTO.OpcodeInformation;
import DTO.SQLQueries;


public class QueryHandler
{
	private Connection _conn;
	private SQLQueries _sqlqueryObject;
	
	private static QueryHandler _queryObject;
	
	private QueryHandler()
	{
		
	}
	
	public static QueryHandler GetQueryHandlerObject()
	{
		if(_queryObject == null)
			_queryObject= new QueryHandler();
		
		return _queryObject;
	}
	
	/**
	 * @param dbName
	 */
	public void InitializeConnection(String dbName)
	{
		try
		{
			// TODO Auto-generated constructor stub
			Class.forName("org.sqlite.JDBC");
			this._conn = DriverManager.getConnection("jdbc:sqlite:"+ dbName +".db");
			this._sqlqueryObject = new SQLQueries();
		}
		catch(Exception exp)
		{
			// TODO: handle exception
			exp.printStackTrace();
		}
	}
	
	public void Create_SAID_Table()
	{
		try
		{
			if(!CheckTableExists(this._sqlqueryObject.SAID_TABLE_NAME))
				ExecuteQuery(this._sqlqueryObject.CREATE_SAID_TABLE);
		}
		catch (Exception exp) 
		{
			// TODO: handle exception
			exp.printStackTrace();
		}
	}
	
	public void Create_Wide_Protocol_Tables()
	{
		try 
		{
			if(!CheckTableExists(this._sqlqueryObject.WIDE_API_TABLE_NAME))
				ExecuteQuery(this._sqlqueryObject.CREATE_WIDE_API_TABLE);
			if(!CheckTableExists(this._sqlqueryObject.WIDE_OPCODE_TABLE_NAME))
				ExecuteQuery(this._sqlqueryObject.CREATE_WIDE_OPCODE_TABLE);
			if(!CheckTableExists(this._sqlqueryObject.WIDE_ELEMENTS_TABLE_NAME))
				ExecuteQuery(this._sqlqueryObject.CREATE_WIDE_ELEMENTS_TABLE);
			if(!CheckTableExists(this._sqlqueryObject.WIDE_CHOICES_TABLE_NAME))
				ExecuteQuery(this._sqlqueryObject.CREATE_WIDE_CHOICES_TABLE);
		}
		catch (Exception exp) 
		{
			// TODO: handle exception
		}
	}

	public void InsertSAIDData(String said, String aeskey, String pwd)
	{
		try
		{
			String query = String.format("INSERT OR REPLACE into %s Values ('%s','%s','%s')", this._sqlqueryObject.SAID_TABLE_NAME, said,aeskey,pwd );
		//	System.out.println(query);
			ExecuteQuery(query);
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
	}
	
	public void InsertAPI(API apiInfo)
	{
		try
		{
			String query = String.format("INSERT OR REPLACE into %s Values (%d,'%s','%s','%s')", this._sqlqueryObject.WIDE_API_TABLE_NAME, Integer.parseInt(apiInfo.API_ID),apiInfo.API_Name,apiInfo.API_Type,apiInfo.API_Version );
		//	System.out.println(query);
			ExecuteQuery(query);
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
	}
	
	public void  InsertOpcode(OpcodeInformation opcodeInfo, int apiId)
	{
		String query = String.format("INSERT OR REPLACE into %s Values (%d,'%s','%s',%d)", this._sqlqueryObject.WIDE_OPCODE_TABLE_NAME, Integer.parseInt(opcodeInfo.Opcode_ID),opcodeInfo.Opcode_Name,opcodeInfo.Opcode_Feedback,apiId );
		//System.out.println(query);
		ExecuteQuery(query);
	}
	
	public void InsertElement(ElementInformation elementInfo, int apiId,int opcodeId)
	{
		String query = String.format("INSERT OR REPLACE into %s Values ('%s','%s',%d,%d,%d,%d,%d)", this._sqlqueryObject.WIDE_ELEMENTS_TABLE_NAME, elementInfo.Element_ID,elementInfo.Element_Keyword,Integer.parseInt(elementInfo.Element_Length),Integer.parseInt(elementInfo.Element_Byte),Integer.parseInt(elementInfo.Element_Bit),apiId,opcodeId );
		//System.out.println(query);
		ExecuteQuery(query);
	}
	
	public void  InsertChoice(ChoiceInformation choiceInfo,int apiId,int opcodeId,String elementId)
	{
		String query = String.format("INSERT OR REPLACE into %s Values ('%s','%s','%s',%d,%d,'%s')", this._sqlqueryObject.WIDE_CHOICES_TABLE_NAME, choiceInfo.Choice_ID,choiceInfo.Choice_Keyword,choiceInfo.Choice_Value,apiId,opcodeId,elementId );
		//System.out.println(query);
		ExecuteQuery(query);
	}
	
	public List<String> GetSAIDS()
	{
		try
		{
			ResultSet result = ExecuteQuerySet(this._sqlqueryObject.GET_SAID_QUERY);
			List<String> saids= new ArrayList<String>();
			if(result!=null)
			{
				while(result.next())
					saids.add(result.getString("SAID"));
			}
			return saids;
		}
		catch(Exception exp)
		{
			// TODO: handle exception
			exp.printStackTrace();
			return null;
		}
	}
	
	public String GetAesKey(String said)
	{
		try
		{
			ResultSet result = ExecuteQuerySet(String.format("Select AES_KEY from VAS_SAID_Table where SAID='%s'", said));
			
			if(result!=null)
			{
				return result.getString("AES_KEY");
			}
			return null;
		}
		catch(Exception exp)
		{
			// TODO: handle exception
			exp.printStackTrace();
			return null;
		}
	}
	
	public String GetPassword(String said)
	{
		try
		{
			ResultSet result = ExecuteQuerySet(String.format("Select PWD from VAS_SAID_Table where SAID='%s'", said));
			
			if(result!=null)
			{
				return result.getString("PWD");
			}
			return null;
		}
		catch(Exception exp)
		{
			return null;
		}
	}
	
	private ResultSet ExecuteQuerySet(String query)
	{
		try
		{
			ResultSet result;
			this._conn.setAutoCommit(false);
			Statement stat = _conn.createStatement();
			result=stat.executeQuery(query);
			this._conn.setAutoCommit(true);
			return result;
		}
		catch(Exception exp)
		{
			// TODO: handle exception
			exp.printStackTrace();
			return null;
		}
	}
	
	private void ExecuteQuery(String query)
	{
		try
		{
			this._conn.setAutoCommit(false);
			Statement stat = _conn.createStatement();
			stat.executeUpdate(query);
			
			this._conn.setAutoCommit(true);
		}
		catch(Exception exp)
		{
			// TODO: handle exception
			exp.printStackTrace();
		}
	}
	
	private Boolean CheckTableExists(String tableName)
	{
		try
		{
			
			ResultSet result = ExecuteQuerySet("SELECT name FROM sqlite_master WHERE type='table' AND name='" +tableName +"';");
			
			
			if(result!=null && result.getString("name")!=null)
				return true;
			else
				return false;
			
		} 
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
}
