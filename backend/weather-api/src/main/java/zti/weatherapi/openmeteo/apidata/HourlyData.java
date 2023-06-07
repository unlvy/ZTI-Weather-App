package zti.weatherapi.openmeteo.apidata;

import java.util.List;

/** OpenMeteo hourly data model. */
public class HourlyData {

  public List<String> getTime() {
    return this.time;
  }

  public List<Double> getTemperature_2m() {
    return this.temperature_2m;
  }

  public List<Integer> getRelativehumidity_2m() {
    return this.relativehumidity_2m;
  }

  public List<Double> getSurface_pressure() {
    return this.surface_pressure;
  }

  public List<Double> getPrecipitation() {
    return this.precipitation;
  }

  public List<Integer> getCloudcover() {
    return this.cloudcover;
  }

  public List<Integer> getWindspeed_10m() {
    return this.windspeed_10m;
  }

  private List<String> time;
  private List<Double> temperature_2m;
  private List<Integer> relativehumidity_2m;
  private List<Double> surface_pressure;
  private List<Double> precipitation;
  private List<Integer> cloudcover;
  private List<Integer> windspeed_10m;

}
