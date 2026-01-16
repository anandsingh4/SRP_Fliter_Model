package Com.SRP_Filter_Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class API_Con {

	public static final String excelPath = "D:\\Excel\\DataSetParam.xlsx";

	public static List<Map<String, String>> getAllQueryParams() {
		return ExcelReader.extractQueryParamsFromExcel(excelPath);
	}
}
