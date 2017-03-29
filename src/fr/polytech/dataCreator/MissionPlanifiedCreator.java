/**
 * @Title: MissionPlanifiedCreater.java 
 * @author Yang Lingbo
 */
package fr.polytech.dataCreator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class MissionPlanified Creator.
 *
 * @author Yang Lingbo
 */
public class MissionPlanifiedCreator {

	/**
	 * The main method.
	 *
	 * @param args void
	 * @throws ParseException the parse exception
	 */
	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		int number = 100;
		Date dateStart;
		Date dateEnd;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateStart=sdf1.parse("2017-02-06");
		dateEnd=sdf1.parse("2017-03-04");
		Date dateM=dateStart;
		int i=1;
		while(dateM.before(dateEnd)){
			
			int numberResource=createNumberResource();
			for(int j=0;j<numberResource;j++ ){
				int[] set =new int[numberResource]; 
				createIdResourceSet(numberResource, set);
				int idResource=set[j];
				int timeStartWork=510;
				for(int k=0;k<createPlanDailySize();k++){
					int isReplanifiable=createIsReplanifiable();
					int timeEndWork=timeStartWork+120;
					if(isReplanifiable==0){
						System.out.println("('" + i + "','" + idResource + "','" + timeStartWork + "','" + timeEndWork + "','"
								+ isReplanifiable+"','"+sdf1.format(dateM)+"'," +"NULL"+","+"NULL" +"),");
					}
					else{
						System.out.println("('" + i + "','" + idResource + "','" + timeStartWork + "','" + timeEndWork + "','"
								+ isReplanifiable+"','"+sdf1.format(dateM)+"','"
								+ sdf2.format(getReplanDayBefore(dateM))+"','"+ sdf2.format(getReplanDayAfter(dateM))+"'),");
					}
					
					timeStartWork=timeEndWork+30;
					i++;
					if(i>number)
						System.exit(0); ;
				}
			}	
			dateM=getDayAfter(dateM);
			
		}
		
	}
	
	/**
	 * Creates the id resource set.
	 *
	 * @param number the number
	 * @param set the set
	 */
	public static void createIdResourceSet(int number,int[] set){
		for(int i=0;i<number;i++){
			int num=1+(int)(Math.random()*9);
			set[i]=num;
		}
	}
	
	/**
	 * Creates the number resource.
	 *
	 * @return the int
	 */
	public static int createNumberResource(){
		return 4+(int)(Math.random()*6);
	}
	
	/**
	 * Creates the plan daily size.
	 *
	 * @return the int
	 */
	public static int createPlanDailySize(){
		return 1+(int)(Math.random()*4);
	}

	/**
	 * Creates the is replanifiable.
	 *
	 * @return the int
	 */
	public static int createIsReplanifiable(){
		
		return Math.random()>0.5?1:0;
	}
	
	
	/**
	 * Gets the day after.
	 *
	 * @param date the date
	 * @return the day after
	 */
	@SuppressWarnings("deprecation")
	public static Date getDayAfter(Date date) {
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
	 * Creates the date rang.
	 *
	 * @return the int
	 */
	public static int createDateRang(){
		return 1+(int)(Math.random()*4);
	}
	
	/**
	 * Gets the replan day before.
	 *
	 * @param date the date
	 * @return the replan day before
	 */
	public static Date getReplanDayBefore(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - createDateRang());
		Date dateTem = c.getTime();
		if (date.getDay() == 6)
			c.set(Calendar.DATE, day - 1);
		if (date.getDay() == 0)
			c.set(Calendar.DATE, day - 2);
		dateTem = c.getTime();
		dateTem.setHours(0);
		dateTem.setMinutes(0);
		return dateTem;
		
	}
	
	/**
	 * Gets the replan day after.
	 *
	 * @param date the date
	 * @return the replan day after
	 */
	public static Date getReplanDayAfter(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + createDateRang());
		Date dateTem = c.getTime();
		if (date.getDay() == 6)
			c.set(Calendar.DATE, day + 2);
		if (date.getDay() == 0)
			c.set(Calendar.DATE, day + 1);
		dateTem = c.getTime();
		dateTem.setHours(23);
		dateTem.setMinutes(59);
		return dateTem;
		
	}
}
