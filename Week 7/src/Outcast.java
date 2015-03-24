
public class Outcast {
	private final WordNet wn;
	public Outcast(WordNet wordnet) {
		this.wn = wordnet;
	}
	public String outcast(String[] nouns) {
		String result = nouns[0];
		int max = 0;
		for (int i = 0; i < nouns.length; i++) {
			int distance = 0;
			for (int j = 0; j < nouns.length; j++) {
				if (i == j) continue;
				distance += wn.distance(nouns[i], nouns[j]);
			}
			if (distance > max) {
				max = distance;
				result = nouns[i];
			}
		}
		
		return result;
	}
	public static void main(String[] args) {
		WordNet wordnet = new WordNet(args[0], args[1]);
	    Outcast outcast = new Outcast(wordnet);
	    for (int t = 2; t < args.length; t++) {
	        In in = new In(args[t]);
	        String[] nouns = in.readAllStrings();
	        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
	    }
	}
}