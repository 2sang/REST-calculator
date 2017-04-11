package univ.lecture.riotapi.controller;

import univ.lecture.riotapi.model.*;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.json.simple.JSONObject;
import univ.lecture.riotapi.model.RPNCalculator;

import java.io.*;
import java.net.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@Log4j
public class RiotApiController {

    @Value("${riot.api.endpoint}")
    private String riotApiEndpoint;

    @Value("${riot.api.key}")
    private String riotApiKey;

	@Value("${target.endpoint}")
	private String targetEndPoint;
	
	private void sendResult(JSONObject jsonObject) throws UnsupportedEncodingException{
		try{
			URL url = new URL(targetEndPoint);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

			osw.write(jsonObject.toString());
			osw.flush();
			BufferedReader br;
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while((line = br.readLine()) != null){
				stringBuilder.append(line+"\n");
			}
			System.out.println(""+stringBuilder.toString());

			osw.close();
			br.close();

		}catch(MalformedURLException e){
			e.printStackTrace();
		}catch(ProtocolException e){
			e.printStackTrace();
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}

	}

	@RequestMapping(value="/calc/{exp}", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String queryExpression(@PathVariable("exp") String expression) throws UnsupportedEncodingException {
		System.out.println("expression is : "+expression);
		
		Info info = new Info(expression);
		
		//this.sendResult(new JSONObject());
		this.sendResult(info.data);
		
		return "SUCCESS!!";
    }
}
