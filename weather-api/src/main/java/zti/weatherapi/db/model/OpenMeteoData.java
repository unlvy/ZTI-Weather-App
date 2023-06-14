package zti.weatherapi.db.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/** Weather data. */
@MappedSuperclass
public class OpenMeteoData {

  protected OpenMeteoData() {}

  /**
   * Constructor.
   *
   * @param latitude      latitude as int
   * @param longitude     longitude as int
   * @param time          time string
   * @param temperature   temperature in celsius
   * @param humidity      humidity percentage
   * @param pressure      pressure in hPa
   * @param precipitation precipitation
   * @param cloudcover    total cloudcover percentage
   * @param windspeed     windspeed m/s
   */
  public OpenMeteoData(int latitude, int longitude, String time, double temperature,
      int humidity, double pressure, double precipitation, int cloudcover, int windspeed) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.time = time;
    this.temperature = temperature;
    this.humidity = humidity;
    this.pressure = pressure;
    this.precipitation = precipitation;
    this.cloudcover = cloudcover;
    this.windspeed = windspeed;
  }

  /**
   * Copy constructor.
   *
   * @param omd instantce to copy
   */
  public OpenMeteoData(OpenMeteoData omd) {
    this.latitude = omd.latitude;
    this.longitude = omd.longitude;
    this.time = omd.time;
    this.temperature = omd.temperature;
    this.humidity = omd.humidity;
    this.pressure = omd.pressure;
    this.precipitation = omd.precipitation;
    this.cloudcover = omd.cloudcover;
    this.windspeed = omd.windspeed;
  }

  public int getLongitude() {
    return this.longitude;
  }

  public void setLongitude(int longitude) {
    this.longitude = longitude;
  }

  public int getLatitude() {
    return this.latitude;
  }

  public void setLatitude(int latitude) {
    this.latitude = latitude;
  }

  public String getTime() {
    return this.time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public double getTemperature() {
    return this.temperature;
  }

  public void setTemperature(double temperature) {
    this.temperature = temperature;
  }

  public int getHumidity() {
    return this.humidity;
  }

  public void setHumidity(int humidity) {
    this.humidity = humidity;
  }

  public double getPressure() {
    return this.pressure;
  }

  public void setPressure(double pressure) {
    this.pressure = pressure;
  }

  public double getPrecipitation() {
    return this.precipitation;
  }

  public void setPrecipitation(double precipitation) {
    this.precipitation = precipitation;
  }

  public int getCloudcover() {
    return this.cloudcover;
  }

  public void setCloudcover(int cloudcover) {
    this.cloudcover = cloudcover;
  }

  public int getWindspeed() {
    return this.windspeed;
  }

  public void setWindspeed(int windspeed) {
    this.windspeed = windspeed;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;
  protected int latitude;
  protected int longitude;
  protected String time;
  protected double temperature;
  protected int humidity;
  protected double pressure;
  protected double precipitation;
  protected int cloudcover;
  protected int windspeed;

}
