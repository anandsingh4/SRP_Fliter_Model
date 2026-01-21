package Com.SRP_Filter_Model;

import java.util.Map;

public class InvalidExceptionRowHandling {
	
	public static boolean isInvalidExcelRow(Map<String, String> params) {
      if (params == null || params.isEmpty()) {
          return true;
      }

      for (Map.Entry<String, String> entry : params.entrySet()) {
          if (entry.getValue() != null && !entry.getValue().trim().isEmpty()) {
              return false; // at least one valid value exists
          }
      }
      return true; // all values are empty
  }

}
