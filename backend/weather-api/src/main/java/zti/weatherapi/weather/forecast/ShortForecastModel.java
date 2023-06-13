package zti.weatherapi.weather.forecast;

/** Short forecast record model. */
public record ShortForecastModel(int latitude, int longitude, String time, double value) {

  /**
   * Record constructor.
   *
   * @param latitude  record latitude
   * @param longitude record longitude
   * @param time      record time
   * @param value     record value
   */
  public ShortForecastModel(int latitude, int longitude, String time, double value) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.time = time;
    this.value = value;
  }
}
