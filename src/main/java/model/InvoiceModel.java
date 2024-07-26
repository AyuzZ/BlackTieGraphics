package model;

public class InvoiceModel {
	private int invoiceId;
	private double grandTotal;
	
	public InvoiceModel(int invoiceId, double grandTotal) {
		super();
		this.invoiceId = invoiceId;
		this.grandTotal = grandTotal;
	}

	public int getInvoiceId() {
		return invoiceId;
	}

	public double getGrandTotal() {
		return grandTotal;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public void setGrandTotal(double grandTotal) {
		this.grandTotal = grandTotal;
	}

}
