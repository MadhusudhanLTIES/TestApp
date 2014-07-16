package DTO;

import java.util.List;


public class ElementInformation
{
	//@Attribute
      public String Element_Keyword ;
	//@Attribute
      public String Element_ID ;
	//@Attribute
      public String Element_Length ;
	//@Attribute
      public String Element_Byte ;
	//@Attribute
      public String Element_Bit ;
	//@ElementList
      public List<ChoiceInformation> Element_Choices ;
      
      public ElementInformation(String keyword, String id, String length, String bytePositon, String bit) 
      {
		// TODO Auto-generated constructor stub
    	Element_Keyword = keyword;
    	Element_ID = id;
    	Element_Length = length;
    	Element_Byte = bytePositon;
    	Element_Bit = bit;
      }
}
