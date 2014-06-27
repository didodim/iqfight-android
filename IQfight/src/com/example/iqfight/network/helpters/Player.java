package com.example.iqfight.network.helpters;

public class Player{
	
	private String name;
	private String points;
	
	
	public Player(String name, String points){
		this.setName(name);
		this.setPoints(points);
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPoints() {
		return points;
	}


	public void setPoints(String points) {
		this.points = points;
	}
	
	
	public String getNameAndPoints(){
		return getName()+":  "+getPoints();
	}

}
