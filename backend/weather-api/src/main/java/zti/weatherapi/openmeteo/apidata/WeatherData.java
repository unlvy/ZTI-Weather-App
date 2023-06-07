package zti.weatherapi.openmeteo.apidata;

public class WeatherData {

  public double getLatitude() {
    return this.latitude;
  }

  public double getLongitude() {
    return this.longitude;
  }

  public double getGenerationtime_ms() {
    return this.generationtime_ms;
  }

  public int getUtc_offset_seconds() {
    return this.utc_offset_seconds;
  }

  public String getTimezone() {
    return this.timezone;
  }

  public String getTimezone_abbreviation() {
    return this.timezone_abbreviation;
  }

  public double getElevation() {
    return this.elevation;
  }

  public HourlyUnits getHourly_units() {
    return this.hourly_units;
  }

  public HourlyData getHourly() {
    return this.hourly;
  }

  private double latitude;
  private double longitude;
  private double generationtime_ms;
  private int utc_offset_seconds;
  private String timezone;
  private String timezone_abbreviation;
  private double elevation;
  private HourlyUnits hourly_units;
  private HourlyData hourly;

}