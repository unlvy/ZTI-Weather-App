package zti.weatherapi.weather.forecast;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import zti.weatherapi.db.model.OpenMeteoData;

/** Weather forecast controller. */
@CrossOrigin(origins = "https://weather-ui.onrender.com")
@RestController
@RequestMapping("/api/v1/forecast")
public class ForecastController {

  /**
   * Endpoint returning 7-day weather forecast for given location.
   *
   * @param longitude location latitude
   * @param latitude  location longitude
   * @return 7 day weather prediction
   */
  @GetMapping("/weather")
  public OpenMeteoData[] getWeatherForecast(
      @RequestParam(value = "latitude") double latitude,
      @RequestParam(value = "longitude") double longitude) {
    this.validateAll(latitude, longitude);
    return this.forecastService.getWeather(latitude, longitude);
  }

  /**
   * Endpoint returning 7-day temperature forecast for given location.
   *
   * @param longitude location latitude
   * @param latitude  location longitude
   * @return 7 day temperature prediction
   */
  @GetMapping("/temperature")
  public ShortForecastModel[] getTemperatureForecast(
      @RequestParam(value = "latitude") double latitude,
      @RequestParam(value = "longitude") double longitude) {
    this.validateAll(latitude, longitude);
    return this.forecastService.getTemperature(latitude, longitude);
  }

  /**
   * Endpoint returning 7-day humidity forecast for given location.
   *
   * @param longitude location latitude
   * @param latitude  location longitude
   * @return 7 day humidity prediction
   */
  @GetMapping("/humidity")
  public ShortForecastModel[] getHumidityForecast(
      @RequestParam(value = "latitude") double latitude,
      @RequestParam(value = "longitude") double longitude) {
    this.validateAll(latitude, longitude);
    return this.forecastService.getHumidity(latitude, longitude);
  }

  /**
   * Endpoint returning 7-day pressure forecast for given location.
   *
   * @param longitude location latitude
   * @param latitude  location longitude
   * @return 7 day pressure prediction
   */
  @GetMapping("/pressure")
  public ShortForecastModel[] getPressureForecast(
      @RequestParam(value = "latitude") double latitude,
      @RequestParam(value = "longitude") double longitude) {
    this.validateAll(latitude, longitude);
    return this.forecastService.getPressure(latitude, longitude);
  }

  /**
   * Endpoint returning 7-day precipitation forecast for given location.
   *
   * @param longitude location latitude
   * @param latitude  location longitude
   * @return 7 day precipitation prediction
   */
  @GetMapping("/precipitation")
  public ShortForecastModel[] getPrecipitationForecast(
      @RequestParam(value = "latitude") double latitude,
      @RequestParam(value = "longitude") double longitude) {
    this.validateAll(latitude, longitude);
    return this.forecastService.getPrecipitation(latitude, longitude);
  }

  /**
   * Endpoint returning 7-day cloudcover forecast for given location.
   *
   * @param longitude location latitude
   * @param latitude  location longitude
   * @return 7 day cloudcover prediction
   */
  @GetMapping("/cloudcover")
  public ShortForecastModel[] getCloudcoverForecast(
      @RequestParam(value = "latitude") double latitude,
      @RequestParam(value = "longitude") double longitude) {
    this.validateAll(latitude, longitude);
    return this.forecastService.getCloudcover(latitude, longitude);
  }

  /**
   * Endpoint returning 7-day windspeed forecast for given location.
   *
   * @param longitude location latitude
   * @param latitude  location longitude
   * @return 7 day windspeed prediction
   */
  @GetMapping("/windspeed")
  public ShortForecastModel[] getWindspeedForecast(
      @RequestParam(value = "latitude") double latitude,
      @RequestParam(value = "longitude") double longitude) {
    this.validateAll(latitude, longitude);
    return this.forecastService.getWindspeed(latitude, longitude);
  }

  private void validateAll(double latitude, double longitude) {
    if (!this.validateLatitudeLongitudeRange(latitude, longitude)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong latitude or longitude.");
    }
  }

  private Boolean validateLatitudeLongitudeRange(double latitude, double longitude) {
    return (latitude > -90.0 && latitude < 90.0
        && longitude > -180.0 && longitude < 180.0);
  }

  @Autowired
  private ForecastService forecastService;
}
