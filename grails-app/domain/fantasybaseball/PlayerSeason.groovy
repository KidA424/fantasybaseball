package fantasybaseball

class PlayerSeason {

    String playerID
    int yearID
    int stint
    String teamID
    String lgID
    int g

    static belongsTo = [player: Player]

    static constraints = {

    }
}
