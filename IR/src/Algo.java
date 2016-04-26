import java.util.ArrayList;
import java.util.Collection;

public class Algo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		WordMatrix wordDocs = new WordMatrix(21, 2);
		WordMatrix wordQuers = new WordMatrix(21, 2);
		// calcVsm(f.getWordMatrix(), wordQuers, d, q)
	}

	public static double calcVsm(WordMatrix wordDocs, WordMatrix wordQuers,
			int d, int q) {

		double sumD = 0;

		for (int j = 0; j < wordDocs.getWordMatrix().length; j++) {
			sumD += wordDocs.tfIdf(j, d);
		}

		double sumQ = 0;
		for (int j = 0; j < wordQuers.getWordMatrix().length; j++) {
			sumQ += wordQuers.tfIdf(j, d);
		}
		
		double lngtD = wordDocs.calcLength( d);
		double lngtQ = wordQuers.calcLength( q);
		
		return sumD * sumQ / (lngtD * lngtQ);
	}
	
	/**
	 * Grades a document given a query
	 * 
	 * @param docs
	 *            the wordMatrix
	 * @param doc
	 *            doc id
	 * @param query
	 *            query Collection<Integer>
	 * @param
	 * @return
	 */
	private static double BM25(WordMatrix docs, int doc, WordMatrix quers,int quryID, double k) {
		double sum = 0;
		
		for (int j = 0; j < quers.getN(); j++) {
			int f = docs.getf(j, doc);
			int docLength = docs.getDocLength(doc);
			int n = docs.getn(j);
			f += .5;
			n += .5;
			sum += (f / (k + docLength + f))
					* Math.log10((docs.getN() - n) / n);
						
		}
		
		return sum;
	}
	
	/**
	 * Grades all documents given a query
	 * 
	 * @param wordMatrix
	 * @param query
	 * @param k
	 * @return
	 */
	private static double[] BM25(WordMatrix wordMatrix, WordMatrix  query,int quryID, double k) {
		double[] documentScores = new double[wordMatrix.getN()];
		for (int doc = 0; doc < wordMatrix.getN(); doc++) {
			documentScores[doc] = BM25(wordMatrix, doc, query,quryID, k);
		}
		return documentScores;
	}
	
	/**
	 * Grades all documents given all queries
	 * 
	 * @param wordMatrix
	 * @param queries
	 * @param k
	 * @return
	 */
	public static double[][] BM25(WordMatrix docs, int doc, WordMatrix quers, double k) {
		double[][] documentScores = new double[quers.getN()][docs.getN()];
		
		for (int query = 0; query < quers.getN(); query++) {
			documentScores[query] = BM25(docs,quers,query ,k);
		}
		return documentScores;
	}
	

}
