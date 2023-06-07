package zti.weatherapi.logger;

/** Logger class. */
public class Logger {
  
  /** 
   * Log data to standard output. 
   *
   * @param type type of log
   * @param data data
   */
  public void log(String type, String data) {
    System.out.println(type + ": " + data);
  }
}
