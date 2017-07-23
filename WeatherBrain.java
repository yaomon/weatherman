/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weatherman;
/** *
 * @author Kyle Sun
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONException;
import org.json.JSONObject;

/**
* GetWeather is an object that stores weather information. User can request weather information
* User can request Weather information, or set the area to get the weather information from based on the
* Latitude and Longitude given, or based on the city given. Uses DarkSky API specific properties.
*/
public final class WeatherBrain {
   JSONObject jsonObj;
   private static String key;   
   /**
   * Create a GetWeather with the given API key for DarkSky.
     * @param wKey
   */
   public WeatherBrain(String wKey) {
      key = wKey;
      try {
         setWeather(0, 0);
      } catch (IOException | JSONException e) {
         System.out.println(e);
         System.exit(1);
      }
   }
   /**
   * Sets the weather info to the location based on given double parameters for
   * Latitude and Longitude.
     * @param lat
     * @param lon
   */
   
   public void setWeather(double lat, double lon) throws IOException, JSONException{
      String weaUrl = "https://api.darksky.net/forecast/" + key + "/" + lat + "," + lon;
      URL url = new URL(weaUrl);
      URLConnection wc = url.openConnection();
      BufferedReader in = new BufferedReader(
      new InputStreamReader(wc.getInputStream()));
      String inputLine;
      String json = "";
      // Read response and add to string
      while ((inputLine = in.readLine()) != null) {
         json = json + inputLine;
      }
      in.close();
      jsonObj = new JSONObject(json);
   }
   
   /*
   * Sets the weather info to the location based on given string parameter for
   * City. 
   */
   public void setWeatherCity(String city) {
    LatLon coord = new LatLon(city);
    try {
        setWeather(coord.getLat(), coord.getLon());
    } catch (IOException | JSONException e) {
        System.out.println(e);
        System.exit(1);
    }
   }
   
   /**
   * Returns the current condition as a String based on DarkSky's formatting.
     * @return 
   */
   public String getCondition() throws JSONException{
      return jsonObj.getJSONObject("currently").getString("icon");
   }
   
   /**
   * Returns information displayed as a Double based on DarkSky's formatting
   * and the given string of information requested.
     * @param info
     * @return 
   */
   public double getDoubleInfo(String info) throws JSONException{
      return jsonObj.getJSONObject("currently").getDouble(info);
   }
   
   /**
   * Returns information displayed as an Integer based on DarkSky's formatting
   * and the given string of information requested.
     * @param info
     * @return 
   */
   public int getIntInfo(String info) throws JSONException{
      return jsonObj.getJSONObject("currently").getInt(info);
   }
}