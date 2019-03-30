package com.ehdes.CreepSore.Craftsino.BlackJack;

public class BlackJackDealer {

	public static final long ACTION_DELAY = 750;
	
	public BlackJack game;
	public BlackJackPlayerData dealerData;
	
	public BlackJackDealer(BlackJack game) {
		this.game = game;
		this.dealerData = new BlackJackPlayerData(game);
	}
	
	public void onDraw() {
		this.game.eventHandler.onDealerDraw(this.dealerData);
		while(this.dealerData.getHandValue() < 17) {
			this.game.drawCard(this.dealerData);
			this.game.eventHandler.onDealerDraw(this.dealerData);
			try {
				Thread.sleep(ACTION_DELAY);
			} catch (InterruptedException e) { }
		}
	}
}
