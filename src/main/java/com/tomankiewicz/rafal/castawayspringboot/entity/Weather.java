package com.tomankiewicz.rafal.castawayspringboot.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

@Entity
@Table(name = "weather")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {

	@Id
	@Column(name = "id")
	private long id;

	@Column(name = "weather_state_name")
	private String weather_state_name;

	@Column(name = "weather_state_abbr")
	private String weather_state_abbr;

	@Column(name = "wind_direction_compass")
	private String wind_direction_compass;

	@Column(name = "applicable_date")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate applicable_date;

	@Column(name = "min_temp")
	private double min_temp;

	@Column(name = "max_temp")
	private double max_temp;

	@Column(name = "the_temp")
	private double the_temp;

	@Column(name = "wind_speed")
	private double wind_speed;

	@Column(name = "wind_direction")
	private double wind_direction;

	@Column(name = "air_pressure")
	private double air_pressure;

	@Column(name = "humidity")
	private int humidity;

	@OneToMany(mappedBy = "weather", fetch = FetchType.EAGER)
	private List<Catch> catches;

	public Weather() {

	}
	
	public void addCatch(Catch theCatch) {
		
		if (catches == null) {
			catches = new ArrayList();
		}
		catches.add(theCatch);
	}
	
	public void removeCatch(Catch theCatch) {
		
		if (catches != null) {
			catches.remove(theCatch);
		}
		
	}

	public String getWeather_state_name() {
		return weather_state_name;
	}

	public void setWeather_state_name(String weather_state_name) {
		this.weather_state_name = weather_state_name;
	}

	public String getWeather_state_abbr() {
		return weather_state_abbr;
	}

	public void setWeather_state_abbr(String weather_state_abbr) {
		this.weather_state_abbr = weather_state_abbr;
	}

	public String getWind_direction_compass() {
		return wind_direction_compass;
	}

	public void setWind_direction_compass(String wind_direction_compass) {
		this.wind_direction_compass = wind_direction_compass;
	}

	public LocalDate getApplicable_date() {
		return applicable_date;
	}

	public void setApplicable_date(LocalDate applicable_date) {
		this.applicable_date = applicable_date;
	}

	public double getMin_temp() {
		return min_temp;
	}

	public void setMin_temp(double min_temp) {
		this.min_temp = min_temp;
	}

	public double getMax_temp() {
		return max_temp;
	}

	public void setMax_temp(double max_temp) {
		this.max_temp = max_temp;
	}

	public double getThe_temp() {
		return the_temp;
	}

	public void setThe_temp(double the_temp) {
		this.the_temp = the_temp;
	}

	public double getWind_speed() {
		return wind_speed;
	}

	public void setWind_speed(double wind_speed) {
		this.wind_speed = wind_speed;
	}

	public double getWind_direction() {
		return wind_direction;
	}

	public void setWind_direction(double wind_direction) {
		this.wind_direction = wind_direction;
	}

	public double getAir_pressure() {
		return air_pressure;
	}

	public void setAir_pressure(double air_pressure) {
		this.air_pressure = air_pressure;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Catch> getCatches() {
		return catches;
	}

	public void setCatches(List<Catch> catches) {
		this.catches = catches;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(air_pressure);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((applicable_date == null) ? 0 : applicable_date.hashCode());
		result = prime * result + ((catches == null) ? 0 : catches.hashCode());
		result = prime * result + humidity;
		result = prime * result + (int) (id ^ (id >>> 32));
		temp = Double.doubleToLongBits(max_temp);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(min_temp);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(the_temp);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((weather_state_abbr == null) ? 0 : weather_state_abbr.hashCode());
		result = prime * result + ((weather_state_name == null) ? 0 : weather_state_name.hashCode());
		temp = Double.doubleToLongBits(wind_direction);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((wind_direction_compass == null) ? 0 : wind_direction_compass.hashCode());
		temp = Double.doubleToLongBits(wind_speed);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Weather other = (Weather) obj;
		if (Double.doubleToLongBits(air_pressure) != Double.doubleToLongBits(other.air_pressure))
			return false;
		if (applicable_date == null) {
			if (other.applicable_date != null)
				return false;
		} else if (!applicable_date.equals(other.applicable_date))
			return false;
		if (catches == null) {
			if (other.catches != null)
				return false;
		} else if (!catches.equals(other.catches))
			return false;
		if (humidity != other.humidity)
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(max_temp) != Double.doubleToLongBits(other.max_temp))
			return false;
		if (Double.doubleToLongBits(min_temp) != Double.doubleToLongBits(other.min_temp))
			return false;
		if (Double.doubleToLongBits(the_temp) != Double.doubleToLongBits(other.the_temp))
			return false;
		if (weather_state_abbr == null) {
			if (other.weather_state_abbr != null)
				return false;
		} else if (!weather_state_abbr.equals(other.weather_state_abbr))
			return false;
		if (weather_state_name == null) {
			if (other.weather_state_name != null)
				return false;
		} else if (!weather_state_name.equals(other.weather_state_name))
			return false;
		if (Double.doubleToLongBits(wind_direction) != Double.doubleToLongBits(other.wind_direction))
			return false;
		if (wind_direction_compass == null) {
			if (other.wind_direction_compass != null)
				return false;
		} else if (!wind_direction_compass.equals(other.wind_direction_compass))
			return false;
		if (Double.doubleToLongBits(wind_speed) != Double.doubleToLongBits(other.wind_speed))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Weather [id=" + id + ", weather_state_name=" + weather_state_name + ", weather_state_abbr="
				+ weather_state_abbr + ", wind_direction_compass=" + wind_direction_compass + ", applicable_date="
				+ applicable_date + ", min_temp=" + min_temp + ", max_temp=" + max_temp + ", the_temp=" + the_temp
				+ ", wind_speed=" + wind_speed + ", wind_direction=" + wind_direction + ", air_pressure=" + air_pressure
				+ ", humidity=" + humidity + ", catches=" + catches + "]";
	}

	

}
