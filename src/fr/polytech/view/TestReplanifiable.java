package fr.polytech.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import fr.polytech.model.Client;
import fr.polytech.model.Mission;
import fr.polytech.model.Planning;
import fr.polytech.model.Ressource;
import fr.polytech.utils.DataLoad;
import fr.polytech.utils.MethodReplanifiable1;
import fr.polytech.utils.MethodSimple;

public class TestReplanifiable {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<Integer, Client> clientsList=new HashMap<Integer,Client>();
		HashMap<Integer, Ressource> ressourcesMap=new HashMap<Integer,Ressource>();
		DataLoad dataLoad=new DataLoad();
		clientsList=dataLoad.getClientsMap();
		ressourcesMap=dataLoad.getRessourcesMap();
		Planning planning=new Planning();
		planning.setMissionPlanifiedsList(dataLoad.getMissionsPlanifiedList());
		dataLoad.loadAllRessourcesDailyInfo(ressourcesMap);
		System.out.print("Sum of distance of this planning: ");
		System.out.println(dataLoad.getSumPlanningDistance());
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
		System.out.println(newmission.toString());
		MethodReplanifiable1 m=new MethodReplanifiable1();
		m.Calculate(planning, newmission, ressourcesMap);
		//dataLoad.setSumPlanningDistance(ressourcesMap);
		
		//System.out.print("Sum of distance of this planning: ");
		//System.out.println(dataLoad.getSumPlanningDistance());

	}

}
