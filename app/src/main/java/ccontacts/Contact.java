package ccontacts;

public class Contact {

	private String contactName;
	private String contactPhone;
	
	public Contact(String contactName, String contactPhone) {
		this.contactName = contactName;
		this.contactPhone = contactPhone;
	}
	
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	@Override
	public String toString() {
		return "ContactName : " + contactName + " ContactPhone : " + contactPhone;
	}
	
	
}
