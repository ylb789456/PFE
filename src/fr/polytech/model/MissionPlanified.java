package fr.polytech.model;

import java.util.Date;
import java.io.Serializable;
import java.text.SimpleDateFormat;

public class MissionPlanified implements Comparable,Serializable  {
	private int id;
	private Date date;
	private Client client;
	private int timeStartWork;
	private int timeEndWork;
	private Ressource ressource;
	private boolean replanifiable;
	private Date timeReplanEarliest;
	private Date timeReplanLatest;
	
	public MissionPlanified() {
		// TODO Auto-generated constructor stub
		replanifiable=false;
	}
	
	public MissionPlanified(int id,Date date,Client client,int timeStartWork,int timeEndWork,Ressource ressource){
		this.id=id;
		this.date=date;
		this.client=client;
		this.timeStartWork=timeStartWork;
		this.timeEndWork=timeEndWork;
		this.ressource=ressource;
	}
	
	@SuppressWarnings("deprecation")
	public MissionPlanified(int id,Date date,Client client,int timeStartWork,int timeEndWork,Ressource ressource,
			boolean replanifiable,Date timeReplanEarliest,Date timeReplanLatest){
		this.id=id;
		this.date=date;
		this.client=client;
		this.timeStartWork=timeStartWork;
		this.timeEndWork=timeEndWork;
		this.ressource=ressource;
		this.replanifiable=replanifiable;
		timeReplanEarliest.setHours(0);
		timeReplanEarliest.setMinutes(0);
		timeReplanLatest.setHours(23);
		timeReplanLatest.setMinutes(59);
		this.timeReplanEarliest=timeReplanEarliest;
		this.timeReplanLatest=timeReplanLatest;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
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

	public Ressource getRessource() {
		return ressource;
	}

	public void setRessource(Ressource ressource) {
		this.ressource = ressource;
	}

	public boolean isReplanifiable() {
		return replanifiable;
	}

	public void setReplanifiable(boolean replanificable) {
		this.replanifiable = replanificable;
	}

	public Date getTimeReplanEarliest() {
		return timeReplanEarliest;
	}

	public void setTimeReplanEarliest(Date timeReplanEarliest) {
		this.timeReplanEarliest = timeReplanEarliest;
	}

	public Date getTimeReplanLatest() {
		return timeReplanLatest;
	}

	public void setTimeReplanLatest(Date timeReplanLatest) {
		this.timeReplanLatest = timeReplanLatest;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		MissionPlanified m=(MissionPlanified)o;
		return (this.getTimeStartWork()<m.getTimeStartWork() ? -1 : (this.getTimeStartWork()==m.getTimeStartWork() ? 0 : 1));
		
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(replanifiable){
			return "MissionPlanified [id=" + id + ", date=" + sdf1.format(date) + ", client=" + client.getId() + ", ressource=" 
					+ ressource.getId() + ", timeStartWork=" + timeStartWork + ", timeEndWork=" + timeEndWork
					+ ", replanificable=" + replanifiable + ", timeReplanEarliest=" + sdf2.format(timeReplanEarliest) + ", timeReplanLatest="
							+ sdf2.format(timeReplanLatest) + "]";
		}
		else{
			return "MissionPlanified [id=" + id + ", date=" + sdf1.format(date) + ", client=" + client.getId() + ", ressource=" 
					+ ressource.getId() + ", timeStartWork=" + timeStartWork + ", timeEndWork=" + timeEndWork
					+ ", replanificable=" + replanifiable + "]";
		}
		
	}


		
	
	
}
