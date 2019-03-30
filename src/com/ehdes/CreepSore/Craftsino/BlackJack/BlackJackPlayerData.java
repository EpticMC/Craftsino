package com.ehdes.CreepSore.Craftsino.BlackJack;

import java.util.ArrayList;
import java.util.List;

import com.ehdes.CreepSore.Craftsino.types.pokercard.EnumPokerCardFace;
import com.ehdes.CreepSore.Craftsino.types.pokercard.PokerCard;

public class BlackJackPlayerData {

	BlackJack game;
	List<PokerCard> hand;
	
	double bettedChips;
	
	public BlackJackPlayerData(BlackJack game) {
		this.hand = new ArrayList<PokerCard>();
		this.game = game;
	}
	
	public void bet(double chips) {
		if(this.game.eventHandler.onPlayerAction(this, EBlackJackPlayerAction.BET)) {
			this.bettedChips = chips;
		}
	}
	
	public double getBettedChips() {
		return this.bettedChips;
	}
	
	public void addCard(PokerCard card) {
		hand.add(card);
	}
	
	public void clearHand() {
		this.hand.clear();
	}
	
	public List<PokerCard> getHand() {
		return new ArrayList<PokerCard>(this.hand);
	}
	
	public boolean isBusted() {
		return this.getHandValue() > 21;
	}
	
	public boolean hasBlackJack() {
		if(this.hand.size() != 2) return false;
		PokerCard firstCard = this.hand.get(0);
		PokerCard secondCard = this.hand.get(1);
		
		if(firstCard.getFace() == EnumPokerCardFace.ACE || secondCard.getFace() == EnumPokerCardFace.ACE) {
			if(firstCard.getFace() == EnumPokerCardFace.JACK || firstCard.getFace() == EnumPokerCardFace.QUEEN || firstCard.getFace() == EnumPokerCardFace.KING
					|| secondCard.getFace() == EnumPokerCardFace.JACK || secondCard.getFace() == EnumPokerCardFace.QUEEN || secondCard.getFace() == EnumPokerCardFace.KING) {
				return true;
			}
		}
		
		return false;
	}
	
	public int getHandValue() {
		int total = 0;
		int aces = 0;
		for(PokerCard card : this.hand) {
			EnumPokerCardFace cardFace = card.getFace();
			if(cardFace == EnumPokerCardFace.ACE)
				aces++;
			
			switch (cardFace) {
			case KING:
			case QUEEN:
			case JACK:
			case TEN:
				total++;
			case NINE:
				total++;
			case EIGHT:
				total++;
			case SEVEN:
				total++;
			case SIX:
				total++;
			case FIVE:
				total++;
			case FOUR:
				total++;
			case THREE:
				total++;
			case TWO:
				total++;
				total++;
			default:
				break;
			}
		}
		
		for(int i = 0; i < aces; i++) {
			if(total+11 > 21)
				total ++;
			else
				total += 11;
		}
		
		return total;
	}
}
