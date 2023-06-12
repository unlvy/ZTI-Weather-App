package zti.weatherapi.openmeteo;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import zti.weatherapi.db.model.OpenMeteoData;
import zti.weatherapi.logger.Logger;
import zti.weatherapi.openmeteo.apidata.WeatherData;

/**
 * Service responsible for communication with open meteo API
 * (https://open-meteo.com).
 */
@Service
public class OpenMeteoService {

  /**
   * Get weather forecast for given location.
   *
   * @param latitude  latitude
   * @param longitude longitude
   * 
   * @return weather forecast for next 7 days
   */
  public ArrayList<OpenMeteoData> getWeatherForecast(
      double latitude, double longitude) {
    int lat = (int) latitude;
    int lon = (int) longitude;
    String uri = String.format(Locale.US,
        API_FORECAST_BASE_URI, lat, lon);
    return this.callOpenMeteoApi(uri);
  }

  /**
   * Get historical weather data for given location in given time period.
   *
   * @param latitude  latitude
   * @param longitude longitude
   * @param startDate start date
   * @param endDate   end date
   * @return historical weather data
   */
  public ArrayList<OpenMeteoData> getHistoricalWeather(
      double latitude, double longitude, String startDate, String endDate) {
    int lat = (int) latitude;
    int lon = (int) longitude;
    String uri = String.format(Locale.US,
        API_ARCHIVE_BASE_URI, lat, lon, startDate, endDate);
    return this.callOpenMeteoApi(uri);
  }

  /**
   * Call open meteo API and process retrieved data.
   *
   * @param uri call uri
   * @return weather data
   */
  private ArrayList<OpenMeteoData> callOpenMeteoApi(String uri) {
    String response = webClient.get().uri(uri).retrieve().bodyToMono(String.class).block();

    WeatherData weatherData = null;
    try {
      weatherData = this.objectMapper.readValue(response, WeatherData.class);
    } catch (Exception e) {
      this.logger.log("ERROR", e.getMessage());
      return null;
    }

    int latitude = (int) weatherData.getLatitude();
    int longitude = (int) weatherData.getLongitude();
    List<String> time = weatherData.getHourly().getTime();
    List<Double> temperature = weatherData.getHourly().getTemperature_2m();
    List<Integer> humidity = weatherData.getHourly().getRelativehumidity_2m();
    List<Double> surfacePressure = weatherData.getHourly().getSurface_pressure();
    List<Double> precipitation = weatherData.getHourly().getPrecipitation();
    List<Integer> cloudcover = weatherData.getHourly().getCloudcover();
    List<Integer> windspeed = weatherData.getHourly().getWindspeed_10m();

    int n = time.size();
    ArrayList<OpenMeteoData> ret = new ArrayList<OpenMeteoData>(n);
    for (int i = 0; i < n; i++) {
      ret.add(new OpenMeteoData(
          latitude, longitude,
          time.get(i), temperature.get(i), humidity.get(i), surfacePressure.get(i),
          precipitation.get(i), cloudcover.get(i), windspeed.get(i)));
    }

    return ret;
  }

  private final ObjectMapper objectMapper = new ObjectMapper();
  private final WebClient webClient = WebClient.create();
  private final String API_ARCHIVE_BASE_URI = "https://archive-api.open-meteo.com/v1/archive?latitude=%d&longitude=%d&start_date=%s&end_date=%s&hourly=temperature_2m,relativehumidity_2m,surface_pressure,precipitation,cloudcover,windspeed_10m";
  private final String API_FORECAST_BASE_URI = "https://api.open-meteo.com/v1/forecast?latitude=%d&longitude=%d&hourly=temperature_2m,relativehumidity_2m,precipitation,surface_pressure,cloudcover,windspeed_10m";
  private final Logger logger = new Logger();
}
