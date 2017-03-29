package fr.polytech.model;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Planning.Model of planning of the company.
 *
 * @author Yang Lingbo
 */
public class Planning {
	
	/**
	 * The mission planifieds list.
	 */
	private List<MissionPlanified> missionPlanifiedsList;
	
	/**
	 * The sum cost.
	 */
	private double sumCost;
	
	/**
	 * Constructor.
	 */
	public Planning() {
		// TODO Auto-generated constructor stub
		missionPlanifiedsList=new ArrayList<MissionPlanified>();
	}
	
	

	/**
	 * Gets the mission planifieds list.
	 *
	 * @return the missionPlanifiedsList
	 */
	public List<MissionPlanified> getMissionPlanifiedsList() {
		return missionPlanifiedsList;
	}



	/**
	 * Sets the mission planifieds list.
	 *
	 * @param missionPlanifiedsList the missionPlanifiedsList to set
	 */
	public void setMissionPlanifiedsList(List<MissionPlanified> missionPlanifiedsList) {
		this.missionPlanifiedsList = missionPlanifiedsList;
	}



	/**
	 * Gets the sum cost.
	 *
	 * @return the sumCost
	 */
	public double getSumCost() {
		return sumCost;
	}



	/**
	 * Sets the sum cost.
	 *
	 * @param sumCost the sumCost to set
	 */
	public void setSumCost(double sumCost) {
		this.sumCost = sumCost;
	}



	/**
	 * Adds the missionPlanified in the list missionPlanifiedsList..
	 *
	 * @param missionPlanified void
	 */
	public void addMission(MissionPlanified missionPlanified){
		missionPlanifiedsList.add(missionPlanified);
	}
	
}
