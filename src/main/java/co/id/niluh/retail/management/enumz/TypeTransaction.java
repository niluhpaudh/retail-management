package co.id.niluh.retail.management.enumz;


import co.id.niluh.retail.management.model.Selection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public enum TypeTransaction {
	OUT("TRANSAKSI KELUAR"),
    REFUND("PENGEMBALIAN BARANG");

	private String label;
	TypeTransaction(String label){
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
		Stream.of(TypeTransaction.values()).forEach(value -> result.add(new Selection(value.name(), value.getLabel())));
		return result;
	}
}
