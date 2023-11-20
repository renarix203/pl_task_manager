import java.lang.NumberFormatException

fun main(args: Array<String>) {
    val itemlist = mutableListOf<Item>()

    while (true) {
        println("Task Manager")
        println()
        println("Options:")
        println("1. Create a task")
        println("2. Show existing tasks")
        println("3. Edit a task")
        println("4. Erase a task")
        println("5. Statistics")
        println()

        println("Enter a number")
        val input = readln()
        try {
            if (input.toInt() == 1) {
                println()
                println("Title:")
                val title = readln()
                println()
                println("Description:")
                val desc = readln()
                println()
                println("Due Date:")
                val date = readln()
                println()
                println("Priority:")
                val prty = readln()
                println()
                val item = Item(title, desc, date, prty)
                println("New item added: " + item.title)
                itemlist.add(item)
                Thread.sleep(2_000)
            } else if (input.toInt() == 2) {
                println()
                println("1. Show all")
                println("2. Show TO DO items")
                val view = readln().toInt()
                println()
                if (view == 1) {
                    for (i in itemlist) {
                        println("Title: " + i.title)
                        println("Description: " + i.desc)
                        println("Due Date: " + i.date)
                        println("Priority: " + i.prty)
                        println("Status: " + i.status)
                        println()
                        Thread.sleep(500)
                    }
                } else if (view == 2) {
                    for (i in itemlist) {
                        if (i.status == "TO DO") {
                            println("Title: " + i.title)
                            println("Description: " + i.desc)
                            println("Due Date: " + i.date)
                            println("Priority: " + i.prty)
                            println("Status: " + i.status)
                            println()
                            Thread.sleep(500)
                        }
                    }
                } else {
                    failed()
                }

            } else if (input.toInt() == 3) {
                println()
                println("Which task would you like to update?")
                println()
                lister(itemlist)
                println()
                val inp = readln().toInt() - 1
                println()
                println("What would you like to edit?")
                println()
                println("1. Title")
                println("2. Description")
                println("3. Due Date")
                println("4. Priority")
                println("5. Status")
                val inp2 = readln()
                println()
                if (inp2.toInt() == 1) {
                    println("Enter a new title: ")
                    itemlist[inp].title = readln()
                    println("Updated.")
                    Thread.sleep(2_000)
                } else if (inp2.toInt() == 2) {
                    println("Enter a new description: ")
                    itemlist[inp].desc = readln()
                    println("Updated.")
                    Thread.sleep(2_000)
                } else if (inp2.toInt() == 3) {
                    println("Enter a new due date: ")
                    itemlist[inp.toInt()].date = readln()
                    println("Updated.")
                    Thread.sleep(2_000)
                } else if (inp2.toInt() == 4) {
                    println("Enter a new priority: ")
                    itemlist[inp].prty = readln()
                    println("Updated.")
                    Thread.sleep(2_000)
                } else if (inp2.toInt() == 5) {
                    println("Status changed to 'Done'")
                    itemlist[inp].status = "Done"
                    Thread.sleep(2_000)
                } else {
                    failed()
                }
            } else if (input.toInt() == 4) {
                println()
                println("Which task would you like to remove?")
                println()
                lister(itemlist)
                println()
                val inp = readln().toInt() - 1
                itemlist.remove(itemlist[inp])
                println("Task removed.")
                Thread.sleep(2_000)
            } else if (input.toInt() == 5) {
                println()
                var total = 0
                var todo = 0
                for (i in itemlist) {
                    total += 1
                    if (i.status == "TO DO") {
                        todo += 1
                    }
                }
                println("Total tasks: " + total.toString())
                println("TO DO tasks: " + todo.toString())
                val done = total - todo
                println("Done tasks: " + done.toString())
                val pc = Math.round((todo.toDouble() / total.toDouble()) * 100.0)
                println("Percentage of TODO tasks: " + pc + "%")
                println()
                Thread.sleep(2_000)
            } else {
                failed()
            }
        } catch (e: NumberFormatException) {
            failed()
        }
    }
}

fun lister (itemlist: MutableList<Item>) {
    var count = 1
    for (i in itemlist) {
        println(count.toString() + ". " + i.title)
        count += 1
    }
}

fun failed () {
    println()
    println("Sorry, I don't recognize that input.")
    Thread.sleep(2_000)
    println()
}
