package Com.SRP_Filter_Model;

public class SRP_Check_Points {

	public void SRP_Validation_check(SRP_Validation validation) {

		if (!validation.NonmatchedCityIds.isEmpty() || validation.NonmatchedCityIds_count > 0) {
			System.out.println("The city is not get matched");
		} else {
			System.out.println("The city id is get matched");
		}

		if (validation.NonMatchedPossD_count > 0) {
			System.out.println("The property possession status is not matched");
		} else {
			System.out.println("The property possession status is matched");
		}

		if (validation.NonMatchedIds_count > 0) {
			System.out.println("The Id is duplicate" + validation.Non_matchedIds);
		} else {
			System.out.println("The Id is unique");
		}

		if (!validation.Matched_Cg_Ids.isEmpty() && validation.Matched_Cg_Ids_count > 0) {

			System.out.println("The cg is matched");
		} else {
			System.out.println("The cg is not matched");
		}

		if (validation.NON_Matched_Pp_Ids_count > 0) {

			System.out.println("The PP is nonmatched");
		} else {
			System.out.println("The PP is matched");
		}

	}
}
