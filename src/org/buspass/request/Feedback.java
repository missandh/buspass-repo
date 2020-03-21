/**
 * 
 */
package org.buspass.request;

import java.sql.Date;
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
}
