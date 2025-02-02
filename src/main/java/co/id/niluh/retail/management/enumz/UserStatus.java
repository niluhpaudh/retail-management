package co.id.niluh.retail.management.enumz;


import co.id.niluh.retail.management.model.Selection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public enum UserStatus {
	NON_ACTIVE("TIDAK AKTIF"),
    ACTIVE("AKTIF"),
    LOCK("TERBLOKIR"),
    CLOSE("TUTUP");

	private String label;
	UserStatus(String label){
		this.setLabel(label);
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public static List<Selection> valuesOfEnum(){
		List<Selection> result = new ArrayList<Selection>();
		Stream.of(UserStatus.values()).forEach(value -> result.add(new Selection(value.name(), value.getLabel())));
		return result;
	}
}
