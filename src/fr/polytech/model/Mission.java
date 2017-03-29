package fr.polytech.model;

import java.text.SimpleDateFormat;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class Mission.Model of mission of the company.
 *
 * @author Yang Lingbo
 */
public class Mission {
	
	/**
	 * The client.
	 */
	private Client client;
	
	/**
	 * The type resource.
	 */
	private String typeResource;
	
	/**
	 * The date start.
	 */
	private Date dateStart;
	
	/**
	 * The date end.
	 */
	private Date dateEnd;
	
	/**
	 * The duration.
	 */
	private int duration;
	
	/**
	 * Constructor.
	 */
	public Mission() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor.
	 *
	 * @param client the client
	 * @param typeResource the type resource
	 * @param dateStart the date start
	 * @param dateEnd the date end
	 * @param duration the duration
	 */
	public Mission(Client client,String typeResource,Date dateStart,Date dateEnd,int duration) {
		this.client=client;
		this.typeResource=typeResource;
		this.dateStart=dateStart;
		this.dateEnd=dateEnd;
		setDuration(duration);
	}
	
	/**
	 * Gets the client.
	 *
	 * @return the client
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * Sets the client.
	 *
	 * @param client the client to set
	 */
	public void setClient(Client client) {
		this.client = client;
	}

	/**
	 * Gets the type resource.
	 *
	 * @return the typeResource
	 */
	public String getTypeResource() {
		return typeResource;
	}

	/**
	 * Sets the type resource.
	 *
	 * @param typeResource the typeRessource to set
	 */
	public void setTypeResource(String typeResource) {
		this.typeResource = typeResource;
	}

	/**
	 * Gets the date start.
	 *
	 * @return the dateStart
	 */
	public Date getDateStart() {
		return dateStart;
	}

	/**
	 * Sets the date start.
	 *
	 * @param dateStart the dateStart to set
	 */
	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	/**
	 * Gets the date end.
	 *
	 * @return the dateEnd
	 */
	public Date getDateEnd() {
		return dateEnd;
	}

	/**
	 * Sets the date end.
	 *
	 * @param dateEnd the dateEnd to set
	 */
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	/**
	 * Gets the duration.
	 *
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}



	/**
	 * Sets the duration.
	 *
	 * @param duration the duration to set
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return "Mission [client=" + client.getId() + ", typeResource=" + typeResource + ", dateStart=" + sdf.format(dateStart)
				+ ", dateEnd=" + sdf.format(dateEnd) + ", duration=" + duration + "]";
	}
	
	
	
}
