package zti.weatherapi.openmeteo;

/** Weather data. */
public record OpenMeteoData(
    String time,
    double temperature,
    int humidity,
    double pressure,
    double precipitation,
    int cloudcover,
    int windspeed
) { }