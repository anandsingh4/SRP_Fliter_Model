package Com.SRP_Filter_Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class API_Con {
	
	public static final String excelPath = "D:\\Excel\\DataSetParam.xlsx";

//    public static Map<String, Object> getParams() {
//
//        Map<String, Object> params = new HashMap<>();
//
//        params.put("editSearch", "Y");
//        params.put("category", "S");
//        params.put("city", "3327");
//        params.put("localityName", "Whitefield");
//        params.put("propertyType", "10002");
//        params.put("budgetMin", "3000000");
//        params.put("budgetMax", "6000000");
//        params.put("page", "1");
//
//        return params;
//    }
	
	public static List<Map<String, String>> getAllQueryParams() {
        return ExcelReader.extractQueryParamsFromExcel(excelPath);
    }
}
