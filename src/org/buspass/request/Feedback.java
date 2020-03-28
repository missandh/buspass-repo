/**
 * This class contains methods to give and read feedback
 * Methods: 
 * 		giveFeedback : User can submit the feedback
 * 		Parameters: String fbacktype, int userid, String fbackdetails
 * 		
 * 		private updateFeedback: Admin can update the feedback after it is read to the "read" state
 * 		Parameters: int fbackid
 * 
 * 		readFeedback: Admin can access the feedback and mark as read
 * 		Parameters: None. Prints the feedback which are in new state 
 * 		and marks them as read by calling updateFeedback method
 * 
 */
package org.buspass.request;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.Calendar;

import org.buspass.connection.Connections;

/**
 * @author missandh
 *
 *feedback table columns:
 	*	fbackid int(11) AI PK 
 	*	fbacktype char(20) 
 	*	userid int(11) 
 	*	dateoffeedback date 
 	*	status char(30)
 	*	fbackdetails varchar(200)

 */

public class Feedback {
	
	private String table = "feedback";
	
	public void giveFeedback(String fbacktype, int userid, String fbackdetails)
	{		
		// Get java.util.date time through Calendar class this java date will represent "now"
		Date curdate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		
		String fbstatus = "new";

		String squery = "INSERT INTO " + table + " VALUES ( DEFAULT, \'" + fbacktype + "\', "+ userid + ", \'" + curdate +"\', \'" + fbstatus +"\', \'" + fbackdetails + "\');" ; 
		if( Connections.sendStatement(squery))
			System.out.println("Successfully submitted the feedback");
	}
	
	private void updateFeedback(int fbackid)
	{
		String squery = "Update " + table + "set status=\'read\' where fbackid=" + fbackid +";";
		if( Connections.sendStatement(squery))
			System.out.println("Read the feedback with Feedback ID: " + fbackid);
	}
	
	public void readFeedback()
	{
		String squery = "SELECT * FROM "+ table + "WHERE status=\'new\';";
		ResultSet rset = null;
		Connection dbcon = Connections.makeConnection();
		try 
		{
			rset = Connections.sendQuery(dbcon, squery);
			System.out.println("Printing result...");
			int count=1;
			
			while (rset.next())
			{
				 
                // Let's fetch the data by column name
                int fbackid = rset.getInt(1);
             	String fbacktype = rset.getString(2);
             	int userid  = rset.getInt(3);
             	String dateoffeedback = rset.getString(4); 
             	String status = rset.getString(4);
             	String fbackdetails = rset.getString(5);
               
                System.out.println(count + ":\tFeedback ID: " + fbackid + 
                        ", Feedback type: " + fbacktype + 
                        ", User ID: " + userid + 
                        ", Date Submitted: " + dateoffeedback +
                        ", Status: " + status +
                        ", Feedback: " + fbackdetails );
               
                //now that admin read this feedback let's update the feedback table to mark status as read
                updateFeedback(fbackid);
			}
		}
		catch(Exception e)
		{
			System.out.println("Apologies! There was a problem submitting your feedback. Please retry.");
			//e.printStackTrace();
		}
	}
}
