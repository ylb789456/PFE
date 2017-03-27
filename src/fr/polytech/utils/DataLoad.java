package fr.polytech.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import fr.polytech.model.Client;
import fr.polytech.model.MissionPlanified;
import fr.polytech.model.Planning;
import fr.polytech.model.Ressource;

public class DataLoad extends CommonCalculateMethod{
	private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
	private List<Client> clientsList = new ArrayList<Client>();
    private List<Ressource> ressourcesList = new ArrayList<Ressource>();
    private List<MissionPlanified> missionPlanifiedsList = new ArrayList<MissionPlanified>();
    
	public Connection getConnection(){
        String url="jdbc:mysql://localhost:3306/pfe";
        String userName="root";
        String password="123456";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("Cannot find driver！");
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
    
    public List<Ressource> getRessourcesList() {
        Ressource ressource;
        String sql="select * from ressource";
        try {
            conn=getConnection();
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery(sql);
            while(rs.next()){
            	ressource=new Ressource();
            	ressource.setId(rs.getInt("id"));
            	ressource.setName(rs.getString("Name"));
            	ressource.setType(rs.getString("Type"));
            	ressource.setTimeStartWork(rs.getInt("TimeStartWork"));
            	ressource.setTimeEndWork(rs.getInt("TimeEndWork"));
            	ressource.setTimeMaxWork(rs.getInt("TimeMaxWork"));
            	ressourcesList.add(ressource); 
                System.out.println(ressource.toString());
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
        System.out.println("Load ressources successful");
		return ressourcesList;
	}
    
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
            	missionPlanified.setReplanifiable(rs.getBoolean("Replanificable"));
            	missionPlanified.setTimeStartWork(rs.getInt("TimeStartWork"));
            	missionPlanified.setTimeEndWork(rs.getInt("TimeEndWork"));
            	missionPlanified.setClient(findClientById(rs.getInt("idClient")));
            	missionPlanified.setRessource(findRessourceById(rs.getInt("idRessource")));
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
                System.out.println("Load ressources successful");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
		return missionPlanifiedsList;
	}
    
    //find Client by id
    public Client findClientById(int idClient){
    	Iterator<Client> iterator=clientsList.iterator();
    	while(iterator.hasNext()){
    		Client client=iterator.next();
    		if(client.getId()==idClient)	
    			return client;
    	}
    	return null;
    }
    
    //find Ressource by id
    public Ressource findRessourceById(int idRessource){
    	Iterator<Ressource> iterator=ressourcesList.iterator();
    	while(iterator.hasNext()){
    		Ressource ressource=iterator.next();
    		if(ressource.getId()==idRessource)	
    			return ressource;
    	}
    	return null;
    }

	//load the infomation of the ressources and load the sumCost of the Plan
	public void loadInfo(List<Ressource> ressourcesList,Planning planning){
		double sumPlanningDistance=0.0;
		Iterator<Ressource> iterator1=ressourcesList.iterator();
		while(iterator1.hasNext()){
			Ressource r=iterator1.next();
			Iterator<MissionPlanified> iterator2=missionPlanifiedsList.iterator();
			while(iterator2.hasNext()){
				MissionPlanified missionPlanified=iterator2.next();
				if(r.getId()==missionPlanified.getRessource().getId()){
					r.addMissionPlanified(missionPlanified);
				}
			}
			sortPlan(r.getPlanningDailyPerson());
			Iterator<Date> iterator3=r.getPlanningDailyPerson().keySet().iterator();
			while(iterator3.hasNext()){
				Date key2=iterator3.next();
				r.setTravelDistanceDaily(key2, calculateDistanceDaily(r.getPlanningDailyPerson().get(key2)));
			}
			r.setCost(calculateRessourceCost(r.getTravelDistanceDaily()));
			sumPlanningDistance+=r.getCost();
		}
		planning.setSumCost(sumPlanningDistance);
	}



    
    		
}
