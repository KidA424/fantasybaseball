package fantasybaseball
import java.io.File

import grails.transaction.Transactional

import java.text.DateFormat
import java.text.SimpleDateFormat

@Transactional
class LoadDataService {

    def grailsApplication

    def load(String domainClass)
    {
        if (grailsApplication.getDomainClass("fantasybaseball." + domainClass).clazz.count() != 0)
        {
            return null
        }

        def lines = new File("C:\\Users\\Kevin Knopf\\IdeaProjects\\FantasyBaseball\\resources\\${domainClass}.csv").readLines()
        def titles = lines.get(0).split(',')

        Class[] types = new Class[titles.size()]
        titles.eachWithIndex
        {
            title, i ->
                def clazz = grailsApplication.getDomainClass("fantasybaseball." + domainClass).clazz
                def done = false
                while (!done)
                {
                    try
                    {
                        types[i] = clazz.getDeclaredField(title).getType()
                        done = true
                    } catch(NoSuchFieldException | MissingPropertyException ex) {
                        clazz = clazz.getSuperclass()
                    }
                }
        }
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd")

        int n = 0
        println "Total: ${lines.size()}"
        for (line in lines[1..-1])
        {
            n++
            if (n % 1000 == 0) println n
            def row = grailsApplication.getDomainClass("fantasybaseball." + domainClass).newInstance()
            def lineList = line.split(',', -1)
            for (i in (0..(titles.size() - 1)))
            {
                if (!(lineList[i] in [null, "", "NA"]))
                {
                    def value = lineList[i]
                    if (types[i] == int)
                    {
                        value = Integer.parseInt(value)
                    }
                    else if (types[i] == double)
                    {
                        value = Double.parseDouble(value)
                    }
                    else if (types[i] == Date)
                    {
                        value = df.parse(value)
                    }
                    row."${titles[i]}" = value
                }
            }
            row.save(failOnError: true)
        }
    }

    def addAssociations()
    {
        PlayerSeason.each
        {
            Player.findByPlayerID(it.playerID).addToSeasons(it)
        }
    }
}
