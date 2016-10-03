import fantasybaseball.Player

class BootStrap {

    def loadDataService

    def init = { servletContext ->
        ["Player", "Batter", "Pitcher"].each
        {
            loadDataService.load(it)
        }
    }
    def destroy = {
    }
}
