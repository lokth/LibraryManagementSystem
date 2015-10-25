package business;

import java.io.Serializable;
import java.util.ArrayList;

public class CheckOutRecord  implements Serializable{
	
	private ArrayList<CheckoutRecordEntry> chkoutRecEnt;

	public CheckOutRecord(ArrayList<CheckoutRecordEntry> chkoutRecEnt) {
		this.chkoutRecEnt = chkoutRecEnt;
	}

	public ArrayList<CheckoutRecordEntry> getChkoutRecEntry() {
		return chkoutRecEnt;
	}
	
	public void addEntry(CheckoutRecordEntry checkoutEntry){
		
		this.chkoutRecEnt.add(checkoutEntry);
	}

}