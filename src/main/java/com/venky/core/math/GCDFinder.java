package com.venky.core.math;


public final class GCDFinder {
	private GCDFinder(){
		
	}
	private static GCDFinder finder = null ;
	public static final GCDFinder getInstance() {
		if (finder == null){
			finder = new GCDFinder();
		}
		return finder;
	}
	public int gcd(int a, int b){
		int r = 0 ;
		a = Math.abs(a);
		b = Math.abs(b);
		while (b != 0){
			r = a % b ; 
			a = b; 
			b = r;
		}
		
		return a;
	}
	public int gcd(int[] number){
		int gcd = 0;
		
		for (int i = 0 ; i < number.length  ; i ++ ){
			gcd = gcd(gcd,number[i]);
		}
		return gcd;
	}
}
