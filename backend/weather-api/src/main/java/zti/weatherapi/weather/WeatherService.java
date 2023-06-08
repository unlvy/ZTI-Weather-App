package zti.weatherapi.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zti.weatherapi.openmeteo.OpenMeteoData;
import zti.weatherapi.openmeteo.OpenMeteoService;

/** Nothing. */
@Service
public class WeatherService {

  /**
   * Get 7 day weather forecast for given location.
   *
   * @param latitude  location latitude
   * @param longitude location longitude
   * @return 7 day weather forecast
   */
  public OpenMeteoData[] getWeatherForecast(double latitude, double longitude) {

    // 1. Check if forecast for that location for that time exists in db
    // yes -> return straight from db
    // no -> get from openMeteo, and update database

    return this.openMeteoService.getWeatherForecast(latitude, longitude)
        .toArray(OpenMeteoData[]::new);
  }

  /**
   * Get historical weather for given location.
   *
   * @param latitude  location latitude
   * @param longitude location longitude
   * @param startDate date from
   * @param endDate   date to
   * @return historical weather
   */
  public OpenMeteoData[] getHistoricalWeather(
      double latitude, double longitude, String startDate, String endDate
  ) {
    return this.openMeteoService.getHistoricalWeather(latitude, longitude, startDate, endDate)
        .toArray(OpenMeteoData[]::new);
  }

  @Autowired
  private OpenMeteoService openMeteoService;

}
