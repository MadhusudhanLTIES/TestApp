package DTO;
import java.util.List;



//@Root
public class OpcodeInformation
{
	 // @Attribute
      public String Opcode_Name ;
	 // @Attribute
      public String Opcode_ID ;
	 // @Attribute
      public String Opcode_Feedback ;
     // @Element
      public List<ElementInformation> Opcode_Elements;
      
      public OpcodeInformation(String name,String id,String feedBack)
      {
    	  Opcode_Name = name;
    	  Opcode_ID = id;
    	  Opcode_Feedback = feedBack;
      }
}
