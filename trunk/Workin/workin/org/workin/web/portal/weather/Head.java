package org.workin.web.portal.weather;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 */
public class Head {
	private String unitTemperature;//head.ut
	private String unitDistance;//head.ud
	private String unitSpeed;//head.us
	private String unitPressure;//head.up

	public String getUnitTemperature() {
		return unitTemperature;
	}

	public void setUnitTemperature(String unitTemperature) {
		this.unitTemperature = unitTemperature;
	}

	public String getUnitDistance() {
		return unitDistance;
	}

	public void setUnitDistance(String unitDistance) {
		this.unitDistance = unitDistance;
	}

	public String getUnitSpeed() {
		return unitSpeed;
	}

	public void setUnitSpeed(String unitSpeed) {
		this.unitSpeed = unitSpeed;
	}

	public String getUnitPressure() {
		return unitPressure;
	}

	public void setUnitPressure(String unitPressure) {
		this.unitPressure = unitPressure;
	}

}
