package fr.polytech.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Mission {
	private Client client;
	private String typeRessource;
	private Date dateStart;
	private Date dateEnd;
	private int duration;
	
	public Mission() {
		// TODO Auto-generated constructor stub
	}
	
	public Mission(Client client,String typeRessource,Date dateStart,Date dateEnd,int duration) {
		// TODO Auto-generated constructor stub
		this.client=client;
		this.typeRessource=typeRessource;
		this.dateStart=dateStart;
		this.dateEnd=dateEnd;
		setDuration(duration);
	}
	
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public String getTypeRessource() {
		return typeRessource;
	}
	public void setTypeRessource(String typeRessource) {
		this.typeRessource = typeRessource;
	}
	public Date getDateStart() {
		return dateStart;
	}
	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}
	public Date getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		if(duration>0&&duration<=120)
			this.duration=120;
		if(duration>120&&duration<=240)
			this.duration=240;
		if(duration>240&&duration<=360)
			this.duration=360;
		if(duration>360&&duration<=480)
			this.duration=480;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return "Mission [client=" + client.getId() + ", typeRessource=" + typeRessource + ", dateStart=" + sdf.format(dateStart)
				+ ", dateEnd=" + sdf.format(dateEnd) + ", duration=" + duration + "]";
	}
	
	
	
}
