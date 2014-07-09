package com.test.pkg;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="root")
public class TestXMLSerialization 
{
	 @Element(name="message")
	   private String text;

	   @Attribute(name="id")
	   private int index;

	   public TestXMLSerialization() {
	      super();
	   }  

	   public TestXMLSerialization(String text, int index) {
	      this.text = text;
	      this.index = index;
	   }

	   public String getMessage() {
	      return text;
	   }

	   public int getId() {
	      return index;
	   }
}
