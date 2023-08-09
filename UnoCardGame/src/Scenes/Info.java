package Scenes;

public class Info {

    public static final String RULES = """
                RULES:
                
                For: 2-10 Players.
                                
                The game starts by dealing 7 cards to each player. After that, the top card from the remaining deck
                is flipped over and set aside to begin the discard pile. In the original rules, the youngest player
                or the player on the left of the dealer starts. For simplicity, the player who starts the game is chosen
                 randomly, and subsequent games proceed clockwise.
                                
                On a player's turn, they must do one of the following:
                - play one card matching the discard in color, number, or symbol
                - play a Wild card, or a playable Wild Draw Four card
                - draw the top card from the deck, and play it if possible
                                
                Card ( Symbol )
                - Effect when played from hand
                - Effect as first discard
                ________________________________________________________________________________________________________
                Skip ( \uD83D\uDEC7 )
                - Next player in sequence misses a turn
                - First player misses a turn
                ________________________________________________________________________________________________________
                Reverse ( \u21BA )
                - Order of play switches directions (clockwise to counterclockwise, or vice versa)
                - Player before first player plays first; play proceeds counterclockwise
                ________________________________________________________________________________________________________
                Draw Two ( +2 )
                - Next player in sequence draws two cards and misses a turn
                - First player draws two cards and misses a turn
                ________________________________________________________________________________________________________
                Shuffle Hands ( \uD83C\uDF00 )
                - Each players cards are collected, shuffled and dealt evenly. Then player who played that card declares
                next color to be matched.
                - Each players cards are collected, shuffled and dealt evenly. Then first player declares first color
                to be matched and takes first turn.
                ________________________________________________________________________________________________________
                Wild  ( \uD83C\uDF08 )
                - Player declares the next color to be matched (may be used on any turn even if the player
                has matching color)
                - First player declares the first color to be matched and takes the first turn
                ________________________________________________________________________________________________________
                Wild Draw Four ( +4\uD83C\uDF08 )
                - Player declares the next color to be matched; next player in sequence draws four cards and misses
                a turn. May be legally played only if the player has no cards of the current color.
                - Card is returned to the deck, then a new card is laid down into the discard pile.
                ________________________________________________________________________________________________________
                                
                                
                The first player to get rid of their last card ("going out") wins the hand and scores points for
                the cards held by the other players. Number cards count their face value, all action cards count 20,
                shuffle hand cards count 40, and Wild and Wild Draw Four cards count 50. If a Draw Two or Wild Draw Four
                card is played to go out, the next player in the sequence must draw the appropriate number of cards
                before the score is tallied.
                                
                The first player to score 500 points wins the game.
                Good luck!
                ________________________________________________________________________________________________________
                """;
}