package com.citibanamex.mafcs.customercatalog.service;

import com.citibanamex.mafcs.customercatalog.viewmodel.lineofbusiness.LineofBusinessResponse;

public interface LineOfBusinessService {
	public LineofBusinessResponse getLineofBusiness(String lineOfBusinessFilter);
}
