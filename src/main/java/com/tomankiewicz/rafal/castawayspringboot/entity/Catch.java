package com.tomankiewicz.rafal.castawayspringboot.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.tomankiewicz.rafal.castawayspringboot.species.Fish;
import com.tomankiewicz.rafal.castawayspringboot.validation.ValidDate;

@Entity
@Table(name = "catch")
public class Catch {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "species")
	@Enumerated(EnumType.STRING)
	@NotNull(message = "Choose species")
	private Fish species;

	@Column(name = "length")
	@DecimalMin(value = "0.0", message = "Enter correct length")
	@DecimalMax(value = "300.0", message = "Enter correct length")
	@NotNull(message = "Length is required")
	private BigDecimal length;

	@Column(name = "weigth")
	@DecimalMin(value = "0.0", message = "Enter correct weight")
	@DecimalMax(value = "300.0", message = "Enter correct weight")
	@NotNull(message = "Weight is required")
	private BigDecimal weigth;

	@Column(name = "catch_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@ValidDate
	private LocalDate date;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "angler_username")
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
	name="catch_weather",
	joinColumns=@JoinColumn(name="catch_id"),
	inverseJoinColumns=@JoinColumn(name="weather_id"))
	private Weather weather;

	@ManyToMany(fetch=FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JoinTable(
	name="competition_catch",
	joinColumns=@JoinColumn(name="catch_id"),
	inverseJoinColumns=@JoinColumn(name="competition_id")
	)
	private List<Competition> competitions;
	
	public Catch() {
	}

	public Catch(int id, Fish species, BigDecimal length, BigDecimal weigth, User user, Weather weather) {
		this.id = id;
		this.species = species;
		this.length = length;
		this.weigth = weigth;
		this.user = user;
		this.weather = weather;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Fish getSpecies() {
		return species;
	}

	public void setSpecies(Fish species) {
		this.species = species;
	}

	public BigDecimal getLength() {
		return length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	public BigDecimal getWeigth() {
		return weigth;
	}

	public void setWeigth(BigDecimal weigth) {
		this.weigth = weigth;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Weather getWeather() {
		return weather;
	}

	public void setWeather(Weather weather) {
		this.weather = weather;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	
	public List<Competition> getCompetitions() {
		return competitions;
	}

	public void setCompetitions(List<Competition> competitions) {
		this.competitions = competitions;
	}



	@Override
	public String toString() {
		return "Catch [id=" + id + ", species=" + species + ", length=" + length + ", weigth=" + weigth + ", date="
				+ date + ", user=" + user + ", weather=" + weather + ", competitions=" + competitions + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + id;
		result = prime * result + ((length == null) ? 0 : length.hashCode());
		result = prime * result + ((species == null) ? 0 : species.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((weather == null) ? 0 : weather.hashCode());
		result = prime * result + ((weigth == null) ? 0 : weigth.hashCode());
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
		Catch other = (Catch) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id != other.id)
			return false;
		if (length == null) {
			if (other.length != null)
				return false;
		} else if (!length.equals(other.length))
			return false;
		if (species == null) {
			if (other.species != null)
				return false;
		} else if (!species.equals(other.species))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (weather == null) {
			if (other.weather != null)
				return false;
		} else if (!weather.equals(other.weather))
			return false;
		if (weigth == null) {
			if (other.weigth != null)
				return false;
		} else if (!weigth.equals(other.weigth))
			return false;
		return true;
	}
	
	
	/** Calculate the number of points for the particular catch using formula:
	 * 
	 * ([CATCH_LENGTH] - [MINIMAL_SPECIES_LENGTH]) * [POINT_FACTOR] * [WEIGTH]
	 * 
	 * Points are awarded only if [CATCH_LENGTH] >= [MINIMAL_SPECIES_LENGTH]
	 */
	
	public int calculatePoints() {
		
		// Retrieve the point factor characteristic to species:
		int pointFactor = this.getSpecies().getPointFactor();
		
		// Retrieve minimal legal length characteristic to species:
		BigDecimal minimalLength = BigDecimal.valueOf(this.getSpecies().getMinLength());
		
		// Calculate if theCatch is of legal length:
		double lengthOverMinimalLength = this.getLength().subtract(minimalLength).doubleValue();
		
		// Retrieve theCatch weight (for clarity in the final formula:
		double weigth = this.getWeigth().doubleValue();
		
		//Retrieve theCatch length (for clarity in the final formula:
		double length = this.getLength().doubleValue();
		
		if (lengthOverMinimalLength >= 0) {
			
			return (int)((lengthOverMinimalLength * pointFactor * weigth) + length);
		
		} else {
			
			return 0;
		}
		
	}
	
	public String formatDate() {
		
		return this.getDate().toString().replaceAll("-", "/");
	}

}
