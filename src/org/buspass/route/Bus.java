package org.buspass.route;

import java.sql.Connection;
import java.sql.ResultSet;

import org.buspass.connection.Connections;

public class Bus 
{
	public String busId,routeId,busType;
	public int capacity =0, availableSeats=0;
	
	////method to see the count of vehicles of each type
	public  void viewTypeCount() {
		Connection con = Connections.makeConnection();
		String query = "select count(busid) as Count, bustype from bus group by bustype";
		try {
			ResultSet rs = Connections.sendQuery(con, query);
			while(rs.next()) {
				String i = rs.getString("bustype");
				int s = rs.getInt("Count");
				System.out.println(i + " " + s );
			}
				Connections.closeConnection();
			}catch(Exception e) {
				e.printStackTrace();
			}
	}


//@overloaded
//method to see the count of vehicles of each type based on routeId
public void viewTypeCount(int routeid) {
	//TODO Print specific route
	Connection con = Connections.makeConnection();
	String query = "select count(busid) as Count, bustype from bus where routeid= "+ routeid +" group by bustype;";
	try {
		ResultSet rs = Connections.sendQuery(con, query);
		while(rs.next()) {
			String i = rs.getString("bustype");
			int s = rs.getInt("Count");
			System.out.println(i + " " + s );
		}
		Connections.closeConnection();
		}catch(Exception e) {
			e.printStackTrace();
		}
}

//method to return capacity of bus based on busid
public  int capacityOfBus(int busid) {
	Connection con = Connections.makeConnection();
	String query = "select sum(tb.capacity) as Bus_Capacity from bus b, typeofbus tb where b.bustype = tb.bustype and busid = "+busid;
	int busCapacity = 0;
	try {
		ResultSet rs = Connections.sendQuery(con, query);
		while(rs.next()) {
			int buscapacity = rs.getInt("Bus_Capacity");
			busCapacity = buscapacity;
			//System.out.println(buscap);
		}
		Connections.closeConnection();
	}catch(Exception e) {
		e.printStackTrace();
	}
	return busCapacity;
}


//gives number of new applications in the route.
public  int newRequests() {
     Connection con = Connections.makeConnection();
     String query1 = "select count(applicationid) as New_Requests from buspassapplication where approvalStatus = 'Pending' ";
     String query2 = "select applicationid from buspassapplication where approvalStatus = 'Pending' ";
     int requests = 0;
     try {
            ResultSet rs1 = Connections.sendQuery(con, query1);
            while(rs1.next()) {
                   int newrequestsinroute = rs1.getInt("New_Requests");
                   requests = newrequestsinroute;
                   System.out.println("\nThere are "+requests+" requests awaiting for approval");
            
                   ResultSet rs2 = Connections.sendQuery(con, query2);     
                   System.out.println("\nHere is the list of pending application ids: ");
                   while (rs2.next()) 
                   {
                         int app = rs2.getInt("applicationid");
                         System.out.print(app +" ");
                   }
            }
            Connections.closeConnection();
     }
     catch(Exception e) {
    	 	System.out.println("Something went wrong!");
            e.printStackTrace();
     }
     return requests;
}

//method to get application details
public  int  getapplicationdetails(int id) {
    Connection con = Connections.makeConnection();
    String query = "select * from buspassapplication where applicationid = "+id+" ;";
    int routeid = 0;
    try {
           ResultSet rs = Connections.sendQuery(con, query);
           while(rs.next()) {
                  System.out.println("\n*****************************");
                  System.out.println("Application id : "+rs.getInt(1));
                  routeid = rs.getInt(2);
                  System.out.println("Route id : "+routeid);
                  System.out.println("Status : "+rs.getString(3));
                  System.out.println("*****************************");
                  
                  System.out.println("\nChecking availability in route "+routeid);
           }
           
    Connections.closeConnection();
    }catch(Exception e) {
           e.printStackTrace();
    }
    return routeid;
}

//metod to vlidate buspass
public  void validateBusPassApplication(int routeid,int applicationid) {
    String query = "update buspassapplication set approvalstatus = 'Approved' where applicationid = "+applicationid;
    try {
           Route route=new Route();
           int available = route.availableCapacity(routeid);
           int filled = route.filledCapacityInRoute(routeid);
           
           if(available > filled) {
        	   	  System.out.println("Good News there are seats available on this route.");
                  System.out.println("\nThe Bus Pass application is approved for applicant with application id: "+ applicationid +". Login credentials will be shared soon");
                  Connections.sendStatement(query);
           }
           else
                  System.out.println("\nService in the route cannot be provided as of now.. Come back later");
    }catch(Exception e) {
           e.printStackTrace();
    }
}

    	
public void changeType(int busid, String newType) {
	String query = "update bus set bustype = \""+newType+"\" where busid= "+busid+" ;";
    try {
          Connections.sendStatement(query);                   
          System.out.println("\nType of the bus changed to "+newType);
         
                   }
     catch(Exception e) 
    {
          e.printStackTrace();
    }
}



//metod to return type of bus based on busid
public String getBusType(int busid) {
	String type = null;
    Connection con = Connections.makeConnection();
    String query = "select bustype from bus where busid= "+busid+" ;";
    try {
           ResultSet rs= Connections.sendQuery(con,query);
           while (rs.next()) 
           {
                 type= rs.getString(1);
                 System.out.println("Current bustype is : "+type);
                 //Connections.closeConnection();
                         }
           }
     catch(Exception e) {
            e.printStackTrace();
                   }
            
     return type;
     }



public int getTypeCapacity(String type) {
	int capacity=0;
    Connection con = Connections.makeConnection();
    String query = "select capacity from typeofbus where bustype= \""+type+"\" ;";
    try {
         ResultSet rs= Connections.sendQuery(con,query);
         while (rs.next()) {
        	 capacity = rs.getInt(1);
                          //Connections.closeConnection();
                         }}
            catch(Exception e) {
                         e.printStackTrace();
                   }
            
            return capacity;
     }

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

public void getAvailableBusses() {
    Connection con = Connections.makeConnection();
    String query = "select * from bus where routeid is NULL ;";
    try {
           ResultSet rs = Connections.sendQuery(con, query);
           System.out.println("\nHere is the list of busses which are available: ");
           System.out.println("\n*******************");
           System.out.println("\nBusid -> Routeid -> bustype");
           while(rs.next()) {
                 System.out.println("\n"+rs.getInt(1)+" -> "+rs.getInt(2)+" -> "+rs.getString(3));
    
           }
           Connections.closeConnection();
           }
    catch(Exception e) {
           e.printStackTrace();
    }
    
}

}