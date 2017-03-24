package fr.polytech.model;

import java.util.ArrayList;
import java.util.List;

public class Planning {
	private List<MissionPlanified> missionPlanifiedsList;
	
	public Planning() {
		// TODO Auto-generated constructor stub
		missionPlanifiedsList=new ArrayList<MissionPlanified>();
	}
	
	public List<MissionPlanified> getMissionPlanifiedsList() {
		return missionPlanifiedsList;
	}

	public void setMissionPlanifiedsList(List<MissionPlanified> missionPlanifiedsList) {
		this.missionPlanifiedsList = missionPlanifiedsList;
	}
	
	public void addMission(MissionPlanified missionPlanified){
		missionPlanifiedsList.add(missionPlanified);
	}
	
}
