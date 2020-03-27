/**
 * 
 */
package org.buspass.route;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.buspass.connection.Connections;

/**
 * @author missandh
 *
 *Methods :
 *
 * getAllRoutes(): To print the route table and return the array lists of Route IDs
 * 
 * getStopsForRoute(int routeid): To print and return all the stops for a route
 * 		Returns ArrayList of Stop objects
 * 
 * viewAllRoutesWithStops(): To print and return all the routes along with their stop details
 * 
 * viewTypeOfVehiclesPerRoute(): To print the type of vehicles, no of vehicles on each route
 * 
 * getAvailabilityInRoute(int routeid) : To print available seats in this route. 
 * Difference of (sum of capacity of each bus on a route) - total active bus passes in this route
 * 
 * getTotalCapacityInRoute(): returning the total capacity of a route 
 * Sum of individual bus capacity in each route	
 * 
 * getSeatsAvailableInRoute(int routeid) : To return number of seats available in the route identified by routeid
 * 
 * 
 */

public class Route {
	
	String table = "route";
	private int routeid;
	
	
	public  int filledCapacityInRoute(int routeId) {
		Connection con = Connections.makeConnection();
		String query = "select count(buspassid) as Filled_Capacity from buspassmaster where routeid = "+routeId+" and buspassstatus = 'Active' ";
		int filled = 0;
		try {
			ResultSet rs = Connections.sendQuery(con, query);
			while(rs.next()) {
				 filled = rs.getInt("Filled_Capacity");
				//filled = filledcapacity;
				//System.out.println(filled);
			}
			
			Connections.closeConnection();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return filled;
		}
	
	//Overloading filledCapacityInRoute
	
	public  int filledCapacityInRoute(int routeId, int scheduleId) {
		Connection con = Connections.makeConnection();
		String query = "select count(buspassid) as Filled_Capacity from buspassmaster where routeid = "+routeId+" and buspassstatus = 'Active' and scheduleid= "+scheduleId+";";
		int filled = 0;
		try {
			ResultSet rs = Connections.sendQuery(con, query);
			while(rs.next()) {
				 filled = rs.getInt("Filled_Capacity");
			}
			
			Connections.closeConnection();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return filled;
		}
	
	//return total capacity of route
	public  int totalRouteCapacity(int routeId) {
		int capacity=0;
		Connection con = Connections.makeConnection();
		String query = "select sum(tb.capacity) as Total_capacity from bus b, typeofbus tb where b.bustype=tb.bustype and routeid =" + routeId + ";";
		try {
			ResultSet rs  = Connections.sendQuery(con, query);
			while(rs.next()) {
				//System.out.println(rs.getString("sum(capacity)"));
				capacity=rs.getInt("Total_capacity");
			}
			Connections.closeConnection();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return capacity;
	}//end of viewTotalRouteCapacity

	//Overloading totalCapacityInRoute
	public  int totalRouteCapacity(int routeId, int scheduleId) {
		int capacity=0;
		Connection con = Connections.makeConnection();
		String query = "select sum(tb.capacity) as Total_capacity from bus b, typeofbus tb,schedule s where b.bustype=tb.bustype and  s.routeid=b.routeid and s.routeid=" +routeId+ " and s.scheduleid="+ scheduleId+";";

		try {
			ResultSet rs  = Connections.sendQuery(con, query);
			while(rs.next()) {
				//System.out.println(rs.getString("sum(capacity)"));
				capacity=rs.getInt("Total_capacity");
			}
			Connections.closeConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return capacity;
	}//end of viewTotalRouteCapacity

	
	/*method used to update route and stop table in db
	 * @parameters
	 * cost:type int for cost of the route
	 * String[] stopArray: all the stop name in order
	 * 
	 * 
	 * 
	 */
	public void addRoute(int cost, String[] stopArray) {
		int routeId=0;
		try {
			Connection con = Connections.makeConnection();
			ResultSet result=Connections.sendQuery(con, "select max(routeid) from route;");
			if(result.next())
				 routeId = result.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connections.closeConnection();
		Connections.sendStatement("insert into route values(" + (routeId+1) + "," +cost + ");");
		
		for(int i=0;i<stopArray.length;i++) {
			Connections.sendStatement("insert into stop values(default,\'" + stopArray[i] + "\'," + (i+1) + "," + (routeId+1) +");");
		}
		
		System.out.println("route is successfully added");
		
	}

//method to print all the buses in a route
public void getBussesInRoute(int routeid) {
    Connection con = Connections.makeConnection();
    String query = "select busid,bustype from bus where routeid= "+routeid+" ;";
    try {
           ResultSet rs = Connections.sendQuery(con, query);
           System.out.println("\nHere is the list of busses under route id :");
           System.out.println("\n*******************");
           System.out.println("\nBusid -> bustype");
           while(rs.next()) {
                 System.out.println("\n"+rs.getInt(1)+" -> "+rs.getString(2)+" ");
    
           }
           Connections.closeConnection();
           }
    catch(Exception e) {
           e.printStackTrace();
    }

}



public void addBusToRoute(int routeid,int busid) {
       // TODO Auto-generated method stub
       //Connection con = Connections.makeConnection();
 String query = "update bus set routeid = "+routeid+" where busid = "+busid+" ;";
 System.out.println("\nBus "+busid + " added to route "+ routeid);
try {
  Connections.sendStatement(query);  
 //System.out.println("added bus in route "+ routeid);
//Connections.closeConnection();
}catch(Exception e){
 //Handle errors for Class.forName
 e.printStackTrace();
}
}




public void removeBusFromRoute(int routeid,int busid) {
       // TODO Auto-generated method stub
       //Connection con = Connections.makeConnection();
 String query = "update bus set routeid = null where busid = "+busid+" ;";
 System.out.println("\nBus "+busid + " removed from route "+ routeid);

try {
  Connections.sendStatement(query);  
 //System.out.println("removed bus in route "+ routeid);
Connections.closeConnection();
}catch(Exception e){
 //Handle errors for Class.forName
 e.printStackTrace();
}        
       
}

	/**
	 * Private inner Class Stop
	 */

	private class Stop 
	{
		private final String table = "stop";
		private int stopid;
		private String stopname;
		private int stoporder;
		private int routeid;
		
		/**
		 * @return the stopid
		 */
		public int getStopid() {
			return stopid;
		}
		
		/**
		 * @param stopid the stopid to set
		 */
		public void setStopid(int stopid) {			
			this.stopid = stopid;
		}
		
		/**
		 * @return the stopname
		 */
		public String getStopname() {
			return stopname;
		}
		
		/**
		 * @param stopname the stopname to set
		 */
		public void setStopname(String stopname) {
			this.stopname = stopname;
		}
		
		/**
		 * @return the stoporder
		 */
		public int getStoporder() {
			return stoporder;
		}
		
		
		/**
		 * @param stoporder the stoporder to set
		 */
		public void setStoporder(int stoporder) {
			this.stoporder = stoporder;
		}


		/**
		 * @return the routeid
		 */
		public int getRouteid() {
			return routeid;
		}

		/**
		 * @param routeid the routeid to set
		 */
		public void setRouteid(int routeid) {
			this.routeid = routeid;
		}

		/**
		 * @param stoporder 
		 * @param eachstop
		 */
		public int addStop(String stopname, int rid, int stoporder) {
			String stopadd = "inser into " + table + "values = (default, \'" + stopname + "\', " + stoporder + ", "+rid +");";
			
			if(Connections.sendStatement(stopadd))
				setStopid(lastAddedID());
			return getStopid();
		}


		/**
		 * @param table the table to set
		 */
		
	}
	// End of Stop inner class
	
	private int lastAddedID()
	{
		String squery= "SELECT LAST_INSERT_ID();";
		ResultSet rset = null;
		int lid = -1;
		
		//Connect to the database
		Connection dbcon = Connections.makeConnection();

		try 
		{
			rset = Connections.sendQuery(dbcon, squery);
			while(rset.next())
				lid = rset.getInt(1);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		Connections.closeConnection();		
		return lid;
	}
	
	public void addRoute(int cost, ArrayList<String> stopnames)
	{
		Stop newstop = new Stop();
		// add a new route to the database
		String routeadd = "insert into " + table + "values = (default, " + cost + ");";
		int sid;
		
		if(Connections.sendStatement(routeadd))
		{
			setRouteid(lastAddedID());
			System.out.println("Successfully added route with routeid: " + getRouteid());
			for (int count =0; count<stopnames.size(); count++)
			{
				//call addStop method. assuming stop names are given in order
				String eachstop = stopnames.get(count);
				int stoporder = count+1;
				sid = newstop.addStop(eachstop, getRouteid(),stoporder);
				if(sid!=-1)
					System.out.println("Stop added: " + eachstop + "\n The Stop ID for this is: " + sid);
			}
		}
	}
	
	public ArrayList<Integer> getAllRoutes()
	{
		ArrayList<Integer> rids = new ArrayList<Integer>();
		//Prepare the query string
		String squery = "SELECT * FROM "+ table +";";
		ResultSet rset = null;
		
		//Connect to the database
		Connection dbcon = Connections.makeConnection();

		try 
		{
			rset = Connections.sendQuery(dbcon, squery);
			System.out.println("Here are all the routes with costs ");
			
			while (rset.next())
			{	 
                // Let's fetch the data by column numbers
                Integer routeid = rset.getInt(1);
                rids.add(routeid);
                Integer cost = rset.getInt(2);
                System.out.println("Route ID: " + routeid + "\t Cost: " + cost);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		Connections.closeConnection();
		return rids;
	}
	
	public ArrayList<Stop> getStopsForRoute(int routeid)
	{
		// Create an ArrayList of all the stops that are in the route
		
		ArrayList<Stop> allstops = new ArrayList<Stop>();
		
		//Create query string
		String squery = "SELECT * FROM  stop  WHERE ROUTEID="+ routeid +" ORDER BY STOPORDER;";
		ResultSet rset = null;
		
		//Connect to the database
		Connection dbcon = Connections.makeConnection();

		try 
		{
			rset = Connections.sendQuery(dbcon, squery);
			System.out.println("\n\nHere are all the stops on the route with Route ID " + routeid +": ");
			
			while (rset.next())
			{	 
				Stop nextstop = new Stop();
                // Let's fetch the data by column numbers
                nextstop.setStopid(rset.getInt(1));
                nextstop.setStopname(rset.getString(2));
                nextstop.setStoporder(rset.getInt(3));
                nextstop.setRouteid(rset.getInt(4));
                //add to the allstops arraylist
                allstops.add(nextstop);
			}
			
			int totalstops = allstops.size();
			
			for(int i=0; i<totalstops; i++)
			{
				if(i!=totalstops-1)
					System.out.print(allstops.get(i).getStopname() + " -----> " );
				else
					System.out.print(allstops.get(i).getStopname());
			}
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		Connections.closeConnection();
		return allstops;
	}
	
	public void viewAllRoutesWithStops() 
	{
    	//View all routes and cost in route table
		ArrayList<Integer> rids = new ArrayList<Integer>();
    	Route test = new Route();
    	rids.addAll(test.getAllRoutes());
    	
    	//View all routes and stops
    	for (Integer eachroute : rids)
    	{
    		test.getStopsForRoute(eachroute);
    	}
	}

	public void viewTypeOfVehiclesPerRoute()
	{
		//select  s.routeid, scheduletime as "Scheduled Time", bustype, count(s.busid) as "Total buses" from schedule as s, bus group by  s.routeid, s.scheduleid, bustype;;

		String squery = "select s.routeid, scheduletime as \"Scheduled Time\", bustype, count(s.busid) as \"Total buses\" from schedule as s, bus group by  s.routeid, s.scheduleid, bustype;";
		ResultSet rset = null;
		
		//Connect to the database
		Connection dbcon = Connections.makeConnection();

		try 
		{
			rset = Connections.sendQuery(dbcon, squery);
			System.out.println("\n\nHere are all the routes with bus types and count:  ");
			
			while (rset.next())
			{	 
                // Let's fetch the data by column numbers
				
				System.out.println("Route ID: " + rset.getInt(1));
				System.out.println("\t Time slot :"+rset.getString(2)+ 
						"\tBus Type: " + rset.getString(3) + " ("+ rset.getInt(4)+")");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		Connections.closeConnection();
	}
	
	/**
	 * @return the routeid
	 */
	public int getRouteid() {
		return routeid;
	}


	/**
	 * @param routeid the routeid to set
	 */
	public void setRouteid(int routeid) {
		this.routeid = routeid;
	}


	/**
	 * @param routeid
	 */
	public boolean isSeatAvailableOnRoute(int routeid, int scheduleid) 
	{	
		boolean availability = false;
		//check if there is any seat available on the route and schedule being requested
		if (totalRouteCapacity(routeid, scheduleid) - filledCapacityInRoute(routeid, scheduleid) > 0)
			availability = true;
		return availability;
		
	}
    
}