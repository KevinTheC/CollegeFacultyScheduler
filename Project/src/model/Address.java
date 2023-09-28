package model;

public class Address {
	private int streetNumber;
	private String street;
	private String city;
	private String state;
	private String zip;
	public Address() {
		
	}
	public Address(String str1,String str2){
		String[] arr1 = str1.split("[ ]");
		String[] arr2 = str2.split("[,]");
		streetNumber = Integer.parseInt(arr1[0]);
		street = "";
		for (int i=1;i<arr1.length;i++) {
			if (i!=1)
				street = street.concat(" ");
			street = street.concat(arr1[i]);
		}
		city = arr2[0];
		arr2 = arr2[1].split("[ ]");
		state = arr2[1];
		zip = arr2[2];
	}
	public String toString() {
		return streetNumber + " " + street + " "
				+ city + ", " + state + " " + zip;
	}
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Address))
			return false;
		Address a2 = (Address)o;
		return a2.streetNumber==streetNumber&&a2.street.equals(street)
				&&a2.city.equals(city)&&a2.state.equals(state)&&
				a2.zip.equals(zip);
	}
}
