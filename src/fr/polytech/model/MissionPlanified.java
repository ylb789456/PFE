package fr.polytech.model;

import java.util.Date;
import java.io.Serializable;
import java.text.SimpleDateFormat;

// TODO: Auto-generated Javadoc
/**
 * The Class MissionPlanified.Model of mission planified of the company.
 *
 * @author Yang Lingbo
 */
public class MissionPlanified implements Comparable,Serializable  {
	
	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 3415544874536541L;
	
	/**
	 * The id.
	 */
	private int id;
	
	/**
	 * The date.
	 */
	private Date date;
	
	/**
	 * The client.
	 */
	private Client client;
	
	/**
	 * The time start work.
	 */
	private int timeStartWork;
	
	/**
	 * The time end work.
	 */
	private int timeEndWork;
	
	/**
	 * The resource.
	 */
	private Resource resource;
	
	/**
	 * The replanifiable.
	 */
	private boolean replanifiable;
	
	/**
	 * The time replan earliest.
	 */
	private Date timeReplanEarliest;
	
	/**
	 * The time replan latest.
	 */
	private Date timeReplanLatest;
	
	/**
	 * Constructor.
	 */
	public MissionPlanified() {
		// TODO Auto-generated constructor stub
		replanifiable=false;
	}
	
	/**
	 * Constructor of MissionPlanified which is un-replanifiable.
	 *
	 * @param id the id
	 * @param date the date
	 * @param client the client
	 * @param timeStartWork the time start work
	 * @param timeEndWork the time end work
	 * @param resource the resource
	 */
	public MissionPlanified(int id,Date date,Client client,int timeStartWork,int timeEndWork,Resource resource){
		this.id=id;
		this.date=date;
		this.client=client;
		this.timeStartWork=timeStartWork;
		this.timeEndWork=timeEndWork;
		this.resource=resource;
	}
	
	/**
	 * Constructor of MissionPlanified which is replanifiable.
	 *
	 * @param id the id
	 * @param date the date
	 * @param client the client
	 * @param timeStartWork the time start work
	 * @param timeEndWork the time end work
	 * @param resource the resource
	 * @param replanifiable the replanifiable
	 * @param timeReplanEarliest the time replan earliest
	 * @param timeReplanLatest the time replan latest
	 */
	@SuppressWarnings("deprecation")
	public MissionPlanified(int id,Date date,Client client,int timeStartWork,int timeEndWork,Resource resource,
			boolean replanifiable,Date timeReplanEarliest,Date timeReplanLatest){
		this.id=id;
		this.date=date;
		this.client=client;
		this.timeStartWork=timeStartWork;
		this.timeEndWork=timeEndWork;
		this.resource=resource;
		this.replanifiable=replanifiable;
		timeReplanEarliest.setHours(0);
		timeReplanEarliest.setMinutes(0);
		timeReplanLatest.setHours(23);
		timeReplanLatest.setMinutes(59);
		this.timeReplanEarliest=timeReplanEarliest;
		this.timeReplanLatest=timeReplanLatest;
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
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Gets the client.
	 *
	 * @return the client
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * Sets the client.
	 *
	 * @param client the client to set
	 */
	public void setClient(Client client) {
		this.client = client;
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
	 * Gets the resource.
	 *
	 * @return the resource
	 */
	public Resource getresource() {
		return resource;
	}

	/**
	 * Sets the resource.
	 *
	 * @param resource the resource to set
	 */
	public void setresource(Resource resource) {
		this.resource = resource;
	}

	/**
	 * Checks if is replanifiable.
	 *
	 * @return the replanifiable
	 */
	public boolean isReplanifiable() {
		return replanifiable;
	}

	/**
	 * Sets the replanifiable.
	 *
	 * @param replanifiable the replanifiable to set
	 */
	public void setReplanifiable(boolean replanifiable) {
		this.replanifiable = replanifiable;
	}

	/**
	 * Gets the time replan earliest.
	 *
	 * @return the timeReplanEarliest
	 */
	public Date getTimeReplanEarliest() {
		return timeReplanEarliest;
	}

	/**
	 * Sets the time replan earliest.
	 *
	 * @param timeReplanEarliest the timeReplanEarliest to set
	 */
	public void setTimeReplanEarliest(Date timeReplanEarliest) {
		this.timeReplanEarliest = timeReplanEarliest;
	}

	/**
	 * Gets the time replan latest.
	 *
	 * @return the timeReplanLatest
	 */
	public Date getTimeReplanLatest() {
		return timeReplanLatest;
	}

	/**
	 * Sets the time replan latest.
	 *
	 * @param timeReplanLatest the timeReplanLatest to set
	 */
	public void setTimeReplanLatest(Date timeReplanLatest) {
		this.timeReplanLatest = timeReplanLatest;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		MissionPlanified m=(MissionPlanified)o;
		return (this.getTimeStartWork()<m.getTimeStartWork() ? -1 : (this.getTimeStartWork()==m.getTimeStartWork() ? 0 : 1));
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(replanifiable){
			return "MissionPlanified [id=" + id + ", date=" + sdf1.format(date) + ", client=" + client.getId() + ", resource=" 
					+ resource.getId() + ", timeStartWork=" + timeStartWork + ", timeEndWork=" + timeEndWork
					+ ", replanificable=" + replanifiable + ", timeReplanEarliest=" + sdf2.format(timeReplanEarliest) + ", timeReplanLatest="
							+ sdf2.format(timeReplanLatest) + "]";
		}
		else{
			return "MissionPlanified [id=" + id + ", date=" + sdf1.format(date) + ", client=" + client.getId() + ", resource=" 
					+ resource.getId() + ", timeStartWork=" + timeStartWork + ", timeEndWork=" + timeEndWork
					+ ", replanificable=" + replanifiable + "]";
		}
		
	}


		
	
	
}
