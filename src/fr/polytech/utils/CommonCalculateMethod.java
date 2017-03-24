package fr.polytech.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import fr.polytech.model.Client;
import fr.polytech.model.Mission;
import fr.polytech.model.MissionPlanified;
import fr.polytech.model.Ressource;

public class CommonCalculateMethod {
	/***
	 * Car speed =1 km/min
	 */
	private static final double speedCar=1;
	private static final Client base=new Client(0,"Base",0,0);
	
	protected double distanceCalculate(Client c1,Client c2){
		double distance;
		distance=Math.sqrt(Math.pow(c1.getxCoordinate()-c2.getxCoordinate(), 2)
				+Math.pow(c1.getyCoordinate()-c2.getyCoordinate(), 2));
		return distance;
	}
	
	protected double calculateDistanceDaily(List<MissionPlanified> missionPlanifiedsList){
		Iterator<MissionPlanified> iterator=missionPlanifiedsList.iterator();
		MissionPlanified missionPlanifiedTep1=iterator.next();
		double sum=distanceCalculate(missionPlanifiedTep1.getClient(), base);
		while(iterator.hasNext()){	
			MissionPlanified missionPlanifiedTep2=iterator.next();
			sum+=distanceCalculate(missionPlanifiedTep2.getClient(),missionPlanifiedTep1.getClient());
			missionPlanifiedTep1=missionPlanifiedTep2;
		}
		return sum;		
	}

	public double travelTime(double distance){
		return distance/speedCar;
	}
	
	public boolean inSameDay(Date d1,Date d2){
		return (d1.getYear()==d2.getYear()&&d1.getMonth()==d2.getMonth()&&d1.getDate()==d2.getDate());
	}
	
	//for one ressource, get all solutions
	public void calculateRessourceSolutionsList(Mission mission,Ressource ressource,
			HashMap<Date, ArrayList<MissionPlanified>> missionPlanifiedsMap,
			HashMap<Double, MissionPlanified> solutionMap,int idBestSolution){
		
		Date missionStart=mission.getDateStart();
		Date missionEnd=mission.getDateEnd();
		MissionPlanified tempMissionP;
		Iterator<Date> iterator=missionPlanifiedsMap.keySet().iterator();
		while(iterator.hasNext()){
			Date key=iterator.next();
			if((key.after(missionStart)&&key.before(missionEnd))||inSameDay(key, missionStart)||inSameDay(key, missionEnd)){
				List<MissionPlanified> mList=missionPlanifiedsMap.get(key);
				for(int j=0;j<mList.size();j++){
					double travelTimePre;
					double travelTimeFol;
					double travelTimePast;
					double extraTime;
					if(j==0){
						travelTimePre=travelTime(distanceCalculate(mission.getClient(),base));
						travelTimeFol=travelTime(distanceCalculate(mission.getClient(), mList.get(0).getClient()));
						travelTimePast=travelTime(distanceCalculate(mList.get(0).getClient(), base));
						extraTime=travelTimeFol+travelTimePre-travelTimePast;
						//Judge if meet the time window
						if(mList.get(0).getTimeStartWork()-480>=mission.getDuration()+extraTime){
							tempMissionP=new MissionPlanified(idBestSolution,key,mission.getClient(),
									(int)(480+travelTimePre),(int)(480+travelTimePre+mission.getDuration()),ressource,true,missionStart,missionEnd);
							solutionMap.put(extraTime,tempMissionP);
						}			
					}
					else{
						if(j==(mList.size()-1)){
							travelTimePre=travelTime(distanceCalculate(mission.getClient(),mList.get(j).getClient()));
							extraTime=travelTimePre;
							//Judge if meet the time window
							if(480+ressource.getTimeMaxWork()-mList.get(j).getTimeEndWork()>=
									mission.getDuration()+extraTime){
								tempMissionP=new MissionPlanified(idBestSolution,key,mission.getClient(),
										(int)(mList.get(j).getTimeEndWork()+extraTime),
										(int)(mList.get(j).getTimeEndWork()+extraTime+mission.getDuration()),
										ressource,true,missionStart,missionEnd);
								solutionMap.put(extraTime, tempMissionP);
							}	
						}
						travelTimePre=travelTime(distanceCalculate(mission.getClient(), mList.get(j-1).getClient()));
						travelTimeFol=travelTime(distanceCalculate(mission.getClient(), mList.get(j).getClient()));
						travelTimePast=travelTime(distanceCalculate(mList.get(j-1).getClient(),mList.get(j).getClient()));
						extraTime=travelTimePre+travelTimeFol-travelTimePast;
						//Judge if meet the time window
						if(mList.get(j).getTimeStartWork()-mList.get(j-1).getTimeEndWork()>=mission.getDuration()+extraTime){
							tempMissionP=new MissionPlanified(idBestSolution,key,mission.getClient(),
									(int)(mList.get(j-1).getTimeEndWork()+extraTime),
									(int)(mList.get(j-1).getTimeEndWork()+extraTime+mission.getDuration()),
									ressource,true,missionStart,missionEnd);
							solutionMap.put(extraTime, tempMissionP);
						}
					}
				}
			}		
		}		
	}		
	
	//calculate the cost of this ressource(the input is the travelDistanceDaily of this ressource)
	public double calculateRessourceCost(HashMap<Date, Double> travelDistanceDaily){
		Iterator<Date> iterator=travelDistanceDaily.keySet().iterator();
		double sum=0;
		while(iterator.hasNext()){
			Date date=iterator.next();
			sum+=travelDistanceDaily.get(date);
		}
		return sum;
	}
	
	public void deleteMissionPlanified(MissionPlanified missionPlanified,
			HashMap<Date, ArrayList<MissionPlanified>> planningDailyPerson){
		Iterator<MissionPlanified> iterator=planningDailyPerson.get(missionPlanified.getDate()).iterator();
		while(iterator.hasNext()){
			MissionPlanified m=iterator.next();
			if(m.getId()==missionPlanified.getId()){
				planningDailyPerson.get(missionPlanified.getDate()).remove(m);
				break;
			}
				
		}
	}
	
	public MissionPlanified findMissionInPlanDailyMap(MissionPlanified missionPlanified,
			HashMap<Date, ArrayList<MissionPlanified>> planningDailyPerson){
		Iterator<MissionPlanified> iterator=planningDailyPerson.get(missionPlanified.getDate()).iterator();
		MissionPlanified mp=new MissionPlanified();
		while(iterator.hasNext()){
			MissionPlanified m=iterator.next();
			if(m.getId()==missionPlanified.getId()){
				mp=(MissionPlanified) deepClone(m);
			}
			
		}
		return mp;					
	}
	
	//sort plan of one ressource by timeStartWork(input is the plan list of this ressource)
	public void sortPlan(HashMap<Date, ArrayList<MissionPlanified>> planningDailyPerson){
		Iterator<Date> iterator=planningDailyPerson.keySet().iterator();
		while(iterator.hasNext()){
			Date key=iterator.next();
			sortPlanDaily(planningDailyPerson.get(key));
		}	
	}
	
	//sort plan daily(one day)
	public void sortPlanDaily(List<MissionPlanified> mpListDaily){
		Collections.sort(mpListDaily,new Comparator<MissionPlanified>() {
			public int compare(MissionPlanified m1,MissionPlanified m2){
				if(m1.getTimeStartWork()>m2.getTimeStartWork())
					return 1;
				if(m1.getTimeStartWork()<m2.getTimeStartWork())
					return -1;
				else 
					return 0;
			}
		});
	}
	
	//add MissionPlanified in the ressource( input planningDailyPerson is the plan list of this ressource)
	public void addMissionPlanified(MissionPlanified missionPlanified,HashMap<Date,ArrayList<MissionPlanified>> planningDailyPerson){
		if(planningDailyPerson==null){
			planningDailyPerson=new HashMap<Date,ArrayList<MissionPlanified>>();
		}
		
		if((planningDailyPerson.get(missionPlanified.getDate()).isEmpty())||(planningDailyPerson.get(missionPlanified.getDate())==null)){
			ArrayList<MissionPlanified> missionList=new ArrayList<MissionPlanified>();
			missionList.add(missionPlanified);
			planningDailyPerson.put(missionPlanified.getDate(),missionList);
		}
		else{
			planningDailyPerson.get(missionPlanified.getDate()).add(missionPlanified);
		}
	}
	

	//get the min time solution
	public double minTravelTimeofSolutions(HashMap<Double, MissionPlanified> solutionMap){
		System.out.println("All the Solutions:");
		Iterator<Double> iterator=solutionMap.keySet().iterator();
		double min=1000;
		while(iterator.hasNext()){
			Double key=iterator.next();
			System.out.print("ExtraTime:"+key+" ");
			System.out.println(solutionMap.get(key).toString());
			if(key<min)
				min=key;
		}
		return min;
	}
	
	//deep clone of one object
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
	
	//
}
