package com.a2m.mail.server.service;

/**
 * @author tiennd
 *
 * @created 2022
 */
import java.util.List;

import com.a2m.mail.shared.domain.ImapFolder;
import com.a2m.mail.shared.domain.User;
import com.a2m.mail.shared.exception.HupaException;

public interface IReadAndUnReadService {
	public void markReadOrUnRead(User user, ImapFolder folder, List uid, String type) throws HupaException;
}
