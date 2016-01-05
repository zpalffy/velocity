package com.eric;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class CollectionTool {

	public CollectionTool() {
	}

	public <T> ImmutableList<T> immutableList(Collection<T> coll) {
		return ImmutableList.copyOf(coll);
	}

	public <T> List<T> reverse(Collection<T> coll) {
		return Lists.reverse(immutableList(coll));
	}
}
