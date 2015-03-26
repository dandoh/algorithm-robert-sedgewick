
public class Dijkstra {
	private double[] d;
	
	public Dijkstra(WeightedGraph G, int s) {
		IndexMinPQ<Double> pq = new IndexMinPQ<Double>(G.V());
		d = new double[G.V()];
		
		for (int i = 0; i < G.V(); i++) {
			d[i] = Double.POSITIVE_INFINITY;
		}
		d[s] = 0;
		
		pq.insert(s, d[s]);
		
		while (!pq.isEmpty()) {
			int v = pq.delMin();
			
			for (DirectedEdge e : G.adj(v)) {
				int t = e.to();
				double w = e.weight();
				
				if (d[t] > d[v] + w) {
					d[t] = d[v] + w;
					if (!pq.contains(t)) {
						pq.insert(t, d[t]);
					} else {
						pq.decreaseKey(t, d[t]);
					}
				}
			}
			if (v + 'A' == 'G') return;
		}
	}
	
	public double[] distance() {
		return d;
	}
}
