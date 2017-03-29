package fr.polytech.dataCreator;

import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class ClientCreater.
 */
public class ClientCreator {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int number=300;
		
		for(int i=0;i<number;i++){
			System.out.println("('"+createName(createLength())+"',"+createCoordinate()+","+createCoordinate()+"),");
		}
		
	}
	
	/**
	 * Creates the name.
	 *
	 * @param length the length
	 * @return the string
	 */
	public static String createName(int length){
		String firstletter="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String base = "abcdefghijklmnopqrstuvwxyz";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    sb.append(firstletter.charAt(random.nextInt(firstletter.length())));
	    for (int i = 1; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();  
	}
	
	/**
	 * Creates the length.
	 *
	 * @return the int
	 */
	public static int createLength(){
		return 4+(int)(Math.random()*6);
	}
	
	/**
	 * Creates the coordinate.
	 *
	 * @return the string
	 */
	public static String createCoordinate(){
		java.text.DecimalFormat df =new java.text.DecimalFormat("#.00");
		String x=df.format((float)(Math.random()*40));
		return x;
	}

}
