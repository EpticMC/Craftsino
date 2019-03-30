package com.ehdes.CreepSore.Craftsino;

import java.util.Scanner;

import com.ehdes.CreepSore.Craftsino.BlackJack.BlackJack;
import com.ehdes.CreepSore.Craftsino.BlackJack.BlackJackDealer;
import com.ehdes.CreepSore.Craftsino.BlackJack.BlackJackPlayerData;
import com.ehdes.CreepSore.Craftsino.BlackJack.EBlackJackPlayerAction;
import com.ehdes.CreepSore.Craftsino.BlackJack.IBlackJackEventHandler;
import com.ehdes.CreepSore.Craftsino.types.pokercard.PokerCard;

public class Test {

	public static void main(String[] args) {
		final Scanner s = new Scanner(System.in);
		final BlackJack bj = new BlackJack();
		
		bj.addPlayer(new BlackJackPlayerData(bj));
		bj.start(new IBlackJackEventHandler() {
			
			BlackJackDealer dealer;
			
			@Override
			public void onRoundStart(BlackJackDealer dealer) {
				this.dealer = dealer;
			}
			
			@Override
			public void onPlayerRemoval(BlackJackPlayerData p) { }
			
			@Override
			public boolean onPlayerAction(BlackJackPlayerData p, EBlackJackPlayerAction action) {
				if(action == EBlackJackPlayerAction.BET)
					return true;
				
				return false;
			}
			
			@Override
			public void onDealerDraw(BlackJackPlayerData dealer) {
				System.out.println("Dealer cards: ");
				for(PokerCard card : dealer.getHand()) {
					System.out.println(" - " + card.getType() + " " + card.getFace());
				}
				System.out.println("Total: " + dealer.getHandValue());
			}
			
			@Override
			public void onCardDraw(BlackJackPlayerData p, PokerCard card) {
				if(p == this.dealer.dealerData) {
					return;
				}
				
			}
			
			@Override
			public double onBetRound(BlackJackPlayerData p) {
				return 0;
			}
			
			@Override
			public void onActivePlayerChange(BlackJackPlayerData p, int index) {
				System.out.println("-----------------------------");
			}
			
			@Override
			public EBlackJackPlayerAction getActionAsync(BlackJackPlayerData p, EBlackJackPlayerAction action) {
				System.out.println("Your cards: ");
				for(PokerCard card : p.getHand()) {
					System.out.println(" - " + card.getType() + " " + card.getFace());
				}
				System.out.println("Total: " + p.getHandValue());
				
				System.out.println("Your turn.");
				
				String in = s.nextLine();
				if(in.equalsIgnoreCase("h")) {
					return EBlackJackPlayerAction.HIT;
				} else {
					return EBlackJackPlayerAction.STAND;
				}
				
				//return EBlackJackPlayerAction.NONE;
			}
			
			@Override
			public void onRoundEnd() {
				System.out.println("-----------------------------");

				System.out.println("Dealer cards: ");
				for(PokerCard card : dealer.dealerData.getHand()) {
					System.out.println(" - " + card.getType() + " " + card.getFace());
				}
				System.out.println("Total: " + dealer.dealerData.getHandValue());
				
				
				BlackJackPlayerData p = bj.getPlayer(0);
				System.out.println("Your cards: ");
				for(PokerCard card : p.getHand()) {
					System.out.println(" - " + card.getType() + " " + card.getFace());
				}
				System.out.println("Total: " + p.getHandValue());
				boolean flag = (dealer.dealerData.isBusted() && !p.isBusted()) || (dealer.dealerData.getHandValue() < p.getHandValue() && !p.isBusted());
				System.out.println(flag ? "You won." : "You lost.");
			}
		});
		bj.startRound();
	}
	
	
	private static void log(String msg) {
		System.out.println("[Test] -> " + msg);
	}
}
