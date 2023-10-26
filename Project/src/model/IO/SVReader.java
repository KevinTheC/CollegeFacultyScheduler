package model.IO;

import java.util.HashMap;
import java.util.List;

public class SVReader<K> extends GenericReader<K>{
	private char seperator;
	/**
	 * strings should be fed row by row
	 * @param seperator comma for csv, tab for tsv
	 */
	protected SVReader(char seperator) {
		bindings = new HashMap<>();
		this.seperator = seperator;
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