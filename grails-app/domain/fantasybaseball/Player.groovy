package fantasybaseball

class Player {

    String playerID
    int birthYear
    int birthMonth
    int birthDay
    String birthCountry
    String birthState
    String birthCity
    int deathYear
    int deathMonth
    int deathDay
    String deathCountry
    String deathState
    String deathCity
    String nameFirst
    String nameLast
    String nameGiven
    int weight
    int height
    String bats
    String throwingHand
    Date debut
    Date finalGame
    String retroID
    String bbrefID

    static hasMany = [seasons: PlayerSeason]
    static constraints = {
        playerID unique: true
    }
}
