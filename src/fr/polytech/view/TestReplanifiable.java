package fr.polytech.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import fr.polytech.model.Client;
import fr.polytech.model.Mission;
import fr.polytech.model.MissionPlanified;
import fr.polytech.model.Planning;
import fr.polytech.model.Ressource;
import fr.polytech.utils.DataLoad;
import fr.polytech.utils.MethodReplanifiable1;
import fr.polytech.utils.MethodSimple;

public class TestReplanifiable {

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
		
		System.out.print("Sum of distance of this planning before Insert: ");
		System.out.println(planning.getSumCost());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateStart=new Date();
		Date dateEnd=new Date();
		try {
			dateStart=sdf.parse("2017-02-11");
			dateEnd = sdf.parse("2017-02-14");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Mission newmission=new Mission(clientsList.get(48), "electricien", dateStart, dateEnd, 80);
		System.out.println("The mission insert:");
		System.out.println(newmission.toString());
		MethodReplanifiable1 m=new MethodReplanifiable1();
		m.Calculate(planning, newmission, ressourcesList);
		System.out.println("------------------------------------------------------------------------------------");
		System.out.println("The Mission List after insert:");
		Iterator<MissionPlanified> iterator=planning.getMissionPlanifiedsList().iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next().toString());
		}
		System.out.print("Sum of distance of this planning after insert this mission: ");
		System.out.println(planning.getSumCost());
		
		//System.out.print("Sum of distance of this planning: ");
		//System.out.println(dataLoad.getSumPlanningDistance());

	}

}
