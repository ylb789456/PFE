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
	private List<MissionPlanified> missionPlanifiedsList;
	private static final Client base=new Client(0,"Base",0,0);
	
	public void Calculate(Planning planning,Mission mission,HashMap<Integer, Ressource> ressourcesMap){
		missionPlanifiedsList=planning.getMissionPlanifiedsList();
		Iterator<Integer> iterator1=ressourcesMap.keySet().iterator();
		List<Ressource> ressourcesObjetList=new ArrayList<Ressource>();
		
		// Get the Ressource equal to the type of mission.
		while(iterator1.hasNext()){
			int key=iterator1.next();
			Ressource r1=ressourcesMap.get(key);
			if(mission.getTypeRessource().equals(r1.getType())){
				ressourcesObjetList.add(r1);
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
		MissionPlanified bestSolution=getBestSolution(ressourcesObjetList,mission);
		if(bestSolution==null){
			System.out.println("No solution can be find.");
			System.exit(0);
		}
		planning.addMission(bestSolution);
		Ressource ressourceAddMission=bestSolution.getRessource();
		ressourcesMap.get(ressourceAddMission.getId()).addMissionPlanified(bestSolution);
		sortPlanDaily(ressourcesMap.get(ressourceAddMission.getId()).getPlanningDailyPerson().get(bestSolution.getDate()));
		ressourcesMap.get(ressourceAddMission.getId()).setTravelDistanceDaily(bestSolution.getDate(),
				calculateDistanceDaily(ressourcesMap.get(ressourceAddMission.getId())
						.getMisssionListDaily().get(bestSolution.getDate())));
		System.out.println("The best Solution:");
		System.out.println(bestSolution);
	}
	

	public MissionPlanified getBestSolution(List<Ressource> ressourcesList,Mission mission){
		HashMap<Double, MissionPlanified> solutionMap=new HashMap<Double,MissionPlanified>();
		//foreach ressourceslist
		for(int i=0;i<ressourcesList.size();i++){
			Ressource ressource=ressourcesList.get(i);
			calculateRessourceSolutionsList(mission,ressource,ressource.getMisssionListDaily(),solutionMap,missionPlanifiedsList.size()+1);
		}
		return solutionMap.get(minTravelTimeofSolutions(solutionMap));
	}
	


}
