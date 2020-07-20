package com.tomankiewicz.rafal.castawayspringboot.analysis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tomankiewicz.rafal.castawayspringboot.entity.Catch;

@Component
public class Tooltip{

	private List<CatchProperties> tooltip;

	public List<CatchProperties> prepareTooltipFrom(List<Catch> catchList, List<Object> dataList){
		
		tooltip = new ArrayList<Tooltip.CatchProperties>(catchList.size());
		CatchProperties prop = null;
		for (int i = 0; i < catchList.size(); i++) {
			prop = new CatchProperties(catchList.get(i), dataList.get(i));
			tooltip.add(prop);
		}
		
		return tooltip;
	}

	public class CatchProperties{
		
		private Double y;
		private String species;
		private String length;
		private String weigth;
		
		private CatchProperties(Catch theCatch, Object dataObject) {
			this.y = Double.valueOf(dataObject.toString());
			this.species = theCatch.getSpecies().getDisplayValue();
			this.length = theCatch.getLength().toString() + " cm";
			this.weigth = theCatch.getWeigth().toString() + " kg";
		}

		public String getSpecies() {
			return species;
		}

		public String getLength() {
			return length;
		}

		public String getWeigth() {
			return weigth;
		}

		public Double getY() {
			return y;
		}
		
		
		
		
	}
}