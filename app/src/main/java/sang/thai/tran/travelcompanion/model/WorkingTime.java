package sang.thai.tran.travelcompanion.model;

import com.google.gson.annotations.SerializedName;

public class WorkingTime extends BaseModel{

    @SerializedName("Day")
    private String day;

    @SerializedName("FromTime")
    private String fromTime;

    @SerializedName("ToTime")
    private String toTime;

}
