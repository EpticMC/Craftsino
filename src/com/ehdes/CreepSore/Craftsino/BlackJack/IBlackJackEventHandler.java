package com.ehdes.CreepSore.Craftsino.BlackJack;

import com.ehdes.CreepSore.Craftsino.types.pokercard.PokerCard;

public interface IBlackJackEventHandler {

	public void onCardDraw(BlackJackPlayerData p, PokerCard card);
	public void onActivePlayerChange(BlackJackPlayerData p, int index);
	public double onBetRound(BlackJackPlayerData p);
	public boolean onPlayerAction(BlackJackPlayerData p, EBlackJackPlayerAction action);
	public EBlackJackPlayerAction getActionAsync(BlackJackPlayerData p, EBlackJackPlayerAction action);
	public void onPlayerRemoval(BlackJackPlayerData p);
	
	public void onDealerDraw(BlackJackPlayerData dealer);
	
	public void onRoundStart(BlackJackDealer dealer);
	public void onRoundEnd();
	
}
