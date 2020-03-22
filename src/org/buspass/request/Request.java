package org.buspass.request;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.buspass.connection.*;
import org.buspass.route.Route;

/**
 * @author missandh
 * Requests by bus users for altering bus pass status or to request admin to add new route or 
 * modify existing routes or to provide any feedback to the admin.
 * 
 * Methods:
 * 
 * getBusPassStatus(int userid) : Get current bus pass status for a user
 * requestCancelBusPass(int userid): Cancel bus pass for a user
 * requestSuspendBusPass(int userid): Suspend bus pass for a user
 * requestReactivateBusPass (int userid, int routeid, int scheduleid): reactivate buspass for a user
 * 
 * 
 * Old: 
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

	/*
	 * Request to cancel, suspend, reactivate the bus pass by bus user
	 * The cancel and suspend request will be auto approved without any checks
	 * The reactivate request will depend on total capacity available on the route he wants
	 */
	public String getBusPassStatus(int userid)
	{
		String status="";
		ResultSet rset;
		Connection dbcon = Connections.makeConnection();
		String squery = "select buspassstatus from buspassmaster where userid = "+ userid +";" ;
		try 
		{
			rset = Connections.sendQuery(dbcon, squery);

			while (rset.next())
			{	 
                status = rset.getString(1);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		Connections.closeConnection();
		return status;
	}
	
	public void requestCancelBusPass (int userid)
	{
		/* 
		 * User can request to cancel the bus pass
		 * Request will be auto approved and data updated
		 */
		String cquery = "update buspassmaster set buspassstatus = \"Cancel\" where userid = " + userid +";";
		if(getBusPassStatus(userid).toLowerCase() != "cancel")
		{
			if (Connections.sendStatement(cquery))
			{
				System.out.println("Successfully cancelled the buspass for userid: "+userid);
				System.out.println("If you want to reactivate the buspass please send a request to administrator. ");

			}
		}
		else 
		{
			System.out.println("The Buspass is already in Cancelled state for user: "+ userid);
		}
	}
	
	public void requestSuspendBusPass (int userid)
	{
		/* 
		 * User can request to suspend the bus pass
		 * Request will be auto approved and data updated
		 */
		String cquery = "update buspassmaster set buspassstatus = \"Suspend\" where userid = " + userid +";";
		if(getBusPassStatus(userid).toLowerCase() != "suspend")
		{
			if (Connections.sendStatement(cquery))
			{
				System.out.println("Successfully Suspended the buspass for userid: "+userid);
				System.out.println("Once you are ready to reactivate the buspass please send a request to administrator. ");
			}
		}
		else 
		{
			System.out.println("The Buspass is already in Suspended state for user: "+ userid);
		}		
	}

	public void requestReactivateBusPass (int userid, int routeid, int scheduleid)
	{
		/* 
		 * User can request to reactivate the bus pass
		 * Request will be auto approved and data updated based on seat availability in route
		 */
		Route userroute = new Route();
		
		String cquery = "update buspassmaster set buspassstatus = \"Active\" where userid = " + userid +";";
		if(getBusPassStatus(userid).toLowerCase() != "active")
		{
			if(userroute.isSeatAvailableOnRoute(routeid,scheduleid))
				if (Connections.sendStatement(cquery))
				{
					System.out.println("Successfully Reactivated the buspass for userid: "+userid + " on Route ID: "+ routeid + " on selected Schedule");
				}
		}
		
		else 
		{
			System.out.println("The Buspass is already in Active state for user: "+ userid);
		}		
	}

	
	
//	public void requestAlterBusPass (String reqtype, int userid, Date dateofrequest) {
//		
//		/*
//		 * Request to cancel, suspend, reactivate the bus pass by bus user
//		 * The cancel and suspend request will be auto approved without any checks
//		 * The reactivate request will depend on total capacity available on the route he wants
//		 */
//		
//		
//		String reqstatus = "New";
//		String alterquery = "INSERT INTO "+alterTable+" VALUES(default,"+reqtype+ ","+ userid+ "," + dateofrequest+ "," +reqstatus+ ")";
//
//		if( Connections.sendStatement(alterquery))
//			System.out.println("Successfully submitted a request to " + reqtype + " the bus pass. Please note an administrator will need to validate your request.");		
//	}
//	
//	public void validateBusPassRequest()
//	{
//		ResultSet result;
//		int breqid;
//		String breqtype = new String();
//		int userid;
//		Date dateofrequest;
//		String reqstatus = new String();
//		
//		Connection dbcon = Connections.makeConnection();
//		String selectquery = "select * from buspassalterrequest where reqstatus = New" ;
//		
//		try {
//			
//			PreparedStatement pst = dbcon.prepareStatement(selectquery);
//			result = pst.executeQuery();
//			
//			while(result.next())
//			{
//				breqid = result.getInt(1);
//				breqtype = result.getString(2);
//				userid = result.getInt(3);
//				dateofrequest= result.getDate(4);
//				reqstatus = result.getString(5);
//			}
//			
//				System.out.println("Successfully updated status of the request " + breqtype + " to " + reqstatus );
//		} 
//			
//		catch (SQLException sqlex) {
//			sqlex.printStackTrace();
//		}
//		catch(Exception e)
//		{ 
//			System.out.println(e);
//		}
//
//		Connections.closeConnection();
//
//	}

}
