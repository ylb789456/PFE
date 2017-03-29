package fr.polytech.test;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import fr.polytech.model.Client;
import fr.polytech.model.Mission;
import fr.polytech.model.MissionPlanified;
import fr.polytech.model.Planning;
import fr.polytech.model.Resource;
import fr.polytech.utils.DataLoad;
import fr.polytech.utils.MethodSimple;


// TODO: Auto-generated Javadoc
/**
 * The Class TestSimple.Test the method for the problem un-replanifiable.
 *
 * @author Yang Lingbo
 */
public class TestSimple {

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
		
		Mission newmission=new Mission(clientsList.get(110), "plombier", dateStart, dateEnd, 80);
		System.out.println(newmission.toString());
		MethodSimple methodSimple=new MethodSimple();
		long startTime = System.currentTimeMillis();
		methodSimple.Calculate(planning, newmission,resourcesList);
		long endTime = System.currentTimeMillis();

		
		System.out.print("Sum of distance of this planning: ");
		System.out.println(planning.getSumCost());
		System.out.println("Time cost of simple method:"+(endTime-startTime)+" ms");
		
	}

}