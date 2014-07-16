package DTO;

import java.util.List;

public class API 
{
	//@Attribute
      public String API_Name ;
	 //@Attribute
      public String API_Type ;
     // @Attribute
      public String API_ID ;
     // @Attribute
      public String API_Version ;
     // @Element
      public List<OpcodeInformation> API_Opcodes ;
      
      public API(String name, String id,String type,String version)
      {
    	  API_Name=name;
    	  API_ID=id;
    	  API_Type=type;
    	  API_Version=version;
    	  
      }
}