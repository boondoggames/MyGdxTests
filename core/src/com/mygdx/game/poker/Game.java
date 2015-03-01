package com.mygdx.game.poker;

import com.badlogic.gdx.utils.Array;

public class Game {
	Deck deck;
	
	int nPlayers = 4;
	Array<Player> players;
	
	
	int nFlop = 5;
	int hand = 2;
	

	public Game() {
		deck = new Deck();
		players = new Array<Player>();
		for (int p = 0; p < nPlayers; p ++){
			Player player = new Player(new CardCollection(deck, 2));
			players.add(player);
		}
		
		Array<Card> cards = new Array<Card>();
		cards.add(new Card("H",0));
		cards.add(new Card("H",1));
		cards.add(new Card("D",2));
		cards.add(new Card("H",3));
		cards.add(new Card("H",4));
		
		CardCollection c = new CardCollection(cards);
		c.calcScore();
		System.out.println(c.score);
	}
	

}
