package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;


public class Parser<K>{
	private HashMap<Pair,BiConsumer<String,K>> bindings;
	private char seperator;
	private class Pair{
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
	/**
	 * strings should be fed row by row
	 * @param seperator comma for csv, tab for tsv
	 */
	public Parser(char seperator) {
		bindings = new HashMap<>();
		this.seperator = seperator;
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
	public void apply(K instance,List<String> str) throws RuntimeException{
		String[][] arr = new String[str.size()][];
		int ct = 0;
		for (int i=0;i<str.size();i++) {
			arr[i] = str.get(i).split(seperator+"(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
			for (int j=0;j<arr[i].length;j++)
				arr[i][j] = arr[i][j].replaceAll("(\")", "");
			if (arr[i].length>=ct)
				ct = arr[i].length;
			else {
				String[] newarr = new String[ct];
				System.arraycopy(arr[i], 0, newarr, 0, arr[i].length);
				for (int k = arr[i].length;k<newarr.length;k++)
					newarr[k] = "";
				arr[i]=newarr;
			}
		}
		var itr = bindings.keySet().iterator();
		while (itr.hasNext()) {
			var pair = itr.next();
			int i = pair.y;
			try {
				if (pair.y==Integer.MAX_VALUE) {
					for (i=0;i<arr.length;i++)
						bindings.get(pair).accept(arr[i][pair.x], instance);
				} else
					if (pair.y<arr.length)
						bindings.get(pair).accept(arr[pair.y][pair.x], instance);
			} catch (IndexOutOfBoundsException e) {System.err.print("Binding at index "+pair.x+","+i+" failed. "+e.toString()+'\n');
			} catch (RuntimeException e) {
				RuntimeException newexp = new RuntimeException("Binding at index "+pair.x+","+i+" failed. Error: "+e.getMessage());
				newexp.setStackTrace(e.getStackTrace());
				throw newexp;
			}
		}
	}
}