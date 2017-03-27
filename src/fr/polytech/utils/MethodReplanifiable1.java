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
	public void Calculate(Planning planning,Mission mission,List<Ressource> ressourcesList){
		
		Iterator<Ressource> iterator1=ressourcesList.iterator();
		List<Ressource> ressourcesObjetList=new ArrayList<Ressource>();
		
		// Get the Ressource equal to the type of mission.
		while(iterator1.hasNext()){
			Ressource r=iterator1.next();
			if(mission.getTypeRessource().equals(r.getType())){
				ressourcesObjetList.add(r);
			}
		}
		
		Iterator<Ressource> iterator2=ressourcesObjetList.iterator();
		double min=999999999;
		HashMap<Date, ArrayList<MissionPlanified>> finalSolution=new HashMap<Date, ArrayList<MissionPlanified>>();
		HashMap<Date, Double> finalTDD=new HashMap<Date, Double>();
		List<MissionPlanified> finalMRL=new ArrayList<MissionPlanified>();
		Ressource finalRessource=new Ressource();
		//Foreach ressource 
		while(iterator2.hasNext()){
			Ressource r=iterator2.next();
			//deepClone
			HashMap<Date, Double> tDD_NPL=(HashMap<Date, Double>) deepClone(r.getTravelDistanceDaily());
			//HashMap<Date, Double> tDD_NPL=new HashMap<Date, Double>(r.getTravelDistanceDaily());
			HashMap<Date, ArrayList<MissionPlanified>> NPL=(HashMap<Date, ArrayList<MissionPlanified>>) deepClone(r.getPlanningDailyPerson());
			//HashMap<Date, ArrayList<MissionPlanified>> NPL=new HashMap<Date,ArrayList<MissionPlanified>>(r.getPlanningDailyPerson());			
			double costRessource=calculateRessourceCost(tDD_NPL);
			//System.out.println("Cost before insert mission:"+costRessource);
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
			//System.out.println("objectMissionPlanified:"+objectMissionPlanified);
			//System.out.println("Cost after inser mission object:"+costNPL);
			double costBS=costNPL; 
			//deepClone
			ArrayList<MissionPlanified> missionReplanList=(ArrayList<MissionPlanified>) deepClone(r.getMissionReplanList());
			
			//List<MissionPlanified> missionReplanList=new ArrayList<MissionPlanified>(r.getMissionReplanList());
			missionReplanList.add(objectMissionPlanified);
			boolean flag=true;
			//交换 循环
			switchInMissionReplanList(missionReplanList,BS,tDD_BS);
			costBS=calculateRessourceCost(tDD_BS);
			//System.out.println("After switch循环 cost="+costBS);
			//插入 循环
			do{
				Iterator<MissionPlanified> iterator3=missionReplanList.iterator();
				while(iterator3.hasNext()){
					MissionPlanified missionPlanified=iterator3.next();
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
						//System.out.println("Cost of this Solution:"+costS);
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
			//System.out.println("The extraTime after insert:"+(costNPL-costRessource));
			if(costNPL-costRessource<min){
				min=costNPL-costRessource;
				//deepClone
				finalSolution=(HashMap<Date, ArrayList<MissionPlanified>>) deepClone(NPL);
				//finalSolution=new HashMap<Date,ArrayList<MissionPlanified>>(NPL);
				finalTDD=(HashMap<Date, Double>) deepClone(tDD_NPL);
				//finalTDD=new HashMap<Date, Double>(tDD_NPL);
				finalMRL=(ArrayList<MissionPlanified>) deepClone(missionReplanList);
				//finalMRL=new ArrayList<MissionPlanified>(missionReplanList);
				finalRessource=r;
			}
			
		}
		finalRessource.setPlanningDailyPerson(finalSolution);
		finalRessource.setTravelDistanceDaily(finalTDD);
		finalRessource.setMissionReplanList(finalMRL);
		finalRessource.setCost(calculateRessourceCost(finalRessource.getTravelDistanceDaily()));
		List<MissionPlanified> missionPlanifiedsList=new ArrayList<MissionPlanified>();
		Iterator<Ressource> iteratorT=ressourcesList.iterator();
		while(iteratorT.hasNext()){
			Ressource r=iteratorT.next();
			Iterator<Date> iteratorD=r.getPlanningDailyPerson().keySet().iterator();
			while(iteratorD.hasNext()){
				Date date=iteratorD.next();
				Iterator<MissionPlanified> iteratorM=r.getPlanningDailyPerson().get(date).iterator();
				while(iteratorM.hasNext()){
					missionPlanifiedsList.add(iteratorM.next());
				}
			}
		}
		planning.setMissionPlanifiedsList(missionPlanifiedsList);
		planning.setSumCost(calculateSumPlanningDistance(ressourcesList));
		
	}
	
	
}
