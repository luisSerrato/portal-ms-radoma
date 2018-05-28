package com.citibanamex.mafcs.customercatalog.c080client;

import javax.validation.Valid;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class C080ClientFallback implements C080Client {
	
	public Object getInformationC080(@RequestBody @Valid SqlRequest query) {
		return null;
	}

}
