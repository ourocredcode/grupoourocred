package br.com.sgo.modelo.cep;


public interface AddressFinder {

	AddressFinder findAddressByZipCode(String zipcode);
	
	String[] asAddressArray();
	
	Address asAddressObject();
	
}
