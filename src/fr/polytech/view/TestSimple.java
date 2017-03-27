package fr.polytech.view;

import java.lang.reflect.Method;
import java.sql.Time;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import fr.polytech.model.Client;
import fr.polytech.model.Mission;
import fr.polytech.model.MissionPlanified;
import fr.polytech.model.Planning;
import fr.polytech.model.Ressource;
import fr.polytech.utils.DataLoad;
import fr.polytech.utils.MethodSimple;


public class TestSimple {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Client> clientsList = new ArrayList<Client>();
	    List<Ressource> ressourcesList = new ArrayList<Ressource>();
		DataLoad dataLoad=new DataLoad();
		clientsList=dataLoad.getClientsList();
		ressourcesList=dataLoad.getRessourcesList();
		Planning planning=new Planning();
		planning.setMissionPlanifiedsList(dataLoad.getMissionsPlanifiedList());
		dataLoad.loadInfo(ressourcesList,planning);
		
		System.out.print("Sum of distance of this planning: ");
		System.out.println(planning.getSumCost());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateStart=new Date();
		Date dateEnd=new Date();
		try {
			dateStart=sdf.parse("2017-02-06");
			dateEnd = sdf.parse("2017-02-10");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Mission newmission=new Mission(clientsList.get(42), "plombier", dateStart, dateEnd, 80);
		System.out.println(newmission.toString());
		MethodSimple methodSimple=new MethodSimple();
		methodSimple.Calculate(planning, newmission,ressourcesList);
//		
//		System.out.println("New Mission list:");
//		Iterator<Integer> iterator1=ressourcesMap.keySet().iterator();
//		while(iterator1.hasNext()){
//			Ressource ressource=ressourcesMap.get(iterator1.next());
//			HashMap<Date, ArrayList<MissionPlanified>> planningDailyPerson=ressource.getMisssionListDaily();
//			Iterator<Date> iterator2=planningDailyPerson.keySet().iterator();
//			while(iterator2.hasNext()){
//				ArrayList<MissionPlanified> missionListDaily=planningDailyPerson.get(iterator2.next());
//				Iterator<MissionPlanified> iterator3=missionListDaily.iterator();
//				while(iterator3.hasNext()){
//					System.out.println(iterator3.next());
//				}
//			}
//		}
		
		System.out.print("Sum of distance of this planning: ");
		System.out.println(planning.getSumCost());
		
	}

}