package zti.weatherapi.openmeteo.apidata;

/** OpenMeteo hourly units model. */
public class HourlyUnits {

  public String getTime() {
    return this.time;
  }

  public String getTemperature_2m() {
    return this.temperature_2m;
  }

  public String getRelativehumidity_2m() {
    return this.relativehumidity_2m;
  }

  public String getSurface_pressure() {
    return this.surface_pressure;
  }

  public String getPrecipitation() {
    return this.precipitation;
  }

  public String getCloudcover() {
    return this.cloudcover;
  }

  public String getWindspeed_10m() {
    return this.windspeed_10m;
  }

  private String time;
  private String temperature_2m;
  private String relativehumidity_2m;
  private String surface_pressure;
  private String precipitation;
  private String cloudcover;
  private String windspeed_10m;
}
