package co.id.niluh.retail.management.model;

import co.id.niluh.retail.management.enumz.UserStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Selection {

    private String value;
    private String label;

    public Selection(){};
    public Selection(String value, String label){
        this.value = value;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    public static List<Selection> valuesOfEnum(){
        List<Selection> result = new ArrayList<Selection>();
        Stream.of(UserStatus.values()).forEach(value -> result.add(new Selection(value.name(), value.getLabel())));
        return result;
    }

}
