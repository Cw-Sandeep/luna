package tv.cloudwalker.lunalauncher.com.data.deeplinkModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MarketAppExtra {
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("dataType")
    @Expose
    private String dataType;
    @SerializedName("value")
    @Expose
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
