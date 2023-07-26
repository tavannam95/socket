
package com.a2m.mail.shared.data;

import com.a2m.mail.shared.domain.MailHeader;

public class MailHeaderImpl implements MailHeader {
	private String name;
    private String value;

    public MailHeaderImpl(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public MailHeaderImpl() {
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setName(String s) {
        name = s;
    }

    @Override
    public void setValue(String s) {
        value = s;
    }

	@Override
	public String toString() {
		return "MailHeaderImpl [name=" + name + ", value=" + value + "]";
	}
}
