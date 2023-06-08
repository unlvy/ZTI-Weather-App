package zti.weatherapi.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import zti.weatherapi.openmeteo.OpenMeteoData;

/** Weather forecast controller. */
@RestController
public class WeatherController {

  /**
   * Endpoint returning 7-day weather forecast for given location.
   *
   * @param longitude location latitude
   * @param latitude  location longitude
   * @return 7 day weather prediction
   */
  @GetMapping("/forecast")
  public OpenMeteoData[] getWeatherForecast(
      @RequestParam(value = "latitude") double latitude,
      @RequestParam(value = "longitude") double longitude) {
    if (!this.validateLatitudeLongitudeRange(latitude, longitude)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong latitude or longitude.");
    }
    return this.weatherService.getWeatherForecast(latitude, longitude);
  }

  /**
   * Endpoint returning 7-day weather forecast for given location.
   *
   * @param longitude location latitude
   * @param latitude  location longitude
   * @return 7 day weather prediction
   */
  @GetMapping("/historical")
  public OpenMeteoData[] getHistoricalWeather(
      @RequestParam(value = "latitude") double latitude,
      @RequestParam(value = "longitude") double longitude,
      @RequestParam(value = "startDate") String startDate,
      @RequestParam(value = "endDate") String endDate) {
    if (!this.validateLatitudeLongitudeRange(latitude, longitude)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong latitude or longitude.");
    }
    if (!this.validateStartEndDate(startDate, endDate)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong date range.");
    }
    return this.weatherService.getHistoricalWeather(latitude, longitude, startDate, endDate);
  }

  private Boolean validateLatitudeLongitudeRange(double latitude, double longitude) {
    return (latitude > -90.0 && latitude < 90.0
        && longitude > -180.0 && longitude < 180.0);
  }

  private Boolean validateStartEndDate(String startDate, String endDate) {
    return true;
  }

  @Autowired
  private WeatherService weatherService;

}
