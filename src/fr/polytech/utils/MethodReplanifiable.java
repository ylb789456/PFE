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
import fr.polytech.model.Resource;

// TODO: Auto-generated Javadoc
/**
 * The Class MethodReplanifiable.It's the algorithm(based on algorithm descente locale) for the problem replanifialbe.
 *
 * @author Yang Lingbo
 */

public class MethodReplanifiable extends CommonCalculateMethod{
	
	/**
	 * Insert a mission, calculate the solutions of all resources matched the type, and get the best solution,then replace the planning.
	 *
	 * @param planning the planning
	 * @param mission the mission
	 * @param resourcesList the resources list
	 */
	@SuppressWarnings("unchecked")
	public void Calculate(Planning planning, Mission mission, List<Resource> resourcesList) {

		Iterator<Resource> iterator1 = resourcesList.iterator();
		List<Resource> resourcesObjetList = new ArrayList<Resource>();

		// Get the resource equal to the type of mission.
		while (iterator1.hasNext()) {
			Resource r = iterator1.next();
			if (mission.getTypeResource().equals(r.getType())) {
				resourcesObjetList.add(r);
			}
		}

		Iterator<Resource> iterator2 = resourcesObjetList.iterator();
		double min = 999999999;
		HashMap<Date, ArrayList<MissionPlanified>> finalSolution = new HashMap<Date, ArrayList<MissionPlanified>>();
		HashMap<Date, Double> finalTDD = new HashMap<Date, Double>();
		List<MissionPlanified> finalMRL = new ArrayList<MissionPlanified>();
		Resource finalResource = new Resource();
		// Foreach resource
		while (iterator2.hasNext()) {
			Resource r = iterator2.next();
			if(r.getPlanningDailyPerson()==null){
				continue;
			}
			// deepClone
			HashMap<Date, Double> tDD_NPL = (HashMap<Date, Double>) deepClone(r.getTravelDistanceDaily());
			
			HashMap<Date, ArrayList<MissionPlanified>> NPL = (HashMap<Date, ArrayList<MissionPlanified>>) deepClone(
					r.getPlanningDailyPerson());
		
			double costResource = calculateResourceCost(tDD_NPL);
			// System.out.println("Cost before insert mission:"+costResource);
			// First, insert mission as the method simple
			HashMap<Double, MissionPlanified> solutionMap1 = new HashMap<Double, MissionPlanified>();
			calculateResourceSolutionsList(mission, r, NPL, solutionMap1,
					planning.getMissionPlanifiedsList().size() + 1);
			if (solutionMap1.isEmpty()) {
				continue;
			}
			MissionPlanified objectMissionPlanified = solutionMap1.get(minTravelTimeofSolutions(solutionMap1));
			addMissionPlanified(objectMissionPlanified, NPL);
			sortPlanDaily(NPL.get(objectMissionPlanified.getDate()));

			// deepClone
		

			HashMap<Date, ArrayList<MissionPlanified>> BS = (HashMap<Date, ArrayList<MissionPlanified>>) deepClone(NPL);
			tDD_NPL.put(objectMissionPlanified.getDate(),
					calculateDistanceDaily(NPL.get(objectMissionPlanified.getDate())));
			// deepClone
			HashMap<Date, Double> tDD_BS = (HashMap<Date, Double>) deepClone(tDD_NPL);
			
			double costNPL = calculateResourceCost(tDD_NPL);
			// System.out.println("objectMissionPlanified:"+objectMissionPlanified);
			// System.out.println("Cost after insert mission object:"+costNPL);
			double costBS = costNPL;
			// deepClone
			ArrayList<MissionPlanified> missionReplanList = (ArrayList<MissionPlanified>) deepClone(
					r.getMissionReplanList());

			missionReplanList.add(objectMissionPlanified);
			boolean flag = true;
			// switch-loop
			switchInMissionReplanList(missionReplanList, BS, tDD_BS);
			costBS = calculateResourceCost(tDD_BS);
			//System.out.println("After switch-loop cost="+costBS);
			// insert-loop
			do {
				Iterator<MissionPlanified> iterator3 = missionReplanList.iterator();
				while (iterator3.hasNext()) {
					MissionPlanified missionPlanified = iterator3.next();
					HashMap<Date, ArrayList<MissionPlanified>> solutionPlan = (HashMap<Date, ArrayList<MissionPlanified>>) deepClone(
							BS);
					
					Mission MRP = new Mission(missionPlanified.getClient(), missionPlanified.getresource().getType(),
							missionPlanified.getTimeReplanEarliest(), missionPlanified.getTimeReplanLatest(),
							missionPlanified.getTimeEndWork() - missionPlanified.getTimeStartWork());
					deleteMissionPlanified(missionPlanified, solutionPlan);
					// System.out.println(BS);
					sortPlanDaily(solutionPlan.get(missionPlanified.getDate()));
					HashMap<Double, MissionPlanified> solutionMap2 = new HashMap<Double, MissionPlanified>();
					calculateResourceSolutionsList(MRP, r, solutionPlan, solutionMap2, missionPlanified.getId());
					MissionPlanified bestSolution = solutionMap2.get(minTravelTimeofSolutions(solutionMap2));

					if (bestSolution != null) {
						addMissionPlanified(bestSolution, solutionPlan);
						sortPlanDaily(solutionPlan.get(bestSolution.getDate()));

						// deepClone
						HashMap<Date, Double> temptDD = (HashMap<Date, Double>) deepClone(tDD_BS);
						
						temptDD.put(bestSolution.getDate(),
								calculateDistanceDaily(solutionPlan.get(bestSolution.getDate())));
						double costS = calculateResourceCost(temptDD);
						// System.out.println("Cost of this Solution:"+costS);
						if (costS < costBS) {
							// deepClone
							BS = (HashMap<Date, ArrayList<MissionPlanified>>) deepClone(solutionPlan);
							
							tDD_BS = (HashMap<Date, Double>) deepClone(temptDD);
							
							costBS = costS;
						}
					}
				}
				// deepClone

				if (costBS < costNPL) {
					// deepClone
					NPL = (HashMap<Date, ArrayList<MissionPlanified>>) deepClone(BS);
					
					tDD_NPL = (HashMap<Date, Double>) deepClone(tDD_BS);
					
					costNPL = costBS;
					continue;
				}
				flag = false;
			} while (flag);
			//System.out.println("The extraTime after insert:"+(costNPL-costResource));
			if (costNPL - costResource < min) {
				min = costNPL - costResource;
				// deepClone
				finalSolution = (HashMap<Date, ArrayList<MissionPlanified>>) deepClone(NPL);
				
				finalTDD = (HashMap<Date, Double>) deepClone(tDD_NPL);
				
				finalMRL = (ArrayList<MissionPlanified>) deepClone(missionReplanList);
				
				finalResource = r;
			}

		}
		finalResource.setPlanningDailyPerson(finalSolution);
		finalResource.setTravelDistanceDaily(finalTDD);
		finalResource.setMissionReplanList(finalMRL);
		finalResource.setCost(calculateResourceCost(finalResource.getTravelDistanceDaily()));
		List<MissionPlanified> missionPlanifiedsList = new ArrayList<MissionPlanified>();
		Iterator<Resource> iteratorT = resourcesList.iterator();
		while (iteratorT.hasNext()) {
			Resource r = iteratorT.next();
			if(r.getPlanningDailyPerson()!=null){
				Iterator<Date> iteratorD = r.getPlanningDailyPerson().keySet().iterator();
				while (iteratorD.hasNext()) {
					Date date = iteratorD.next();
					Iterator<MissionPlanified> iteratorM = r.getPlanningDailyPerson().get(date).iterator();
					while (iteratorM.hasNext()) {
						missionPlanifiedsList.add(iteratorM.next());
					}
				}
			}
		}
		planning.setMissionPlanifiedsList(missionPlanifiedsList);
		planning.setSumCost(calculateSumPlanningDistance(resourcesList));

	}
	
	
}
