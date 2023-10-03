package chrono;

import java.util.Objects;

public class Triple <K,V,T>{
	public K k;
	public V v;
	public T t;
	public Triple(K k, V v, T t){
		this.k = k;
		this.v = v;
		this.t = t;
	}
	public String toString() {
		return "["+k+","+v+","+t+"]";
	}
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Triple))
			return false;
		Triple trip = (Triple)o;
		return Objects.equals(this.k, trip.k)&&
				Objects.equals(this.v,trip.v)&&
				Objects.equals(this.t, trip.t);
	}
}
