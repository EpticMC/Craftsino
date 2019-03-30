package com.ehdes.CreepSore.Craftsino.types.pokercard;

public class PokerCard {

	EnumPokerCardFace face;
	EnumPokerCardType type;
	
	public PokerCard(EnumPokerCardType type, EnumPokerCardFace face) {
		this.type = type;
		this.face = face;
	}
	
	public EnumPokerCardType getType() {
		return this.type;
	}
	
	public EnumPokerCardFace getFace() {
		return this.face;
	}
	
}
