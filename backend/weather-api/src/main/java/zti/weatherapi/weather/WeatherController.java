package zti.weatherapi.weather;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import zti.weatherapi.db.model.OpenMeteoData;
import zti.weatherapi.logger.Logger;

/** Historical weather and weather forecast controller. */
@RestController
public class WeatherController {

  /**
   * Endpoint returning 7-day weather forecast for given location.
   *
   * @param longitude location latitude
   * @param latitude  location longitude
   * @return 7 day weather prediction
   */
  @GetMapping("/v1/forecast")
  public OpenMeteoData[] getWeatherForecast(
      @RequestParam(value = "latitude") double latitude,
      @RequestParam(value = "longitude") double longitude) {
    if (!this.validateLatitudeLongitudeRange(latitude, longitude)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong latitude or longitude.");
    }
    return this.weatherService.getWeatherForecast(latitude, longitude);
  }

  /**
   * Endpoint returning historical weather for given location.
   *
   * @param longitude location latitude
   * @param latitude  location longitude
   * @param startDate date from
   * @param endDate   date to
   * @return historical weather
   */
  @GetMapping("/v1/historical")
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
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    try {
      Date start = dateFormat.parse(startDate);
      Date end = dateFormat.parse(endDate);
      if (start.before(end)
          && start.before(
              // 8 day ago
              new Date(new Date().getTime() - (8 * 1000 * 60 * 60 * 24)))
          // max 30 days
          && TimeUnit.DAYS.convert(
              Math.abs(end.getTime() - start.getTime()), TimeUnit.MILLISECONDS) < 30) {
        return true;
      }
    } catch (Exception e) {
      this.logger.log("ERROR", e.getMessage());
    }
    return false;
  }

  @Autowired
  private WeatherService weatherService;
  private final Logger logger = new Logger();

}
