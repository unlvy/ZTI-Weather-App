package zti.weatherapi.weather.historical;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import zti.weatherapi.db.model.OpenMeteoData;

/** Historical weather and weather forecast controller. */
@CrossOrigin(origins = "https://weather-ui.onrender.com")
@RestController
@RequestMapping("/api/v1/historical")
public class HistoricalController {

  /**
   * Endpoint returning historical weather for given location.
   *
   * @param longitude location latitude
   * @param latitude  location longitude
   * @param startDate date from
   * @param endDate   date to
   * @return historical weather
   */
  @GetMapping("/weather")
  public OpenMeteoData[] getHistoricalWeather(
      @RequestParam(value = "latitude") double latitude,
      @RequestParam(value = "longitude") double longitude,
      @RequestParam(value = "startDate") String startDate,
      @RequestParam(value = "endDate") String endDate) {
    this.validateAll(latitude, longitude, startDate, endDate);
    return this.historicalService.getWeather(latitude, longitude, startDate, endDate);
  }

  /**
   * Endpoint returning historical temperature for given location.
   *
   * @param longitude location latitude
   * @param latitude  location longitude
   * @param startDate date from
   * @param endDate   date to
   * @return historical temperature
   */
  @GetMapping("/temperature")
  public ShortHistoricalModel[] getHistoricalTemperature(
      @RequestParam(value = "latitude") double latitude,
      @RequestParam(value = "longitude") double longitude,
      @RequestParam(value = "startDate") String startDate,
      @RequestParam(value = "endDate") String endDate) {
    this.validateAll(latitude, longitude, startDate, endDate);
    return this.historicalService.getTemperature(latitude, longitude, startDate, endDate);
  }

  /**
   * Endpoint returning historical humidity for given location.
   *
   * @param longitude location latitude
   * @param latitude  location longitude
   * @param startDate date from
   * @param endDate   date to
   * @return historical humidity
   */
  @GetMapping("/humidity")
  public ShortHistoricalModel[] getHistoricalHumidity(
      @RequestParam(value = "latitude") double latitude,
      @RequestParam(value = "longitude") double longitude,
      @RequestParam(value = "startDate") String startDate,
      @RequestParam(value = "endDate") String endDate) {
    this.validateAll(latitude, longitude, startDate, endDate);
    return this.historicalService.getHumidity(latitude, longitude, startDate, endDate);
  }

  /**
   * Endpoint returning historical pressure for given location.
   *
   * @param longitude location latitude
   * @param latitude  location longitude
   * @param startDate date from
   * @param endDate   date to
   * @return historical pressure
   */
  @GetMapping("/pressure")
  public ShortHistoricalModel[] getHistoricalPressure(
      @RequestParam(value = "latitude") double latitude,
      @RequestParam(value = "longitude") double longitude,
      @RequestParam(value = "startDate") String startDate,
      @RequestParam(value = "endDate") String endDate) {
    this.validateAll(latitude, longitude, startDate, endDate);
    return this.historicalService.getPressure(latitude, longitude, startDate, endDate);
  }

  /**
   * Endpoint returning historical precipitation for given location.
   *
   * @param longitude location latitude
   * @param latitude  location longitude
   * @param startDate date from
   * @param endDate   date to
   * @return historical precipitation
   */
  @GetMapping("/precipitation")
  public ShortHistoricalModel[] getHistoricalPrecipitation(
      @RequestParam(value = "latitude") double latitude,
      @RequestParam(value = "longitude") double longitude,
      @RequestParam(value = "startDate") String startDate,
      @RequestParam(value = "endDate") String endDate) {
    this.validateAll(latitude, longitude, startDate, endDate);
    return this.historicalService.getPrecipitation(latitude, longitude, startDate, endDate);
  }

  /**
   * Endpoint returning historical cloudcover for given location.
   *
   * @param longitude location latitude
   * @param latitude  location longitude
   * @param startDate date from
   * @param endDate   date to
   * @return historical cloudcover
   */
  @GetMapping("/cloudcover")
  public ShortHistoricalModel[] getHistoricalCloudcover(
      @RequestParam(value = "latitude") double latitude,
      @RequestParam(value = "longitude") double longitude,
      @RequestParam(value = "startDate") String startDate,
      @RequestParam(value = "endDate") String endDate) {
    this.validateAll(latitude, longitude, startDate, endDate);
    return this.historicalService.getCloudcover(latitude, longitude, startDate, endDate);
  }

  /**
   * Endpoint returning historical windspeed for given location.
   *
   * @param longitude location latitude
   * @param latitude  location longitude
   * @param startDate date from
   * @param endDate   date to
   * @return historical windspeed
   */
  @GetMapping("/windspeed")
  public ShortHistoricalModel[] getHistoricalWindspeed(
      @RequestParam(value = "latitude") double latitude,
      @RequestParam(value = "longitude") double longitude,
      @RequestParam(value = "startDate") String startDate,
      @RequestParam(value = "endDate") String endDate) {
    this.validateAll(latitude, longitude, startDate, endDate);
    return this.historicalService.getWindspeed(latitude, longitude, startDate, endDate);
  }

  private void validateAll(double latitude, double longitude, String startDate, String endDate) {
    if (!this.validateLatitudeLongitudeRange(latitude, longitude)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong latitude or longitude.");
    }
    if (!this.validateStartEndDate(startDate, endDate)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong date range.");
    }
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
    } catch (Exception ignored) {
      return false;
    }
    return false;
  }

  @Autowired
  private HistoricalService historicalService;
}
