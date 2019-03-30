package com.ehdes.CreepSore.Craftsino.types.pokercard;

public enum EnumPokerCardType {
	HEARTS,
	TILES,
	CLOVERS,
	PIKES;
	
	@Override
	public String toString() {
		char[] cArr = this.name().toCharArray();
		String result = Character.toString(Character.toUpperCase(cArr[0]));
		for(int i = 1; i < cArr.length; i++) {
			result += Character.toLowerCase(cArr[i]);
		}
		
		return result;
	}
}
