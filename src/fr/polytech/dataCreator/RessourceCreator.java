package fr.polytech.dataCreator;

import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class RessourceCreater.
 *
 * @author Yang Lingbo
 */

public class RessourceCreator {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int number=10;
		
		for(int i=0;i<number;i++){
			System.out.println("('"+createName(createLength())+"','"+createType()+"',"+480+","+1080+","+createMaxWorkTime()+"),");
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
	 * Creates the type.
	 *
	 * @return the string
	 */
	public static String createType(){
		String[] types={"electricien","plombier","charpentier"};
		Random random = new Random(); 
		int index=random.nextInt(3);   

		return types[index];
	}
	
	/**
	 * Creates the max work time.
	 *
	 * @return the int
	 */
	public static int createMaxWorkTime(){
		int[] time={1080,1140,1200,1260};
		Random random = new Random(); 
		int index= random.nextInt(4);   
		return time[index];
	}
}
