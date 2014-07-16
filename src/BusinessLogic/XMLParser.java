package BusinessLogic;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import DTO.*;
import DataAccessLayer.*;


public class XMLParser 
{
	APIs _apiInfo;
	QueryHandler _queryHandler;
	
	public XMLParser()
	{
		try
		{
			//_docBuilderfactory = DocumentBuilderFactory.newInstance();
			//_docBuilder= _docBuilderfactory.newDocumentBuilder();
			_apiInfo = new APIs();
			_apiInfo.API = new ArrayList<API>();
			//_queryHandler = new QueryHandler("test");
			_queryHandler = QueryHandler.GetQueryHandlerObject();
		}
		catch(Exception exp)
		{
			//Log to file
		}
	}
	
	private void CreateDataTable()
	{
			try
			{
			_queryHandler.Create_Wide_Protocol_Tables();
			
			for (API api : _apiInfo.API)
			{
				_queryHandler.InsertAPI(api);
				
				//Insert Opcode Data
				for (OpcodeInformation opc : api.API_Opcodes)
				{
					_queryHandler.InsertOpcode(opc, Integer.parseInt(api.API_ID));
					
					//Insert Element Data
					for (ElementInformation einfo : opc.Opcode_Elements)
					{
						_queryHandler.InsertElement(einfo, Integer.parseInt(api.API_ID), Integer.parseInt(opc.Opcode_ID));
						
						//Insert Choice Data
						
						for (ChoiceInformation cinfo : einfo.Element_Choices)
						{
							_queryHandler.InsertChoice(cinfo, Integer.parseInt(api.API_ID), Integer.parseInt(opc.Opcode_ID), einfo.Element_ID);
						}
					}
				}
			}
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
	}
	
	public Boolean Parse(Document doc)
	{
		try
		{
			Element docElement=doc.getDocumentElement();
			NodeList nList= docElement.getElementsByTagName("API");
			
			if(nList!=null && nList.getLength()>0)
			{
				for(int index=0;index<nList.getLength();index++)
				{
					API apiInfo=GetApiInfo(nList.item(index));
					apiInfo.API_Opcodes=GetOpcodeInformation(((Element)nList.item(index)).getElementsByTagName("opcode"));
					_apiInfo.API.add(apiInfo);
				}
			}
			
			CreateDataTable();
			
			return true;
		}
		catch(Exception exp)
		{
			return false;
		}
	}
	
	private API GetApiInfo(Node node)
	{
		return new API(getNodeAttr("name", node),getNodeAttr("api", node),getNodeAttr("type", node),getNodeAttr("version", node));
	}
	
	private OpcodeInformation GetOpcodeInfo(Node node)
	{
		return new OpcodeInformation(getNodeAttr("name", node),getNodeAttr("id", node),getNodeAttr("feedback", node));
	}

	private ElementInformation GetElementInfo(Node node)
	{
		return new ElementInformation(getNodeAttr("keyword", node), getNodeAttr("id", node), getNodeAttr("length", node), getNodeAttr("byte", node), getNodeAttr("bit", node));
	}
	
	private ChoiceInformation GetChoiceInfo(Node node)
	{
		return new ChoiceInformation(getNodeAttr("keyword", node),getNodeAttr("id", node) ,getNodeAttr("value", node));
	}
	
	private List<OpcodeInformation> GetOpcodeInformation(NodeList nList)
	{
		List<OpcodeInformation> opcodeInfo = new ArrayList<OpcodeInformation>();
		if(nList!=null && nList.getLength()>0)
		{
			for(int index=0;index < nList.getLength();index++)
			{
				OpcodeInformation opcode = GetOpcodeInfo(nList.item(index));
				opcode.Opcode_Elements = GetElementInformation(((Element)nList.item(index)).getElementsByTagName("element"));
				opcodeInfo.add(opcode);
			}
		}
		return opcodeInfo;
	}
	
	
	private List<ElementInformation> GetElementInformation(NodeList nList)
	{
		List<ElementInformation> elementInfo = new ArrayList<ElementInformation>();
		
		if(nList!=null && nList.getLength()>0)
		{
			for(int index=0 ; index < nList.getLength();index++)
			{
				ElementInformation eInfo = GetElementInfo(nList.item(index));
				eInfo.Element_Choices = GetChoiceInformation(((Element)nList.item(index)).getElementsByTagName("choice"));
				elementInfo.add(eInfo);
			}
		}
		
		return elementInfo;
	}
	
	private List<ChoiceInformation> GetChoiceInformation(NodeList nList)
	{
		List<ChoiceInformation>choiceInfo = new ArrayList<ChoiceInformation>();
		
		if(nList!=null && nList.getLength()>0)
		{
			for(int index=0; index < nList.getLength();index++)
			{
				choiceInfo.add(GetChoiceInfo(nList.item(index)));
			}
		}
		
		return choiceInfo;
	}
	
	private String getNodeAttr(String attrName, Node node ) {
        NamedNodeMap attrs = node.getAttributes();
        for (int y = 0; y < attrs.getLength(); y++ ) {
            Node attr = attrs.item(y);
            if (attr.getNodeName().equalsIgnoreCase(attrName)) {
                return attr.getNodeValue();
            }
        }
        return "";
    }
}
