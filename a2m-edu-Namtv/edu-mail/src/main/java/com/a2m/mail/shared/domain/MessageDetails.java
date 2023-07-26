/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/

package com.a2m.mail.shared.domain;

import java.util.List;

public interface MessageDetails {

	void setUid(long uid);

	void setText(String filterHtmlDocument);

	void setMessageAttachments(List<MessageAttachment> attachmentList);

	void setMailHeaders(List<MailHeader> mailHeader);

	void setCc(List<String> cc);

	void setBcc(List<String> bcc);

	long getUid();

	String getText();

	List<MessageAttachment> getMessageAttachments();

	String getMessageId();

	String getReferences();

	List<MailHeader> getMailHeaders();

	List<String> getCc(List<String> cc);
	
	List<String> getBcc(List<String> bcc);
}
