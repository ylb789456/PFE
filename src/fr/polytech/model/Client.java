package fr.polytech.model;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class Client.Model of client of the company.
 *
 * @author Yang Lingbo
 */
public class Client implements Serializable {


	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -7477626454973096417L;
	
	/**
	 * The id.
	 */
	private int id;
	
	/**
	 * The name.
	 */
	private String name;
	
	/**
	 * The x coordinate.
	 */
	private float xCoordinate;
	
	/**
	 * The y coordinate.
	 */
	private float yCoordinate;
	

	
	
	/**
	 * Constructor.
	 */
	public Client() {
		// TODO Auto-generated constructor stub
	}
	

	/**
	 * Constructor.
	 *
	 * @param id the id
	 * @param name the name
	 * @param xCoordinate the x coordinate
	 * @param yCoordinate the y coordinate
	 */
	public Client(int id,String name,float xCoordinate,float yCoordinate) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.name=name;
		this.xCoordinate=xCoordinate;
		this.yCoordinate=yCoordinate;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * Sets the id.
	 *
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * Sets the name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * Gets the x coordinate.
	 *
	 * @return the xCoordinate
	 */
	public float getxCoordinate() {
		return xCoordinate;
	}


	/**
	 * Sets the x coordinate.
	 *
	 * @param xCoordinate the xCoordinate to set
	 */
	public void setxCoordinate(float xCoordinate) {
		this.xCoordinate = xCoordinate;
	}


	/**
	 * Gets the y coordinate.
	 *
	 * @return the yCoordinate
	 */
	public float getyCoordinate() {
		return yCoordinate;
	}


	/**
	 * Sets the y coordinate.
	 *
	 * @param yCoordinate the yCoordinate to set
	 */
	public void setyCoordinate(float yCoordinate) {
		this.yCoordinate = yCoordinate;
	}


	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Client [id=" + id + ", name=" + name + ", xCoordinate=" + xCoordinate + ", yCoordinate=" + yCoordinate
				+ "]";
	}
	
	
	
}
