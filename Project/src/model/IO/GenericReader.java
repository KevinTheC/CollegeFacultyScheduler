package model.IO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;


public abstract class GenericReader<K> {
	protected HashMap<Pair,BiConsumer<String,K>> bindings;
	protected class Pair{
		public Pair(int x,int y) {
			this.x=x;
			this.y=y;
		}
		public int x;
		public int y;
		public int hashCode() {
			int hash = 7;
		    hash = 31 * hash + x;
		    hash = 31 * hash + y;
		    return hash;
		}
		public String toString() {
			return "[x: "+x+" y: "+y+"]";
		}
	}
	public boolean unbind(int x,int y) {
		if (!bindings.containsKey(new Pair(x,y)))
			return false;
		bindings.remove(new Pair(x,y));
		return true;
	}
	public boolean unbind(int x) {
		boolean flag = false;
		var itr = bindings.keySet().iterator();
		while (itr.hasNext()) {
			var next = itr.next();
			if (next.x==x) {
				flag = true;
				bindings.remove(next);
			}
		}
		return flag;
	}
	public boolean unbind(int x,int[] ys) {
		boolean flag = false;
		for (int y : ys)
			flag = flag||unbind(x,y);
		return flag;
	}
	/**
	 * 
	 * @param colidx
	 * @param rowidx
	 * @param cons consumer, should end with an instructor setter
	 * @return boolean value, true if a previous binding was overwritten
	 */
	public boolean bind(int x,int y,BiConsumer<String,K> cons) {
		var pair = new Pair(x,y);
		if (bindings.containsKey(pair)) {
			bindings.put(pair,cons);
			return true;
		}
		bindings.put(pair, cons);
		return false;
	}
	public boolean bind(int x,BiConsumer<String,K> cons) {
		return bind(x,Integer.MAX_VALUE,cons);
	}
	public boolean bind(int x,int[] ys,BiConsumer<String,K> cons) {
		boolean flag = false;
		for (int y : ys)
			flag = flag||bind(x,y,cons);
		return flag;
	}
	public void apply(K instance,String str) throws RuntimeException{
		List<String> list = new ArrayList<String>(2);
		list.add(str);
		apply(instance,list);
	}
	public abstract void apply(K instance, List<String> strs) throws RuntimeException;
}
