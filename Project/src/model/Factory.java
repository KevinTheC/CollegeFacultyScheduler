package model;

import java.util.List;

import model.IO.GenericReader;

public interface Factory<K> {
	public K getInstance(GenericReader<K> parser, List<String> strings);
	public K getInstance(GenericReader<K> parser, String string);
}
