package fr.polytech.utils;

import java.util.Date;
import java.awt.RenderingHints.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import fr.polytech.model.Client;
import fr.polytech.model.Mission;
import fr.polytech.model.MissionPlanified;
import fr.polytech.model.Planning;
import fr.polytech.model.Resource;

// TODO: Auto-generated Javadoc
/**
 * The Class MethodSimple.Calculate for the problem unreplanifiable.
 *
 * @author Yang Lingbo
 */
public class MethodSimple extends CommonCalculateMethod {
	
	
	/**
	 * Insert the mission in the planning(No changes of the missions planified),calculate the solutions of all 
	 * resources matched the type, and get the best solution,and replace the planning.
	 *
	 * @param planning the planning
	 * @param mission the mission
	 * @param resourcesList the resources list
	 */
	public void Calculate(Planning planning,Mission mission,List<Resource> resourcesList){
		List<MissionPlanified> missionPlanifiedsList=planning.getMissionPlanifiedsList();
		Iterator<Resource> iterator1=resourcesList.iterator();
		List<Resource> resourcesObjetList=new ArrayList<Resource>();
		
		// Get the resource equal to the type of mission.
		while(iterator1.hasNext()){
			Resource r=iterator1.next();
			if(mission.getTypeResource().equals(r.getType())){
				resourcesObjetList.add(r);
			}
		}
			
		HashMap<Double, MissionPlanified> solutionMap=new HashMap<Double,MissionPlanified>();
		//foreach resourceslist
		for(int i=0;i<resourcesObjetList.size();i++){
			Resource resource=resourcesObjetList.get(i);
			if(resource.getPlanningDailyPerson()!=null)
				calculateResourceSolutionsList(mission,resource,resource.getPlanningDailyPerson(),solutionMap,missionPlanifiedsList.size()+1);
		}
		System.out.println("All solutions:");
		Iterator<Double> iteratorE=solutionMap.keySet().iterator();
		while(iteratorE.hasNext()){
			Double key=iteratorE.next();
			System.out.print("ExtraTime:"+key+" ");
			System.out.println(solutionMap.get(key).toString());
		}
		MissionPlanified bestSolution=solutionMap.get(minTravelTimeofSolutions(solutionMap));
		if(bestSolution==null){
			System.out.println("No solution can be find.");
			System.exit(0);
		}
		planning.addMission(bestSolution);
		Resource resourceAddMission=bestSolution.getresource();
		resourceAddMission.addMissionPlanified(bestSolution);
		sortPlanDaily(resourceAddMission.getPlanningDailyPerson().get(bestSolution.getDate()));
		resourceAddMission.setTravelDistanceDaily(bestSolution.getDate(),
				calculateDistanceDaily(resourceAddMission.getPlanningDailyPerson().get(bestSolution.getDate())));
		resourceAddMission.setCost(calculateResourceCost(resourceAddMission.getTravelDistanceDaily()));
		planning.setSumCost(calculateSumPlanningDistance(resourcesList));
		System.out.println("The best Solution:");
		System.out.println(bestSolution);
	}
	

}
