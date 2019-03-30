package com.ehdes.CreepSore.Craftsino.BlackJack;

public enum EBlackJackPlayerAction {

	NONE,
	BET,
	HIT,
	STAND,
	SPLIT,
	DOUBLE_DOWN,
	CANCEL;
	
	@Override
	public String toString() {
		char[] cArr = this.name().toCharArray();
		String result = Character.toString(Character.toUpperCase(cArr[0]));
		
		boolean nextUp = true;
		for(int i = 1; i < cArr.length; i++) {
			char c = cArr[i];
			
			if(!nextUp) {
				c = Character.toLowerCase(c);
			} else {
				nextUp = false;
			}
			
			if(c == '_') {
				c = ' ';
				nextUp = true;
			}
			
			result += c;
		}
		
		return result;
	}
	
}
