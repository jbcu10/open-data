package dev.jbcu10.opendata.controller;

 import dev.jbcu10.opendata.utils.CSVtoJSON;
 import dev.jbcu10.opendata.utils.Scraped;
 import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;

 import java.io.IOException;
 import java.util.LinkedList;
 import java.util.List;
 import java.util.Map;
 import java.util.logging.Logger;


@RestController
@RequestMapping("/api/society")
public class SocietyResource {

    Logger logger = Logger.getLogger(this.getClass().getName());
    OkHttpClient client = new OkHttpClient();
    public final CSVtoJSON csVtoJSON;
    public final Scraped scraped;

    public SocietyResource(CSVtoJSON csVtoJSON, Scraped scraped) {
        this.csVtoJSON = csVtoJSON;
        this.scraped = scraped;
    }

    @GetMapping(value = "/csv", params = {"file"},produces = "application/json")
    @ResponseBody
    public ResponseEntity csvToJSON(@RequestParam String file) throws IOException{
               Request request = new Request.Builder()
                    .url(buildURL(file))
                    .build();
            Response response = client.newCall(request).execute();
        return ResponseEntity.ok(csVtoJSON.convert(response.body().string()));
    }
    @GetMapping(value = "/csv", params = {"file","key","value"},produces = "application/json")
    @ResponseBody
    public ResponseEntity getCommodities(@RequestParam String file ,@RequestParam String key,@RequestParam String value) throws IOException{
        Request request = new Request.Builder()
                .url(buildURL(file))
                .build();
        Response response = client.newCall(request).execute();
        List<Map<String,String>> json = csVtoJSON.convert(response.body().string());


        return ResponseEntity.ok(filterByKey(key,value,json));
    }


    private List<Map<String,String>> filterByKey(String key,String value,List<Map<String,String>> json){
        List<Map<String,String>> filteredCommodities = new LinkedList<>();
        for (Map map: json) {

            if(map.get(key).toString().toLowerCase().contains(value.toLowerCase())){
                filteredCommodities.add(map);
            }
        }
        return filteredCommodities;
    }

    @GetMapping( produces = "application/json")
    @ResponseBody
    public ResponseEntity society() throws Exception {

        return ResponseEntity.ok(scraped.getSocietyJSON());
    }
    static String buildURL(String file){
        return "http://data.gov.ph/sites/default/files/"+file;

    }

}


