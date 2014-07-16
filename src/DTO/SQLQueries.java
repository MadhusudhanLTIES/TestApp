package DTO;

public class SQLQueries
{
	public final String CREATE_WIDE_API_TABLE = "Create Table VAS_WIDE_API_Table( API_ID int NOT NULL PRIMARY KEY, API_NAME varchar(255), API_TYPE varchar(255), API_VERSION varchar(255))";
	public final String CREATE_WIDE_OPCODE_TABLE = "Create Table VAS_WIDE_Opcode_Table(Opcode_ID int NOT NULL,Opcode_Name varchar(255),Opcode_Feedback varchar(1),Opcode_Api_Id int, FOREIGN KEY(Opcode_Api_Id) REFERENCES VAS_WIDE_API_Table(API_ID),PRIMARY KEY (Opcode_Id, Opcode_Api_Id,Opcode_Feedback))";
	public final String CREATE_WIDE_ELEMENTS_TABLE = "Create Table VAS_WIDE_Elements_Table( Element_ID TEXT NOT NULL , Element_Keyword varchar(255), Element_Length int, Element_Byte int, Element_Bit int, Element_Api_ID int, Element_Opcode_ID int, FOREIGN KEY(Element_Api_ID) REFERENCES VAS_WIDE_API_Table(API_ID), FOREIGN KEY(Element_Opcode_ID) REFERENCES VAS_WIDE_Opcode_Table(Opcode_ID),PRIMARY KEY(Element_ID, Element_Api_ID, Element_Opcode_ID))";
	public final String CREATE_WIDE_CHOICES_TABLE = "Create Table VAS_WIDE_Choices_Table( Choice_ID TEXT NOT NULL , Choice_Keyword varchar(255), Choice_Value varchar(255), Choice_Api_ID int , Choice_Opcode_ID int , Choice_Element_ID int, FOREIGN KEY(Choice_Api_ID)REFERENCES VAS_WIDE_API_Table(API_ID), FOREIGN KEY(Choice_Opcode_ID) REFERENCES VAS_WIDE_Opcode_Table(Opcode_ID) FOREIGN KEY(Choice_Element_ID) REFERENCES VAS_WIDE_Elements_Table(Element_ID), PRIMARY KEY(Choice_ID,Choice_Element_ID,Choice_Opcode_ID,Choice_Api_ID))";
	public final String CREATE_DEVICE_DETAILS_TABLE = "Create Table VAS_DEVICE_DETAILS_TABLE ";
	public final String CREATE_SAID_TABLE="Create Table VAS_SAID_Table( SAID varchar(255) PRIMARY KEY, AES_KEY varchar(255), PWD varchar(255))";
	
	public final String DEVICE_DETAILS_TABLE_NAME="VAS_DEVICE_DETAILS_TABLE";
	public final String WIDE_API_TABLE_NAME = "VAS_WIDE_API_Table";
	public final String WIDE_OPCODE_TABLE_NAME = "VAS_WIDE_Opcode_Table";
	public final String WIDE_ELEMENTS_TABLE_NAME = "VAS_WIDE_Elements_Table";
	public final String WIDE_CHOICES_TABLE_NAME = "VAS_WIDE_Choices_Table";
	public final String SAID_TABLE_NAME = "VAS_SAID_Table";
	
	public final String GET_SAID_QUERY = "Select SAID from VAS_SAID_Table";
}
