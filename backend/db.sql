CREATE TABLE historical (
  id integer,
  longitude smallint,
  latitude smallint,
  time varchar(20),
  temperature real,
  humidity smallint,
  pressure real,
  precipitation real,
  cloudcover smallint,
  windspeed smallint
);

CREATE TABLE forecast (
  id integer,
  longitude smallint,
  latitude smallint,
  time varchar(20),
  temperature real,
  humidity smallint,
  pressure real,
  precipitation real,
  cloudcover smallint,
  windspeed smallint
);

CREATE TABLE forecast_update (
  longitude smallint,
  latitude smallint,
  updated timestamp
);

CREATE OR REPLACE FUNCTION check_historical_record(latitude integer, longitude integer, record_time varchar(20))
RETURNS boolean AS $$
BEGIN
  RETURN EXISTS (
    SELECT 1 FROM historical h
    WHERE h.latitude = check_historical_record.latitude
      AND h.longitude = check_historical_record.longitude
      AND h.time LIKE '%' || check_historical_record.record_time || '%'
    LIMIT 1
  );
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION check_update_forecast(latitude integer, longitude integer)
RETURNS boolean AS $$
DECLARE
  upd timestamp;
  difference integer;
BEGIN
  SELECT updated 
  FROM forecast_update fu 
  INTO upd
  WHERE fu.latitude = check_update_forecast.latitude 
  AND fu.longitude = check_update_forecast.longitude;
  IF upd IS NULL THEN
    RETURN TRUE;
  END IF;
  SELECT EXTRACT(EPOCH FROM (NOW() - upd))
  INTO difference;
  RETURN difference > 3600;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION pre_update_forecast(latitude integer, longitude integer)
RETURNS void AS $$
BEGIN
  DELETE FROM forecast_update fu
  WHERE fu.latitude = pre_update_forecast.latitude
    AND fu.longitude = pre_update_forecast.longitude;
  DELETE FROM forecast fo
  WHERE fo.latitude = pre_update_forecast.latitude
    AND fo.longitude = pre_update_forecast.longitude;
  INSERT INTO forecast_update
  VALUES (pre_update_forecast.latitude, pre_update_forecast.longitude, NOW());
END;
$$ LANGUAGE plpgsql;
