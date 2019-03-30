package com.ehdes.CreepSore.Craftsino.BlackJack;

import java.util.ArrayList;
import java.util.List;

import com.ehdes.CreepSore.Craftsino.types.pokercard.PokerCard;
import com.ehdes.CreepSore.Craftsino.types.pokercard.PokerDeck;

public class BlackJack {

	public static final long MAX_WAITTIME = 15000;
	
	boolean isRunning;
	
	IBlackJackEventHandler eventHandler;
	
	BlackJackDealer dealer;
	
	List<BlackJackPlayerData> players;
	List<BlackJackPlayerData> playersQueuedForRemoval;
	
	public PokerDeck deck;
	private int activePlayer;
	
	private EBlackJackStatus status = EBlackJackStatus.ROUND_END;
	
	public BlackJack() {
		this.isRunning = false;
		this.deck = new PokerDeck();
		this.dealer = new BlackJackDealer(this);
		
		this.players = new ArrayList<BlackJackPlayerData>();
		this.playersQueuedForRemoval = new ArrayList<BlackJackPlayerData>();
	}
	
	public void start(IBlackJackEventHandler eventHandler) {
		this.isRunning = true;
		this.eventHandler = eventHandler;
		this.initDeck();
	}
	
	public void startRound() {
		this.eventHandler.onRoundStart(dealer);
		
		if(this.deck.getCardCount() < this.players.size() * 2) {
			this.initDeck();
		}
		
		this.dealer.dealerData.clearHand();
		for(BlackJackPlayerData p : players) {
			p.clearHand();
			if(this.playersQueuedForRemoval.contains(p)) {
				continue;
			}
			p.bet(this.eventHandler.onBetRound(p));
		}
		
		this.drawCard(this.dealer.dealerData);
		this.drawCard(this.dealer.dealerData);
		for(int i = 0; i < 2; i++) {
			for(BlackJackPlayerData p : players) {
				if(this.playersQueuedForRemoval.contains(p)) {
					continue;
				}
				
				this.drawCard(p);
			}
		}
		
		this.dealer.onDraw();
		if(this.dealer.dealerData.isBusted()) {
			this.eventHandler.onRoundEnd();
			return;
		}
		
		for(activePlayer = 0; activePlayer < players.size(); activePlayer++) {
			BlackJackPlayerData p = players.get(activePlayer);
			if(this.playersQueuedForRemoval.contains(p)) {
				this.players.remove(p);
				this.playersQueuedForRemoval.remove(p);
				this.eventHandler.onPlayerRemoval(p);
				continue;
			}
			
			this.eventHandler.onActivePlayerChange(p, activePlayer);
			
			handlePlayerActions(p);
		}
		
		this.eventHandler.onRoundEnd();
	}
	
	/*
	 * PLAYER FUNCTIONS
	 */
	private void handlePlayerActions(BlackJackPlayerData p) {
		EBlackJackPlayerAction action = EBlackJackPlayerAction.NONE;
		long waitingSince = System.currentTimeMillis();
		
		while(action == EBlackJackPlayerAction.NONE && System.currentTimeMillis() - waitingSince < MAX_WAITTIME) {
			action = this.eventHandler.getActionAsync(p, action);
			if(action == EBlackJackPlayerAction.STAND || action == EBlackJackPlayerAction.CANCEL)
				break;
			
			handlePlayerAction(p, action);
			action = EBlackJackPlayerAction.NONE;
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) { }
		}
	}
	
	private void handlePlayerAction(BlackJackPlayerData p, EBlackJackPlayerAction action) {
		
		switch(action) {
		
		case HIT:
			drawCard(p);
			break;
			
		default:
		case NONE:
		case BET:
			break;
		}
		
		this.eventHandler.onPlayerAction(p, action);
	}
	
	/*
	 * PLAYER LIST FUNCTIONS
	 */
	public void addPlayer(BlackJackPlayerData player) {
		this.players.add(player);
	}
	
	public BlackJackPlayerData getPlayer(int index) {
		return this.players.get(index);
	}
	
	public void removePlayer(BlackJackPlayerData player) {
		this.playersQueuedForRemoval.add(player);
	}
	
	public EBlackJackStatus currentStatus() {
		return this.status;
	}
	
	/*
	 * MISC FUNCTIONS
	 */
	
	public void drawCard(BlackJackPlayerData p) {
		PokerCard card = this.deck.drawCard();
		p.addCard(card);
		this.eventHandler.onCardDraw(p, card);
	}
	
	private void initDeck() {
		this.deck.clearDeck();
		for(int i = 0; i < 3; i++) {
			this.deck.setup();
			this.deck.shuffle();
		}
	}
}
