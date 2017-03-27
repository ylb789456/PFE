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
import fr.polytech.model.Ressource;

public class MethodSimple extends CommonCalculateMethod {
	
	
	public void Calculate(Planning planning,Mission mission,List<Ressource> ressourcesList){
		List<MissionPlanified> missionPlanifiedsList=planning.getMissionPlanifiedsList();
		Iterator<Ressource> iterator1=ressourcesList.iterator();
		List<Ressource> ressourcesObjetList=new ArrayList<Ressource>();
		
		// Get the Ressource equal to the type of mission.
		while(iterator1.hasNext()){
			Ressource r=iterator1.next();
			if(mission.getTypeRessource().equals(r.getType())){
				ressourcesObjetList.add(r);
			}
		}
			
		
//	for(int i=0;i<ressourcesObjetList.size();i++){
//		System.out.println(ressourcesObjetList.get(i).toString());
//		ressourcesObjetList.get(i).sortPlan();
//		HashMap<Date, ArrayList<MissionPlanified>> missionPlanifiedsMap=ressourcesObjetList.get(i).getMisssionListDaily();
//		Iterator<Date> iterator=missionPlanifiedsMap.keySet().iterator();
//		while(iterator.hasNext()){
//			Date key=iterator.next();
//			for(int j=0;j<missionPlanifiedsMap.get(key).size();j++)
//				System.out.println(missionPlanifiedsMap.get(key).get(j).toString());
//		}
//	}
		HashMap<Double, MissionPlanified> solutionMap=new HashMap<Double,MissionPlanified>();
		//foreach ressourceslist
		for(int i=0;i<ressourcesObjetList.size();i++){
			Ressource ressource=ressourcesObjetList.get(i);
			calculateRessourceSolutionsList(mission,ressource,ressource.getPlanningDailyPerson(),solutionMap,missionPlanifiedsList.size()+1);
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
		Ressource ressourceAddMission=bestSolution.getRessource();
		ressourceAddMission.addMissionPlanified(bestSolution);
		sortPlanDaily(ressourceAddMission.getPlanningDailyPerson().get(bestSolution.getDate()));
		ressourceAddMission.setTravelDistanceDaily(bestSolution.getDate(),
				calculateDistanceDaily(ressourceAddMission.getPlanningDailyPerson().get(bestSolution.getDate())));
		ressourceAddMission.setCost(calculateRessourceCost(ressourceAddMission.getTravelDistanceDaily()));
		planning.setSumCost(calculateSumPlanningDistance(ressourcesList));
		System.out.println("The best Solution:");
		System.out.println(bestSolution);
	}
	

}
