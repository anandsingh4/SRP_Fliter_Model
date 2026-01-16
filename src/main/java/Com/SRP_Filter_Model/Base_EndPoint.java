package Com.SRP_Filter_Model;

import java.util.List;

public class Base_EndPoint {
//	public static String Base_Url = "https://www.magicbricks.com";
//	public static String End_Url = "/mbsrp/propertySearch.html";

	public static String excelPath = "D:/Excel/DataSet.xlsx";
	public static List<String> Final_Url = ExcelReader.getFullUrl(excelPath, "Sheet1");

}
