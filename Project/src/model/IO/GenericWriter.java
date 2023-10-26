package model.IO;

import java.io.File;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

public abstract class GenericWriter<K> {
	public void write(File f, K k) {
		List<K> list = new LinkedList<>();
		write(f,list);
	}
	public abstract void write(File f, List<K> k);
	public void bind(BiConsumer<PrintWriter,K> writer) {
		writers.add(writer);
	}
	protected List<BiConsumer<PrintWriter,K>> writers;
	public GenericWriter() {
		writers = new LinkedList<>();
	}
}
