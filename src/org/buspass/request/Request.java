package org.buspass.request;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.buspass.connection.*;

/**
 * @author missandh
 * Requests by bus users for altering bus pass status or to request admin to add new route or 
 * modify existing routes or to provide any feedback to the admin.
 * Methods:
 * 
 * requestAlterBusPass:  For bus user request modification on the bus pass status
 * Takes 3 parameters type of request - String, 
 * userid - int, date of request - pass todays date - Date
 * 
 * viewBusPassRequests() : to view all requests
 * 
 * validateBusPassRequest() : For admin to validate the request for bus pass updates
 * 
 * requestAddRoute() : For bus user request addition of new route to include a specific stop
 * 
 * requestModifyRoute() : For bus user request modification of his route to include a specific stop
 * the stop position is taken as input from the user
 * 
 * validateRouteAddRequests() : For Admin to validate ( approve/reject) route add requests.
 * 
 * validateRouteModifyRequests() : For Admin to validate (approve/reject) route modification requests.
 * 
 */
public class Request {

	public void requestAlterBusPass (String reqtype, int userid, Date dateofrequest) {
		
		/*
		 * Table buspassalterrequest Columns:
		 * breqid int(11) AI PK 
		 * breqtype char(20) 
		 * userid int(11) 
		 * dateofrequest date 
		 * breqstatus char(30)
		 */
		
		int result = -1;
		Connection dbcon = Connections.makeConnection();
		String reqstatus = "New";
		String alterquery = "INSERT INTO buspassalterrequest VALUES(default,?,?,?,?)" ;
		
		try {
			
			PreparedStatement pst = dbcon.prepareStatement(alterquery);
			pst.setString(2, reqtype);
			pst.setInt(3, userid);
			pst.setDate(4, dateofrequest);
			pst.setString(5, reqstatus);
			result = pst.executeUpdate();
			
			if (result!= -1) {
				System.out.println("Successfully submitted a request to " + reqtype + " the bus pass");
			}
		} 
			
		catch (SQLException sqlex) {
			sqlex.printStackTrace();
		}
		catch(Exception e)
		{ 
			System.out.println(e);
		}

		Connections.closeConnection();
		
	}
	
	
	
	public void validateBusPassRequest()
	{
		ResultSet result;
		int breqid;
		String breqtype;
		int userid;
		Date dateofrequest;
		String reqstatus;
		
		Connection dbcon = Connections.makeConnection();
		String selectquery = "select * from buspassalterrequest where reqstatus = New" ;
		
		try {
			
			PreparedStatement pst = dbcon.prepareStatement(selectquery);
			result = pst.executeQuery();
			
			while(result.next())
			{
				breqid = result.getInt(1);
				breqtype = result.getString(2);
				userid = result.getInt(3);
				dateofrequest= result.getDate(4);
				reqstatus = result.getString(5);
			}
			
				System.out.println("Successfully updated status of the request " + breqtype + " to " + );
		} 
			
		catch (SQLException sqlex) {
			sqlex.printStackTrace();
		}
		catch(Exception e)
		{ 
			System.out.println(e);
		}

		Connections.closeConnection();

	}
}
