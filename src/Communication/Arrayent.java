package Communication;

/* $Author: Madhusudhan.G $
$Revision: #1.0 $
$DateTime: 2014/06/27  $
*/


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import BusinessLogic.RevealationMessageHandler;
import Common.*;
import DTO.*;
import Interface.*;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;



public class Arrayent implements IArrayent,CompletionHandler<Integer, ByteBuffer>
{
	
	 ByteBuffer _receivingBuffer;// = ByteBuffer.allocateDirect(1024);
	 ByteBuffer _sendingBuffer;// = ByteBuffer.wrap("Hello".getBytes());
	 AsynchronousSocketChannel _asynchronousSocketChannel;
	Constants _constantobj;
	IRevealationMessageHandler _handler;
	
	 public synchronized void SubscribeMessageHandler( IRevealationMessageHandler msgHandler ) 
	 {
	        _handler = msgHandler;
	 }
	    
	 public synchronized void UnSubscribeMessageHandler()
	 {
	       _handler = null;
	 }
	
 
  @Override
	public void completed(Integer result, ByteBuffer attachment)
 {
 	 try
 	 {
 		 	if(_asynchronousSocketChannel.isOpen())
 		 	{
		    	 
 		 		//System.out.println("mess1:" + HexBin.encode( Arrays.copyOfRange(_receivingBuffer.array(),0, result) ));
		         
		         if(result>=7)
		         {
		        	 _receivingBuffer.flip();
		        	 System.out.println("mess1:" + HexBin.encode( Arrays.copyOfRange(_receivingBuffer.array(),0, result) ));
		        	 RaiseEvent(Arrays.copyOfRange(_receivingBuffer.array(),0, result));
				
		        	 _receivingBuffer.clear();
		         }
		         else
		         {
		        	 _asynchronousSocketChannel.read(_receivingBuffer, null, this);
		         }
			  // 	_asynchronousSocketChannel.read(_receivingBuffer, null, this);
 		 	}
 	 } 
 	 catch (Exception e) 
 	 {
				// TODO Auto-generated catch block
 		 	e.printStackTrace();
 		 	_asynchronousSocketChannel.read(_receivingBuffer, null, this);
 	 } 
 }

	@Override
	public void failed(Throwable exc, ByteBuffer attachment) 
	{
		// TODO Auto-generated method stub
		System.out.println(exc.getMessage());
	}
  
	@Override
	public Boolean Connect(String IpAddress,int portNumber) {
		// TODO Auto-generated method stub
		
		try
		{
			  Void connect = _asynchronousSocketChannel.connect(new InetSocketAddress(_constantobj.SERVER_IP_ADDRESS, portNumber)).get();
	          if (connect == null)
	          {
	               System.out.println("Local address: " + _asynchronousSocketChannel.getLocalAddress());
	              _asynchronousSocketChannel.read(_receivingBuffer, null, this);
	          }
		}
     catch (ExecutionException e)
     {
 		// TODO Auto-generated catch block
  		e.printStackTrace();
     } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Boolean Disconnect() {
		// TODO Auto-generated method stub
		try
		{
			if(_asynchronousSocketChannel.isOpen())
				_asynchronousSocketChannel.close();
			return true;
		}
		catch(Exception exp)
		{
			//TODO : Log files
			return false;
		}
	}

	@Override
	public Boolean SendData(byte[] data) {
		// TODO Auto-generated method stub
		 //sending data
     try 
     {
     	if(data!=null)
     	{
	        	System.out.println("Send Data:" + HexBin.encode( data ));
	        	_sendingBuffer=ByteBuffer.wrap(data);
	       		_asynchronousSocketChannel.write(_sendingBuffer).get();
	       		_asynchronousSocketChannel.read(_receivingBuffer, null, this);
     	}
    		
		} 
     catch (InterruptedException e) 
     {
			// TODO Auto-generated catch block
     	_asynchronousSocketChannel.read(_receivingBuffer, null, this);
			e.printStackTrace();
		}
     catch (ExecutionException e)
     {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public byte[] ReceivedData() {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean InitializeArrayentInterface(String said,String pwd,String aesKey)
	{
		return null;
	}
	
	public Arrayent()
	{
		
		try 
		{
			_handler= new RevealationMessageHandler();
			_constantobj= new Constants();
			_receivingBuffer = ByteBuffer.allocate(1024);
			_asynchronousSocketChannel=AsynchronousSocketChannel.open();
			
			//_handler.HandleRevealMessage(new byte[]{0,0,(byte)0XED,00,02,(byte)0xE3,0X01});
		} 
		catch (Exception ex)
     {
         System.err.println(ex);
     }
	}
	
	public void SendMessage(byte[] data)
	{
		// TODO Auto-generated method stub
		 //sending data
	    try 
	    {
		       	if(data!=null)
		       	{
			        	System.out.println("Send Data:" + HexBin.encode( data ));
			        	_sendingBuffer=ByteBuffer.wrap(data);
			       		_asynchronousSocketChannel.write(_sendingBuffer).get();
		       	}
	    } 
	    catch (InterruptedException e) 
	    {
				// TODO Auto-generated catch block
	   			e.printStackTrace();
	    }
	    catch (ExecutionException e)
	    {
				// TODO Auto-generated catch block
				e.printStackTrace();
	    }
	}
	
	private synchronized void RaiseEvent(byte[] data) 
	{
		RevealationMessageEventObject e = new RevealationMessageEventObject(this, data);
        SendData(_handler.HandleRevealationMessage(e)); 
    }
}
