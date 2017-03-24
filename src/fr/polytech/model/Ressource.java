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

public class Ressource implements Serializable {
	private int id;
	private String name;
	private String type;
	private int timeStartWork;
	private int timeEndWork;
	private int timeMaxWork;
	private HashMap<Date, Double> travelDistanceDaily;
	private HashMap<Date, ArrayList<MissionPlanified>> planningDailyPerson;
	private List<MissionPlanified> MissionReplanList;
	private double cost;
	
	public HashMap<Date, ArrayList<MissionPlanified>> getMisssionListDaily() {
		return planningDailyPerson;
	}
	public void setMisssionListDaily(HashMap<Date, ArrayList<MissionPlanified>> planningDailyPerson) {
		this.planningDailyPerson = planningDailyPerson;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getTimeStartWork() {
		return timeStartWork;
	}
	public void setTimeStartWork(int timeStartWork) {
		this.timeStartWork = timeStartWork;
	}
	public int getTimeEndWork() {
		return timeEndWork;
	}
	public void setTimeEndWork(int timeEndWork) {
		this.timeEndWork = timeEndWork;
	}
	public int getTimeMaxWork() {
		return timeMaxWork;
	}
	public void setTimeMaxWork(int timeMaxWork) {
		this.timeMaxWork = timeMaxWork;
	}
	
	public HashMap<Date, Double> getTravelDistanceDaily() {
		return travelDistanceDaily;
	}
	public void setTravelDistanceDaily(HashMap<Date, Double> travelDistanceDaily) {
		this.travelDistanceDaily = travelDistanceDaily;
	}
	public HashMap<Date, ArrayList<MissionPlanified>> getPlanningDailyPerson() {
		return planningDailyPerson;
	}
	public void setPlanningDailyPerson(HashMap<Date, ArrayList<MissionPlanified>> planningDailyPerson) {
		this.planningDailyPerson = planningDailyPerson;
	}
	

	
	public List<MissionPlanified> getMissionReplanList() {
		return MissionReplanList;
	}
	public void setMissionReplanList(List<MissionPlanified> missionReplanList) {
		MissionReplanList = missionReplanList;
	}
	
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public void addMissionReplan(MissionPlanified missionPlanified){
		if(MissionReplanList==null){
			MissionReplanList=new ArrayList<MissionPlanified>();
		}
		else{
			MissionReplanList.add(missionPlanified);
		}
	}
	
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
	}
	
	
	
	public void setTravelDistanceDaily(Date date,double distance){
		if(travelDistanceDaily==null)
			travelDistanceDaily=new HashMap<Date,Double>();
		travelDistanceDaily.put(date, distance);
	}	
	

	@Override
	public String toString() {
		return "Ressource [id=" + id + ", name=" + name + ", type=" + type + ", timeStartWork=" + timeStartWork
				+ ", timeEndWork=" + timeEndWork + ", timeMaxWork=" + timeMaxWork + "]";
	}


	
}
