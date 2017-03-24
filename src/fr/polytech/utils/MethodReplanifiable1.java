package fr.polytech.utils;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import fr.polytech.model.Mission;
import fr.polytech.model.MissionPlanified;
import fr.polytech.model.Planning;
import fr.polytech.model.Ressource;

public class MethodReplanifiable1 extends CommonCalculateMethod{
	
	@SuppressWarnings("unchecked")
	public void Calculate(Planning planning,Mission mission,HashMap<Integer, Ressource> ressourcesMap){
		
		Iterator<Integer> iterator1=ressourcesMap.keySet().iterator();
		List<Ressource> ressourcesObjetList=new ArrayList<Ressource>();
		
		// Get the Ressource equal to the type of mission.
		while(iterator1.hasNext()){
			int key=iterator1.next();
			Ressource r=ressourcesMap.get(key);
			if(mission.getTypeRessource().equals(r.getType())){
				ressourcesObjetList.add(r);
			}
		}
		
		Iterator<Ressource> iterator2=ressourcesObjetList.iterator();
		double min=999999999;
		HashMap<Date, ArrayList<MissionPlanified>> finalSolution=new HashMap<Date, ArrayList<MissionPlanified>>();
		HashMap<Date, Double> finalTDD=new HashMap<Date, Double>();
		HashMap<Date, ArrayList<MissionPlanified>> finalMRL=new HashMap<Date, ArrayList<MissionPlanified>>();
		Ressource finalRessource=new Ressource();
		//Foreach ressource 
		while(iterator2.hasNext()){
			Ressource r=iterator2.next();
			//deepClone
			HashMap<Date, Double> tDD_NPL=(HashMap<Date, Double>) deepClone(r.getTravelDistanceDaily());
			//HashMap<Date, Double> tDD_NPL=new HashMap<Date, Double>(r.getTravelDistanceDaily());
			HashMap<Date, ArrayList<MissionPlanified>> NPL=(HashMap<Date, ArrayList<MissionPlanified>>) deepClone(r.getPlanningDailyPerson());
			//HashMap<Date, ArrayList<MissionPlanified>> NPL=new HashMap<Date,ArrayList<MissionPlanified>>(r.getPlanningDailyPerson());			
			
			
			//First, insert mission as the method simple
			HashMap<Double, MissionPlanified> solutionMap1=new HashMap<Double,MissionPlanified>();
			calculateRessourceSolutionsList(mission, r,NPL,solutionMap1,planning.getMissionPlanifiedsList().size()+1);
			if(solutionMap1.isEmpty()){
				continue;
			}
			MissionPlanified objectMissionPlanified=solutionMap1.get(minTravelTimeofSolutions(solutionMap1));
			addMissionPlanified(objectMissionPlanified, NPL);
			sortPlanDaily(NPL.get(objectMissionPlanified.getDate()));
			
			//deepClone
			//HashMap<Date, ArrayList<MissionPlanified>> BS=new HashMap<Date,ArrayList<MissionPlanified>>(NPL);
			
			HashMap<Date, ArrayList<MissionPlanified>> BS=(HashMap<Date, ArrayList<MissionPlanified>>) deepClone(NPL);
			tDD_NPL.put(objectMissionPlanified.getDate(), calculateDistanceDaily(NPL.get(objectMissionPlanified.getDate())));
			//deepClone
			HashMap<Date, Double> tDD_BS=(HashMap<Date, Double>) deepClone(tDD_NPL);
			//HashMap<Date, Double> tDD_BS=new HashMap<Date, Double>(tDD_NPL);
			double costNPL=calculateRessourceCost(tDD_NPL);
			System.out.println("objectMissionPlanified:"+objectMissionPlanified);
			System.out.println("Cost after inser mission object:"+costNPL);
			double costBS=costNPL; 
			//deepClone
			HashMap<Date, ArrayList<MissionPlanified>> missionReplanMap=(HashMap<Date, ArrayList<MissionPlanified>>) deepClone(r.getMissionReplanMap());
			
			//List<MissionPlanified> missionReplanList=new ArrayList<MissionPlanified>(r.getMissionReplanList());
			addMissionPlanified(objectMissionPlanified, missionReplanMap);
			boolean flag=true;
			do{
				Iterator<Date> iterator3=missionReplanMap.keySet().iterator();
				while(iterator3.hasNext()){
					Date key=iterator3.next();
					List<MissionPlanified> missionReplanDailyList=missionReplanMap.get(key);
					if(missionReplanDailyList.size()>1){
						//增加switch 方法    交换两个的位置，看cost是否变小，变小就。。
						
					}
					Iterator<MissionPlanified> iterator4=missionReplanDailyList.iterator();
					while(iterator4.hasNext()){
						MissionPlanified missionPlanified=iterator4.next();
						HashMap<Date, ArrayList<MissionPlanified>> solutionPlan=(HashMap<Date, ArrayList<MissionPlanified>>) deepClone(BS);
						//HashMap<Date, ArrayList<MissionPlanified>> solutionPlan=new HashMap<Date,ArrayList<MissionPlanified>>(BS);
						Mission MRP=new Mission(missionPlanified.getClient(), missionPlanified.getRessource().getType(), 
								missionPlanified.getTimeReplanEarliest(), missionPlanified.getTimeReplanLatest(), 
								missionPlanified.getTimeEndWork()-missionPlanified.getTimeStartWork());
						deleteMissionPlanified(missionPlanified, solutionPlan);
						//System.out.println(BS);
						sortPlanDaily(solutionPlan.get(missionPlanified.getDate()));
						HashMap<Double, MissionPlanified> solutionMap2=new HashMap<Double,MissionPlanified>();
						calculateRessourceSolutionsList(MRP, r,solutionPlan,solutionMap2,missionPlanified.getId());
						MissionPlanified bestSolution=solutionMap2.get(minTravelTimeofSolutions(solutionMap2));
						
						if(bestSolution!=null){
							addMissionPlanified(bestSolution, solutionPlan);
							sortPlanDaily(solutionPlan.get(bestSolution.getDate()));
							
							//deepClone
							HashMap<Date, Double> temptDD=(HashMap<Date, Double>) deepClone(tDD_BS);
							//HashMap<Date, Double> temptDD=new HashMap<Date, Double>(tDD_BS);
							temptDD.put(bestSolution.getDate(), calculateDistanceDaily(solutionPlan.get(bestSolution.getDate())));
							double costS=calculateRessourceCost(temptDD);
							System.out.println("Cost of this Solution:"+costS);
							if(costS<costBS){
								//deepClone
								BS=(HashMap<Date, ArrayList<MissionPlanified>>) deepClone(solutionPlan);
								//BS=new HashMap<Date,ArrayList<MissionPlanified>>(solutionPlan);
								tDD_BS=(HashMap<Date, Double>) deepClone(temptDD);
								//tDD_BS=new HashMap<Date, Double>(temptDD);
								costBS=costS;
							}
						}
					}
					//deepClone
					
				}
				if(costBS<costNPL){
					//deepClone
					NPL=(HashMap<Date, ArrayList<MissionPlanified>>) deepClone(BS);
					//NPL=new HashMap<Date,ArrayList<MissionPlanified>>(BS);
					tDD_NPL=(HashMap<Date, Double>) deepClone(tDD_BS);
					//tDD_NPL=new HashMap<Date, Double>(tDD_BS);
					costNPL=costBS;
					continue;
				}
				flag=false;
			}while(flag);
			
			if(costNPL<min){
				min=costNPL;
				//deepClone
				finalSolution=(HashMap<Date, ArrayList<MissionPlanified>>) deepClone(NPL);
				//finalSolution=new HashMap<Date,ArrayList<MissionPlanified>>(NPL);
				finalTDD=(HashMap<Date, Double>) deepClone(tDD_NPL);
				//finalTDD=new HashMap<Date, Double>(tDD_NPL);
				finalMRL=(HashMap<Date, ArrayList<MissionPlanified>>) deepClone(missionReplanMap);
				//finalMRL=new ArrayList<MissionPlanified>(missionReplanList);
				finalRessource=r;
			}
			
		}
		finalRessource.setMisssionListDaily(finalSolution);
		finalRessource.setTravelDistanceDaily(finalTDD);
		finalRessource.setMissionReplanMap(finalMRL);
		finalRessource.setCost(min);
	}
	
	
	
}
