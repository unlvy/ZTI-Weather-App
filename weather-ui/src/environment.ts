const backendUrl = "https://weather-api-ghsw.onrender.com/api/v1"

export const environment = {
  forecastTemperature: `${backendUrl}/forecast/temperature`,
  forecastHumidity: `${backendUrl}/forecast/humidity`,
  forecastPressure: `${backendUrl}/forecast/pressure`,
  forecastPrecipitation: `${backendUrl}/forecast/precipitation`,
  forecastCloudcover: `${backendUrl}/forecast/cloudcover`,
  forecastWindspeed: `${backendUrl}/forecast/windspeed`,
  forecastAll: `${backendUrl}/forecast/weather`,
  historicalTemperature: `${backendUrl}/historical/temperature`,
  historicalHumidity: `${backendUrl}/historical/humidity`,
  historicalPressure: `${backendUrl}/historical/pressure`,
  historicalPrecipitation: `${backendUrl}/historical/precipitation`,
  historicalCloudcover: `${backendUrl}/historical/cloudcover`,
  historicalWindspeed: `${backendUrl}/historical/windspeed`,
  historicalAll: `${backendUrl}/historical/weather`
}