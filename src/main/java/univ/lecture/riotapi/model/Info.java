package univ.lecture.riotapi.model;

import net.minidev.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by tchi on 2017. 4. 1..
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Info {
    
	
	private String teamId;
	private String now;
    private String result;
    
    
    public Info(String args){
    	JSONObject data = new JSONObject();
     
    	data.put(teamId, "9");
    	data.put(now, String.valueOf(System.currentTimeMillis()));
    	data.put(result, RPNCalculator.calculateExpression(args));
    }
}