package fr.polytech.model;

import java.io.Serializable;
import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Resource.Model of resource of the company,most information of the planning are modeled in this class.
 *
 * @author Yang Lingbo
 */
public class Resource implements Serializable {
	
	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -2520503242313017112L;
	
	/**
	 * The id.
	 */
	private int id;
	
	/**
	 * The name.
	 */
	private String name;
	
	/**
	 * The type.
	 */
	private String type;
	
	/**
	 * The time start work.Default 480 (8H).
	 */
	private int timeStartWork;
	
	/**
	 * The time end work.Default 1080 (18H).
	 */
	private int timeEndWork;
	
	/**
	 * The time max work include the overtime.
	 */
	private int timeMaxWork;
	
	/**
	 * A HashMap to store the distance daily.
	 */
	private HashMap<Date, Double> travelDistanceDaily;
	
	/**
	 * A HashMap to store the MissionPlanified of one day.
	 */
	private HashMap<Date, ArrayList<MissionPlanified>> planningDailyPerson;
	
	/**
	 * A List to store all mission which can be replanned of resource.
	 */
	private List<MissionPlanified> MissionReplanList;
	
	/**
	 * Total distance cost of all working days of resource.
	 */
	private double cost;
	
	


	/**
	 * Constructor.
	 */
	public Resource() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the time start work.
	 *
	 * @return the timeStartWork
	 */
	public int getTimeStartWork() {
		return timeStartWork;
	}

	/**
	 * Sets the time start work.
	 *
	 * @param timeStartWork the timeStartWork to set
	 */
	public void setTimeStartWork(int timeStartWork) {
		this.timeStartWork = timeStartWork;
	}

	/**
	 * Gets the time end work.
	 *
	 * @return the timeEndWork
	 */
	public int getTimeEndWork() {
		return timeEndWork;
	}

	/**
	 * Sets the time end work.
	 *
	 * @param timeEndWork the timeEndWork to set
	 */
	public void setTimeEndWork(int timeEndWork) {
		this.timeEndWork = timeEndWork;
	}

	/**
	 * Gets the time max work.
	 *
	 * @return the timeMaxWork
	 */
	public int getTimeMaxWork() {
		return timeMaxWork;
	}

	/**
	 * Sets the time max work.
	 *
	 * @param timeMaxWork the timeMaxWork to set
	 */
	public void setTimeMaxWork(int timeMaxWork) {
		this.timeMaxWork = timeMaxWork;
	}

	/**
	 * Gets the travel distance daily.
	 *
	 * @return the travelDistanceDaily
	 */
	public HashMap<Date, Double> getTravelDistanceDaily() {
		return travelDistanceDaily;
	}

	/**
	 * Sets the travel distance daily.
	 *
	 * @param travelDistanceDaily the travelDistanceDaily to set
	 */
	public void setTravelDistanceDaily(HashMap<Date, Double> travelDistanceDaily) {
		this.travelDistanceDaily = travelDistanceDaily;
	}

	/**
	 * Gets the planning daily person.
	 *
	 * @return the planningDailyPerson
	 */
	public HashMap<Date, ArrayList<MissionPlanified>> getPlanningDailyPerson() {
		return planningDailyPerson;
	}

	/**
	 * Sets the planning daily person.
	 *
	 * @param planningDailyPerson the planningDailyPerson to set
	 */
	public void setPlanningDailyPerson(HashMap<Date, ArrayList<MissionPlanified>> planningDailyPerson) {
		this.planningDailyPerson = planningDailyPerson;
	}

	/**
	 * Gets the mission replan list.
	 *
	 * @return the missionReplanList
	 */
	public List<MissionPlanified> getMissionReplanList() {
		return MissionReplanList;
	}

	/**
	 * Sets the mission replan list.
	 *
	 * @param missionReplanList the missionReplanList to set
	 */
	public void setMissionReplanList(List<MissionPlanified> missionReplanList) {
		MissionReplanList = missionReplanList;
	}

	/**
	 * Gets the cost.
	 *
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * Sets the cost.
	 *
	 * @param cost the cost to set
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * Add missionPlanified can be replanned in the List MissionReplanList.
	 *
	 * @param missionPlanified void
	 */
	public void addMissionReplan(MissionPlanified missionPlanified){
		if(MissionReplanList==null){
			MissionReplanList=new ArrayList<MissionPlanified>();
		}
		else{
			MissionReplanList.add(missionPlanified);
		}
	}
	
	
	/**
	 * Add mission planified in the planningDaily of this resource,and if it is replanifiable,add it in the MissionReplanList.
	 *
	 * @param missionPlanified void
	 */
	public void addMissionPlanified(MissionPlanified missionPlanified){
		if(planningDailyPerson==null){
			planningDailyPerson=new HashMap<Date,ArrayList<MissionPlanified>>();
		}
		
		if(planningDailyPerson.get(missionPlanified.getDate())==null){
			ArrayList<MissionPlanified> missionList=new ArrayList<MissionPlanified>();
			missionList.add(missionPlanified);
			planningDailyPerson.put(missionPlanified.getDate(),missionList);
		}
		else{
			planningDailyPerson.get(missionPlanified.getDate()).add(missionPlanified);
		}
		if(missionPlanified.isReplanifiable()){
			addMissionReplan(missionPlanified);
		}
	}
	
	
	
	/**
	 * Set the distance of one day by the Date and distance in the HashMap travelDistanceDaily.
	 *
	 * @param date the date
	 * @param distance void
	 */
	public void setTravelDistanceDaily(Date date,double distance){
		if(travelDistanceDaily==null)
			travelDistanceDaily=new HashMap<Date,Double>();
		travelDistanceDaily.put(date, distance);
	}	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Resource [id=" + id + ", name=" + name + ", type=" + type + ", timeStartWork=" + timeStartWork
				+ ", timeEndWork=" + timeEndWork + ", timeMaxWork=" + timeMaxWork + "]";
	}


	
}
