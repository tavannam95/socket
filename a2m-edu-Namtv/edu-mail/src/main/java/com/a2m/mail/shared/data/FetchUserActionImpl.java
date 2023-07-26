package com.a2m.mail.shared.data;

import org.springframework.stereotype.Service;

import com.a2m.mail.shared.domain.FetchUserActionProxy;

@Service
public class FetchUserActionImpl implements FetchUserActionProxy {

	private int start;
	private int offset;
	private String searchString;
	
	@Override
	public int getStart() {
		return start;
	}

	@Override
	public int getOffset() {
		return offset;
	}

	@Override
	public String getSearchString() {
		return searchString;
	}

	@Override
	public void setStart(int start) {
		this.start = start;
	}

	@Override
	public void setOffset(int offset) {
		this.offset = offset;
	}

	@Override
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

}
