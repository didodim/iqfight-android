package com.empters.iqfight.network.helpters;

public class Player{
	
	private String name;
	private int points;
	
	
	public Player(String name, int points){
		this.setName(name);
		this.setPoints(points);
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	
	
	public String getNameAndPoints(){
		if(points<0){
			return getName();
		}
		return getName()+":  "+getPoints();
	}


	public int getPoints() {
		return points;
	}


	public void setPoints(int points) {
		this.points = points;
	}

}
