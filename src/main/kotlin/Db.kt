import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

class Db() {
    val url = "jdbc:sqlite:items.db"

    fun connect(): Connection {
    return DriverManager.getConnection(url)
    }

    fun create() {
        connect().use { connection ->
            connection.createStatement().use { statement ->
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS items (title TEXT, desc TEXT, date TEXT, prty TEXT, status TEXT)")
            }
        }
    }

    fun insert(title: String, desc: String, date: String, prty: String) {
        connect().use { connection ->
            connection.createStatement().use { statement ->
                statement.executeUpdate("INSERT INTO items (title, desc, date, prty, status) VALUES ('$title', '$desc', '$date', '$prty', 'TO DO')")
            }
        }
    }

    fun fetchItems(): List<Item> {
        val items = mutableListOf<Item>()

        connect().use { connection ->
            connection.createStatement().use { statement ->
                val resultSet: ResultSet = statement.executeQuery("SELECT * FROM items")

                while (resultSet.next()) {
                    val title = resultSet.getString("title")
                    val desc = resultSet.getString("desc")
                    val date = resultSet.getString("date")
                    val prty = resultSet.getString("prty")
                    val status = resultSet.getString("status")

                    val item = Item(title, desc, date, prty, status)
                    items.add(item)
                }
            }
        }

        return items
    }

    fun update(w: String, str: String, new: String) {
        connect().use { connection ->
            connection.createStatement().use { statement ->
                if (w == "title") {
                    statement.executeUpdate("UPDATE items SET title = '$new' WHERE title = '$str'")
                } else if (w == "desc") {
                    statement.executeUpdate("UPDATE items SET desc = '$new' WHERE title = '$str'")
                } else if (w == "date") {
                    statement.executeUpdate("UPDATE items SET date = '$new' WHERE title = '$str'")
                } else if (w == "prty") {
                    statement.executeUpdate("UPDATE items SET prty = '$new' WHERE title = '$str'")
                } else {
                    statement.executeUpdate("UPDATE items SET status = '$new' WHERE title = '$str'")
                }
            }
        }
    }

    fun delete(str: String) {
        connect().use { connection ->
            connection.createStatement().use { statement ->
                statement.executeUpdate("DELETE FROM items WHERE title = '$str'")
            }
        }
    }
}
