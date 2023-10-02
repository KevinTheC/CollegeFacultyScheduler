package model;

import java.util.List;

public interface Factory<K> {
	public K getInstance(Parser<K> parser, List<String> strings);
}
