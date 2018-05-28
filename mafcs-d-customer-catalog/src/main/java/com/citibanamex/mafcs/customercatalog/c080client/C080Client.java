package com.citibanamex.mafcs.customercatalog.c080client;

import javax.validation.Valid;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "BackC080", url = "${feign.url}", fallback = C080ClientFallback.class)
public interface C080Client {

	@RequestMapping(value = "/connwl/services/getSql80", method = RequestMethod.POST)
	public Object getInformationC080(@RequestBody @Valid SqlRequest query);

}