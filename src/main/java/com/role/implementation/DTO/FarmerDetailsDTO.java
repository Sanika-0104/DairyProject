package com.role.implementation.DTO;

import org.springframework.stereotype.Component;

@Component
public class FarmerDetailsDTO {
	private Double milkUnitsPerDay=0.0;

	public Double getMilkUnitsPerDay() {
		return milkUnitsPerDay;
	}

	public void setMilkUnitsPerDay(Double milkUnitsPerDay) {
		this.milkUnitsPerDay = milkUnitsPerDay;
	}
	
	
}
