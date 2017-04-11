package univ.lecture.riotapi.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;

/**
 * Created by tchi on 2017. 4. 1..
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Info {
    
    public JSONObject data;
    
    public Info(String expression){
    	data = new JSONObject();
    	data.put("teamId", 9);
    	data.put("now", System.currentTimeMillis());
    	data.put("result", RPNCalculator.calculateExpression(expression));
    	
    }
}
