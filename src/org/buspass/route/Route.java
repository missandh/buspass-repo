/**
 * 
 */
package org.buspass.route;

/**
 * @author missandh
 *
 *Methods :
 *
 *getAvailabilityInRoute(int routeid) : To print available seats in this route. 
 * Difference of (sum of capacity of each bus on a route) - total active bus passes in this route
 * 
 * getTotalCapacityInRoute(): returning the total capacity of a route 
 * Sum of individual bus capacity in each route	
 * 
 * 
 *
 */
public class Route {

	/*
	public void viewRoute(String originStop) throws SQLException {
	    
	    ArrayList<Integer> routeId = new ArrayList<Integer>();
	    Connection con=Connections.makeConnection();
	    String query="select routeid from BusPass.stop where stopname='HYD 13' and stoporder=1;";
	    ResultSet result=Connections.sendQuery(con, "select routeid from BusPass.stop where stopname="+ originStop + " and stoporder=1 ;");
	    //ResultSet result=Connections.sendQuery(con,query);
	    while(result.next())
	        routeId.add(result.getInt(1));
	    if(routeId.isEmpty()) {
	        System.out.println("No Bus is available from this stop");
	        //Connections.closeConnection();
	        return;
	    }
	    System.out.println(routeId.size());
	    System.out.println(routeId);
	    for(int i=0;i<routeId.size();i++) {
	        String query1="select stoporder,stopname from BusPass.stop where routeid="+routeId.get(i) + ";";
	        result=Connections.sendQuery(con,query1);
	        
	        System.out.println("Order" + " Stop Name     for route"+ (i+1) );
	        while(result.next()) {
	            System.out.println(result.getInt(1) + "     " + result.getString(2));            
	        }        
	    }
	    */
}
