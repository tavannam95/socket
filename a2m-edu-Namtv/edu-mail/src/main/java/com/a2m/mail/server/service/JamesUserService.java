package com.a2m.mail.server.service;

import com.a2m.mail.shared.domain.CRUDUserActionProxy;
import com.a2m.mail.shared.domain.FetchUserActionProxy;
import com.a2m.mail.shared.domain.FetchUserResultProxy;
import com.a2m.mail.shared.domain.GenericResult;
import com.a2m.mail.shared.domain.JamesUserProxy;

public interface JamesUserService {
	FetchUserResultProxy fetch(FetchUserActionProxy action) throws Exception;
	FetchUserResultProxy fetchAll() throws Exception;
	JamesUserProxy fetchByName(String name) throws Exception;
	int count() throws Exception;
	GenericResult insert(CRUDUserActionProxy action) throws Exception;
	GenericResult update(CRUDUserActionProxy action) throws Exception;
	GenericResult delete(CRUDUserActionProxy action) throws Exception;
}
