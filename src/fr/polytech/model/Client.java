package fr.polytech.model;

import java.io.Serializable;

public class Client implements Serializable {
	private int id;
	private String name;
	private float xCoordinate;
	private float yCoordinate;
	
	public Client() {
		// TODO Auto-generated constructor stub
	}
	
	public Client(int id,String name,float xCoordinate,float yCoordinate) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.name=name;
		this.xCoordinate=xCoordinate;
		this.yCoordinate=yCoordinate;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getxCoordinate() {
		return xCoordinate;
	}
	public void setxCoordinate(float xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	public float getyCoordinate() {
		return yCoordinate;
	}
	public void setyCoordinate(float yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	
	public double distanceTo(Client c){
		double distance;
		distance=Math.sqrt(Math.pow(xCoordinate-c.getxCoordinate(), 2)
				+Math.pow(yCoordinate-c.getyCoordinate(), 2));
		return distance;
	}
	
	@Override
	public String toString() {
		return "Client [id=" + id + ", name=" + name + ", xCoordinate=" + xCoordinate + ", yCoordinate=" + yCoordinate
				+ "]";
	}
	
	
	
}
