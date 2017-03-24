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
import fr.polytech.model.Ressource;

public class DataLoad extends CommonCalculateMethod{
	private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
	private HashMap<Integer,Client> clientsMap = new HashMap<Integer,Client>();
    private HashMap<Integer,Ressource> ressourcesMap = new HashMap<Integer,Ressource>();
    private List<MissionPlanified> missionPlanifiedsList = new ArrayList<MissionPlanified>();
    private double sumPlanningDistance;
    
    public double getSumPlanningDistance() {
		return sumPlanningDistance;
	}

	public void setSumPlanningDistance(double sumPlanningDistance) {
		this.sumPlanningDistance = sumPlanningDistance;
	}

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
	
    public HashMap<Integer,Client> getClientsMap(){
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
                clientsMap.put(client.getId(),client);
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
		return clientsMap;
    }
    
    public HashMap<Integer,Ressource> getRessourcesMap() {
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
            	ressourcesMap.put(ressource.getId(),ressource); 
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
		return ressourcesMap;
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
            	int idClient=rs.getInt("idClient");
            	int idRessource=rs.getInt("idRessource");
            	missionPlanified.setClient(clientsMap.get(rs.getInt("idClient")));
            	missionPlanified.setRessource(ressourcesMap.get(rs.getInt("idRessource")));
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
    

	//load the infomation of the ressources
	public void loadAllRessourcesDailyInfo(HashMap<Integer, Ressource> ressourcesMap){
		sumPlanningDistance=0.0;
		Iterator<Integer> iterator1=ressourcesMap.keySet().iterator();
		while(iterator1.hasNext()){
			int key1=iterator1.next();
			Ressource r=ressourcesMap.get(key1);
			Iterator<MissionPlanified> iterator2=missionPlanifiedsList.iterator();
			while(iterator2.hasNext()){
				MissionPlanified missionPlanified=iterator2.next();
				if(r.getId()==missionPlanified.getRessource().getId()){
					r.addMissionPlanified(missionPlanified);
					//add missionPlanified in the list of missionReplan(prepare for the methods replanifiable)
					if(missionPlanified.isReplanifiable()){
						r.addMissionReplan(missionPlanified);
					}
				}
			}
			sortPlan(r.getPlanningDailyPerson());
			Iterator<Date> iterator3=r.getMisssionListDaily().keySet().iterator();
			while(iterator3.hasNext()){
				Date key2=iterator3.next();
				r.setTravelDistanceDaily(key2, calculateDistanceDaily(r.getMisssionListDaily().get(key2)));
				sumPlanningDistance+=r.getTravelDistanceDaily().get(key2);
			}
		}
	}
    
	// get the sum of the cost of all ressources
    public void setSumPlanningDistance(HashMap<Integer,Ressource> ressourcesMap){
    	Iterator<Integer> iterator1=ressourcesMap.keySet().iterator();
    	double sum=0.0;
		while(iterator1.hasNext()){
			int key1=iterator1.next();
			Iterator<Date> iterator2=ressourcesMap.get(key1).getMisssionListDaily().keySet().iterator();
			while(iterator2.hasNext()){
				Date key2=iterator2.next();
				sum+=ressourcesMap.get(key1).getTravelDistanceDaily().get(key2);
			}
		}
		sumPlanningDistance=sum;
    }
    
    		
}
