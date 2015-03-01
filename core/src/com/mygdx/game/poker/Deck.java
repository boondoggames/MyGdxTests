package com.mygdx.game.poker;

import java.util.Random;

import com.badlogic.gdx.utils.Array;

/**
 * A deck starts with 52 cards (no jokers).
 * 
 * It can be shuffled, and cards can be removed. 
 * 
 * @author george
 *
 */

public class Deck {
	// Avoid varible arrays, as they can be slow.
	Card[] cards = new Card[52];
	int currentPos;
	
	
	public Deck() {
		getShuffledDeck();
		currentPos = 0;
	}

	public Card drawCard() {
		return cards[currentPos++];
	}

	public void getShuffledDeck() {
		Array<Card> tmpCards = makeDeck();
		Random r = new Random();
		for (int i = 0; i < 52; i ++) {
			int nextOut = r.nextInt(tmpCards.size);
			cards[i] = tmpCards.removeIndex(nextOut);
		}
	}


	private Array<Card> makeDeck() {
		Array<Card> cards = new Array<Card>();
		for (String s : Card.suits) {
			for (int n : Card.numbers) {
				cards.add(new Card(s,n));
			}
		}
		return cards;
	}
	
	public void printDeck() {
		for (Card c : cards){
			System.out.print(c + " ");
		}
		System.out.println();
	}
	
	
}
