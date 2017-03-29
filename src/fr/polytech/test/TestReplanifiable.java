package fr.polytech.test;

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
import fr.polytech.model.Resource;
import fr.polytech.utils.DataLoad;
import fr.polytech.utils.MethodReplanifiable;
import fr.polytech.utils.MethodSimple;

// TODO: Auto-generated Javadoc
/**
 * The Class TestReplanifiable.Test the method for the problem replanifiable.
 *
 * @author Yang Lingbo
 */
public class TestReplanifiable {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Client> clientsList = new ArrayList<Client>();
	    List<Resource> resourcesList = new ArrayList<Resource>();
		DataLoad dataLoad=new DataLoad();
		clientsList=dataLoad.getClientsList();
		resourcesList=dataLoad.getResourcesList();
		Planning planning=new Planning();
		planning.setMissionPlanifiedsList(dataLoad.getMissionsPlanifiedList());
		dataLoad.loadInfo(resourcesList,planning);
		
		System.out.print("Sum of distance of this planning before Insert: ");
		System.out.println(planning.getSumCost());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateStart=new Date();
		Date dateEnd=new Date();
		try {
			dateStart=sdf.parse("2017-02-09");
			dateEnd = sdf.parse("2017-02-28");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Mission newmission=new Mission(clientsList.get(210), "electricien", dateStart, dateEnd, 80);
		System.out.println("The mission insert:");
		System.out.println(newmission.toString());
		MethodReplanifiable m=new MethodReplanifiable();
		long startTime = System.currentTimeMillis();
		m.Calculate(planning, newmission, resourcesList);
		long endTime = System.currentTimeMillis();
		System.out.println("------------------------------------------------------------------------------------");
		System.out.println("The Mission List after insert:");
//		Iterator<MissionPlanified> iterator=planning.getMissionPlanifiedsList().iterator();
//		while(iterator.hasNext()){
//			System.out.println(iterator.next().toString());
//		}
		System.out.print("Sum of distance of this planning after insert this mission: ");
		System.out.println(planning.getSumCost());
		System.out.println("Time cost of replanifiable method:"+(endTime-startTime)+" ms");
		
		//System.out.print("Sum of distance of this planning: ");
		//System.out.println(dataLoad.getSumPlanningDistance());

	}

}
