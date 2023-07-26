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

package com.a2m.mail.shared.data;

import org.springframework.stereotype.Service;

import com.a2m.mail.shared.domain.GetMessageDetailsAction;
import com.a2m.mail.shared.domain.ImapFolder;

@Service
public class GetMessageDetailsActionImpl implements GetMessageDetailsAction {
    public GetMessageDetailsActionImpl() {
        super();
    }
    public GetMessageDetailsActionImpl(ImapFolder folder, long uid) {
        super();
        this.folder = folder;
        this.uid = uid;
    }

    private ImapFolder folder;
    private long uid;

    @Override
    public ImapFolder getFolder() {
        return folder;
    }
    @Override
    public void setFolder(ImapFolder folder) {
        this.folder = folder;
    }
    @Override
    public long getUid() {
        return uid;
    }
    @Override
    public void setUid(long uid) {
        this.uid = uid;
    }
}
