package com.mygdx.game.poker;

public class Player {
	CardCollection cards;
	
	public Player(CardCollection cards) {
		this.cards = cards;
	}
	
	@Override
	public String toString() {
		return cards.toString();
	}
	
}
