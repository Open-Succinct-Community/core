package com.venky.core.string;

import java.util.HashMap;
import java.util.Map;


public class NumberToWordsConverter {
	static Map<Integer,String> wordsMap = new HashMap<Integer, String>();

	private static final int CRORE = (int)Math.pow(10, 7);
	private static final int LAKH = (int)Math.pow(10, 5);
	private static final int THOUSAND = (int)Math.pow(10, 3);
	private static final int HUNDRED = (int)Math.pow(10, 2);
	private static final int TEN = (int)Math.pow(10, 1);
	static {
		wordsMap.put(1, "ONE");
		wordsMap.put(2, "TWO");
		wordsMap.put(3, "THREE");
		wordsMap.put(4, "FOUR");
		wordsMap.put(5, "FIVE");
		wordsMap.put(6, "SIX");
		wordsMap.put(7, "SEVEN");
		wordsMap.put(8, "EIGHT");
		wordsMap.put(9, "NINE");
		wordsMap.put(10, "TEN");
		wordsMap.put(11, "ELEVEN");
		wordsMap.put(12, "TWELVE");
		wordsMap.put(13, "THIRTEEN");
		wordsMap.put(14, "FOURTEEN");
		wordsMap.put(15, "FIFTEEN");
		wordsMap.put(16, "SIXTEEN");
		wordsMap.put(17, "SEVENTEEN");
		wordsMap.put(18, "EIGHTEEN");
		wordsMap.put(19, "NINETEEN");
		wordsMap.put(20, "TWENTY");
		wordsMap.put(30, "THIRTY");
		wordsMap.put(40, "FORTY");
		wordsMap.put(50, "FIFTY");
		wordsMap.put(60, "SIXTY");
		wordsMap.put(70, "SEVENTY");
		wordsMap.put(80, "EIGHTY");
		wordsMap.put(90, "NINETY");

		wordsMap.put(HUNDRED, "HUNDRED");
		wordsMap.put(THOUSAND, "THOUSAND");
		wordsMap.put(LAKH, "LAKH");
		wordsMap.put(CRORE, "CRORE");
		
	}

	
	private int number;
	private StringBuilder words  = new StringBuilder();
	public NumberToWordsConverter(int number){
		this.number = number;
		fillWords(this.number);
	}
	private void fillWords(int remaining){
		if (remaining == 0){
			return;
		}
		int divisor = TEN;
		if (remaining >= CRORE){
			divisor = CRORE;
		}else if (remaining >= LAKH){
			divisor = LAKH;
		}else if (remaining >= THOUSAND){
			divisor = THOUSAND;
		}else if (remaining >= HUNDRED){
			divisor = HUNDRED;
		}
		if (divisor >= HUNDRED){
			int q = remaining / divisor;
			fillWords(q);
			words.append(" ");
			words.append(wordsMap.get(divisor));
			fillWords(remaining % divisor);
		}else {
			if (words.length() > 0){
				words.append(" ");
			}
			if (wordsMap.containsKey(remaining)){
				words.append(wordsMap.get(remaining));
			}else {
				//Remaining is clearly > 20.
				int q = remaining / divisor;
				words.append(wordsMap.get(q * divisor));
				fillWords(remaining % divisor);
			}
		}
	}
	
	@Override
	public String toString(){
		return words.toString();
	}
	
	@Override
	public int hashCode(){
		return number;
	}
	
	@Override
	public boolean equals(Object another){
		if (another == null || !(another instanceof NumberToWordsConverter)){
			return false;
		}
		return hashCode() == another.hashCode();
	}
}
