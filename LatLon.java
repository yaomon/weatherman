/*
Kyle Sun
*/

package weatherman;

import java.net.*;
import java.io.*;
import org.json.*;

/** 
 * LatLon takes in a city and can return the latitude and longitude location of that city. 
 */
public final class LatLon {
    String city;
    JSONObject obj;
    double lat;
    double lon;
    public LatLon(String city) {
        setCity(city);
    }
    
    /**
     * Takes in a string parameter for the city and stores its information.    
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
        if ((this.city != null) && (this.city.length() > 0)) {
           this.city = this.city.replace(' ', '%');
           try {
           	//find the lat and lon by chosen city
               String mapURL = "https://maps.googleapis.com/maps/api/geocode/json?address=" + this.city;
               URL url = new URL(mapURL);
               URLConnection wc = url.openConnection();
               BufferedReader in = new BufferedReader(
               new InputStreamReader(
               wc.getInputStream()));
               String inputLine;
               String json = "";
               // Read response and add to string
               while ((inputLine = in.readLine()) != null) {
                   json = json + inputLine;
               }
               // Parse JSON
               obj = new JSONObject(json);
               in.close();
               JSONArray resultArr = obj.getJSONArray("results");
               lat = resultArr.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
               lon = resultArr.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng"); 
           } catch (IOException| JSONException i) {
               System.out.println(i);
               System.exit(1);
           }
       }
    }
     /**
     * Return the latitude of the city as a double.    
     * @return 
     */
    public double getLat() {
        return lat;
    }
    /**
     * Return the longitude of the city as a double.    
     * @return 
     */
    public double getLon() {
        return lon;    
    }

}
