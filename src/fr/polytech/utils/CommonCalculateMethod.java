package fr.polytech.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import fr.polytech.model.Client;
import fr.polytech.model.Mission;
import fr.polytech.model.MissionPlanified;
import fr.polytech.model.Resource;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonCalculateMethod.Common calculate methods are all in here.
 *
 * @author Yang Lingbo
 */
public class CommonCalculateMethod {

	/**
	 * The Constant speedCar.Car speed =1 km/min .
	 */
	private static final double speedCar=1;
	
	/**
	 * The Constant base.Create the base as a Client.
	 */
	private static final Client base=new Client(0,"Base",0,0);
	

	
	/**
	 * Calculate the distance between two clients.
	 *
	 * @param c1 the c 1
	 * @param c2 the c 2
	 * @return double
	 */
	protected double distanceCalculate(Client c1, Client c2) {
		double distance;
		distance = Math.sqrt(Math.pow(c1.getxCoordinate() - c2.getxCoordinate(), 2)
				+ Math.pow(c1.getyCoordinate() - c2.getyCoordinate(), 2));
		return distance;
	}
	
	/**
	 * Calculate the distance cost of one day of the resource.
	 *
	 * @param missionPlanifiedsList the mission planified list
	 * @return double
	 */
	protected double calculateDistanceDaily(List<MissionPlanified> missionPlanifiedsList) {
		Iterator<MissionPlanified> iterator = missionPlanifiedsList.iterator();
		MissionPlanified missionPlanifiedTep1 = iterator.next();
		double sum = distanceCalculate(missionPlanifiedTep1.getClient(), base);
		while (iterator.hasNext()) {
			MissionPlanified missionPlanifiedTep2 = iterator.next();
			sum += distanceCalculate(missionPlanifiedTep2.getClient(), missionPlanifiedTep1.getClient());
			missionPlanifiedTep1 = missionPlanifiedTep2;
		}
		return sum;
	}

	/**
	 * Calculate the time cost of the travel.
	 *
	 * @param distance the distance
	 * @return double
	 */
	public double travelTime(double distance) {
		return distance / speedCar;
	}
	
	/**
	 * Judge if two dates in a same day.
	 *
	 * @param d1 the d 1
	 * @param d2 the d 2
	 * @return boolean
	 */
	public boolean inSameDay(Date d1, Date d2) {
		return (d1.getYear() == d2.getYear() && d1.getMonth() == d2.getMonth() && d1.getDate() == d2.getDate());
	}

	/**
	 * Calculate the distance cost total of the plan.
	 *
	 * @param resourcesList the resources list
	 * @return double
	 */
	public double calculateSumPlanningDistance(List<Resource> resourcesList) {
		Iterator<Resource> iterator = resourcesList.iterator();
		double sum = 0.0;
		while (iterator.hasNext()) {
			Resource r = iterator.next();
			sum += r.getCost();
		}
		return sum;
	}
	
	//for one resource, get all solutions
	/**
	 * Calculate the solutions that after inserting a mission in the resource, put the result in the HashMap solutionMap.
	 *
	 * @param mission the mission
	 * @param resource the resource
	 * @param missionPlanifiedsMap the mission planifieds map
	 * @param solutionMap the solution map
	 * @param idBestSolution the id best solution
	 */
	public void calculateResourceSolutionsList(Mission mission, Resource resource,
			HashMap<Date, ArrayList<MissionPlanified>> missionPlanifiedsMap,
			HashMap<Double, MissionPlanified> solutionMap, int idBestSolution) {

		Date missionStart = mission.getDateStart();
		Date missionEnd = mission.getDateEnd();
		MissionPlanified tempMissionP;
		Iterator<Date> iterator = missionPlanifiedsMap.keySet().iterator();
		while (iterator.hasNext()) {
			Date key = iterator.next();
			if ((key.after(missionStart) && key.before(missionEnd)) || inSameDay(key, missionStart)
					|| inSameDay(key, missionEnd)) {
				List<MissionPlanified> mList = missionPlanifiedsMap.get(key);
				for (int j = 0; j < mList.size(); j++) {
					double travelTimePre;
					double travelTimeFol;
					double travelTimePast;
					double extraTime;
					if (j == 0) {
						travelTimePre = travelTime(distanceCalculate(mission.getClient(), base));
						travelTimeFol = travelTime(distanceCalculate(mission.getClient(), mList.get(0).getClient()));
						travelTimePast = travelTime(distanceCalculate(mList.get(0).getClient(), base));
						extraTime = travelTimeFol + travelTimePre - travelTimePast;
						// Judge if meet the time window
						if (mList.get(0).getTimeStartWork() - 480 >= mission.getDuration() + extraTime) {
							tempMissionP = new MissionPlanified(idBestSolution, key, mission.getClient(),
									(int) (480 + travelTimePre), (int) (480 + travelTimePre + mission.getDuration()),
									resource, true, missionStart, missionEnd);
							solutionMap.put(extraTime, tempMissionP);
						}
					} else {
						if (j == (mList.size() - 1)) {
							travelTimePre = travelTime(
									distanceCalculate(mission.getClient(), mList.get(j).getClient()));
							extraTime = travelTimePre;
							// Judge if meet the time window
							if (480 + resource.getTimeMaxWork()
									- mList.get(j).getTimeEndWork() >= mission.getDuration() + extraTime) {
								tempMissionP = new MissionPlanified(idBestSolution, key, mission.getClient(),
										(int) (mList.get(j).getTimeEndWork() + extraTime),
										(int) (mList.get(j).getTimeEndWork() + extraTime + mission.getDuration()),
										resource, true, missionStart, missionEnd);
								solutionMap.put(extraTime, tempMissionP);
							}
						}
						travelTimePre = travelTime(
								distanceCalculate(mission.getClient(), mList.get(j - 1).getClient()));
						travelTimeFol = travelTime(distanceCalculate(mission.getClient(), mList.get(j).getClient()));
						travelTimePast = travelTime(
								distanceCalculate(mList.get(j - 1).getClient(), mList.get(j).getClient()));
						extraTime = travelTimePre + travelTimeFol - travelTimePast;
						// Judge if meet the time window
						if (mList.get(j).getTimeStartWork() - mList.get(j - 1).getTimeEndWork() >= mission.getDuration()
								+ extraTime) {
							tempMissionP = new MissionPlanified(idBestSolution, key, mission.getClient(),
									(int) (mList.get(j - 1).getTimeEndWork() + extraTime),
									(int) (mList.get(j - 1).getTimeEndWork() + extraTime + mission.getDuration()),
									resource, true, missionStart, missionEnd);
							solutionMap.put(extraTime, tempMissionP);
						}
					}
				}
			}
		}
		if (solutionMap.isEmpty()) {
			for (Date date = missionStart; date.before(missionEnd)
					|| date.equals(missionEnd); date = getDayAfter(date)) {
				if (missionPlanifiedsMap.get(date) == null) {
					double preTime = travelTime(distanceCalculate(mission.getClient(), base));
					tempMissionP = new MissionPlanified(idBestSolution, date, mission.getClient(), 480 + (int) preTime,
							480 + (int) preTime + mission.getDuration(), resource, true, missionStart, missionEnd);
					addMissionPlanified(tempMissionP, missionPlanifiedsMap);
					solutionMap.put(preTime, tempMissionP);
					break;
				}
			}
		}
	}
	
	/**
	 * Get the day after the input date.
	 *
	 * @param date the date
	 * @return Date
	 */
	@SuppressWarnings("deprecation")
	public Date getDayAfter(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		if (date.getDay() == 5)
			c.set(Calendar.DATE, day + 3);
		else if (date.getDay() == 6)
			c.set(Calendar.DATE, day + 2);
		else
			c.set(Calendar.DATE, day + 1);
		Date dateTem = c.getTime();
		return dateTem;
	} 
	
	/**
	 * Calculate the cost of this resource.
	 *
	 * @param travelDistanceDaily the travel distance daily
	 * @return double
	 */
	public double calculateResourceCost(HashMap<Date, Double> travelDistanceDaily) {
		Iterator<Date> iterator = travelDistanceDaily.keySet().iterator();
		double sum = 0;
		while (iterator.hasNext()) {
			Date date = iterator.next();
			sum += travelDistanceDaily.get(date);
		}
		return sum;
	}
	
	/**
	 * Delete MissionPlanified from the HashMap.
	 *
	 * @param missionPlanified the mission planified
	 * @param planningDailyPerson the planning daily person
	 */
	public void deleteMissionPlanified(MissionPlanified missionPlanified,
			HashMap<Date, ArrayList<MissionPlanified>> planningDailyPerson) {
		Iterator<MissionPlanified> iterator = planningDailyPerson.get(missionPlanified.getDate()).iterator();
		while (iterator.hasNext()) {
			MissionPlanified m = iterator.next();
			if (m.getId() == missionPlanified.getId()) {
				planningDailyPerson.get(missionPlanified.getDate()).remove(m);
				break;
			}
		}
	}

	/**
	 * Find MissionPlanified in the HashMap.
	 *
	 * @param missionPlanified the mission planified
	 * @param planningDailyPerson the planning daily person
	 * @return MissionPlanified
	 */
	public MissionPlanified findMissionInPlanDailyMap(MissionPlanified missionPlanified,
			HashMap<Date, ArrayList<MissionPlanified>> planningDailyPerson) {
		Iterator<MissionPlanified> iterator = planningDailyPerson.get(missionPlanified.getDate()).iterator();
		MissionPlanified mp = new MissionPlanified();
		while (iterator.hasNext()) {
			MissionPlanified m = iterator.next();
			if (m.getId() == missionPlanified.getId()) {
				mp = m;
			}

		}
		return mp;
	}
	
	/**
	 * Sort plan of one resource by timeStartWork.
	 *
	 * @param planningDailyPerson the planning daily 
	 */
	public void sortPlan(HashMap<Date, ArrayList<MissionPlanified>> planningDailyPerson) {
		Iterator<Date> iterator = planningDailyPerson.keySet().iterator();
		while (iterator.hasNext()) {
			Date key = iterator.next();
			sortPlanDaily(planningDailyPerson.get(key));
		}
	}
	
	//sort plan daily(one day)
	/**
	 * Sort plan of one day of the resource.
	 *
	 * @param mpListDaily void
	 */
	public void sortPlanDaily(List<MissionPlanified> mpListDaily) {
		Collections.sort(mpListDaily, new Comparator<MissionPlanified>() {
			public int compare(MissionPlanified m1, MissionPlanified m2) {
				if (m1.getTimeStartWork() > m2.getTimeStartWork())
					return 1;
				if (m1.getTimeStartWork() < m2.getTimeStartWork())
					return -1;
				else
					return 0;
			}
		});
	}
	
	/**
	 * Add MissionPlanified in the HashMap planningDailyPerson of the resource.
	 *
	 * @param missionPlanified the mission planified
	 * @param planningDailyPerson void
	 */
	public void addMissionPlanified(MissionPlanified missionPlanified,HashMap<Date,ArrayList<MissionPlanified>> planningDailyPerson){
		if(planningDailyPerson==null){
			planningDailyPerson=new HashMap<Date,ArrayList<MissionPlanified>>();
		}
		
		if(planningDailyPerson.get(missionPlanified.getDate())==null){
			ArrayList<MissionPlanified> missionList=new ArrayList<MissionPlanified>();
			missionList.add(missionPlanified);
			planningDailyPerson.put(missionPlanified.getDate(),missionList);
		}
		else{
			planningDailyPerson.get(missionPlanified.getDate()).add(missionPlanified);
		}
	}
	

	/**
	 * Get the min time solution of the solutionMap.
	 *
	 * @param solutionMap the solution map
	 * @return double
	 */
	public double minTravelTimeofSolutions(HashMap<Double, MissionPlanified> solutionMap){
		//System.out.println("All the Solutions:");
		Iterator<Double> iterator=solutionMap.keySet().iterator();
		double min=1000;
		while(iterator.hasNext()){
			Double key=iterator.next();
		//	System.out.print("ExtraTime:"+key+" ");
		//	System.out.println(solutionMap.get(key).toString());
			if(key<min)
				min=key;
		}
		return min;
	}
	
	/**
	 * Deep clone of one object.Use the input stream and output stream.
	 *
	 * @param src the src
	 * @return Object
	 */
	public Object deepClone(Object src)  
    {  
        Object o = null;  
        try  
        {  
            if (src != null)  
            {  
                ByteArrayOutputStream baos = new ByteArrayOutputStream();  
                ObjectOutputStream oos = new ObjectOutputStream(baos);  
                oos.writeObject(src);  
                oos.close();  
                ByteArrayInputStream bais = new ByteArrayInputStream(baos  
                        .toByteArray());  
                ObjectInputStream ois = new ObjectInputStream(bais);  
                o = ois.readObject();  
                ois.close();  
            }  
        } catch (IOException e)  
        {  
            e.printStackTrace();  
        } catch (ClassNotFoundException e)  
        {  
            e.printStackTrace();  
        }  
        return o;  
    }  
	
	/**
	 * switch the missionplanified replanfiable list in the solutionResource(is a HashMap store MissionPlanified List of every 
	 * working day).Put the result in the solutionResource(replace the result),and in the tDD(store the cost of every working day).
	 *
	 * @param missionReplanList the mission replan list
	 * @param solutionResource the solution resource
	 * @param tDD void
	 */
	@SuppressWarnings("unchecked")
	public void switchInMissionReplanList(ArrayList<MissionPlanified> missionReplanList,
			HashMap<Date, ArrayList<MissionPlanified>> solutionResource,
			HashMap<Date, Double> tDD){
		
		for(int i=0;i<missionReplanList.size();i++){
			for(int j=i+1;j<missionReplanList.size();j++){
				MissionPlanified m1=findMissionInPlanDailyMap(missionReplanList.get(i), solutionResource);
				MissionPlanified m2=findMissionInPlanDailyMap(missionReplanList.get(j), solutionResource);
				List<MissionPlanified> dailyList1=solutionResource.get(m1.getDate());
				List<MissionPlanified> dailyList2=solutionResource.get(m2.getDate());
				double cost1=tDD.get(m1.getDate())+tDD.get(m2.getDate());
				int index1=dailyList1.indexOf(m1);
				int index2=dailyList2.indexOf(m2);
				int duration1=m1.getTimeEndWork()-m1.getTimeStartWork();
				int duration2=m2.getTimeEndWork()-m2.getTimeStartWork();
				double preTime1,folTime1,preTime2,folTime2;
				int tempStartTime1, tempStartTime2,timeWindow1,timeWindow2;
				boolean changeFlag=false;
				//************************************************************************************//
				//m1,m2 in the same day and in order
				if(m1.getDate().equals(m2.getDate())&&Math.abs(index1-index2)==1){
					int windowStart,windowEnd;
					if(index1<index2){
						preTime2=folTime1=travelTime(distanceCalculate(m1.getClient(), m2.getClient()));
						if(index1==0){
							preTime1=travelTime(distanceCalculate(m2.getClient(), base));
							tempStartTime1=480+(int)preTime1;
							windowStart=480;
						}
						else{
							preTime1=travelTime(distanceCalculate(m2.getClient(), dailyList1.get(index1-1).getClient()));
							tempStartTime1=dailyList1.get(index1-1).getTimeEndWork()+(int)preTime1;
							windowStart=dailyList1.get(index1-1).getTimeEndWork();
						}
						if(index2==dailyList1.size()-1){
							folTime2=0;
							windowEnd=480+m1.getresource().getTimeMaxWork();
						}
						else{
							folTime2=travelTime(distanceCalculate(m1.getClient(),dailyList1.get(index2+1).getClient()));
							windowEnd=dailyList1.get(index2+1).getTimeStartWork();
						}
						tempStartTime2=tempStartTime1+(int)(duration2+folTime1);
						timeWindow1=duration1+duration2+(int)(preTime1+folTime1+folTime2);
					}
					else{
						preTime1=folTime2=travelTime(distanceCalculate(m1.getClient(), m2.getClient()));
						if(index2==0){
							preTime2=travelTime(distanceCalculate(m1.getClient(), base));
							tempStartTime2=480+(int)preTime2;
							windowStart=480;
						}
						else{
							preTime2=travelTime(distanceCalculate(m1.getClient(), dailyList1.get(index2-1).getClient()));
							tempStartTime2=dailyList1.get(index2-1).getTimeEndWork()+(int)preTime2;
							windowStart=dailyList1.get(index2-1).getTimeEndWork();
						}
						if(index1==dailyList1.size()-1){
							folTime1=0;
							windowEnd=480+m1.getresource().getTimeMaxWork();
						}
						else{
							folTime1=travelTime(distanceCalculate(m2.getClient(),dailyList1.get(index1+1).getClient()));
							windowEnd=dailyList1.get(index1+1).getTimeStartWork();
						}
						tempStartTime1=tempStartTime2+(int)(duration1+folTime2);
						timeWindow1=duration1+(int)(duration2+preTime2+folTime2+folTime1);
					}
					if(timeWindow1<windowEnd-windowStart){
						changeFlag=true;
					}
				}
				//m1,m2 not in the same day or not in order
				else{
					if(index1!=0&&index1!=dailyList1.size()-1){
						preTime1=travelTime(distanceCalculate(m2.getClient(), dailyList1.get(index1-1).getClient()));
						folTime1=travelTime(distanceCalculate(m2.getClient(), dailyList1.get(index1+1).getClient()));
						timeWindow1=dailyList1.get(index1+1).getTimeStartWork()-dailyList1.get(index1).getTimeEndWork();
						tempStartTime1=dailyList1.get(index1-1).getTimeEndWork()+(int)preTime1;
					}
					else if(index1==0&&dailyList1.size()==1){
						preTime1=travelTime(distanceCalculate(m2.getClient(), base));
						folTime1=0;
						timeWindow1=m1.getresource().getTimeMaxWork();	
						tempStartTime1=480+(int)preTime1;
					}
					else if(index1==0&&dailyList1.size()!=1){
						preTime1=travelTime(distanceCalculate(m2.getClient(), base));
						folTime1=travelTime(distanceCalculate(m2.getClient(), dailyList1.get(1).getClient()));
						timeWindow1=dailyList1.get(1).getTimeStartWork()-480;
						tempStartTime1=480+(int)preTime1;
					}else{
						preTime1=travelTime(distanceCalculate(m2.getClient(), dailyList1.get(index1-1).getClient()));
						folTime1=0;
						timeWindow1=480+m1.getresource().getTimeMaxWork()-dailyList1.get(index1-1).getTimeEndWork();
						tempStartTime1=dailyList1.get(index1-1).getTimeEndWork()+(int)preTime1;
					}
					
					if(index2!=0&&index2!=dailyList2.size()-1){
						preTime2=travelTime(distanceCalculate(m1.getClient(), dailyList2.get(index2-1).getClient()));
						folTime2=travelTime(distanceCalculate(m1.getClient(), dailyList2.get(index2+1).getClient()));
						timeWindow2=dailyList2.get(index2+1).getTimeStartWork()-dailyList2.get(index2).getTimeEndWork();
						tempStartTime2=dailyList2.get(index2-1).getTimeEndWork()+(int)preTime2;
					}
					else if(index2==0&&dailyList2.size()==1){
						preTime2=travelTime(distanceCalculate(m1.getClient(), base));
						folTime2=0;
						timeWindow2=m1.getresource().getTimeMaxWork();
						tempStartTime2=480+(int)preTime2;
					}
					else if(index2==0&&dailyList2.size()!=1){
						preTime2=travelTime(distanceCalculate(m1.getClient(), base));
						folTime2=travelTime(distanceCalculate(m1.getClient(), dailyList2.get(1).getClient()));
						timeWindow2=dailyList2.get(1).getTimeStartWork()-480;
						tempStartTime2=480+(int)preTime2;
					}else{
						preTime2=travelTime(distanceCalculate(m1.getClient(), dailyList2.get(index2-1).getClient()));
						folTime2=0;
						timeWindow2=480+m1.getresource().getTimeMaxWork()-dailyList2.get(index2-1).getTimeEndWork();
						tempStartTime2=dailyList2.get(index2-1).getTimeEndWork()+(int)preTime2;
					}
					if(timeWindow1>(duration2+preTime1+folTime1)&&timeWindow2>(duration1+preTime2+folTime2)){
						changeFlag=true;
					}
				}
				//***********************************************************************************************************//
				
				if(changeFlag){
					if(m1.getDate().equals(m2.getDate())){
						ArrayList<MissionPlanified> tempDailyList=(ArrayList<MissionPlanified>)deepClone(dailyList1);
						MissionPlanified tempM=(MissionPlanified)deepClone(dailyList1.get(index2));
						tempDailyList.set(index2, tempDailyList.get(index1));
						tempDailyList.set(index1, tempM);
						double cost2=2*calculateDistanceDaily(tempDailyList);
						if(cost2<cost1){
							tempDailyList.get(index1).setTimeStartWork(tempStartTime1);
							tempDailyList.get(index1).setTimeEndWork(tempStartTime1+duration2);
							tempDailyList.get(index2).setTimeStartWork(tempStartTime2);
							tempDailyList.get(index2).setTimeEndWork(tempStartTime2+duration1);
							solutionResource.put(m1.getDate(), tempDailyList);
							missionReplanList.set(i, tempDailyList.get(index2));
							missionReplanList.set(j, tempDailyList.get(index1));
							//dailyList1=(List<MissionPlanified>) deepClone(tempDailyList);
							tDD.put(m1.getDate(), calculateDistanceDaily(tempDailyList));
						}
					}
					else{
						ArrayList<MissionPlanified> tempDailyList1=(ArrayList<MissionPlanified>) deepClone(dailyList1);
						ArrayList<MissionPlanified> tempDailyList2=(ArrayList<MissionPlanified>) deepClone(dailyList2);
						MissionPlanified tempM=(MissionPlanified) deepClone(dailyList2.get(index2));
						tempDailyList2.set(index2,tempDailyList1.get(index1));
						tempDailyList1.set(index1, tempM);
						double cost2=calculateDistanceDaily(tempDailyList1)+calculateDistanceDaily(tempDailyList2);
						if(cost2<cost1){
							tempDailyList1.get(index1).setDate(m1.getDate());
							tempDailyList1.get(index1).setTimeStartWork(tempStartTime1);
							tempDailyList1.get(index1).setTimeEndWork(tempStartTime1+duration2);
							tempDailyList2.get(index2).setDate(m2.getDate());
							tempDailyList2.get(index2).setTimeStartWork(tempStartTime2);
							tempDailyList2.get(index2).setTimeEndWork(tempStartTime2+duration1);
							missionReplanList.set(i, tempDailyList2.get(index2));
							missionReplanList.set(j, tempDailyList1.get(index1));
							solutionResource.put(m1.getDate(), tempDailyList1);
							solutionResource.put(m2.getDate(),	tempDailyList2);
							tDD.put(m1.getDate(), calculateDistanceDaily(tempDailyList1));
							tDD.put(m2.getDate(),calculateDistanceDaily(tempDailyList2));
						}	
					}
				}	
			}		
		}
	}	
	
	
}
