package Com.SRP_Filter_Model;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class SRP_Validation {
	public final List<Integer> matchedCityIds = new ArrayList<>();
	public final List<Integer> NonmatchedCityIds = new ArrayList<>();
	// List<String> allowedStatuses = Arrays.asList("immediate", "ready to move",
	// "under construction");
	// List<String> CG_Status = Arrays.asList("B", "S", "R");
	// List<String> PP_Status = Arrays.asList("I", "A", "B", "Z");
	public final List<String> NonMatchedPossD = new ArrayList<>();
	public final HashSet<String> matchedIds = new HashSet<>();
	public final HashSet<String> Non_matchedIds = new HashSet<>();
	public final List<String> Non_matched_Cg_Ids = new ArrayList<>();
	public final List<String> Matched_Cg_Ids = new ArrayList<>();
	public final List<String> Non_Matched_Pp_Ids = new ArrayList<>();

	public int NonmatchedCityIds_count = 0;
	public int NonMatchedPossD_count = 0;
	public int NonMatchedIds_count = 0;
	public int Non_matched_Cg_Ids_count = 0;
	public int Matched_Cg_Ids_count = 0;
	public int NON_Matched_Pp_Ids_count = 0;

	public void SRP_Validation_check(String BaseUrl) {

		Map<String, String> queryParams_base = API_Con.getAllQueryParams().get(0);

		int pageNum = 1;
		int maxPages = 5;

		while (pageNum <= maxPages) {
			Map<String, String> queryParams = new HashMap<>(queryParams_base);
			queryParams.put("page", String.valueOf(pageNum));

			Response res = given().baseUri(BaseUrl)
//			.basePath(Base_EndPoint.End_Url)
					.queryParams(queryParams).accept(ContentType.JSON).contentType(ContentType.JSON)
					.header("User-Agent", "PostmanRuntime/7.6.0")
//			.header("Cookie",
//					"SRPSESSIONID=MGVlNWQxYmQtNzNkMy00ZmNmLWFiYTYtNDM2OTM3OTYyNmE4; cityCode=3327; cityCookie=3327; cityNameCookie=Bangalore; cityNameTTvl=Bangalore; cookieDtfirstIntr=20250724; firstInteractionCookie=F; firstLocality=Whitefield; localityCookie=88527; localityNameCookie=Whitefield; projectCategory=B; propCategory=Residential; propertyTypeCookie=Multistorey-Apartment%2CBuilder-Floor-Apartment%2CPenthouse%2CStudio-Apartment; subPropertyType=Multistorey-Apartment%2CBuilder-Floor-Apartment%2CPenthouse%2CStudio-Apartment; subPropertyTypeCookie=10002%2C10003%2C10021%2C10022; uniqUserSearchId=3d549cf256144875b7afa36f4f8eae300ee5d1bd_1753304797026; luxuryCookie=N")
					.header("Cookie", Config_Utile.config.getProperty("cookie")).when().get().then().extract()
					.response();

			System.out.println("The response: " + res.asString());

			if (res.statusCode() != 200) {
				System.out.println("🚫 Non-200 response on page " + pageNum + ": " + res.statusCode());
				break;
			}

			JsonPath jsonPath = res.jsonPath();
			List<Map<String, Object>> properties = jsonPath.getList("resultList");

			if (properties == null || properties.isEmpty()) {
				System.out.println("🚫 No results on page " + pageNum);
				break;
			}

			JSONObject root = new JSONObject(res.asString());
			// JSONArray Store = root.getJSONArray("stores");
			JSONArray resultList = root.getJSONArray("resultList");

			for (int i = 0; i < resultList.length(); i++) {
				JSONObject resultList_Obj = resultList.getJSONObject(i);
				int ct_Obj = resultList_Obj.getInt("ct");
				String possStatusD = resultList_Obj.optString("possStatusD").trim();
				String id_Obj = resultList_Obj.getString("id");
				// System.out.println("The city id is:"+ct_Obj);
				// System.out.println("The city id is:"+possStatusD);
				String cg_Obj = resultList_Obj.getString("cg").trim();
				String PP_Obj = resultList_Obj.getString("pp").trim();

				if (ct_Obj != 0 && ct_Obj == 6245)
				// (ct_Obj != 0 && ct_Obj ==
				// Integer.parseInt(Config_Utile.config.getProperty("city")))
				{
					matchedCityIds.add(ct_Obj);
					// System.out.println("The value is:"+matchedCityIds);
				} else {
					NonmatchedCityIds.add(ct_Obj);
					NonmatchedCityIds_count++;
//			System.out.println("The value is:"+NonmatchedCityIds);
				}
				if (!possStatusD.isEmpty()) {
					String normalizedStatus = possStatusD.toLowerCase();
					if (Generic.allowedStatuses.contains(normalizedStatus)) {

						// System.out.println("Valid Possession Status: " + possStatusD);
					} else {
						NonMatchedPossD.add(possStatusD);
						NonMatchedPossD_count++;
						System.out.println("Invalid Possession Status Found: " + possStatusD);
					}
				}

				if (!id_Obj.isEmpty()) {
					if (matchedIds.contains(id_Obj)) {
						Non_matchedIds.add(id_Obj);
						NonMatchedIds_count++;
					} else {
						matchedIds.add(id_Obj);
					}
				}

				if (!cg_Obj.isEmpty()) {
					String normalizedStatus_cg = cg_Obj.toUpperCase();
					if (Generic.CG_Status.contains(normalizedStatus_cg)) {
						Matched_Cg_Ids.add(id_Obj);
						Matched_Cg_Ids_count++;
					} else {

						Non_matched_Cg_Ids.add(id_Obj);
						Non_matched_Cg_Ids_count++;
					}
				}

				if (!PP_Obj.isEmpty()) {
					String normalizedStatus_pp = PP_Obj.toUpperCase();
					if (Generic.PP_Status.contains(normalizedStatus_pp)) {

					} else {
						Non_Matched_Pp_Ids.add(id_Obj);
						NON_Matched_Pp_Ids_count++;
					}
				}

			}
			pageNum++;
		}

	}
}