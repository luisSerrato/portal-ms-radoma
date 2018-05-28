package com.citibanamex.mafcs.customercatalog.service;

import com.citibanamex.mafcs.customercatalog.viewmodel.profession.ProfessionResponse;

public interface ProfessionService {
	public ProfessionResponse getProfession( String professionFilter );
}
