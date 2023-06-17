export interface WeatherData {
  latitude: number,
  longitude: number,
  time: string,
  temperature: number,
  humidity: number,
  pressure: number,
  precipitation: number,
  cloudcover: number,
  windspeed: number
}

export interface ShortWeatherData {
  latitude: number,
  longitude: number,
  time: string,
  value: number
}