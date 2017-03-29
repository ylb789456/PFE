package fr.polytech.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import fr.polytech.model.Client;
import fr.polytech.model.MissionPlanified;
import fr.polytech.model.Planning;
import fr.polytech.model.Resource;

// TODO: Auto-generated Javadoc
/**
 * The Class DataLoad.Loading Data and initial the data.
 *
 * @author Yang Lingbo 
 */
public class DataLoad extends CommonCalculateMethod{
	
	/**
	 * Connection of database.
	 *
	 */
	private Connection conn;
    
    /**
     * Sql statement of query.
     *
     */
    private PreparedStatement ps;
    
    /**
     * ResultSet of query from database.
     *
     */
    private ResultSet rs;
	
	/**
	 * List of Client to store data from database.
	 *
	 */
	private List<Client> clientsList = new ArrayList<Client>();
    
    /**
     * The list of Resource to store data from database.
     *
     */
    private List<Resource> resourcesList = new ArrayList<Resource>();
    
    /**
     * The list of MissionPlanified to store data from database.
     *
     */
    private List<MissionPlanified> missionPlanifiedsList = new ArrayList<MissionPlanified>();
    
	/**
	 * Gets the connection to the database.
	 *
	 * @return Connection
	 */
	public Connection getConnection(){
        String url="jdbc:mysql://localhost:3306/pfe";
        String userName="root";
        String password="123456";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("Cannot find driver!");
            e.printStackTrace();
        }
        try {
            conn=DriverManager.getConnection(url, userName, password);
            if(conn!=null){
                System.out.println("connection successful");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
                System.out.println( "connection fail");
            e.printStackTrace();
        }
        return conn;
    }
	
    /**
     * Get the Client list from the database.
     *
     * @return the clients list
     */
    public List<Client> getClientsList(){
        Client client;
        String sql="select * from client";
        try {
            conn=getConnection();
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery(sql);
            while(rs.next()){
                client=new Client();
                client.setId(rs.getInt("id"));
                client.setName(rs.getString("Name"));
                client.setxCoordinate(rs.getFloat("xCoordinate"));
                client.setyCoordinate(rs.getFloat("yCoordinate"));
                clientsList.add(client);
                System.out.println(client.toString());
            }           
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                rs.close();
                ps.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Load clients successful");
		return clientsList;
    }
    
    /**
     * Get the Resource list from the database.
     *
     * @return the resources list
     */
    public List<Resource> getResourcesList() {
        Resource resource;
        String sql="select * from resource";
        try {
            conn=getConnection();
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery(sql);
            while(rs.next()){
            	resource=new Resource();
            	resource.setId(rs.getInt("id"));
            	resource.setName(rs.getString("Name"));
            	resource.setType(rs.getString("Type"));
            	resource.setTimeStartWork(rs.getInt("TimeStartWork"));
            	resource.setTimeEndWork(rs.getInt("TimeEndWork"));
            	resource.setTimeMaxWork(rs.getInt("TimeMaxWork"));
            	resourcesList.add(resource); 
                System.out.println(resource.toString());
            }           
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                rs.close();
                ps.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Load resources successful");
		return resourcesList;
	}
    
    /**
     * Get the MissionPlanified list from the database.
     *
     * @return the missions planified list
     */
    public List<MissionPlanified> getMissionsPlanifiedList() {
    	MissionPlanified missionPlanified;
        String sql="select * from missionplanified";
        try {
            conn=getConnection();
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery(sql);
            while(rs.next()){
            	missionPlanified=new MissionPlanified();
            	missionPlanified.setId(rs.getInt("id"));
            	missionPlanified.setDate(rs.getDate("Date"));
            	missionPlanified.setReplanifiable(rs.getBoolean("Replanifiable"));
            	missionPlanified.setTimeStartWork(rs.getInt("TimeStartWork"));
            	missionPlanified.setTimeEndWork(rs.getInt("TimeEndWork"));
            	missionPlanified.setClient(findClientById(rs.getInt("idClient")));
            	missionPlanified.setresource(findResourceById(rs.getInt("idresource")));
            	if(missionPlanified.isReplanifiable()){
            		missionPlanified.setTimeReplanEarliest(rs.getTimestamp("timeReplanEarliest"));
            		missionPlanified.setTimeReplanLatest(rs.getTimestamp("timeReplanLatest"));
            	}
            	
            	missionPlanifiedsList.add(missionPlanified);
                System.out.println(missionPlanified.toString());
            }           
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                rs.close();
                ps.close();
                conn.close();
                System.out.println("Load resources successful");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
		return missionPlanifiedsList;
	}
    
    /**
     * Find client by id.
     *
     * @param idClient the id client
     * @return Client
     */
    public Client findClientById(int idClient){
    	Iterator<Client> iterator=clientsList.iterator();
    	while(iterator.hasNext()){
    		Client client=iterator.next();
    		if(client.getId()==idClient)	
    			return client;
    	}
    	return null;
    }
    
    
    /**
     * Find resource by id.
     *
     * @param idresource the idresource
     * @return Resource
     */
    public Resource findResourceById(int idresource){
    	Iterator<Resource> iterator=resourcesList.iterator();
    	while(iterator.hasNext()){
    		Resource resource=iterator.next();
    		if(resource.getId()==idresource)	
    			return resource;
    	}
    	return null;
    }

	
	/**
	 * Load the information(cost,MissionPlanified List/HashMap) of the resources and load the sumCost of the Plan.
	 *
	 * @param resourcesList the resources list
	 * @param planning the planning
	 */
	public void loadInfo(List<Resource> resourcesList,Planning planning){
		double sumPlanningDistance=0.0;
		Iterator<Resource> iterator1=resourcesList.iterator();
		while(iterator1.hasNext()){
			Resource r=iterator1.next();
			Iterator<MissionPlanified> iterator2=missionPlanifiedsList.iterator();
			while(iterator2.hasNext()){
				MissionPlanified missionPlanified=iterator2.next();
				if(r.getId()==missionPlanified.getresource().getId()){
					r.addMissionPlanified(missionPlanified);
				}
			}
			if(r.getPlanningDailyPerson()!=null){
				sortPlan(r.getPlanningDailyPerson());
				Iterator<Date> iterator3=r.getPlanningDailyPerson().keySet().iterator();
				while(iterator3.hasNext()){
					Date key2=iterator3.next();
					r.setTravelDistanceDaily(key2, calculateDistanceDaily(r.getPlanningDailyPerson().get(key2)));
				}
				r.setCost(calculateResourceCost(r.getTravelDistanceDaily()));
				sumPlanningDistance+=r.getCost();
			}
		}
		planning.setSumCost(sumPlanningDistance);
	}



    
    		
}
