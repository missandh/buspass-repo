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
		 * @return the table
		 */
		public String getTable() {
			return table;
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
	
    public static void main(String args[]) 
    {
    	Route test = new Route();
    	ArrayList<String> stopnames = new ArrayList();
    	stopnames.add("HYD13");
    	stopnames.add("Manikonda");
    	stopnames.add("Gowlidoddi");
    	stopnames.add("Gopanpally");
    	stopnames.add("Nallagandla");
    	test.addRoute(1500, stopnames);
    	test.viewAllRoutesWithStops();
    	test.viewTypeOfVehiclesPerRoute();
    }



	/**
	 * @param routeid
	 */
	public boolean isSeatAvailableOnRoute(int routeid, int scheduleid) {
		
		boolean availability = false;
		return availability;
		
	}
    
}
