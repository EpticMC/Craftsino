package com.ehdes.CreepSore.Craftsino.types.pokercard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PokerDeck {

	List<PokerCard> cards;
	
	public PokerDeck() {
		this.cards = new ArrayList<PokerCard>();
	}
	
	public int getCardCount() {
		return this.cards.size();
	}
	
	public PokerCard drawCard() {
		if(this.cards.size() == 0) return null;
		
		PokerCard result = this.cards.get(0);
		this.cards.remove(0);
		return result;
	}
	
	public void addCard(PokerCard card) {
		this.cards.add(card);
	}
	
	public void clearDeck() {
		this.cards.clear();
	}
	
	public void setup() {
		for(EnumPokerCardType cType : EnumPokerCardType.values()) { 
			for(EnumPokerCardFace cFace : EnumPokerCardFace.values()) {
				this.cards.add(new PokerCard(cType, cFace));
			}
		}
	}
	
	public void shuffle() {
		Collections.shuffle(cards);
	}
	
}
