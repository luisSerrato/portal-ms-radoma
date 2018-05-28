package com.citibanamex.mafcs.customercatalog.viewmodel.persontype;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jp34651
 * @version 1.0
 * @created 23-Jan-2018 5:01:54 PM
 */
public class PersonTypeResponse {

	@ApiModelProperty (required = true)
	public List<Customer> customer;

	public PersonTypeResponse(){
		this.setCustomer(new ArrayList<>());
	}

	public List<Customer> getCustomer() {
		return customer;
	}

	public void setCustomer(List<Customer> customer) {
		this.customer = customer;
	}

	public void finalize() throws Throwable {

	}

}